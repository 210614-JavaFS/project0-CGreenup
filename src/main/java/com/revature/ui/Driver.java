package com.revature.ui;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.services.ServiceData;

public class Driver {

	public static Logger log = LoggerFactory.getLogger(Driver.class);
	
	public static void main(String[] args) {
		
		boolean loggedIn = false;
		Scanner scanner = new Scanner(System.in);
		
		while (!loggedIn) {
			System.out.println("Welcome to Purity Bank!");
			System.out.println("");
			scanner.nextLine();
		}
		
	}

}
