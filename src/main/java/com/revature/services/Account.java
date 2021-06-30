package com.revature.services;

public class Account {

	//Class fields
	private String name;
	private String accountType;
	private double balance;
	
	//Getters
	public String getName() 			{ return name; }
	public String getAccountType() 		{ return accountType; }
	public double getBalance() 			{ return balance; }

	//Setters
	public void setName(String name) { 
		this.name = name; 
		}
	
	public void setAccountType(String accountType) { 
		this.accountType = accountType; 
		}
	
	//Changes the balance of the account
	//Positive change signifies a deposit
	//Negative change signifies a withdrawal
	public void changeBalance(double change) { 
		double temp = roundToTwo(balance) + roundToTwo(change);
		}
	
	public static double roundToTwo(double input) {
		return (double) (Math.round(input * 100.0) / 100.0);
	}
	
}
