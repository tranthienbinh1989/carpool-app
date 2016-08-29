package com.mum.carpooling.controller;

import com.mum.carpooling.model.User;
import com.mum.carpooling.ultil.DBConnection;

import java.sql.*;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SavePost
 */
@WebServlet("/SavePost")
public class SavePost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SavePost() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		try {
				HttpSession session = request.getSession(false);
				User CurrentUser = getUser();//(User)session.getAttribute("CurrentUser");
				
			if(request.getParameter("post")!=null && !request.getParameter("post").isEmpty() && request.getParameter("posttype")!=null && !request.getParameter("posttype").isEmpty()){
				PreparedStatement  statement = DBConnection.getConnection().prepareStatement("INSERT INTO posts(userid,post,posttype) VALUES (?,?,?)");
				statement.setLong(1,CurrentUser.getUserid());
				statement.setString(2, request.getParameter("post"));
				statement.setInt(3,  Integer.parseInt(request.getParameter("posttype")));
				statement.executeUpdate();
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
