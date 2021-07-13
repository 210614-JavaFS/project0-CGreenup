package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.AccountTypes;
import com.revature.models.Profile;
import com.revature.utilities.ConnectionUtil;


public class ProfileDAOImpl implements ProfileDAO {

	Logger log = LoggerFactory.getLogger(ProfileDAOImpl.class);
	
	@Override
	public List<Profile> findAll() {

		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM profile";
			
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			List<Profile> list = new ArrayList<Profile>();
			
			//ResultSets have a cursor; like how a Scanner has a cursor
			while(result.next()) {
				Profile profile = new Profile();
				profile.setFirstName(result.getString("first_name"));
				profile.setLastName(result.getString("last_name"));
				profile.setUsername(result.getString("user_name"));
				profile.setPassword(result.getString("user_pass"));
				String userLevel = result.getString("user_level").toLowerCase();
				switch(userLevel) {
				case "adm":
					profile.setAccountType(AccountTypes.ADMIN);
					break;
				case "emp":
					profile.setAccountType(AccountTypes.EMPLOYEE);
					break;
				case "use":
				default:
					profile.setAccountType(AccountTypes.USER);
					break;
				}
				list.add(profile);
			}
			
			
			return list;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		log.info("findAll successful");
		
		return null;
	}

	@Override
	public Profile findProfile(String username) {
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "SELECT * FROM profile WHERE user_name = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			//This is where SQL injection is checked for.
			statement.setString(1, username);
			
			ResultSet result = statement.executeQuery();
			
			Profile profile = new Profile();
			
			//ResultSets have a cursor; like how a Scanner has a cursor
			while(result.next()) {
				profile.setFirstName(result.getString("first_name"));
				profile.setLastName(result.getString("last_name"));
				profile.setUsername(result.getString("user_name"));
				profile.setPassword(result.getString("user_pass"));
				String userLevel = result.getString("user_level").toLowerCase();
				switch(userLevel) {
				case "adm":
					profile.setAccountType(AccountTypes.ADMIN);
					break;
				case "emp":
					profile.setAccountType(AccountTypes.EMPLOYEE);
					break;
				case "use":
				default:
					profile.setAccountType(AccountTypes.USER);
					break;
				}
			
				return profile;
			}
			
		}catch(SQLException e) {
			log.error("Tried to find profile " + username +", ran into SQLException.");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Profile findProfile(String username, String password) {
try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "SELECT * FROM profile WHERE user_name = ? and user_pass = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			//This is where SQL injection is checked for.
			statement.setString(1, username);
			statement.setString(2, password);
			
			ResultSet result = statement.executeQuery();
			
			Profile profile = new Profile();
			
			//ResultSets have a cursor; like how a Scanner has a cursor
			while(result.next()) {
				profile.setFirstName(result.getString("first_name"));
				profile.setLastName(result.getString("last_name"));
				profile.setUsername(result.getString("user_name"));
				profile.setPassword(result.getString("user_pass"));
				String userLevel = result.getString("user_level").toLowerCase();
				switch(userLevel) {
				case "adm":
					profile.setAccountType(AccountTypes.ADMIN);
					break;
				case "emp":
					profile.setAccountType(AccountTypes.EMPLOYEE);
					break;
				case "use":
				default:
					profile.setAccountType(AccountTypes.USER);
					break;
				}
			
				return profile;
			}
			
		}catch(SQLException e) {
			log.error("Tried to find profile " + username +", ran into SQLException.");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean updateProfile(Profile profile, String username) {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "UPDATE profile SET "
					+ "first_name = ?, "
					+ "last_name = ?, "
					+ "user_pass = ?, "
					+ "user_level = ? "
					+ "WHERE user_name = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int index = 0;
			statement.setString(++index, profile.getFirstName());
			statement.setString(++index, profile.getLastName());
			statement.setString(++index, profile.getPassword());			
			
			switch(profile.getAccountType()) {
			case ADMIN:
				statement.setString(++index, "ADM");
				break;
			case EMPLOYEE:
				statement.setString(++index, "EMP");
				break;
			case USER:
			default:
				statement.setString(++index, "USE");
				break;
			}
			
			statement.setString(++index, profile.getUsername());
			
			//log.debug(statement.toString());
			
			statement.execute();
			
		}catch(SQLException e) {
			log.error("Tried to update profile, ran into SQLException");
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean addProfile(Profile profile) {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO profile (first_name, last_name, user_name, user_pass, user_level) "
					+ "VALUES (?, ?, ?, ?, ?);";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int index = 0;
			statement.setString(++index, profile.getFirstName());
			statement.setString(++index, profile.getLastName());
			statement.setString(++index, profile.getUsername());
			statement.setString(++index, profile.getPassword());
			
			switch(profile.getAccountType()) {
			case ADMIN:
				statement.setString(++index, "ADM");
				break;
			case EMPLOYEE:
				statement.setString(++index, "EMP");
				break;
			case USER:
			default:
				statement.setString(++index, "USE");
			}
			
			statement.execute();
			return true;
			
			
		}catch(SQLException e) {
			log.error("Tried to update profile, ran into SQLException");
			e.printStackTrace();
		}
		
		return false;
	}

	//Checks if the username is already taken within the database
	//Returns true if the username is taken
	//Returns false if the username is available to take
	@Override
	public boolean usernameTaken(String username) {
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "SELECT user_name FROM profile WHERE user_name = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			//This is where SQL injection is checked for.
			statement.setString(1, username);
			
			ResultSet result = statement.executeQuery();
			
			Profile profile = new Profile();
			
			//If anything was returned, return true
			if(result.next())
				return true;
			
		}catch(SQLException e) {
			log.error("Tried to check username, ran into SQLException.\n");
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Profile> findAllProfilesWithAccounts(){
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM profile INNER JOIN account ON account.account_maker = profile.user_name;";
			
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			List<Profile> list = new ArrayList<Profile>();
			
			//ResultSets have a cursor; like how a Scanner has a cursor
			while(result.next()) {
				Profile profile = new Profile();
				profile.setFirstName(result.getString("first_name"));
				profile.setLastName(result.getString("last_name"));
				profile.setUsername(result.getString("user_name"));
				profile.setPassword(result.getString("user_pass"));
				String userLevel = result.getString("user_level").toLowerCase();
				switch(userLevel) {
				case "adm":
					profile.setAccountType(AccountTypes.ADMIN);
					break;
				case "emp":
					profile.setAccountType(AccountTypes.EMPLOYEE);
					break;
				case "use":
				default:
					profile.setAccountType(AccountTypes.USER);
					break;
				}
				if (!list.contains(profile))
					list.add(profile);
			}
			
			
			return list;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		log.info("findAll successful");
		
		return null;		
	}


}
