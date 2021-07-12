package com.revature.ui;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.services.ProfileServiceData;

public class Driver {

	public static Logger log = LoggerFactory.getLogger(Driver.class);
	
	public static void main(String[] args) {
		

		boolean wantToQuit = false;
		Scanner scanner = new Scanner(System.in);
		//Stretch goal, make this a hash map instead of a String array. Map string to function
		String[] menuOptions = {
				"Login",
				"Create New Account",
				"Quit"
		};
		
		for(int i = 0; i < menuOptions.length; i++) {
			menuOptions[i] = menuOptions[i] + (i+1);
		}
		
		
		System.out.println("Welcome to Purity Bank!\n");
		
		while (!wantToQuit) {
			//Print all the options the user has
			printMenu(menuOptions);
						
			wantToQuit = parseInput(scanner, menuOptions);
			System.out.println();
		}
		
		scanner.close();
		
	}

	//Prints out all of the items
	public static void printMenu(String[] menuOptions) {
		
		//Spacing is used for the printing of the menu
		//This number controls the maximum space between the number and the menu option itself
		//IE if spacing is 5, there is a maximum of 5 spaces between [1] and "Login" in the menu
		final int spacing = 5;
		System.out.println("Would you like to:");
		for(int i = 0; i < menuOptions.length; i++)
			System.out.printf("%-"+spacing+"s %s\n", "[" + (i+1) + "]", menuOptions[i].replace("" + (i+1), ""));
			//Print out the line: [#]	menuOption[i], but remove the number from menu
		
	}
	
	//Parses the user's input
	//returns a boolean to know if it is time to quit the program
	//wantToQuit is used
	private static boolean parseInput( Scanner scanner, String[] menuOptions) {
		
		String userInput;
		String[] inputArray;
		//get the user's response
		userInput = scanner.nextLine().strip().toLowerCase();
		inputArray = userInput.split(" ");
		
		//If the user doesn't enter anything do nothing
		if (userInput.equals("")) {
			System.out.println("buh");
		}
		//Otherwise, if the user entered anything within login, call the login method
		else if(menuOptions[0].toLowerCase().contains(inputArray[0])) {
			//DEBUG
			ProfileServiceData.getServiceData();
			ProfileServiceData.printProfiles();
			
			
			login();
		}
		//Otherwise, if the user entered anything within "create new account" call the createAccount method
		else if (menuOptions[1].toLowerCase().contains(inputArray[0])) {
			//DEBUG
			ProfileServiceData.getServiceData();
			ProfileServiceData.printProfiles();
			
			createAccount();
		}
		//Otherwise, if the user enters a word to quit, end the program
		else if(menuOptions[2].concat("leave end exit").toLowerCase().contains(inputArray[0])) {
			//TODO
			System.out.println(menuOptions[2] + " " + inputArray[0]);
			return true;
		}
		else {
			System.out.println("I don't know what you mean by " + userInput + ".\n"
					+ "Please try entering one of the following commands again.");
		}
		return false;
	}
	
	private static void login() {
		ProfileMenu.login();
		System.out.println();
	}
	
	private static void createAccount() {
		ProfileServiceData.getServiceData();
		ProfileServiceData.createProfile();
		System.out.println();
	}
	
}
