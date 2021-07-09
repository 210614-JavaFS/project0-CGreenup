package com.revature.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.data.DatabaseConnector;
import com.revature.models.AccountTypes;
import com.revature.models.Profile;
import com.sun.tools.sjavac.Log;

public class ServiceData {

	public static Logger log = LoggerFactory.getLogger(ServiceData.class);
	
	private static Map<String, Profile> allProfiles;
	private static ServiceData serviceData = null;
	private static Scanner scanner;
	
	//Constructor for the singleton
	private ServiceData() {
		allProfiles = new HashMap<String, Profile>();
		initializeProfiles();
	}
	
	//Initialize Profiles
	//Gets all the profiles in the database and stores them into the allProfiles Map
	private void initializeProfiles() {
		//TODO
		//Get database connection
		//Put all the profiles in the list
		//
		
		Profile profile = new Profile("June Greenup", "JuneAdmin", "ThePasswordForThisProgram", AccountTypes.ADMIN);
		allProfiles.put(profile.getUsername(), profile);
	}
	
	public static ServiceData getServiceData() {
		if (serviceData == null) {
			log.info("created service data instance.");
			serviceData = new ServiceData();
		}
		return serviceData;
	}
	
	public static void createProfile() {
		//Open up the scanner
		scanner = new Scanner(System.in);
		String userInput;
		
		//This do-while loop ensures the username is unique
		do {
			System.out.println("Please enter a username for your profile:");
			userInput = scanner.nextLine().strip();
			
			//If the user input isn't just whitespace / empty, then continue
			if(userInput == "" || userInput != null) {
				//Check if the username is unique using the Map
				//If the username is in the map, it's taken
				if(allProfiles.get(userInput) != null) {
					System.out.println("That username is taken, please choose another.");
				}
			}
		} while (userInput == "" || allProfiles.get(userInput) != null);
		
		String username = userInput;
		
		System.out.println();
		//Get a password		
		do {
			System.out.println("Please enter a password for your profile:");
			userInput = scanner.nextLine().strip();
			
		} while (userInput.equals(""));
		
		String password = userInput;
		System.out.println();
		
		//TODO: stretch goal - validate password entering, do-while
		
		System.out.println("Finally, what is your name?\n");
		String name = scanner.nextLine().stripLeading().stripTrailing();
		
		Profile profile = new Profile(name, username, password);
		
		allProfiles.put(username, profile);
	}

	
	//Method for Logging into a profile given a username and password
	//	- Checks if the username is one registered with the service
	//	- Checks if the password is correct for that account
	//	- Informs user if there was an error with any of their inputs
	public static Profile loginProfile(String username, String password) {
		
		Profile profile = null;
		
		//If there is a profile with the given username...
		if(allProfiles.containsKey(username)) {
//			profile = allProfiles.get(username).getPassword().equals(password)? allProfiles.get(username) : null;
			
			//...and the password is correct
			if (allProfiles.get(username).getPassword().equals(password)) {
				//Then set the profile to the profile with that username
				profile = allProfiles.get(username);
				log.info(username + " logged in successfully.");
				
			}else {
				System.out.println("Error logging in: password is incorrect");
				log.error("The account " + username + " does not use the password " + password);
			}
		}else {
			System.out.println("Error logging in: There is no account with that username");
			log.error("There is no account with username:" + username);
		}
		
		return profile;
	}
	
	//DEBUG
	public static void printProfiles() {
		for (Profile p : allProfiles.values()) {
			System.out.println(p.getUsername() + " " + p.getPassword() + " " + p.getName() + ": " + p.getAccountType());
		}
	}
	
	
}
