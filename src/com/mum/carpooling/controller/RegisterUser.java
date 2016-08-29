package com.mum.carpooling.controller;

import com.mum.carpooling.model.*;
import com.mum.carpooling.ultil.*;

import java.sql.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterUser
 */
@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		try {
			PreparedStatement  statement = DBConnection.getConnection().prepareStatement("INSERT INTO Employees (fullname,gender,state,street,zipcode,birthyear,email,password,datecreated,dateupdated) "
	        							+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)");
	        statement.setString(1, "Issac");
	        statement.setInt(2, 1);
	        statement.setString(3, "IOWA");
	        statement.setString(4, "Fairfield");
	        statement.setString(5, "100");
	        statement.setInt(6, 1);
	        statement.setInt(7, 1);
	        statement.setString(8, "esaaqo");
	        statement.setString(8, "esaaqo");
	        statement.setString(10, "2013-08-05 18:19:03");
	        statement.setString(11, "2013-08-05 18:19:03");
	        statement.executeUpdate();
	        DBConnection.getConnection().commit();
	        
	        
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

        //statement.executeUpdate(command);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
