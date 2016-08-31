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
	public static int New(long afterPostId){
		try {
			PreparedStatement  statementSel = DBConnection.getConnection().prepareStatement("SELECT count(*) as count FROM posts WHERE postid>?");
			statementSel.setLong(1, afterPostId);
			ResultSet rs = statementSel.executeQuery();
			if(rs.next())					
				return rs.getInt("count");
			return 0;
        
	} catch (SQLException e) {
		System.out.println(e.getMessage());
	}
		return 0;
	}
	public static boolean Like(long userid,long postid){
		try {
			PreparedStatement  statementSel = DBConnection.getConnection().prepareStatement("SELECT likes.postid FROM likes WHERE userid=? AND postid=?");
			statementSel.setLong(1, userid);
			statementSel.setLong(2,  postid);
			ResultSet rs = statementSel.executeQuery();
			
			if(!rs.next()){			
				PreparedStatement  statementIn = DBConnection.getConnection().prepareStatement("INSERT INTO likes(userid,postid) VALUES (?,?)");
				statementIn.setLong(1, userid);
				statementIn.setLong(2,  postid);
				System.out.println(statementIn);
				statementIn.executeUpdate();
			}
			return true;
        
	} catch (SQLException e) {
		System.out.println(e.getMessage());
	}
		return true;
	}
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
	public static ArrayList<Post> GetPosts(long UserId,int From, int To){
		ArrayList<Post> Posts = new ArrayList<Post>();
		try {
			PreparedStatement  statement = DBConnection.getConnection().prepareStatement("SELECT posts.postid,posts.post,posts.posttype,posts.userid,likeCount.likes,users.firstname,users.lastname,userLiked.liked,commentCount.comments FROM posts LEFT OUTER JOIN((select postid,count(*) AS likes FROM likes group by postid) AS likeCount) ON likeCount.postid=posts.postid LEFT OUTER JOIN users ON posts.userid = users.userid LEFT OUTER JOIN (SELECT count(*) as liked,userid,postid  FROM likes group by userid,postid having likes.userid=?) as userLiked  on userLiked.postid=posts.postid LEFT OUTER JOIN((select postid,count(*) AS comments FROM comments group by postid) AS commentCount) ON commentCount.postid=posts.postid ORDER BY postid DESC LIMIT ?,?");
			statement.setLong(1, UserId);
			statement.setInt(2, From);
			statement.setInt(3,  To);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Post post = new Post(rs.getInt("userid"),rs.getString("post"),rs.getInt("posttype"));
				post.setPostid(rs.getInt("postid"));
				post.setFullname(rs.getString("firstname") + " "+rs.getString("lastname"));				
				post.setLikes(rs.getInt("likes"));
				post.setIsLiked(rs.getInt("liked"));
				post.setComments(rs.getInt("Comments"));
				Posts.add(post);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return Posts;
	}
}
