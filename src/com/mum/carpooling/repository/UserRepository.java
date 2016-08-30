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
	          		+ "where userid = ?");
	          prepStatement.setString(1, user.getFirstname());
	          prepStatement.setString(2, user.getLastname());
	          prepStatement.setInt(3, user.getGender());
	          prepStatement.setString(4, user.getStreet());
	          prepStatement.setString(5, user.getCity());
	          prepStatement.setString(6, user.getState());
	          prepStatement.setInt(7, user.getZipcode());
	          prepStatement.setInt(8, user.getBirthyear());
	          prepStatement.setString(9, user.getEmail());
	          prepStatement.setLong(10, user.getUserid());
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
	            	  return this.getUser(result);
	              }
	          }
	          
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	      return null;
	  }
	  
	  public User findUserById(long id) {
		  try {
	          PreparedStatement prepStatement = dbConnection.prepareStatement("select * from users where userid = ?");
	          prepStatement.setLong(1, id);
	           
	          ResultSet result = prepStatement.executeQuery();
	          if (result != null) {   
	              while (result.next()) {
	            	  return this.getUser(result);
	              }
	          }
	          
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	      return null;
	  }
	  
	  protected User getUser(ResultSet rs) throws SQLException {
		  User u = new User();
    	  u.setUserid(rs.getLong("userid"));
    	  u.setFirstname(rs.getString("firstname"));
    	  u.setLastname(rs.getString("lastname"));
    	  u.setEmail(rs.getString("email"));
    	  u.setBirthyear(rs.getInt("birthyear"));
    	  u.setGender(rs.getInt("gender"));
    	  u.setStreet(rs.getString("street"));
    	  u.setCity(rs.getString("city"));
    	  u.setState(rs.getString("state"));
    	  u.setZipcode(rs.getInt("zipcode"));
    	  return u;
	  }
}