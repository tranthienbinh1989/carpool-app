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
	          PreparedStatement prepStatement = dbConnection.prepareStatement("insert into users(firstname, gender, state, city, street, zipcode, birthyear, email, password, lastname ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	          prepStatement.setString(1, user.getFirstname());
	          prepStatement.setInt(2, user.getGender());
	          prepStatement.setString(3, user.getState());
	          prepStatement.setString(4, user.getCity());
	          prepStatement.setString(5, user.getStreet());
	          prepStatement.setInt(6, user.getZipcode());
	          prepStatement.setInt(7, user.getBirthyear());
	          prepStatement.setString(8, user.getEmail());
	          prepStatement.setString(9, user.getPassword());
	          prepStatement.setString(10, user.getLastname());
	          prepStatement.executeUpdate();
	      } catch (SQLException e) {
	          e.printStackTrace();
	      }
	  }
	  
	  public void update(User user) {
	      try {
	          PreparedStatement prepStatement = dbConnection.prepareStatement("update users set "
	          		+ "firstname = ?, lastname = ?, gender = ?, street = ?, city = ?, "
	          		+ "state = ?, zipcode = ?, birthyear = ?, email = ?"
	          		+ "where email = ?");
	          prepStatement.setString(1, user.getFirstname());
	          prepStatement.setString(2, user.getLastname());
	          prepStatement.setInt(3, user.getGender());
	          prepStatement.setString(4, user.getStreet());
	          prepStatement.setString(5, user.getCity());
	          prepStatement.setString(6, user.getState());
	          prepStatement.setInt(7, user.getZipcode());
	          prepStatement.setInt(8, user.getBirthyear());
	          prepStatement.setString(9, user.getEmail());
	          prepStatement.setString(10, user.getEmail());
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
	  
	  public User findUserByEmail(String email) {
		  try {
	          PreparedStatement prepStatement = dbConnection.prepareStatement("select * from users where email = ?");
	          prepStatement.setString(1, email);   
	           
	          ResultSet result = prepStatement.executeQuery();
	          if (result != null) {   
	              while (result.next()) {
	            	  User u = new User();
	            	  u.setUserid(result.getLong("userid"));
	            	  u.setFirstname(result.getString("firstname"));
	            	  u.setLastname(result.getString("lastname"));
	            	  u.setEmail(result.getString("email"));
	            	  u.setBirthyear(result.getInt("birthyear"));
	            	  u.setGender(result.getInt("gender"));
	            	  u.setStreet(result.getString("street"));
	            	  u.setCity(result.getString("city"));
	            	  u.setState(result.getString("state"));
	            	  u.setZipcode(result.getInt("zipcode"));
	            	  return u;
	              }
	          }
	          
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	      return null;
	  }
}