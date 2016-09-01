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
		 int i=0;
		 JSONArray Comments = new JSONArray();
		 while(i<2){
		 ArrayList<Post> Posts = PostRepository.GetPosts(CurrentUser.getUserid(),0, 25, i);
		 for(Post post: Posts){
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
		 i++;
		 }
		 out.print("<script> var LoadedPosts; UserId=" + CurrentUser.getUserid()+ "</script>");
		if(Comments.size()>0)
			out.print("<script> LoadedPosts="+Comments.toJSONString()+"</script>");		

	 }
}