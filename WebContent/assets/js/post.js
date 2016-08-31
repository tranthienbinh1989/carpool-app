/**
 * 
 */

$(document).ready(
		function($){
			var startFetch=true;
			$(window).scroll(function() {
				   if($(window).scrollTop() + $(window).height() > $(document).height() - 50) {
					   if(startFetch){
						   var From = $("#posts").children("li").length;
						   GetPost(From,From + 10,true);
						   startFetch=false;
						   setInterval(function(){startFetch=true},5000)
					   }
					   
				   }
				});
			 $("#Submit_Post").click(
					 function(){
						 var NoError=true;
						 $("#error_SelectRole").empty();
						 $("#error_PostText").empty();
						 if(!($("#Rider:checked").val()=="on" || $("#Driver:checked").val()=="on")){					 
							 $("#error_SelectRole").text("Select either one");	
							 NoError=false;
						 }
						 if($("#compose").val()==""){
							 $("#error_PostText").text("Please type a post")
							 NoError=false;
						 }
						 if(NoError){
							 var PostType = ($("#Rider:checked").val()=="on")?1:0;
							 var Post = $("#compose").val();
							 $.ajax("PostController",{
								 data:{
									 "Action":"Save",
									 "PostType":PostType,
									 "Post":Post
								 }
		 						}	
								).done(
										function(){
											$("#error_SelectRole").empty();
											$("#error_PostText").empty();
											$('#compose').val("")
											$("#Rider").prop('checked', false);
											$("#Driver").prop('checked', false);
											$('#modal1').closeModal();											
											GetPost(0,20,false)
											window.scrollTo(0, 0);
											})
								.fail(
										function(){
											console.log("fail")
											});
						 }
					 })
					
			function GetPost(From,To,Prepend){
				 $.ajax("PostController",{
					 data:{
						 "Action":"Get",
						 "From":From,
						 "To":To
					 }
						}	
					).done(
							function(Posts){
								if(Posts!="undefined")
									for(var i=Posts.length-1;i>=0;i--){
										if(!$("#__"+Posts[i]["PostId"]).length){
											if(Prepend==0)
												$("#posts").prepend(createAPost(Posts[i]["PostId"],Posts[i]["Post"],Posts[i]["Fullname"],Posts[i]["Likes"],Posts[i]["PostType"],Posts[i]["Liked"],Posts[i]["UserId"],Posts[i]["Comments"]));
											else
												$("#posts").append(createAPost(Posts[i]["PostId"],Posts[i]["Post"],Posts[i]["Fullname"],Posts[i]["Likes"],Posts[i]["PostType"],Posts[i]["Liked"],Posts[i]["UserId"],Posts[i]["Comments"]));
									}
											
							}
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
				 var TU =$("<i class='material-icons' id='__i'" + postid + ">thumb_up</i>");
				 var aComments = $("<a href='javascript:void(0)'><span class='comments' id='__c" + postid + "'>"+comments+"</span><i class='material-icons'>chat_bubble</i></a>");
				 
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
						 console.log("here")
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
					 var DeleteComment = $("<a href='#!' class='secondary-content'><i class='material-icons icon-black Tiny'>delete</i></a>");
					 PC.append(DeleteComment)
					 DeleteComment.click(
							 function(){
								 $.ajax("PostController",{
									 data:{
										 "Action":"Delete",
										 "PostId":postid							 
									 }
										}	
									).done(
											function(Posts){
												li.remove();
									})
											
									.fail(function(){console.log("fail")});
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
										comTextArea.val("");
										createComment(fullname,datecreated,comTextArea.val()).insertBefore(cmContainer.children()[lastC]);
										
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
					$("#posts").prepend(createAPost(LoadedPosts[i]["PostId"],LoadedPosts[i]["Post"],LoadedPosts[i]["Fullname"],LoadedPosts[i]["Likes"],LoadedPosts[i]["PostType"],LoadedPosts[i]["Liked"],LoadedPosts[i]["UserId"],LoadedPosts[i]["Comments"]));
			
			
			
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
		                  notification.onclick=function(){
		                	  GetPost(0,newPostCounts,false);
		                	  window.scrollTo(0, 0);
		                	  //this.close();
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
		                  notification.onclick=function(){
		                	  GetPost(0,newPostCounts,false);
		                	  window.scrollTo(0, 0);
		                	  //this.close();
		                	  }
		              }
		            });
		          }
		        };
		        var latestPostId=0;
		        var newPostCounts=0;
		        function updateLatestPostID(){
		        	if($("#posts").children("li").length>0){
		        		var ID = ($($("#posts").children("li")[0])).attr("id");
		        		latestPostId=parseInt(($($("#posts").children("li")[0])).attr("id").substring(2,($($("#posts").children("li")[0])).attr("id").length));
		        	}
		        }
		        updateLatestPostID();
		        window.setInterval(function(){
		        	$.ajax("PostController",{
						 data:{
							 "Action":"New",
							 "afterPostId":($("#posts").children("li").length!=0)?parseInt(($($("#posts").children("li")[0])).attr("id").substring(2,($($("#posts").children("li")[0])).attr("id").length)):0
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

			function GetPost(From,To,Prepend){
				 $.ajax("PostController",{
					 data:{
						 "Action":"Get",
						 "From":From,
						 "To":To
					 }
						}	
					).done(
							function(Posts){
								if(Posts!="undefined")
									for(var i=Posts.length-1;i>=0;i--){
										if(!$("#__"+Posts[i]["PostId"]).length){
											if(!Prepend)
												$("#posts").prepend(createAPost(Posts[i]["PostId"],Posts[i]["Post"],Posts[i]["Fullname"],Posts[i]["Likes"],Posts[i]["PostType"],Posts[i]["Liked"],Posts[i]["UserId"],Posts[i]["Comments"]));
											else
												$("#posts").append(createAPost(Posts[i]["PostId"],Posts[i]["Post"],Posts[i]["Fullname"],Posts[i]["Likes"],Posts[i]["PostType"],Posts[i]["Liked"],Posts[i]["UserId"],Posts[i]["Comments"]));
									}
											
							}
					})
							
					.fail(function(){console.log("fail")});
			 }
