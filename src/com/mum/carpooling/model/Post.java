package com.mum.carpooling.model;

public class Post {
	long postid,likes;
	long userid,Comments;
	
	String fromAddress;
	String toAddress;
	String fromLatitue;
	String fromLongitue;
	String toLatitue;
	String toLongitue;
	
	public long getComments() {
		return Comments;
	}
	public void setComments(long comments) {
		Comments = comments;
	}
	String post,fullname;
	int isLiked;
	public int getIsLiked() {
		return isLiked;
	}
	public void setIsLiked(int isLiked) {
		this.isLiked = isLiked;
	}
	public long getLikes() {
		return likes;
	}
	public void setLikes(long likes) {
		this.likes = likes;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	int posttype;
	
	public Post() {
		
	}
	
	public Post(long userid, String post, int posttype) {
		super();
		this.userid = userid;
		this.post = post;
		this.posttype = posttype;
	}
	public long getPostid() {
		return postid;
	}
	public void setPostid(long postid) {
		this.postid = postid;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public int getPosttype() {
		return posttype;
	}
	public void setPosttype(int posttype) {
		this.posttype = posttype;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getFromLatitue() {
		return fromLatitue;
	}
	public void setFromLatitue(String fromLatitue) {
		this.fromLatitue = fromLatitue;
	}
	public String getFromLongitue() {
		return fromLongitue;
	}
	public void setFromLongitue(String fromLongitue) {
		this.fromLongitue = fromLongitue;
	}
	public String getToLatitue() {
		return toLatitue;
	}
	public void setToLatitue(String toLatitue) {
		this.toLatitue = toLatitue;
	}
	public String getToLongitue() {
		return toLongitue;
	}
	public void setToLongitue(String toLongitue) {
		this.toLongitue = toLongitue;
	}
}
