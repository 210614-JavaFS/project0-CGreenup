package com.revature.ui;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.services.ServiceData;

public class Driver {

	public static Logger log = LoggerFactory.getLogger(Driver.class);
	
	public static void main(String[] args) {
		
		int spacing = 5; //6
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
		
//		//These are the strings used to compare to what the user wants to do
//		//These will be used to see if the user typed any keywords or numbers 
//		String login = "1 Login".toLowerCase().replaceAll(" ", "");
//		String create = "2 Create New Account".toLowerCase().replaceAll(" ", "");
//		String exitProgram = "3 Exit Quit Leave".toLowerCase().replaceAll(" ", "");
		
		
		System.out.println("Welcome to Purity Bank!\n");
		
		while (!wantToQuit) {
			//Print all the options the user has
			printMainMenu(menuOptions, spacing);
						
			wantToQuit = parseInput(wantToQuit, scanner, menuOptions);
			System.out.println();
		}
		
		scanner.close();
		
	}

	//Prints out all of the items
	public static void printMainMenu(String[] menuOptions, int spacing) {
		System.out.println("Would you like to:");
		for(int i = 0; i < menuOptions.length; i++)
			System.out.printf("%-"+spacing+"s %s\n", "[" + (i+1) + "]", menuOptions[i].replace("" + (i+1), ""));
			//Print out the line: [#]	menuOption[i], but remove the number from menu
		
	}
	
	//Parses the user's input
	private static boolean parseInput(boolean wantToQuit, Scanner scanner, String[] menuOptions) {
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
			ServiceData.getServiceData();
			ServiceData.printProfiles();
			
			
			login(scanner);
		}
		//Otherwise, if the user entered anything within "create new account" call the createAccount method
		else if (menuOptions[1].toLowerCase().contains(inputArray[0])) {
			//DEBUG
			ServiceData.getServiceData();
			ServiceData.printProfiles();
			
			createAccount();
		}
		//Otherwise, if the user enters a word to quit, end the program
		else if(menuOptions[2].concat("leave end exit").toLowerCase().contains(inputArray[0])) {
			//TODO
			System.out.println(menuOptions[2] + " " + inputArray[0]);
			wantToQuit = true;
		}
		else {
			System.out.println("I don't know what you mean by " + userInput + ".\n"
					+ "Please try entering one of the following commands again.");
		}
		return wantToQuit;
	}
	
	private static void login(Scanner scanner) {
		ProfileMenu.login(scanner);
		System.out.println();
	}
	
	private static void createAccount() {
		ServiceData.getServiceData();
		ServiceData.createProfile();
		System.out.println();
		System.out.println("Account created successfully!");
	}
	
}
