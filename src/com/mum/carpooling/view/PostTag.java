package com.mum.carpooling.view;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mum.carpooling.model.*;
import com.mum.carpooling.repository.PostRepository;
import com.mum.carpooling.controller.*;

public class PostTag extends SimpleTagSupport {
	 public void doTag() throws JspException, IOException {
		 //User CurrentUser = PostController.getUser();//getJspContext().getAttribute("CurrentUser",PageContext.SESSION_SCOPE);
		 
		 User CurrentUser =(User) getJspContext().getAttribute("currentUser",PageContext.SESSION_SCOPE);
		 if(CurrentUser==null)return;
		 JspWriter out = getJspContext().getOut();
		 
		 ArrayList<Post> Posts = PostRepository.GetPosts(CurrentUser.getUserid(),0, 25);
		 JSONArray Comments = new JSONArray();
		 for(Post post: Posts){
//			 out.print("<li class='collection-item avatar email-unread selected' id='__"+post.getPostid()+"'>" +
//              "<i class='material-icons circle green'>android</i>" +
//              "<span class='post_title'>" + post.getFullname()+ "</span>" + 
//              "<p id='post_title'>" + post.getPost()+ "</p>" + 
//              " <div class='secondary-content'>" +
//               "<a href='j'><span class='likes'>"+ post.getLikes()+"</span><i class='material-icons'>thumb_up</i></a>" +
//               "<a href='#!'><i class='material-icons'>chat_bubble</i></a>" +
//              "</div>" +
//             "</li> ");

					JSONObject Comment = new JSONObject();;
					Comment.put("PostId",post.getPostid());
					Comment.put("PostType",post.getPosttype());
					Comment.put("Post",post.getPost());
					Comment.put("UserId",post.getUserid());
					Comment.put("Fullname",post.getFullname());
					Comment.put("Likes",post.getLikes());
					Comment.put("Liked",post.getIsLiked());
					Comment.put("Comments",post.getComments());
					Comments.add(Comment);
				}
		 
		 out.print("<script> var LoadedPosts; UserId=" + CurrentUser.getUserid()+ "</script>");
		if(Comments.size()>0)
			out.print("<script> LoadedPosts="+Comments.toJSONString()+"</script>");

	 }
}