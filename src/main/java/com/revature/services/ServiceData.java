package com.revature.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.data.DatabaseConnector;

public class ServiceData {

	public static Logger log = LoggerFactory.getLogger(ServiceData.class);
	
	private static Map<String, Profile> allProfiles;
	private static ServiceData serviceData = null;
	private static Scanner scanner;
	
	private ServiceData() {
		allProfiles = new HashMap<String, Profile>();
		initializeProfiles();
	}
	
	private void initializeProfiles() {
		//TODO
		//Get database connection
		//Put all the profiles in the list
		//
		
		Profile profile = new Profile("dave", "DaveAcc", "pass");
		allProfiles.put(profile.getUsername(), profile);
	}
	
	public static ServiceData getServiceData() {
		if (serviceData == null) {
			log.info("created service data instance.");
			serviceData = new ServiceData();
		}
		return serviceData;
	}
	
	public static void createProfile() {
		//Open up the scanner
		scanner = new Scanner(System.in);
		String userInput;
		
		//This do-while loop ensures the username is unique
		do {
			System.out.println("Please enter a username for your profile:\n");
			userInput = scanner.nextLine().strip();
			
			//If the user input isn't just whitespace / empty, then continue
			if(userInput != "" || userInput != null) {
				//Check if the username is unique using the Map
				//If the username is in the map, it's taken
				if(allProfiles.get(userInput) != null) {
					System.out.println("That username is taken, please choose another.");
				}
			}
		} while (allProfiles.get(userInput) != null);
		
		String username = userInput;
		
		//Get a password
		System.out.println("Please enter a password for your profile:\n");
		String password = scanner.nextLine().strip();
		
		//TODO: stretch goal - validate password entering, do-while
		
		System.out.println("Finally, what is your name?\n");
		String name = scanner.nextLine().stripLeading().stripTrailing();
		
		Profile profile = new Profile(name, username, password);
		
		allProfiles.put(username, profile);
		printProfiles();
	}
	
	//DEBUG
	public static void printProfiles() {
		for (Profile p : allProfiles.values()) {
			System.out.println(p.getUsername() + " " + p.getPassword() + " " + p.getName());
		}
	}
}
