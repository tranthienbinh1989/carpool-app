/**
 * 
 */
$(document).ready(
		function($){
			var startFetch=true;
			$(window).scroll(function() {
				   if($(window).scrollTop() + $(window).height() > $(document).height() - 50) {
					   if(startFetch){
						   var From = $("#posts"+(($('#post-list a.active').attr('href')=='#ride')?1:0)).children("li").length;
						   GetPost(From,From + 10,true,($('#post-list a.active').attr('href')=='#ride')?1:0);
						   startFetch=false;
						   setInterval(function(){startFetch=true},5000)
					   }
					   
				   }
				});
			
			$("#deletePostBTN").click(
					 function(){
						 $.ajax("PostController",{
							 data:{
								 "Action":"Delete",
								 "PostId":$("#deletemodal").attr("deleteid")							 
							 }
								}	
							).done(
									function(Posts){
										$("#__"+$("#deletemodal").attr("deleteid")).remove();
							})
									
							.fail(function(){console.log("fail")});
					 }
			)
			 $("#Submit_Post").click(
					 function(){
						 var NoError=true;
						 $("#error_SelectRole").empty();
						 $("#error_PostText").empty();
						 if($("#compose").val()==""){
							 $("#error_PostText").text("Please type a post")
							 NoError=false;
						 }
						 if(NoError){
							 var PostType = ($("#postType").is(':checked')) ? 0 : 1;
							 var Post = $("#compose").val();
							 var departure = $("#departure").val();
							 var destination = $("#destination").val();
							 var departureLat = $("#departureLat").val();
							 var departureLong = $("#departureLong").val();
							 var destinationLat = $("#destinationLat").val();
							 var destinationLong = $("#destinationLong").val();
							 $.ajax("PostController",{
								 data:{
									 "Action":"Save",
									 "PostType":PostType,
									 "Post":Post,
									 "departure": departure,
									 "destination": destination,
									 "departureLat": departureLat,
									 "departureLong": departureLong,
									 "destinationLat": destinationLat,
									 "destinationLong": destinationLong
								 }
		 						}	
								).done(
										function(){
											$("#error_SelectRole").empty();
											$("#error_PostText").empty();
											$('#compose').val("");
											$("#departure").val("");
											$("#destination").val("");
											$('#modal1').closeModal();											
											GetPost(0,20,false,PostType)
											window.scrollTo(0, 0);
											})
								.fail(
										function(){
											console.log("fail")
											});
						 }
					 })
					
			function GetPost(From,To,Prepend,PostType){
				if(PostType=='undefined')
					PostType=($('#post-list a.active').attr('href')=='#ride')?1:0
				 $.ajax("PostController",{
					 data:{
						 "Action":"Get",
						 "From":From,
						 "To":To,
						 "PostType":PostType
					 }
						}	
					).done(
							function(Posts){
								if(Posts!="undefined"){									
									for(var i=Posts.length-1;i>=0;i--){
										if(!$("#__"+Posts[i]["PostId"]).length){
											if(PostType==1)
												$('ul.tabs').tabs('select_tab', 'ride');
											else
												$('ul.tabs').tabs('select_tab', 'driver');
											if(Prepend==0)
												$("#posts"+Posts[i]["PostType"]).prepend(createAPost(Posts[i]["PostId"],Posts[i]["Post"],Posts[i]["Fullname"],Posts[i]["Likes"],Posts[i]["PostType"],Posts[i]["Liked"],Posts[i]["UserId"],Posts[i]["Comments"]));											
												else
												$("#posts"+Posts[i]["PostType"]).append(createAPost(Posts[i]["PostId"],Posts[i]["Post"],Posts[i]["Fullname"],Posts[i]["Likes"],Posts[i]["PostType"],Posts[i]["Liked"],Posts[i]["UserId"],Posts[i]["Comments"]));											
										}									
									}}
								else{alert("no data")}
					})							
					.fail(function(){console.log("fail")});
			 }
			 function createAPost(postid,post,fullname,likes,posttype,liked,userid,comments){		 
				 var ClassRole =(posttype==0)?"driver":"rider";
				 var li =$("<li class='collection-item avatar email-unread selected' id='__" + postid + "'>" +
			              "<i class='"+ClassRole+"'></i>" +
			               "<span class='title'>" + fullname+ "</span>" + 
			               "<p class ='post_text'>" + post + "</p></div></li>");
				 var PC = $("<div></div>");
				 var aLikes = $("<a href='javascript:void(0)'><span class='likes' id='__l" + postid + "'>" + likes + "</span></a>");
				 var TU =$("<i class='material-icons tiny' id='__i'" + postid + ">thumb_up</i>");
				 var aComments = $("<a href='javascript:void(0)'><span class='comments' id='__c" + postid + "'>"+comments+"</span><i class='material-icons tiny'>chat_bubble</i></a>");
				 
				 if(!liked){			 
					 aLikes.click(function(){
						 if(!TU.hasClass("icon-black"))
						 $.ajax("PostController",{
							 data:{
								 "Action":"Like",
								 "PostId":postid
							 }
						 }	
						 ).done(
								function(data){
									 TU.addClass("icon-black");									 
									 $("#__l" + postid).text(parseInt($("#__l" + postid ).text())+ 1);
									})
							.fail(
								function(){
									console.log("fail")
									});
					 })}
				 else{
					 TU.addClass("icon-black");
					 aLikes.click(function(evt){
						 evt.preventDefault();
					 })
				 }
				 aComments.click(
						 function(){							 
							 var cm = $("#__comments_" + postid);
							 if(cm.children().length==0){
								 GetComments(cm,postid);
							 }
							 else
								 cm.empty();
						 }
				 )
				 aLikes.append(TU);
				 PC.append(aLikes);
				 PC.append(aComments);
				 if(UserId==userid){
					 var DeleteComment = $("<a href='#!' class='secondary-content'><i class='material-icons icon-red tiny'>delete</i></a>");
					 PC.append(DeleteComment)
					 DeleteComment.click(
							 function(){
								 $("#deletemodal").attr("deleteid",postid)
								 $("#deletemodal").openModal()
							 }
					 );
				 }
				 var CommentSection =$("<div class='commentContainer' id='__comments_" + postid + "'></div>");
				 PC.append(CommentSection);
				 li.append(PC)
				 return li;		              
			 }

			 var GetComments = function(cmContainer,PostId){
				 var Comments;
				 $.ajax("comment",{
					 data:{
						 "c":"Get",
						 "postId":PostId
					 }
						}	
					).done(
							function(Comments){
								if(Comments){
									var comWriter=$("<div class='commentSingle'></div>");
									comTextArea=$("<textarea class='wholewidth postcomment'></textarea>");
									comWriter.append(comTextArea);
									var comSubmit= $("<button value='Comment'>Comment</button>")
									var today = new Date();
									var todayString = today.getFullYear() + "-" + (today.getMonth()+1) + "-" + today.getDate();
									comSubmit.click(createCommentClick(cmContainer,PostId,comTextArea,"You",todayString));
									comWriter.append(comSubmit)									
									for(var i=0;i<Comments.length;i++){
										 cmContainer.append(createComment(Comments[i].fullname,Comments[i].datecreated,Comments[i].comment));
									}
									cmContainer.append(comWriter)
								}	
					})
							
					.fail(function(){console.log("fail")});
			 }
			 function createCommentClick(cmContainer,PostId,comTextArea,fullname,datecreated){
				 return function(){
						//alert(PostId + "  " + UserId + " " + comTextArea.val())
						 $.ajax("comment",{
							 data:{
								 "c":"Insert",
								 "postid":PostId,
								 "userId":UserId,
								 "comments":comTextArea.val()
							 }
								}	
							).done(
									function(Posts){
										var lastC = cmContainer.children().length-1;
										$("#__c" + PostId).text(parseInt($("#__c" + PostId ).text())+ 1);
										createComment(fullname,datecreated,comTextArea.val()).insertBefore(cmContainer.children()[lastC]);
										comTextArea.val("");
										
							})
									
							.fail(function(){console.log("fail")});
					}
			 }
			 function createComment(fullname,datecreated,comment){
				 var comDivCon=$("<div class='commentSingle'></div>");
				 comDivCon.append("<p><strong>"+fullname +"</strong> "+ datecreated+"</p>")
				 comDivCon.append("<div class='commentText'>"+comment+"</div>")
				 return comDivCon;
			 }
			 function unBind(anchor){
				 anchor.click(function(){});
			 }
			if(LoadedPosts!=null&&LoadedPosts.length!=null)
				for(var i=LoadedPosts.length-1;i>=0;i--)
					$("#posts"+LoadedPosts[i]["PostType"]).prepend(createAPost(LoadedPosts[i]["PostId"],LoadedPosts[i]["Post"],LoadedPosts[i]["Fullname"],LoadedPosts[i]["Likes"],LoadedPosts[i]["PostType"],LoadedPosts[i]["Liked"],LoadedPosts[i]["UserId"],LoadedPosts[i]["Comments"]));
			
			
			
			//notifications
			
			
			
			
			
			 var notification = window.Notification || window.mozNotification || window.webkitNotification;

		        // The user needs to allow this
		        if ('undefined' === typeof notification) {
		        	alert('Web notification not supported');
		        } else {
		        	notification.requestPermission(function(permission){});
		        }
		        function notifyMe(Message) {
		          if (!("Notification" in window)) {
		            alert("This browser does not support desktop notification");
		          }
		          else if (Notification.permission === "granted") {
		                var options = {
		                        body: Message,
		                        icon: "assets/img/icon.png",
		                        dir : "ltr"
		                     };
		                  var notification = new Notification("Carpooling",options);
		                  setTimeout(notification.close.bind(notification), 4000);
		                  notification.onclick=function(){
		                	  GetPost(0,newPostCounts,false,2);
		                	  newPostCounts=0;
		                	  window.scrollTo(0, 0);
		                	  window.focus();
		                	  this.close();
		                	  }
		          }
		          else if (Notification.permission !== 'denied') {
		            Notification.requestPermission(function (permission) {
		              if (!('permission' in Notification)) {
		                Notification.permission = permission;
		              }
		            
		              if (permission === "granted") {
		                var options = {
		                      body: Message,
		                      icon: "assets/img/icon.png",
		                      dir : "ltr"
		                  };
		                var notification = new Notification("Carpooling",options);
		                setTimeout(notification.close.bind(notification), 4000);
		                  notification.onclick=function(){
		                	  GetPost(0,newPostCounts,false,2);
		                	  newPostCounts=0;
		                	  window.scrollTo(0, 0);
		                	  window.focus();
		                	  this.close();
		                	  }
		              }
		            });
		          }
		        };
		        var newPostCounts=0;
		        window.setInterval(function(){
		        	
		        	var PT;
		        	var P0=($("#posts0").children("li").length!=0)?parseInt(($($("#posts0").children("li")[0])).attr("id").substring(2,($($("#posts0").children("li")[0])).attr("id").length)):0
		        	var P1=($("#posts1").children("li").length!=0)?parseInt(($($("#posts1").children("li")[0])).attr("id").substring(2,($($("#posts1").children("li")[0])).attr("id").length)):0
		
		        	if(P0>P1)
		        		PT=P0;
		        	else
		        		PT=P1;
		        	$.ajax("PostController",{
						 data:{
							 "Action":"New",
							 "afterPostId":PT	 
						 }
							}	
						).done(
								function(newPosts){		
									if(newPostCounts<newPosts["NewComments"]){
										newPostCounts=newPosts["NewComments"];
										notifyMe("There are " + newPostCounts + " new posts, click here to load them." );
									}

						})
						.fail(function(){console.log("fail")});		        	
		        }, 10000);
		        function GetNewPostCount(){
		        	
		        }

		 }
		
)

			function GetPost(From,To,Prepend,PostType){
				if(PostType=='undefined')
					PostType=($('#post-list a.active').attr('href')=='#ride')?1:0
				 $.ajax("PostController",{
					 data:{
						 "Action":"Get",
						 "From":From,
						 "To":To,
						 "PostType":PostType
					 }
						}	
					).done(
							function(Posts){
								if(Posts!="undefined"){									
									for(var i=Posts.length-1;i>=0;i--){
										if(!$("#__"+Posts[i]["PostId"]).length){
											if(PostType==1)
												$('ul.tabs').tabs('select_tab', 'ride');
											else
												$('ul.tabs').tabs('select_tab', 'driver');
											if(Prepend==0)
												$("#posts"+Posts[i]["PostType"]).prepend(createAPost(Posts[i]["PostId"],Posts[i]["Post"],Posts[i]["Fullname"],Posts[i]["Likes"],Posts[i]["PostType"],Posts[i]["Liked"],Posts[i]["UserId"],Posts[i]["Comments"]));											
												else
												$("#posts"+Posts[i]["PostType"]).append(createAPost(Posts[i]["PostId"],Posts[i]["Post"],Posts[i]["Fullname"],Posts[i]["Likes"],Posts[i]["PostType"],Posts[i]["Liked"],Posts[i]["UserId"],Posts[i]["Comments"]));											
										}									
									}}
								else{alert("no data")}
					})							
					.fail(function(){console.log("fail")});
			 }
