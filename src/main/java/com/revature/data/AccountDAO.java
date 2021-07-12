package com.revature.data;

import java.util.List;

import com.revature.models.Account;

public interface AccountDAO {
	public List<Account> findAll();
	public List<Account> findAll(String username);
	public Account find(int accountId);
	public int findId(Account acc);
	public boolean updateAccount(Account acc, int index);
	public boolean addAccount(Account acc);
	public boolean removeAccount(Account acc);
	
	public List<Account> findAllApplications();
}
