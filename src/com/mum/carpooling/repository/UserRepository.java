package com.mum.carpooling.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.mum.carpooling.model.User;
import com.mum.carpooling.ultil.DBConnection;

public class UserRepository {
	private Connection dbConnection;
	  
	  public UserRepository() {
	      dbConnection = DBConnection.getConnection();
	  }
	  
	  public boolean login(User user) {
		  try {
	          PreparedStatement prepStatement = dbConnection.prepareStatement("select password from users where email = ?");
	          prepStatement.setString(1, user.getEmail());           
	          
	          ResultSet result = prepStatement.executeQuery();
	          if (result != null) {
	              while (result.next()) {
	                  if (result.getString(1).equals(user.getPassword())) {
	                      return true;
	                  }
	              }               
	          }           
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	      return false;
	  }
	  
	  public void register(User user) {
	      try {
	          PreparedStatement prepStatement = dbConnection.prepareStatement("insert into users(email, password, fullname) values (?, ?, ?)");
	          prepStatement.setString(1, user.getEmail());
	          prepStatement.setString(2, user.getPassword());
	          prepStatement.setString(3, user.getFullname());
	          
	          prepStatement.executeUpdate();
	      } catch (SQLException e) {
	          e.printStackTrace();
	      }
	  }
	  
	  public boolean findByEmail(String email) {
	      try {
	          PreparedStatement prepStatement = dbConnection.prepareStatement("select count(*) from users where email = ?");
	          prepStatement.setString(1, email);   
	                      
	          ResultSet result = prepStatement.executeQuery();
	          if (result != null) {   
	              while (result.next()) {
	                  if (result.getInt(1) == 1) {
	                      return true;
	                  }               
	              }
	          }
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	      return false;
	  }
}
