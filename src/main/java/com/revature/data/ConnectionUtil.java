package com.revature.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	public static Connection getConnection() throws SQLException {
		
//		 For many frameworks using JDBC it is necessary to "register" the driver
//		 in order for the framework to be aware of it
		 try {
			Class.forName("org.postgresql.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url = "jdbc:postgresql://training-db.cfhaaft3h8hf.us-east-2.rds.amazonaws.com:5432/postgres";
		String username = "postgres";
		String password = "password";
		
		return DriverManager.getConnection(url, username, password);
		
	}
	
	public static void main(String[] args) {
		try (Connection con = ConnectionUtil.getConnection()){
			System.out.println("Connection Successful");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
