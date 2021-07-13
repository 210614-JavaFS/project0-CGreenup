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

import com.revature.models.Account;
import com.revature.models.Profile;
import com.revature.utilities.ConnectionUtil;

public class AccountDAOImpl implements AccountDAO {
	Logger log = LoggerFactory.getLogger(AccountDAOImpl.class);
	
	
	
	@Override
	public List<Account> findAll() {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM account WHERE account_maker = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			ResultSet result = statement.executeQuery(sql);
			ResultSet keys = statement.getGeneratedKeys();
			
			List<Account> list = new ArrayList<Account>();
			
			while(result.next()) {
				keys.next();
				ProfileDAOImpl implement = new ProfileDAOImpl();
				
				Profile accMaker;
				accMaker = implement.findProfile(result.getString("account_maker"));
				Account acc = new Account(
						-1,
						accMaker, 
						result.getString("account_name"), 
						result.getString("account_type"), 
						result.getDouble("balance"), 
						result.getBoolean("is_application"));
				acc.setId(findId(acc));
				list.add(acc);
			}
			
			
			return list;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		log.info("findAll successful");
		
		return null;
	}

	@Override
	public List<Account> findAll(String username) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM account WHERE account_maker = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, username);
			
			ResultSet result = statement.executeQuery();
			List<Account> list = new ArrayList<Account>();
			
			while(result.next()) {
				ProfileDAOImpl implement = new ProfileDAOImpl();
				
				Profile accMaker;
				accMaker = implement.findProfile(result.getString("account_maker"));
				Account acc = new Account(
						-1,
						accMaker, 
						result.getString("account_name"), 
						result.getString("account_type"), 
						result.getDouble("balance"), 
						result.getBoolean("is_application"));
				acc.setId(findId(acc));
				log.debug(acc.toString());
				list.add(acc);
			}
			
			
			return list;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		log.info("findAll successful");
		
		return null;
	}

	@Override
	public boolean updateAccount(Account acc, int index) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "UPDATE account SET "
					+ "account_maker = ?, "
					+ "account_name = ?, "
					+ "account_type = ?, "
					+ "balance = ?, "
					+ "account_hash = ?,"
					+ "is_application = ? "
					+ "WHERE "
					+ "account_id = ?;";	
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int statementIndex = 0;
			statement.setString(++statementIndex, acc.getOwner().getUsername());
			statement.setString(++statementIndex, acc.getAccountName());
			statement.setString(++statementIndex, acc.getAccountType());
			statement.setDouble(++statementIndex, acc.getBalance());
			statement.setInt(++statementIndex, acc.hashCode());
			statement.setBoolean(++statementIndex, acc.getIsApplication());
			statement.setInt(++statementIndex, acc.getId());
			
			statement.execute();
			
			log.debug(statement.toString());
			
			return true;
			
		}catch (SQLException e) {
			log.error("");
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean addAccount(Account acc) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO account (account_maker, account_name, account_type, balance, account_hash) "
					+ "VALUES (?, ?, ?, ?, ?);";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int index = 0;
			statement.setString(++index, acc.getOwner().getUsername());
			statement.setString(++index, acc.getAccountName());
			statement.setString(++index, acc.getAccountType());
			statement.setDouble(++index, acc.getBalance());
			statement.setInt(++index, acc.hashCode());
			
			statement.execute();
			return true;
			
			
		}catch(SQLException e) {
			log.error("Tried to update profile, ran into SQLException");
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean removeAccount(Account acc) {
		try(Connection conn = ConnectionUtil.getConnection()){
			
		}catch (SQLException e) {
			log.error("");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Account find(int accountId) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM account WHERE account_id = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setInt(1, accountId);
			
			log.debug(statement.toString());
			
			ResultSet result = statement.executeQuery();
			
			Account acc = null;
			
			//ResultSets have a cursor; like how a Scanner has a cursor
			while(result.next()) {
				ProfileDAOImpl implement = new ProfileDAOImpl();
				
				Profile accMaker;
				accMaker = implement.findProfile(result.getString("account_maker"));
				acc = new Account(
						result.getInt("account_id"),
						accMaker, 
						result.getString("account_name"), 
						result.getString("account_type"), 
						result.getDouble("balance"), 
						result.getBoolean("is_application"));		
			
			}
			
			
			return acc;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
				
		return null;
	}

	@Override
	public int findId(Account acc) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM account WHERE account_hash = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);

			int hash = acc.hashCode();
			statement.setInt(1, hash);
			ResultSet result = statement.executeQuery();
						
			if(result.next())
				return result.getInt("account_id");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public List<Account> findAllApplications() {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM account WHERE is_application = TRUE";
			
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			List<Account> list = new ArrayList<Account>();
			
			while(result.next()) {				
				ProfileDAOImpl implement = new ProfileDAOImpl();
				
				Profile accMaker;
				accMaker = implement.findProfile(result.getString("account_maker"));
				Account acc = new Account(
						result.getInt("account_id"),
						accMaker, 
						result.getString("account_name"), 
						result.getString("account_type"), 
						result.getDouble("balance"), 
						result.getBoolean("is_application"));	
				list.add(acc);
			}
			
			
			return list;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

}





//try(Connection conn = ConnectionUtil.getConnection()){
//	
//}catch (SQLException e) {
//	log.error("");
//	e.printStackTrace();
//}