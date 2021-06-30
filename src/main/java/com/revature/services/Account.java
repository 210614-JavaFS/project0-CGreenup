package com.revature.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Account {

	//Class fields
	private String name;
	private String accountType;
	private double balance;
	Logger log = LoggerFactory.getLogger(Account.class);
	
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
	public void changeBalance(double change) throws NegativeAccountException { 
		double temp = roundToTwo(balance) + roundToTwo(change);
		
		if (temp < 0.00) {
			log.error("Account attempted a negative balance.");
			log.error(roundToTwo(balance) + " + " + roundToTwo(change) + " = " + temp);
			throw new NegativeAccountException("Account attempted a negative balance.");
		}
		
		this.balance = temp;
	}
	
	public static double roundToTwo(double input) {
		return (double) (Math.round(input * 100.0) / 100.0);
	}
	
}
