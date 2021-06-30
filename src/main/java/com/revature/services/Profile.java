package com.revature.services;

import java.util.HashMap;

public class Profile {

	private String name;
	
	private String username;
	private String password;
	
	private HashMap<Integer, Account> connectedAcounts = new HashMap<Integer, Account>();
	
}
