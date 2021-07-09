package com.revature.services;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.data.ProfileDAOImplement;
import com.revature.models.Profile;

public class ServiceData {

	public static Logger log = LoggerFactory.getLogger(ServiceData.class);
	private static ProfileDAOImplement implement = new ProfileDAOImplement();
	
	private static ServiceData serviceData = null;
	private static Scanner scanner = new Scanner(System.in);
	
	//Constructor for the singleton
	private ServiceData() {
		initializeProfiles();
	}
	
	//Initialize Profiles
	//Gets all the profiles in the database and stores them into the allProfiles Map
	private void initializeProfiles() {
		//TODO
		//Get database connection
		//Put all the profiles in the list
	}
	
	public static ServiceData getServiceData() {
		if (serviceData == null) {
			log.info("created service data instance.");
			serviceData = new ServiceData();
		}
		return serviceData;
	}
	
	//Creates a profile, validates user input, then puts it into the database if it can
	public static void createProfile() {
		Profile profile = new Profile();
		String userInput;
		
		//Set Username
		boolean nameTaken;
		do {					
			
			do {
				System.out.println("Please enter a username for your profile:");
				userInput = scanner.nextLine().strip().toLowerCase();
			} while(userInput.equals(""));
			
			nameTaken = implement.usernameTaken(userInput);
			
			if(nameTaken) {
				System.out.println("That username is taken, try another one.");
				log.error("User tried to register profile under name: " + userInput + ", but it was taken.");
			}
		}while (nameTaken);
		
		profile.setUsername(userInput);
		
		//Set Password
		userInput = "";
		boolean passwordTooShort;
		int passwordMinimumLength = 8;
		do {
			System.out.println("Please enter a password: ");
			userInput = scanner.nextLine().strip();
			
			passwordTooShort = userInput.length() < passwordMinimumLength;
			
			if(passwordTooShort) {
				System.out.println("That password is too short, it needs to be at least " + passwordMinimumLength + 
						" characters long.");
			}
		}while(passwordTooShort);
		
		profile.setPassword(userInput);
		
		//Set First Name
		
		userInput = "";
		
		do {
			System.out.println("Please enter your first name:");
			userInput = scanner.nextLine().strip();
			
		}while(userInput.equals(""));
		profile.setFirstName(userInput);
		
		//Set Last Name
		
		userInput = "";
		
		do {
			System.out.println("Please enter your last name:");
			userInput = scanner.nextLine().strip();
			
		}while(userInput.equals(""));
		
		profile.setLastName(userInput);
		
		System.out.println("Working...");
		
		if(implement.addProfile(profile)) {
			System.out.println("\n\nProfile created successfully!\n");
			log.info("New profile created: " + profile.toString());
		}else {
			System.out.println("ERROR: profile could not be created");
			log.error("Profile was not able to be added to database: " + profile.toString());
		}
	}
	
	public static Profile loginProfile() {
		String username;
		String password;
		boolean usernameInDatabase;
		Profile profile;
		
		System.out.println();
		System.out.println("If you would like to leave, type \"Cancel\"");
		
		//Check Username
		do {
			do {
				System.out.println("Please enter your username: ");
				username = scanner.nextLine().strip().toLowerCase();
			} while (username.equals(""));
			
			if(username.equals("cancel"))
				return null;
			usernameInDatabase = implement.usernameTaken(username);
		}while (!usernameInDatabase);
		
		//Check password
		do {
			System.out.println("Please enter your password: ");
			password = scanner.nextLine().strip();
			profile = implement.findProfile(username, password);
			
			if(password.equals("cancel"))
				return null;
			else if(password.equals("")) {/*Do nothing*/}
			else if(profile == null) {
				System.out.println("Password does not match, please try again");
				log.error("Wrong password was entered");
			}
				
		}while(profile == null);
		
		profile = implement.findProfile(username);
		
		log.info("Login successful");
		return profile;
	}
	
	
	
	public static void printProfiles() {
		List<Profile> allProfiles = implement.findAll();
		
		for (Profile p : allProfiles) {
			System.out.println(p.getUsername() + " " + p.getPassword() + " " + p.getFirstName() + " " + p.getLastName() + ": " + p.getAccountType());
		}
}
	
//	public static void createProfile() {
//		//Open up the scanner
//		scanner = new Scanner(System.in);
//		String userInput;
//		
//		//This do-while loop ensures the username is unique
//		do {
//			System.out.println("Please enter a username for your profile:");
//			userInput = scanner.nextLine().strip();
//			
//			//If the user input isn't just whitespace / empty, then continue
//			if(userInput == "" || userInput != null) {
//				//Check if the username is unique using the Map
//				//If the username is in the map, it's taken
//				if(allProfiles.get(userInput) != null) {
//					System.out.println("That username is taken, please choose another.");
//				}
//			}
//		} while (userInput == "" || allProfiles.get(userInput) != null);
//		
//		String username = userInput;
//		
//		System.out.println();
//		//Get a password		
//		do {
//			System.out.println("Please enter a password for your profile:");
//			userInput = scanner.nextLine().strip();
//			
//		} while (userInput.equals(""));
//		
//		String password = userInput;
//		System.out.println();
//		
//		//TODO: stretch goal - validate password entering, do-while
//		
//		System.out.println("Finally, what is your name?\n");
//		String name = scanner.nextLine().stripLeading().stripTrailing();
//		
//		Profile profile = new Profile(name, username, password);
//		
//		allProfiles.put(username, profile);
//	}
//
//	
//	//Method for Logging into a profile given a username and password
//	//	- Checks if the username is one registered with the service
//	//	- Checks if the password is correct for that account
//	//	- Informs user if there was an error with any of their inputs
//	public static Profile loginProfile(String username, String password) {
//		
//		Profile profile = null;
//		
//		//If there is a profile with the given username...
//		if(allProfiles.containsKey(username)) {
////			profile = allProfiles.get(username).getPassword().equals(password)? allProfiles.get(username) : null;
//			
//			//...and the password is correct
//			if (allProfiles.get(username).getPassword().equals(password)) {
//				//Then set the profile to the profile with that username
//				profile = allProfiles.get(username);
//				log.info(username + " logged in successfully.");
//				
//			}else {
//				System.out.println("Error logging in: password is incorrect");
//				log.error("The account " + username + " does not use the password " + password);
//			}
//		}else {
//			System.out.println("Error logging in: There is no account with that username");
//			log.error("There is no account with username:" + username);
//		}
//		
//		return profile;
//	}


	
	
}
