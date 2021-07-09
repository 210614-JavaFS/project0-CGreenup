package com.revature.ui;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Profile;
import com.revature.services.ServiceData;

public class ProfileMenu {

	private static Logger logger = LoggerFactory.getLogger(ProfileMenu.class);
	
	public static void login(Scanner scanner) {
		ServiceData.getServiceData();
		
		Profile profile = null;
		
		do {
			profile = validateUserLoginInput(scanner);
			
			System.out.println();
			
		}while (profile == null);
		
		System.out.println("Welcome, " + profile.getName().split(" ")[0] + ".");
		
		//TODO
		//PROFILE MENU AND OPTIONS
		boolean loggedIn = true;
		do {
			loggedIn = menuOptions(profile, scanner);
		}while(loggedIn);
		
		System.out.println("Logging out...");
	}

	//Validate the user's input
	//Validates that the username is unique
	//Validates that the fields are valid (IE aren't just an empty string)
	//Once the fields have been verified, a profile is logged accessed and returned
	private static Profile validateUserLoginInput(Scanner scanner) {
		Profile profile;
		String username;
		String password;
		do {
			System.out.println("Please enter your username:\n");
			username = scanner.nextLine().strip();
		} while (username == "");
		
		do {
			System.out.println("Please enter your password:\n");
			password = scanner.nextLine().strip();
		} while (password == "");
		
		profile = ServiceData.loginProfile(username, password);
		
		if(profile == null) {
			System.out.println("Please try again.");
		}
		return profile;
	}
	
	private static boolean menuOptions(Profile profile, Scanner scanner) {
		//Create a String Array of the menu options
		String[] menuOptions = {
				"Manage Bank Accounts",
				"Apply for New Account",
				"Logout",
				"Quit"
		};
		
		//Add their corresponding numbers so that they can be accessed
		for(int i = 0; i < menuOptions.length; i++) {
			menuOptions[i] = menuOptions[i] + (i+1);
		}
		
		Driver.printMenu(menuOptions);
		System.out.println("");
		
		String userInput;
		String[] inputArray;
		//get the user's response
		userInput = scanner.nextLine().stripLeading();
		inputArray = userInput.split(" ");
		userInput = inputArray[0].toLowerCase();
		
		//This is obscene
//		userInput = scanner.nextLine().strip().split(" ")[0].toLowerCase();
		
		//If the user doesn't enter anything do nothing.
		if (userInput.equals("")) {
			logger.info("User entered nothing");
		}
		//If the user wants to manage accounts, call the lookAtAccount() method
		else if(menuOptions[0].toLowerCase().contains(userInput)) {
			logger.info(profile.getName() + " applied for a new account.");
			applyForAccount(profile);
		}
		//If the user wants to apply for a new account, call the applyForAccount() method
		else if(menuOptions[1].toLowerCase().contains(userInput)) {
			logger.info(profile.getName() + " applied for a new account.");
			applyForAccount(profile);
		}
		
		else if(menuOptions[2].toLowerCase().contains(userInput)){
			logger.info(profile.getName() + " logged out.");
			return false;
		}
		else if(menuOptions[3].concat("leave end exit").toLowerCase().contains(inputArray[0])) {
			logger.info(profile.getName() + " exited the program from profile.");
			System.out.println("Exiting Program");
			System.exit(0);
		}
		
		return true;
	}
	
	private static void applyForAccount(Profile profile) {
		//TODO display accounts applied for
		//
		
	}

	private static void lookAtAccounts(Profile profile, Scanner scanner) {
		
	}
	
}
