package com.mum.carpooling.model;

public class UserSession {
	private User user;
	public UserSession(User user){
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
