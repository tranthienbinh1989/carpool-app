package com.mum.carpooling.comment;

import java.util.Date;

import com.mum.carpooling.ultil.DBConnection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class Comment {
	
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
	}
	
	
	

}
