package com.mum.carpooling.comment;

import java.util.Date;

import com.mum.carpooling.ultil.DBConnection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLException;

public class Comment {
	
	private Connection dbConnect;
	
//	private int commentId;
	private int userId;
	private int postId;
	private String comments;
	private Date datecreated;
	private Date dateupdated;
	
		
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getDatecreated() {
		return datecreated;
	}
	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}
	public Date getDateupdated() {
		return dateupdated;
	}
	public void setDateupdated(Date dateupdated) {
		this.dateupdated = dateupdated;
	}
	
	//Initilazied
	Comment(){
		dbConnect = DBConnection.getConnection();
	}
	
	//Insert new comment from user
	public void Insert(int userId, int postId, String coms, java.sql.Date dCreated) throws SQLException{
		try{
						
			String sql = "INSERT INTO comments(userid,postid,comment,datecreated) VALUES (?,?,?,?)";
			PreparedStatement pStatement = (PreparedStatement) dbConnect.prepareStatement(sql);
			pStatement.setInt(1, userId);
			pStatement.setInt(2, postId);
			pStatement.setString(3, coms);
			pStatement.setDate(4, dCreated);
			
			pStatement.execute();
			
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		finally{
			try{
				dbConnect.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			
		}
		
	}
	
	//Update context of old commment from user.
	public void Update(int commentId, String coms, java.sql.Date dUpdated){
		try{
			String sql = "UPDATE comments SET comment=?, dateupdated=? WHERE commentid=?";
			java.sql.PreparedStatement pStatement = dbConnect.prepareStatement(sql);
			pStatement.setString(1, coms);
			pStatement.setDate(2, dUpdated);
			pStatement.setInt(3, commentId);
			
			pStatement.execute();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				dbConnect.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			
		}
	}
	
	//delete the comment from user
	public void Delete(int commentId){
		try{
			String sql = "DELETE FROM comments WHERE commentid=?";
			java.sql.PreparedStatement pStatement = dbConnect.prepareStatement(sql);
			pStatement.setInt(1, commentId);
			
			pStatement.execute();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				dbConnect.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	

}
