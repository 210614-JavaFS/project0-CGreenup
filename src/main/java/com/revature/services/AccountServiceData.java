package com.revature.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.data.AccountDAOImplement;
import com.revature.models.Account;
import com.revature.models.Profile;

public class AccountServiceData {
	private static AccountServiceData serviceData = null;
	private static AccountDAOImplement aImplement;
	private static Logger log = LoggerFactory.getLogger(AccountServiceData.class);
	
	
		
	private AccountServiceData() {
		super();
		aImplement = new AccountDAOImplement();
	}
	
	public static AccountServiceData getServiceData() {
		if (serviceData == null) {
			log.info("created account service data instance.");
			serviceData = new AccountServiceData();
		}
		return serviceData;
	}

	//apply for account
	//TODO
	public static boolean applyForAccount(Account account) {
		log.info("User Applied for account");
		if(aImplement.findId(account) == 0) {
			aImplement.addAccount(account);
			log.info("Account application successful");
			return true;
		}
		log.error("Account Application unsuccessful");
		return false;
	}
	
	
	//Look at accounts
	public static List<Account> getConnectedAccounts(Profile profile) {
		log.info("User: " + profile.getUsername() + " looked at all their accounts.\ngetConnectedAccounts() called in AccountService.java");
		List<Account> allAccounts = aImplement.findAll(profile.getUsername());
		
		return allAccounts;
	}
	
	
	//Deposit
	//Withdraw
	
	//Transfer Funds
	//Deposit + withdraw

	//Approve deny applications
	//
	
	
	
	
	
	
	
	
	
	//debug
	public static void printAllAccounts(){
		for(Account i : aImplement.findAll()) {
			System.out.println(i.toString());
		}
	}
}
