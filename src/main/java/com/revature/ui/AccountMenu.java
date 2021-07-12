package com.revature.ui;

import java.util.List;
import java.util.Scanner;

import com.revature.models.Account;
import com.revature.models.Profile;
import com.revature.services.AccountServiceData;

public class AccountMenu {
	private static Scanner scanner = new Scanner(System.in);
	
	//TODO
	public void manageAccounts(Profile profile) {
		AccountServiceData.getServiceData();
		final int spacing = 5;
		System.out.println("Here are all your accounts");
		System.out.printf("%-"+spacing+"s %-"+ spacing*2 +"s %-" + spacing*3 +"s %s \n",
				"#", "Balance" , "Account Type", "Account Name" );
		System.out.println("====================================================");
		
		List<Account> userAccounts = AccountServiceData.getConnectedAccounts(profile);
		
		for(int i = 0; i < userAccounts.size(); i++){
			Account acc = userAccounts.get(i);
			System.out.printf("%-"+spacing+"s %-"+ spacing*2 +"s %-" + spacing*3 +"s %s \n", (i+1) + ")", acc.getBalance(), acc.getAccountType(), acc.getAccountName());
		}
		
		if(userAccounts.size() <= 0) {
			System.out.println("Sorry, we could not find any accounts under your details.\n"
							+ "You can always apply for a bank account, and any approved bank accounts will appear here.");
		}
		
		System.out.println();
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
			}while (userInput.equals(""));
			
			account.setAccountType(userInput);
			
			
			System.out.println("What would you like to name this account?");
			userInput = "";
			do {
				userInput = scanner.nextLine().strip();
			}while(userInput.equals(""));
			
			account.setAccountName(userInput);
			
			validAccount = AccountServiceData.applyForAccount(account);
			
		}while(!validAccount);
		
	}
}
