package com.mum.carpooling.model;

import java.sql.*;

public class User {
	private long userid;
	private String fullname;
	private int gender;
	private String state;
	private String city;
	private String street;
	private int zipcode;
	private int birthyear;
	private String email;
	private String password;
	private Timestamp datecreated;
	private Timestamp dateupdated;

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public int getBirthyear() {
		return birthyear;
	}

	public void setBirthyear(int birthyear) {
		this.birthyear = birthyear;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getDatecreated() {
		return datecreated;
	}

	public void setDatecreated(Timestamp datecreated) {
		this.datecreated = datecreated;
	}

	public Timestamp getDateupdated() {
		return dateupdated;
	}

	public void setDateupdated(Timestamp dateupdated) {
		this.dateupdated = dateupdated;
	}

	public static boolean saveNewUser(User user) {
		return false;
	}

	public static boolean editUser(String userid, User user) {
		return false;
	}
}
