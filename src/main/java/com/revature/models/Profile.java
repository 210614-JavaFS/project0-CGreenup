package com.revature.models;

import java.util.HashMap;

public class Profile {

	//Name of the profile owner
	private String firstName;
	private String lastName;
	
	private String username;
	private String password;
	
	//Account type: 
	//	Whether the user is a customer(USER), an employee (EMPLOYEE), or admin(ADMIN)
	private AccountTypes accountType;

	
	
	public Profile(String firstName, String lastName, String username, String password, AccountTypes accountType) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.accountType = accountType;
	}

	public Profile() {
		super();
		this.accountType = AccountTypes.USER;
	}

	public String 		getFirstName() 		{return firstName;}
	public String 		getLastName() 		{return lastName;}
	public String 		getUsername() 		{return username;}
	public String 		getPassword() 		{return password;}
	public AccountTypes getAccountType() 	{return accountType;}

	public void setFirstName(String name) 					{this.firstName = name;}
	public void setLastName(String lastName) 				{this.lastName = lastName;}
	public void setUsername(String username) 				{this.username = username;}
	public void setPassword(String password) 				{this.password = password;}
	public void setAccountType(AccountTypes accountType) 	{this.accountType = accountType;}

	@Override
	public String toString() {
		return "Profile [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username + ", password="
				+ password + ", accountType=" + accountType + "]";
	}
	
	
	
}
