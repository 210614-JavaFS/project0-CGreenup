package com.revature.ui;

import java.util.Scanner;

import com.revature.services.Profile;
import com.revature.services.ServiceData;

public class profileMenu {

	public static void login(Scanner scanner) {
		ServiceData.getServiceData();
		
		Profile profile = null;
		String username = null;
		String password = null;
		
		do {
			profile = validateUserInput(scanner);
			
			System.out.println();
			
		}while (profile == null);
		
		System.out.println("Welcome, " + profile.getName().split(" ")[0] + ".");
		
		//TODO
		//PROFILE MENU AND OPTIONS
		
	}

	private static Profile validateUserInput(Scanner scanner) {
		Profile profile;
		String username;
		String password;
		do {
			System.out.println("Please enter your username:\n");
			username = scanner.nextLine().strip();
		} while (username == "");
		
		do {
			System.out.println("Please enter your password:\n");
			password = scanner.nextLine().strip();
		} while (password == "");
		
		profile = ServiceData.loginProfile(username, password);
		
		if(profile == null) {
			System.out.println("Please try again.");
		}
		return profile;
	}
	
	
}
