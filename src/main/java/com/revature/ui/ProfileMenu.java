package com.revature.ui;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Profile;
import com.revature.services.ProfileServiceData;

public class ProfileMenu {

	private static Logger logger = LoggerFactory.getLogger(ProfileMenu.class);
	private static Scanner scanner = new Scanner(System.in);
	
	public static void login() {
		ProfileServiceData.getServiceData();
		
		Profile profile = null;
		boolean loggedIn = true;
		
		profile = ProfileServiceData.loginProfile();
		
		//If no profile is returned, the user must have canceled or there was an error.
		//Exit the login menu and return to main menu
		if(profile == null) {
			logger.info("User Canceled login");
			return;
		}
			
		
		System.out.println("Welcome, " + profile.getFirstName() + ".");
		
		//PROFILE MENU AND OPTIONS
		do {
			loggedIn = menuOptions(profile);
		}while(loggedIn);
		
		System.out.println("Logging out...");
	}
	
	private static boolean menuOptions(Profile profile) {
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
		//If the user wants to manage accounts, call the manageAccount() method
		else if(menuOptions[0].toLowerCase().contains(userInput)) {
			manageAccounts(profile);
		}
		//If the user wants to apply for a new account, call the applyForAccount() method
		else if(menuOptions[1].toLowerCase().contains(userInput)) {
			applyForAccount(profile);
		}
		
		else if(menuOptions[2].toLowerCase().contains(userInput)){
			return false;
		}
		else if(menuOptions[3].concat("leave end exit").toLowerCase().contains(inputArray[0])) {
			logger.info("Program ended cleanly\n");
			logger.info("===============================================");
			System.out.println("Exiting Program");
			System.exit(0);
		}
		
		return true;
	}
	
	private static void applyForAccount(Profile profile) {
		AccountMenu accountMenu = new AccountMenu();
		accountMenu.applyForAccount(profile);
	}

	private static void manageAccounts(Profile profile) {
		AccountMenu accountMenu = new AccountMenu();
		accountMenu.manageAccounts(profile);
	}
	
}
