package com.revature.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.AccountTypes;
import com.revature.models.Profile;

public class ProfileDAOImplement implements ProfileDAO {

	Logger log = LoggerFactory.getLogger(ProfileDAOImplement.class);
	
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
		
		
		
		return null;
	}

	@Override
	public boolean updateProfile(Profile profile) {
		
		
		
		return false;
	}

	@Override
	public boolean addProfile(Profile profile) {
		
		
		
		return false;
	}

}
