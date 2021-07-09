package com.revature.models;

import java.util.HashMap;

public class Profile {

	//Name of the profile owner
	private String name;
	
	private String username;
	private String password;
	
	//Account type: 
	//	Whether the user is a customer(USER), an employee (EMPLOYEE), or admin(ADMIN)
	private AccountTypes accountType;
	

	public Profile(String name, String username, String password, AccountTypes accountTypes) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.accountType = accountTypes;
	}
	
	public Profile(String name, String username, String password) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.accountType = AccountTypes.USER;
	}
	
	public Profile() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AccountTypes getAccountType() {
		return accountType;
	}

	//This should only be accessible to admins
	public void setAccountType(AccountTypes accountType) {
		this.accountType = accountType;
	}
	
	
	
}
