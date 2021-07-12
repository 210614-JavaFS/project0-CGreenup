package com.revature.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.services.NegativeAccountException;

public class Account {

	//Class fields
	private Profile owner;
	private String accountName;
	private String accountType;
	private double balance;
	private int id;
	private boolean isApplication;
	Logger log = LoggerFactory.getLogger(Account.class);
	
	public Account() {
		super();
		balance = 0.00;
	}
	public Account(int id, Profile owner, String accountName, String accountType, double balance, boolean isApplication) {
		super();
		this.owner = owner;
		this.accountName = accountName;
		this.accountType = accountType;
		this.balance = balance;
		this.isApplication = isApplication;
	}
	//Getters
	public String 	getAccountName() 		{ return accountName; 	}
	public String 	getAccountType() 		{ return accountType; 	}
	public double 	getBalance() 			{ return balance; 		}
	public Profile 	getOwner()				{ return owner;			}	
	public int 		getId() 				{ return id;			}
	public boolean	getIsApplication()		{ return isApplication;	}

	//Setters
	public void setAccountName(String name) 		{ this.accountName = name;			}
	public void setAccountType(String accountType) 	{ this.accountType = accountType; 	}
	public void setOwner(Profile owner) 			{ this.owner = owner; 				}	
	public void setId(int id) 						{ this.id = id;						}
	public void setIsApplication(boolean bool)		{ this.isApplication = bool;		}	
	
	
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
	@Override
	public String toString() {
		return "Account [owner = " + owner + ", accountName = " + accountName + ", accountType = " + accountType
				+ ", balance = " + balance + ", id = " + id + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
		result = prime * result + ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		if (accountType == null) {
			if (other.accountType != null)
				return false;
		} else if (!accountType.equals(other.accountType))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}

	
	
	
}
