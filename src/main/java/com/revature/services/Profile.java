package com.revature.services;

import java.util.HashMap;

public class Profile {

	private String name;
	
	private String username;
	private String password;
	
	private AccountTypes accountType;
	
	//TODO
	//REFACTOR ADMIN PRIVELAGES
	//MAKE IT SO EACH FUNCTION IS A BOOL
	
	
	private HashMap<Integer, Account> connectedAcounts;

	public Profile(String name, String username, String password, AccountTypes accountTypes) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.accountType = accountTypes;
		
		connectedAcounts = new HashMap<Integer, Account>();
	}
	
	public Profile(String name, String username, String password) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.accountType = AccountTypes.USER;
		
		connectedAcounts = new HashMap<Integer, Account>();
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

	public Account getAccount(int key) {
		return connectedAcounts.get(key);
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
	
	public void addAccount(Account acc) {
		int size = connectedAcounts.keySet().size();
		connectedAcounts.put(size, acc);
	}
	
	
	//debug
	public void printConnectedAccounts() {
		for (Account a: connectedAcounts.values()) {
			System.out.println(a.getAccountType() + ": " + a.getAccountName() + " " + a.getBalance());
		}
	}
	
}
