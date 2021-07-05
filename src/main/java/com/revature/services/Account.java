package com.revature.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Account {

	//Class fields
	private Profile owner;
	private String accountName;
	private String accountType;
	private double balance;
	Logger log = LoggerFactory.getLogger(Account.class);
	
	public Account(String name, String accountType, double balance) {
		super();
		this.accountName = name;
		this.accountType = accountType;
		this.balance = balance;
	}
	
	//Getters
	public String getAccountName() 		{ return accountName; 	}
	public String getAccountType() 		{ return accountType; 	}
	public double getBalance() 			{ return balance; 		}
	public Profile getOwner()			{ return owner;			}

	//Setters
	public void setAccountName(String name) 		{ this.accountName = name;	}
	public void setAccountType(String accountType) 	{ this.accountType = accountType; }
	public void setOwner(Profile owner) 			{ this.owner = owner; }
	
	//Changes the balance of the account
	//Positive change signifies a deposit
	//Negative change signifies a withdrawal
	public void changeBalance(double change) throws NegativeAccountException { 
		double newBalance = roundToTwo(balance) + roundToTwo(change);
		
		if (newBalance < 0.00) {
			log.error("Account attempted a negative balance.");
			log.error(roundToTwo(balance) + " + " + roundToTwo(change) + " = " + newBalance);
			throw new NegativeAccountException("Account attempted a negative balance.");
		}
		
		this.balance = newBalance;
	}
	
	//Rounds an input to two decimal places
	//Returns the result
	//Ex. roundToTwo(1234.56789) returns 1234.57
	public static double roundToTwo(double input) {
		return (double) (Math.round(input * 100.0) / 100.0);
	}
	
}
