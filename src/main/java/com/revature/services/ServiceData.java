package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.data.DatabaseConnector;

public class ServiceData {

	public static Logger log = LoggerFactory.getLogger(ServiceData.class);
	
	private List<Profile> allProfiles;
	private static ServiceData serviceData = null;
	
	private ServiceData() {
		allProfiles = new ArrayList<Profile>();
		initializeProfiles();
	}
	
	private void initializeProfiles() {
		//TODO
		//Get database connection
		//Put all the profiles in the list
	}
	
	public static ServiceData getServiceData() {
		if (serviceData == null) {
			log.info("created service data instance.");
			serviceData = new ServiceData();
		}
		return serviceData;
	}
	
	
}
