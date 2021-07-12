package com.revature.ui;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.Profile;
import com.revature.services.AccountServiceData;

public class AccountMenu {
	
	//TODO
	public void manageAccounts(Profile profile) {
		AccountServiceData.getServiceData();
		final int spacing = 5;
		System.out.println("Here are all your accounts");
		System.out.println("========================================");
		
		List<Account> userAccounts = AccountServiceData.getConnectedAccounts(profile);
		
		System.out.printf("%-"+spacing+"s %-"+ spacing*2 +"s %-" + spacing*3 +"s %s \n", "#", "Balance" , "Account Type", "Account Name" );
		
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
		
	}
}
