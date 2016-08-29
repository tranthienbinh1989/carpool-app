package com.mum.carpooling.controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import com.mum.carpooling.model.User;
import com.mum.carpooling.ultil.DBConnection;

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
		try {
			HttpSession session = request.getSession(false);
			User CurrentUser = getUser();//(User)session.getAttribute("CurrentUser");
			
		String Action = request.getParameter("Action");
		if(Action!=null && !Action.isEmpty()){
			if(Action.equals("Save")){
				String PostText = request.getParameter("Post");
				String PostType = request.getParameter("PostType");
				if(PostText!=null && !PostText.isEmpty() && PostType!=null && !PostType.isEmpty()){
					PreparedStatement  statement = DBConnection.getConnection().prepareStatement("INSERT INTO posts(userid,post,posttype) VALUES (?,?,?)");
					statement.setLong(1,CurrentUser.getUserid());
					statement.setString(2, PostText);
					statement.setInt(3,  Integer.parseInt(PostType));
					statement.executeUpdate();
				}
			}
			else if(Action.equals("Delete")){
				String PostId = request.getParameter("PostId");
				if(PostId!=null && !PostId.isEmpty()){
					PreparedStatement  statement = DBConnection.getConnection().prepareStatement("DELETE FROM posts where userid=? and postid=?");
					statement.setLong(1,CurrentUser.getUserid());
					statement.setInt(2,  Integer.parseInt(PostId));
					statement.executeUpdate();
				}
			}
			else if(Action.equals("Get")){
				String From = request.getParameter("From");
				String To = request.getParameter("To");
				if(From!=null && !From.isEmpty() && To!=null && !To.isEmpty()){
					PreparedStatement  statement = DBConnection.getConnection().prepareStatement("select * from posts limit ?,?");
					statement.setInt(1,  Integer.parseInt(From));
					statement.setInt(2,  Integer.parseInt(To));
					ResultSet rs = statement.executeQuery();
					JSONArray Comments = new JSONArray();
					while (rs.next()) {
						JSONObject Comment = new JSONObject();;
						Comment.put("PostId",rs.getString("postid"));
						Comment.put("PostType",rs.getString("posttype"));
						Comment.put("Post",rs.getString("post"));
						Comments.add(Comment);
					}
					
					response.setContentType("application/json");
					response.getWriter().write(Comments.toJSONString());
				}
			}			
			else
				System.out.println("wrong opertion");
			
		}
        
        
	} catch (SQLException e) {
		System.out.println(e.getMessage());
	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	User getUser(){
		User u = new User();
		u.setUserid(1000);
		u.setFullname("Issac");
		u.setGender(1);
		return u;
	}
	
}
