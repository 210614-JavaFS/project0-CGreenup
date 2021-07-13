package com.revature.services;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.data.AccountDAOImpl;
import com.revature.models.Account;
import com.revature.models.Profile;

public class AccountServiceData {
	private static AccountServiceData serviceData = null;
	private static AccountDAOImpl aImplement = new AccountDAOImpl();;
	private static Logger log = LoggerFactory.getLogger(AccountServiceData.class);
	
	
		
	private AccountServiceData() {
		super();
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
	//Returns the new balance after depositing the funds
	//Returns -1 if an error occurred
	public static double depositFunds(Account account, double funds) {
		try {
			account.changeBalance(funds);
			aImplement.updateAccount(account, account.getId());
			log.info(account.getOwner() + " successfully desposited funds to account");
			return account.getBalance();
		} catch (NegativeAccountException e) {

			log.error("Could not fulfill " + account.getOwner() + "'s deposit request");
			e.printStackTrace();
		}
		return -1;
	}
	
	//Withdraw
	//Returns the new balance after depositing the funds
	//Returns -1 if an error occurred
	public static double withdrawFunds(Account account, double funds) {
		try {
			account.changeBalance(-funds);
			account.changeBalance(0);
			aImplement.updateAccount(account, account.getId());
			log.info(account.getOwner() + " successfully withdrew funds from account");
			return account.getBalance();
		} catch (NegativeAccountException e) {
			log.error("Could not fulfill " + account.getOwner() + "'s withdraw request");
		}
		return -1;
	}
	
	//Transfer Funds
	//Deposit + withdraw
	public static boolean transferFunds(Account from, Account to, double funds) {
		double result;
		log.info("Withdrawing $" + funds + " from " + from.toString());
		result = withdrawFunds(from, funds);
		if(result == -1){
			log.error("Tranfer Failed");
			return false;
		}
		log.info("Depositing $" + funds + " from " + from.toString());
		result = depositFunds(to, funds);
		if(result == -1) {
			log.error("Transfer Failed");
			return false;
		}
		
		return true;
		
	}

	//Approve deny applications
	public static boolean acceptApplication(Account account, Boolean isAccepted) {
		if(aImplement.findId(account) != 0) {
			log.debug("Account is valid");
			if(isAccepted) {
				account.setIsApplication(false);
				aImplement.updateAccount(account, account.getId());
				System.out.println("Account approved!");
				log.info("Application for account approved");
			}else {
				System.out.println("Account denied");
				log.info("Application for accound denied. Deleting account.");
				aImplement.removeAccount(account);
			}
			
		}
		
		return false;
	}
	
	
	//Delete Account
	public static boolean removeAccount(Account account) {
		log.info("Removing Account: " + account.toString());
		if(aImplement.findId(account) != 0) {
			aImplement.removeAccount(account);
			log.info("Account Removed successful");
			return true;
		}
		
		log.info("Account removal failed: no such account exists in the database");
		return false;
	}
	
	
	//debug
	public static void printAllAccounts(){
		for(Account i : aImplement.findAll()) {
			System.out.println(i.toString());
		}
	}
	
	public static boolean deleteAccount(Account account) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Are you sure you want to delete this account? (y/n)");
		String userInput = "";
		do {
			userInput = scanner.nextLine().strip().toLowerCase();
		}while(!"yes".contains(userInput) && !"no".contains(userInput));
		
		if(userInput.equals("no")) {
			log.info("admin did not delete account");
			return false;
		}else {
			log.info("Admin decided to delte account: " + account.toString());
			return aImplement.removeAccount(account);
			
			
		}
		
	}
	
	public static List<Account> getApplications(){
		return aImplement.findAllApplications();
	}
	
	
}
