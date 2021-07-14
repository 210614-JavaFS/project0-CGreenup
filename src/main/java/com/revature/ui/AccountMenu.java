package com.revature.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.models.Account;
import com.revature.models.Profile;
import com.revature.services.AccountServiceData;

public class AccountMenu {
	private static Scanner scanner = new Scanner(System.in);
	
	public static void manageAccounts(Profile profile) {
		AccountServiceData.getServiceData();
		List<Account> connectedAccounts = printAccounts(profile);
		Account managedAccount;
		
		if(connectedAccounts.size() == 0)
			return;
		
		System.out.println("Select the number of the account you would like to manage, or cancel to quit.");
		System.out.println();
		String userInput;
		int userInt;
		do {
			userInput = scanner.nextLine().strip();
			if (!"cancel quit leave exit".contains(userInput)) {
				try {
					userInt = Integer.parseInt(userInput);
					
					if (userInt > connectedAccounts.size() || userInt < 1) {
						throw new NumberFormatException();
					}
					
					//Input validation complete
					//The user does not want to leave
					//And the user selected an account within range
					
					managedAccount = connectedAccounts.get(userInt - 1);
					
					if(!managedAccount.getIsApplication()) {
						List<Account> validAccounts = getValidAccounts(connectedAccounts);
						transactionMenu(managedAccount, validAccounts);
						
					}
					else {
						System.out.println("This account is still pending approval.");
					}
				}
				catch(NumberFormatException e) {
					System.out.println("That is not a valid choice.");
				}

				
				
				
			}
		} while (!"cancel quit leave exit".contains(userInput));
	}


	private static void transactionMenu(Account managedAccount, List<Account> validAccounts) {
		List<String> menuOptions = printTransactionMenu(validAccounts);
		String transactionInput;
		do {
			transactionInput = scanner.nextLine().strip();
			
		}while(transactionInput.equals(""));
		
		for(String s : menuOptions) {
			if (s.toLowerCase().contains(transactionInput)) {
				if(s.contains("Transfer")) {
					transfer(managedAccount, validAccounts);
					break;
				}
				else if(s.contains("Deposit")) {
					deposit(managedAccount);
					break;
				}
				else if(s.contains("Withdraw")) {
					withdraw(managedAccount);
					break;
				}
			}
		}
	}


	private static List<String> printTransactionMenu(List<Account> validAccounts) {
		List<String> menuOptions = new ArrayList<String>();
		menuOptions.add("Deposit");
		menuOptions.add("Withdraw");
		if(validAccounts.size() >= 2)
			menuOptions.add("Transfer");
		menuOptions.add("Exit");
		
		for(int i = 0; i < menuOptions.size(); i++) {
			menuOptions.set(i, "[" + (i+1) + "]   " + menuOptions.get(i));
			System.out.println(menuOptions.get(i));
		}
		return menuOptions;
	}


	private static List<Account> getValidAccounts(List<Account> accounts) {
		List<Account> temp = new ArrayList<Account>();
		
		for(Account a : accounts) {
			if (!a.getIsApplication()) {
				temp.add(a);
			}
		}
		
		return temp;
	}
	
	public void applyForAccount(Profile profile) {
		
		AccountServiceData.getServiceData();
		Account account = new Account();
		account.setOwner(profile);
		
		boolean validAccount = false;
		do {
			System.out.println("What kind of bank account would you like to create?");
			String userInput = "";
			do {
				userInput = scanner.nextLine().strip();
				if (userInput.length() > 10)
					userInput = "";
			}while (userInput.equals(""));
			
			account.setAccountType(userInput);
			
			
			System.out.println("What would you like to name this account?");
			userInput = "";
			do {
				userInput = scanner.nextLine().strip();
			}while(userInput.equals(""));
			
			if (userInput.length() > 50)
				userInput.subSequence(0, 10);
			
			account.setAccountName(userInput);
			
			validAccount = AccountServiceData.applyForAccount(account);
			
		}while(!validAccount);
		
	}
	
	private static List<Account> printAccounts(Profile profile) {
		final int spacing = 5;
		System.out.println("Here are all your accounts");
		System.out.printf("%-"+spacing+"s %-"+ spacing*2 +"s %-" + spacing*3 +"s %s \n",
				"#", "Balance" , "Account Type", "Account Name" );
		System.out.println("====================================================");
		
		List<Account> userAccounts = AccountServiceData.getConnectedAccounts(profile);
		
		for(int i = 0; i < userAccounts.size(); i++){
			Account acc = userAccounts.get(i);
			String varString;
			if(userAccounts.get(i).getIsApplication())
				varString = "(PENDING)";
			else
				varString = userAccounts.get(i).getAccountType();
			System.out.printf("%-"+spacing+"s %-"+ spacing*2 +"s %-" + spacing*3 +"s %s \n", (i+1) + ")", acc.getBalance(), varString, acc.getAccountName());
		}
		
		if(userAccounts.size() <= 0) {
			System.out.println("Sorry, we could not find any accounts under your details.\n"
							+ "You can always apply for a bank account, and any approved bank accounts will appear here.");
		}
		
		return userAccounts;
	}
	
	private static void deposit(Account account) {
		System.out.println("How much would you like to deposit into this account?");
		double funds = 0;
		boolean validInput = false;
		double newBalance;
		do {
			try {
				funds = scanner.nextDouble();
				
				if(funds < 0)
					throw new NumberFormatException("Input cannot be negative");
				validInput = true;
			}catch(Exception e) {
				System.out.println("Invalid Input");
				scanner = new Scanner(System.in);
			}
		}while(!validInput);
		AccountServiceData.getServiceData();
		newBalance = AccountServiceData.depositFunds(account, funds);
		if(newBalance == -1) {
			System.out.println("Deposit did not complete.");
		}else {
			System.out.println("This account's new balance is " + newBalance);
		}
	}
	
	private static void withdraw(Account account) {
		System.out.println("How much would you like to withdraw from this account?");
		double funds = 0;
		boolean validInput = false;
		double newBalance;
		do {
			try {
				funds = scanner.nextDouble();
				
				if(funds < 0)
					throw new NumberFormatException("Input cannot be negative");
				validInput = true;
			}catch(Exception e) {
				System.out.println("Invalid Input");
				scanner = new Scanner(System.in);
			}
		}while(!validInput);
		AccountServiceData.getServiceData();
		newBalance = AccountServiceData.withdrawFunds(account, funds);
		if(newBalance == -1) {
			System.out.println("Withdraw did not complete.");
		}else {
			System.out.println("This account's new balance is " + newBalance);
		}
	}
	
	private static void transfer(Account account, List<Account> validAccounts) {
		validAccounts.remove(account);
		
		System.out.println("Please select the number corresponding to the account you would like to transfer money to:");
		
		final int spacing = 5;		
		
		for(int i = 0; i < validAccounts.size(); i++){
			Account acc = validAccounts.get(i);
			String varString;
			varString = validAccounts.get(i).getAccountType();
			System.out.printf("%-"+spacing+"s %-"+ spacing*2 +"s %-" + spacing*3 +"s %s \n", (i+1) + ")", acc.getBalance(), varString, acc.getAccountName());
		}
		
		int userIntput = 0;
		
		boolean validInput = false;
		do {
			try {
				userIntput = scanner.nextInt();
				
				if(userIntput > validAccounts.size() || userIntput < 1) {
					throw new NumberFormatException("That number is too big or small");
				}
				validInput=true; 
			}catch(NumberFormatException e) {
				System.out.println("That isn't a valid option");
			}
		}while(!validInput);
		
		Account transferToAccount = validAccounts.get(userIntput-1);
		
		System.out.println("How much money would you like to transfer to that account?");
		double funds = 0;
		validInput = false;
		double newBalance;
		do {
			try {
				funds = scanner.nextDouble();
				
				if(funds < 0)
					throw new NumberFormatException("Input cannot be negative");
				validInput = true;
			}catch(NumberFormatException e) {
				System.out.println("Invalid Input");
			}
		}while(!validInput);
		
		AccountServiceData.getServiceData();
		if( AccountServiceData.transferFunds(account, transferToAccount, funds)) {
			System.out.println("Transfer successful.");
		}else {
			System.out.println("Transfer not successful.");
		}
		
	}
}
