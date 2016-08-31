package com.mum.carpooling.controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import com.mum.carpooling.model.User;
import com.mum.carpooling.ultil.DBConnection;
import com.mum.carpooling.model.Post;
import com.mum.carpooling.repository.PostRepository;
/**
 * Servlet implementation class Post
 */
@WebServlet("/PostController")
public class PostController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session==null||session.getAttribute("currentUser")==null){
			response.sendRedirect("/carpool-app/login");
			return;
		}
		//User CurrentUser = getUser();
		User CurrentUser = (User)session.getAttribute("currentUser");
	String Action = request.getParameter("Action");
	if(Action!=null && !Action.isEmpty()){
		if(Action.equals("Save")){
			String PostText = request.getParameter("Post");
			String PostType = request.getParameter("PostType");
			if(PostText!=null && !PostText.isEmpty() && PostType!=null && !PostType.isEmpty()) {
				Post post = new Post();
				post.setUserid(CurrentUser.getUserid());
				post.setPost(PostText);
				post.setPosttype(Integer.parseInt(PostType));
				post.setFromAddress(request.getParameter("departure"));
				post.setToAddress(request.getParameter("destination"));
				post.setFromLatitue(request.getParameter("departureLat"));
				post.setFromLongitue(request.getParameter("departureLong"));
				post.setToLatitue(request.getParameter("destinationLat"));
				post.setToLongitue(request.getParameter("destinationLong"));
				System.out.println("create a post");
				PostRepository.SavePost(post);
			}
				
		}
		else if(Action.equals("Delete")){
			String PostId = request.getParameter("PostId");
			if(PostId!=null && !PostId.isEmpty())
				PostRepository.DeletePost(CurrentUser.getUserid(),Long.parseLong(PostId));
		}
		else if(Action.equals("Like")){			
			String PostId = request.getParameter("PostId");
			if(PostId!=null && !PostId.isEmpty())
				PostRepository.Like(CurrentUser.getUserid(),Long.parseLong(PostId));
		}
		else if(Action.equals("New")){			
			String afterPostId = request.getParameter("afterPostId");
			int newPostCount=0;
			JSONObject postCounts = new JSONObject();
			if(afterPostId!=null && !afterPostId.isEmpty())
				newPostCount=PostRepository.New(Long.parseLong(afterPostId));
			response.setContentType("application/json");
			postCounts.put("NewComments", newPostCount);
			response.getWriter().write(postCounts.toJSONString());
		}
		else if(Action.equals("Get")){
			String From = request.getParameter("From");
			String To = request.getParameter("To");
			if(From!=null && !From.isEmpty() && To!=null && !To.isEmpty()){
				ArrayList<Post> Posts = PostRepository.GetPosts(CurrentUser.getBirthyear(),Integer.parseInt(From), Integer.parseInt(To));
				JSONArray Comments = new JSONArray();
				for(Post post: Posts) {
					JSONObject Comment = new JSONObject();
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
				response.setContentType("application/json");
				response.getWriter().write(Comments.toJSONString());
			}
		}			
		else
			System.out.println("wrong opertion");	
	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	public static User getUser(){
		User u = new User();
		u.setUserid(1000);
		u.setFirstname("Issac");
		u.setLastname("Issac Last");
		u.setGender(1);
		return u;
	}
	
}
