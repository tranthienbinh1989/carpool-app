package com.mum.carpooling.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//import com.mum.carpooling.controller.Comment;
import com.mum.carpooling.ultil.DBConnection;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.mum.carpooling.model.Comment;

public class ComRepository {
private Connection dbConnect;
	
	//Initilazied
	public ComRepository() {
		dbConnect = DBConnection.getConnection();
	}
	
	//Insert new comment from user
	public void Insert(int userId, int postId, String coms) throws SQLException{
		try{
						
			String sql = "INSERT INTO comments(userid,postid,comment) VALUES (?,?,?)";
			PreparedStatement pStatement  = dbConnect.prepareStatement(sql);
			pStatement.setLong(1, userId);
			pStatement.setLong(2, postId);
			pStatement.setString(3, coms);
//			pStatement.setDate(4, dCreated);			
			pStatement.executeUpdate();			
			
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		finally{

		}
		
	}
	
	//Update context of old commment from user.
	public void Update(int commentId, String coms){
		try{
			String sql = "UPDATE comments SET comment=?, dateupdated=curdate() WHERE commentid=?";
			java.sql.PreparedStatement pStatement = dbConnect.prepareStatement(sql);
			pStatement.setString(1, coms);
			pStatement.setInt(2, commentId);			
			pStatement.execute();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
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
	
	public ArrayList<Comment> Select(int postId){
		ArrayList<Comment> comments = new ArrayList<>();
		try{
			String sql ="SELECT comments.userid,comments.postid,comments.comment,comments.datecreated,comments.dateupdated,users.firstname,users.lastname FROM comments left outer join users on users.userid=comments.userid where postid=?";
			PreparedStatement statement = (PreparedStatement) dbConnect.prepareStatement(sql);
			statement.setInt(1, postId);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				Comment com = new Comment();
				com.setUserId(rs.getInt("userid"));
				com.setPostId(rs.getInt("postid"));
				com.setComments(rs.getString("comment"));
				com.setDatecreated(rs.getDate("datecreated"));
				com.setDateupdated(rs.getDate("dateupdated"));
				com.setFullName(rs.getString("firstname") + " " + rs.getString("lastname"));
				comments.add(com);
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
		}
		return comments;
	}
}
