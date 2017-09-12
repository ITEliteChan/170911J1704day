package com.rimi.bean;

public class UserBean {

	private int id;
	private String userName;
	private String password;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserBean(int userId, String userName, String userPs) {
		
		this.id = userId;
		this.userName = userName;
		this.password = userPs;
	}
	
	
}
