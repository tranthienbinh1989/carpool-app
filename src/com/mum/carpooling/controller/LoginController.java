package com.mum.carpooling.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mum.carpooling.model.User;
import com.mum.carpooling.repository.UserRepository;

/**
 * @author binhtran
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserRepository userRepository;

	private static String USER_SIGNUP = "WEB-INF/view/signup.jsp";
	private static String USER_LOGIN = "WEB-INF/view/login.jsp";
	private static String LOGIN_SUCCESS = "";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        userRepository = new UserRepository();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward = "";
		
		HttpSession session = request.getSession(true);
		User currentUser = (User) session.getAttribute("currentUser");
		if(currentUser != null) {
			forward = LOGIN_SUCCESS;
		} else {
			forward = USER_LOGIN;
		}
		
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward = "";
		User user = new User();
		user.setEmail(request.getParameter("email"));
		user.setPassword(request.getParameter("password"));
		HttpSession session = request.getSession(true);
		boolean result = userRepository.login(user);
		if (result == true) {
			User currentUser = userRepository.findUserByEmail(user.getEmail());
	        session.setAttribute("currentUser",currentUser); 
	        response.sendRedirect(request.getContextPath());
			return;
		} else {
			request.setAttribute("validClass", "invalid");
			forward = USER_LOGIN;
		}
		
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

}
