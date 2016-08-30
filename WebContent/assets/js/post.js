/**
 * 
 */
$(document).ready(
		function($){
			
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
							 var PostType = ($("#Rider").val()=="on")?1:0;
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
											$('#modal1').closeModal();
											GetPost(0,20)
											})
								.fail(
										function(){
											console.log("fail")
											});
						 }
					 })
					
			function GetPost(From,To){
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
										if(!$("#__"+Posts[i]["PostId"]).length)
										$("#posts").prepend(createAPost(Posts[i]["PostId"],Posts[i]["Post"],Posts[i]["Fullname"],Posts[i]["Likes"],Posts[i]["PostType"],Posts[i]["Liked"],Posts[i]["UserId"],Posts[i]["Comments"]));		
							}
					})
							
					.fail(function(){console.log("fail")});
			 }
			 function createAPost(postid,post,fullname,likes,posttype,liked,userid,comments){		 
				 var ClassRole =(posttype==1)?"driver":"rider";
				 var li =$("<li class='collection-item avatar email-unread selected' id='_" + postid + "'>" +
			              "<i class='"+ClassRole+"'></i>" +
			               "<span class='title'>" + fullname+ "</span>" + 
			               "<p class ='post_text'>" + post + "</p></div></li>");
				 var PC = $("<div></div>");
				 var aLikes = $("<a href='javascript:void(0)'><span class='likes' id='__l" + postid + "'>" + likes + "</span></a>");
				 var TU =$("<i class='material-icons' id='__i'" + postid + ">thumb_up</i>");
				 var aComments = $("<a href='javascript:void(0)'><span class='comments'>"+comments+"</span><i class='material-icons'>chat_bubble</i></a>");
				 
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
								 cm.append("<div class='commentSingle'>erhehrhehrhehrhhehrh</div>")
								 cm.append("<div class='commentSingle'>erhehrhehrhehrhhehrh</div>")
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
			 function unBind(anchor){
				 anchor.click(function(){});
			 }
			if(LoadedPosts!="undefined")
				for(var i=LoadedPosts.length-1;i>=0;i--)
					$("#posts").prepend(createAPost(LoadedPosts[i]["PostId"],LoadedPosts[i]["Post"],LoadedPosts[i]["Fullname"],LoadedPosts[i]["Likes"],LoadedPosts[i]["PostType"],LoadedPosts[i]["Liked"],LoadedPosts[i]["UserId"],LoadedPosts[i]["Comments"]));
			
		 }	
		
)
// $(function($){
//	
//	 $("#Submit_Post").click(
//			 function(){
//				 var NoError=true;
//				 $("#error_SelectRole").empty();
//				 $("#error_PostText").empty();
//				 if(!($("#Rider:checked").val()=="on" || $("#Driver:checked").val()=="on")){					 
//					 $("#error_SelectRole").text("Select either one");	
//					 NoError=false;
//				 }
//				 if($("#compose").val()==""){
//					 $("#error_PostText").text("Please type a post")
//					 NoError=false;
//				 }
//				 if(NoError){
//					 var PostType = ($("#Rider").val()=="on")?1:0;
//					 var Post = $("#compose").val();
//					 $.ajax("PostController",{
//						 data:{
//							 "Action":"Save",
//							 "PostType":PostType,
//							 "Post":Post
//						 }
// 						}	
//						).done(
//								function(){
//									$("#error_SelectRole").empty();
//									$("#error_PostText").empty();
//									$('#modal1').closeModal();
//									GetPost(0,20)
//									})
//						.fail(
//								function(){
//									console.log("fail")
//									});
//				 }
//			 })
//			
//	function GetPost(From,To){
//		 $.ajax("PostController",{
//			 data:{
//				 "Action":"Get",
//				 "From":From,
//				 "To":To
//			 }
//				}	
//			).done(
//					function(Posts){
//						if(Posts!="undefined")
//							for(var i=Posts.length-1;i>=0;i--){
//								if(!$("#__"+Posts[i]["PostId"]).length)
//								$("#posts").prepend(createAPost(Posts[i]["PostId"],Posts[i]["Post"],Posts[i]["Fullname"],Posts[i]["Likes"],Posts[i]["PostType"],Posts[i]["Liked"],Posts[i]["UserId"],Posts[i]["Comments"]));		
//					}
//			})
//					
//			.fail(function(){console.log("fail")});
//	 }
//	 function createAPost(postid,post,fullname,likes,posttype,liked,userid,comments){		 
//		 var ClassRole =(posttype==1)?"driver":"rider";
//		 var li =$("<li class='collection-item avatar email-unread selected' id='_" + postid + "'>" +
//	              "<i class='"+ClassRole+"'></i>" +
//	               "<span class='title'>" + fullname+ "</span>" + 
//	               "<p class ='post_text'>" + post + "</p></div></li>");
//		 var PC = $("<div></div>");
//		 var aLikes = $("<a href='javascript:void(0)'><span class='likes'>" + likes + "</span></a>");
//		 var TU =$("<i class='material-icons' id='__i'" + postid + ">thumb_up</i>");
//		 var aComments = $("<a href='javascript:void(0)'><span class='comments'>"+comments+"</span><i class='material-icons'>chat_bubble</i></a>");
//		 
//		 if(!liked){			 
//			 aLikes.click(function(){
//				 if(!TU.hasClass("icon-black"))
//				 $.ajax("PostController",{
//					 data:{
//						 "Action":"Like",
//						 "PostId":postid
//					 }
//				 }	
//				 ).done(
//						function(data){
//							 TU.addClass("icon-black");
//							 $("#__" + postid + " a>span.likes").text(parseInt($("#__" + postid + " a>span.likes").text())+ 1);
//							})
//					.fail(
//						function(){
//							console.log("fail")
//							});
//			 })}
//		 else{
//			 TU.addClass("icon-black");
//			 aLikes.click(function(evt){
//				 console.log("here")
//				 evt.preventDefault();
//			 })
//		 }
//		 aComments.click(
//				 function(){
//					 var cm = $("#__comments_" + postid);
//					 if(cm.children().length==0){
//						 cm.append("<div class='commentSingle'>erhehrhehrhehrhhehrh</div>")
//						 cm.append("<div class='commentSingle'>erhehrhehrhehrhhehrh</div>")
//					 }
//					 else
//						 cm.empty();
//				 }
//		 )
//		 aLikes.append(TU);
//		 PC.append(aLikes);
//		 PC.append(aComments);
//		 if(UserId==userid){
//			 var DeleteComment = $("<a href='#!' class='secondary-content'><i class='material-icons icon-black Tiny'>delete</i></a>");
//			 PC.append(DeleteComment)
//			 DeleteComment.click(
//					 function(){
//						 $.ajax("PostController",{
//							 data:{
//								 "Action":"Delete",
//								 "PostId":postid							 
//							 }
//								}	
//							).done(
//									function(Posts){
//										li.remove();
//							})
//									
//							.fail(function(){console.log("fail")});
//					 }
//			 );
//		 }
//		 var CommentSection =$("<div class='commentContainer' id='__comments_" + postid + "'></div>");
//		 PC.append(CommentSection);
//		 li.append(PC)
//		 return li;		              
//	 }
//	 function unBind(anchor){
//		 anchor.click(function(){});
//	 }
//	if(LoadedPosts!="undefined")
//		for(var i=LoadedPosts.length-1;i>=0;i--)
//			$("#posts").prepend(createAPost(LoadedPosts[i]["PostId"],LoadedPosts[i]["Post"],LoadedPosts[i]["Fullname"],LoadedPosts[i]["Likes"],LoadedPosts[i]["PostType"],LoadedPosts[i]["Liked"],LoadedPosts[i]["UserId"],LoadedPosts[i]["Comments"]));
//	
// })(jQuery);