package com.revature.services;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.data.ProfileDAOImpl;
import com.revature.models.Profile;

public class ProfileServiceData {

	private static Logger log = LoggerFactory.getLogger(ProfileServiceData.class);
	private static ProfileDAOImpl implement;
	
	private static ProfileServiceData serviceData = null;
	private static Scanner scanner;
	
	//Constructor for the singleton
	private ProfileServiceData() {
		super();
		ProfileServiceData.implement = new ProfileDAOImpl();
		ProfileServiceData.scanner = new Scanner(System.in);
	}

	
	public static ProfileServiceData getServiceData() {
		if (serviceData == null) {
			log.info("created profile service data instance.");
			serviceData = new ProfileServiceData();
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
				if(userInput.equals("cancel")){
					return;
				}
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
			if(userInput.equals("cancel")){
				return;
			}
			
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
	

	
}
