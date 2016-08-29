package com.mum.carpooling.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mum.carpooling.model.User;
import com.mum.carpooling.repository.UserRepository;

/**
 * UserController servlet
 * @author binhtran
 *
 */

@SuppressWarnings("serial")
public class UserController extends HttpServlet {
	private UserRepository userRepository;

	private static String USER_SIGNUP = "WEB-INF/view/signup.jsp";
	private static String USER_LOGIN = "WEB-INF/view/login.jsp";
	private static String LOGIN_SUCCESS = "WEB-INF/view/success.jsp";
	private static String LOGIN_FAILURE = "WEB-INF/view/failure.jsp";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserController() {
		super();
		userRepository = new UserRepository();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		String forward = "";
	
		HttpSession session = request.getSession(true);
		User currentUser = (User) session.getAttribute("currentUser");
		if(currentUser != null) {
			forward = LOGIN_SUCCESS;
		} else {
			String action = request.getParameter("action");
			if(action != null && !action.isEmpty()) {
				if(action == "signup") {
					forward = USER_SIGNUP; 
				} else {
					forward = USER_LOGIN;
				}
			}
		}
		
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		String pageName = request.getParameter("pageName");
		String forward = "";		
		User user = new User();
		user.setEmail(request.getParameter("email"));
		user.setPassword(request.getParameter("password"));
		if (userRepository != null) {
			if (pageName.equals("signup")) {
				if (userRepository.findByEmail(user.getEmail())) { 
					request.setAttribute("message", "Email is exists. Try another email");
					forward = USER_SIGNUP;
					RequestDispatcher view = request.getRequestDispatcher(forward);
					view.forward(request, response);
					return;
				}

				userRepository.register(user);
				forward = USER_LOGIN;
			} else if (pageName.equals("login")) {
				HttpSession session = request.getSession(true);
				User currentUser = (User) session.getAttribute("currentUser");
				if(currentUser != null) {
					forward = LOGIN_SUCCESS;
				} else {
					boolean result = userRepository.login(user);
					if (result == true) {
				         session.setAttribute("currentUser",user); 
						forward = LOGIN_SUCCESS;
					} else {
						request.setAttribute("validClass", "invalid");
						forward = USER_LOGIN;
					}
				}
			}
		}
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}
}
