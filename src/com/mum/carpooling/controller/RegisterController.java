package com.mum.carpooling.controller;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.math.NumberUtils;

import com.mum.carpooling.model.User;
import com.mum.carpooling.repository.UserRepository;

import sun.misc.FpUtils;

/**
 * Servlet implementation class RegisterController
 */
@WebServlet("/register")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserRepository userRepository;

	private static String USER_SIGNUP = "WEB-INF/view/signup.jsp";
	private static String USER_LOGIN = "WEB-INF/view/login.jsp";
	private static String LOGIN_SUCCESS = "";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterController() {
        super();
        this.userRepository = new UserRepository();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String forward = "";
		
			HttpSession session = request.getSession(true);
			User currentUser = (User) session.getAttribute("currentUser");
			if(currentUser != null) {
				response.sendRedirect(request.getContextPath());
				return;
			} else {
				forward = USER_SIGNUP;
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
		int birthYear = 0;
		if(request.getParameter("birthYear") != null) {
			if(NumberUtils.isNumber(request.getParameter("birthYear"))) {
				birthYear = Integer.parseInt(request.getParameter("birthYear"));
			}
		}
		if(checkAge(birthYear)) {
			user.setBirthyear(birthYear);
		} else {
			request.setAttribute("errorYear", "invalid");
			request.setAttribute("yearMessage", "You need to be at least 18 years old");
			forward = USER_SIGNUP;
			RequestDispatcher view = request.getRequestDispatcher(forward);
			view.forward(request, response);
			return;
		}
		if(request.getParameter("gender") != null) {
			if(NumberUtils.isNumber(request.getParameter("gender"))) {
				user.setGender(Integer.parseInt(request.getParameter("gender")));
			}
		}
		user.setFirstname(request.getParameter("firstname"));
		user.setLastname(request.getParameter("lastname"));
		user.setStreet(request.getParameter("street"));
		user.setCity(request.getParameter("city"));
		user.setState(request.getParameter("state"));
		if(request.getParameter("zipcode") != null) {
			if(NumberUtils.isNumber(request.getParameter("zipcode"))) {
				user.setZipcode(Integer.parseInt(request.getParameter("zipcode")));
			}
		}

		if (userRepository != null) {
			if (userRepository.findByEmail(user.getEmail())) { 
				request.setAttribute("errorEmail", "invalid");
				
				forward = USER_SIGNUP;
			} else {
				userRepository.register(user);
				User currentUser = userRepository.findUserByEmail(user.getEmail());
				HttpSession session = request.getSession();
		        session.setAttribute("currentUser",currentUser); 
		        response.sendRedirect(request.getContextPath());
				return;
			}
		}
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}
	
	protected boolean checkAge(int year) {
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);

		if(thisYear - year >= 18) {
			return true;
		}
		return false;
	}
}
