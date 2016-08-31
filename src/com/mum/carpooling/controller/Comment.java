package com.mum.carpooling.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//import org.apache.tomcat.jni.User;
import com.mum.carpooling.model.User;
import com.mum.carpooling.repository.ComRepository;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

/**
 * Servlet implementation class Comment
 */
@WebServlet("/comment")
public class Comment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		try{
			HttpSession session = request.getSession(false);
			if(session==null||session.getAttribute("currentUser")==null){
				response.sendRedirect("/carpool-app/login");
				return;
			}
			User curUser = (User)session.getAttribute("currentUser");;
			String action = request.getParameter("c").toString();
			if(action.equals("Get")){
				int comId = 0;
				comId = Integer.parseInt(request.getParameter("postId"));
				ComRepository comRe = new ComRepository();
				ArrayList<com.mum.carpooling.model.Comment> comments = comRe.Select(comId);
				JSONArray jArr = new JSONArray();
				
				for(int i = 0; i< comments.size(); i++){
					JSONObject jObj = new JSONObject();
					jObj.put("comment", comments.get(i).getComments());
					jObj.put("userid", comments.get(i).getUserId());
					jObj.put("fullname", comments.get(i).getFullName());
					jObj.put("datecreated", comments.get(i).getDatecreated().toString());
					jArr.add(jObj);
				}	
				
				response.setContentType("application/json");
				response.getWriter().write(jArr.toJSONString());
				
			}
			else if(action.equals("Insert")){				
				int userId = Integer.parseInt(request.getParameter("userId"));
				int postid = Integer.parseInt( request.getParameter("postid"));
				String comment = request.getParameter("comments");				
				ComRepository comRe = new ComRepository();
				comRe.Insert(userId, postid, comment);
				
			}
			else if(action.equals("Update")){	
				int commentid = Integer.parseInt(request.getParameter("commentid"));
				String coms = request.getParameter("comments");
				ComRepository comRe = new ComRepository();
				comRe.Update(commentid, coms);
				
			}
			else if(action.equals("Delete")){
				int commentid = Integer.parseInt(request.getParameter("commentid"));
				ComRepository comRe = new ComRepository();
				comRe.Delete(commentid);
			}
			else{
				System.out.println("Wrong action with "+ request.getParameter("c").toString()+"!");
				
			}
				
		}
		catch(ParseException e){
			e.printStackTrace();			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
