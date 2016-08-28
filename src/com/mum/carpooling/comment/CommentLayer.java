package com.mum.carpooling.comment;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLException;
import com.mum.carpooling.ultil.DBConnection;
import com.mysql.jdbc.PreparedStatement;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class CommentLayer {
	private Connection dbConnect;
	
	//Initilazied
	public CommentLayer() {
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
