package com.revature.ui;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.services.ServiceData;

public class Driver {

	public static Logger log = LoggerFactory.getLogger(Driver.class);
	
	public static void main(String[] args) {
		
		boolean wantToQuit = false;
		Scanner scanner = new Scanner(System.in);
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
			printMainMenu(menuOptions);
						
			wantToQuit = parseInput(wantToQuit, scanner, menuOptions);
		}
		
		scanner.close();
		
	}

	//Prints out all of the items
	private static void printMainMenu(String[] menuOptions) {
		System.out.println("Would you like to:");
		for(int i = 0; i < menuOptions.length; i++)
			System.out.printf("%-6s %s\n", "[" + (i+1) + "]", menuOptions[i].replace("" + (i+1), ""));
			//Print out the line: [#]	menuOption[i], but remove the number from menu
		
	}
	
	//Parses the user's input
	private static boolean parseInput(boolean wantToQuit, Scanner scanner, String[] menuOptions) {
		String userInput;
		String[] inputArray;
		//get the user's response
		userInput = scanner.nextLine().strip().toLowerCase();
		inputArray = userInput.split(" ");
		
		//If the user doesn't enter anything do TODO
		if (userInput.equals("")) {
			System.out.println("buh");
		}
		//Otherwise, if the user entered anything within login, do TODO
		else if(menuOptions[0].toLowerCase().contains(inputArray[0])) {
			//DEBUG
			System.out.println(menuOptions[0] + " " + inputArray[0]);
		}
		//Otherwise, if the user entered anything within "create new account" do TODO
		else if (menuOptions[1].toLowerCase().contains(inputArray[0])) {
			//DEBUG
			System.out.println(menuOptions[1] + " " + inputArray[0]);
		}
		//Otherwise, if the user enters a word to quit, end the program
		else if(menuOptions[2].concat("leave end").toLowerCase().contains(inputArray[0])) {
			//DEBUG
			System.out.println(menuOptions[2] + " " + inputArray[0]);
			wantToQuit = true;
		}
		else {
			System.out.println("I don't know what you mean by " + userInput + ".\n"
					+ "Please try entering the command again");
		}
		return wantToQuit;
	}
	
	private void login() {
		//send task off to service layer
	}
	
	private void createAccount() {
		//send task off to service layer
	}
	
}
