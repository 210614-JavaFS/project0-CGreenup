package com.revature.ui;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Account;
import com.revature.models.AccountTypes;
import com.revature.models.Profile;
import com.revature.services.AccountServiceData;
import com.revature.services.ProfileServiceData;

public class EmployeeAdminMenu {
	private static Logger log = LoggerFactory.getLogger(EmployeeAdminMenu.class);
	
	public static void menu(Profile profile) {
		List<String> menuOptions = new ArrayList<String>();
		
		Scanner scanner = new Scanner(System.in);
		
		if(profile.getAccountType() == AccountTypes.ADMIN){
			menuOptions.add("Manage A User's Bank Accounts");
			menuOptions.add("Transfer funds between Users' Accounts");
			menuOptions.add("Cancel Accounts");
		}
		menuOptions.add("Approve/Deny Applications");
		menuOptions.add("View Profiles");
		menuOptions.add("Log Out");
		menuOptions.add("Quit");
		
		for(int i = 0; i < menuOptions.size(); i++) {
			menuOptions.set(i, (i+1) + menuOptions.get(i));
		}
		
		

		String userInput;
		boolean validInput = false;
		
		do {
			scanner = new Scanner(System.in);
			printMenuOptions(menuOptions);
			userInput = scanner.nextLine().strip().toLowerCase();
			if(!userInput.equals(""))
				userInput = userInput.split(" ")[0];
			
			
			for(String s: menuOptions) {
				if(s.toLowerCase().contains(userInput)) {
					if(s.contains("Manage")) {
						AccountMenu.manageAccounts(selectProfile("Enter the username of the accounts"));
						
					}
					else if(s.contains("Transfer funds between")) {
						crossProfileTransfer();
						break;
					}
					else if(s.contains("Cancel")) {
						
						deleteAccount();
						break;
					}
					else if(s.contains("Applications")) {
						
						break;
					}
					else if(s.contains("View")) {
						System.out.println("view");
						break;
					}
					else if(s.contains("Quit")) {
						log.info("Program ended cleanly\n");
						log.info("===============================================");
						System.out.println("Exiting Program");
						System.exit(0);
					}
					else if(s.contains("Log Out")){
						validInput = true;
					}
				}
			}
			
		}while(!validInput);
		
		 
	}

	private static void deleteAccount() {
		Account accountToDelete = selectAccount("Enter the username of the account to delete");
		AccountServiceData.getServiceData();
		if( AccountServiceData.deleteAccount(accountToDelete)) {
			System.out.println("Account deleted successfully");
		}else {
			System.out.println("Account deletion canceled");
		}
	}

	private static void crossProfileTransfer() {
		Scanner scanner;
		Account fromAccount = selectAccount("Enter the username of the withdrawing account");
		Account toAccount = selectAccount("Enter the username of the depositing account");
		
		while(true) {
			System.out.println("How much would you like to transfer?");
			try{
				scanner = new Scanner(System.in);
				double funds = scanner.nextDouble();
				
				if(funds < 0) {
					throw new InputMismatchException();
				}
				
				if(fromAccount != null && toAccount != null) {
					if( AccountServiceData.transferFunds(fromAccount, toAccount, funds)) {
						System.out.println("Transfer Successful");
						log.info("Transfer between accounts successful");
					}else {
						System.out.println("Transfer Failed");
						log.error("Transfer between accounts failed");
					}
				}else {
					log.error("One of the accounts in transfer is null");
				}
				
				return;
			}catch(InputMismatchException e) {
				System.out.println("That is not a valid amount of money");
			}
			
		}
		
		 
	}

	private static void printMenuOptions(List<String> input) {
		
		for(int i = 0; i < input.size(); i++) {
			System.out.println("[" + (i+1) + "]\t" + input.get(i).replace(Integer.toString(i+1), ""));
		}
	}
	
	private static Profile selectProfile(String prompt) {
		log.debug("SelectProfile Started");
		Profile profile = null;
		ProfileServiceData.getServiceData();
		List<Profile> allProfiles = ProfileServiceData.getAllProfilesWithAccounts();
		
		System.out.println(prompt);
		
		printAllProfiles(allProfiles);
		
		
		//getUserInput
		Scanner scanner = new Scanner(System.in);
		String userInput = null;
		
		boolean validInput = false;
		while(!validInput) {
			userInput = scanner.nextLine().toLowerCase().strip();
			if(userInput.length() > 0) {
				userInput = userInput.split(" ")[0];
			}			
			int test;
			if(userInput.equals("")) {
				System.out.println("Please enter a username.");
			}
			else if(userInput.matches("\\d+")) {
				test = Integer.parseInt(userInput);
				if(test <= allProfiles.size() && test > 0) {
					return allProfiles.get(test - 1);
				}
				else {
					System.out.println("That number is not within the range, please try again.");
				}
			}
			else if(ProfileServiceData.checkUsername(userInput)) {
				validInput = true;
			}else {
				System.out.println("That username was not found, please try again.");
			}
		}
		
		
		
		ProfileServiceData.getServiceData();
		profile = ProfileServiceData.findProfile(userInput);
		
		 
		return profile;
		
		
		
	}

	private static void printAllProfiles(List<Profile> allProfiles) {
		System.out.printf("%-5s %-17s %s\n", 
				"[#]", 
				"Full Name",
				"Username");
		System.out.println("=".repeat(5+1+17+1+12));
		
		for(int i = 0; i < allProfiles.size(); i++) {
			Profile p = allProfiles.get(i);
			System.out.printf("%-5s %-17s %s\n", 
					"[" + (i+1) + "]", 
					p.getFirstName() + " " + p.getLastName(),
					p.getUsername());
		}
	}
	
	private static Account selectAccount(String prompt) {
		Account selectedAccount = null;
		AccountServiceData.getServiceData();
		Profile profile = selectProfile(prompt);
		List<Account> allAccounts = AccountServiceData.getConnectedAccounts(profile);
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Select the number corresponding to the account:");
		printAccounts(allAccounts);
		
		//getuserInput
		boolean validInput = false;
		int userInput = -1;
		while(!validInput) {
			try {;
				userInput = Integer.parseInt(scanner.nextLine());
				if(userInput < 1 || userInput > allAccounts.size()) {
					throw new NumberFormatException();
				}
				validInput = true;
				
			}catch(NumberFormatException e) {
				System.out.println("That isn't a valid number, please select a valid number on screen.");
			}
		}
		
		selectedAccount = allAccounts.get(userInput-1);
		
		 
		log.debug("Finished SelectAccount in EmployeeAdminMenu: account:" + selectedAccount.toString());
		return selectedAccount;
	}

	private static void printAccounts(List<Account> allAccounts) {
		final int spacing = 5;
		
		for(int i = 0; i < allAccounts.size(); i++){
			Account acc = allAccounts.get(i);
			String varString;
			if(allAccounts.get(i).getIsApplication())
				varString = "(PENDING)";
			else
				varString = allAccounts.get(i).getAccountType();
			System.out.printf("%-"+spacing+"s %-"+ spacing*2 +"s %-" + spacing*3 +"s %s \n", (i+1) + ")", acc.getBalance(), varString, acc.getAccountName());
		}
	}

	
}
