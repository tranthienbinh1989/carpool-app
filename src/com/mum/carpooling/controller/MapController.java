package com.mum.carpooling.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mum.carpooling.model.Post;
import com.mum.carpooling.model.User;
import com.mum.carpooling.repository.PostRepository;

/**
 * Servlet implementation class MapController
 */
@WebServlet("/ridemap")
public class MapController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static String MAP_VIEW = "WEB-INF/view/ridemap.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MapController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(MAP_VIEW);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String Action = request.getParameter("Action");
		if(Action!=null && !Action.isEmpty()){
			if(Action.equals("getFrom")){
				String fromLat = request.getParameter("fromLat");
				String fromLong = request.getParameter("fromLong");
				if(fromLat !=null && fromLong != null) {
					ArrayList<Post> Posts = PostRepository.getNearestFromPosts(fromLat, fromLong);
					JSONArray returnPosts = new JSONArray();
					for(Post post: Posts) {
						JSONObject p = new JSONObject();
						p.put("postId", post.getPostid());
						p.put("postType", post.getPosttype());
						p.put("fullname", post.getFullname());
						p.put("fromLat", post.getFromLatitue());
						p.put("fromLong", post.getFromLongitue());
						returnPosts.add(p);
					}			
					response.setContentType("application/json");
					response.getWriter().write(returnPosts.toJSONString());
				}
					
			} else {
				response.setContentType("application/json");
				response.getWriter().write("Wrong action");
			}
		}
	}

}
