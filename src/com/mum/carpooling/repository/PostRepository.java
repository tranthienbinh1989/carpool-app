package com.mum.carpooling.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mum.carpooling.model.Post;
import com.mum.carpooling.model.User;
import com.mum.carpooling.ultil.DBConnection;

import java.util.*;

public class PostRepository {
	public static boolean SavePost(Post post){
		try {
			PreparedStatement  statement = DBConnection.getConnection().prepareStatement("INSERT INTO posts(userid,post,posttype) VALUES (?,?,?)");
			statement.setLong(1,post.getUserid());
			statement.setString(2, post.getPost());
			statement.setInt(3,  post.getPosttype());
			statement.executeUpdate();
			return true;
        
	} catch (SQLException e) {
		System.out.println(e.getMessage());
	}
		return true;
	}
	public static boolean DeletePost(long userid,long postid){
		try {
			PreparedStatement  statement = DBConnection.getConnection().prepareStatement("DELETE FROM posts where userid=? and postid=?");
			statement.setLong(1,userid);
			statement.setLong(2,  postid);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}
	public static ArrayList<Post> GetPosts(int From, int To){
		ArrayList<Post> Posts = new ArrayList<Post>();
		try {
			PreparedStatement  statement = DBConnection.getConnection().prepareStatement("select * from posts limit ?,?");
			statement.setInt(1, From);
			statement.setInt(2,  To);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Post post = new Post(rs.getInt("userid"),rs.getString("post"),rs.getInt("posttype"));
				post.setPostid(rs.getInt("postid"));
				Posts.add(post);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return Posts;
	}
}
