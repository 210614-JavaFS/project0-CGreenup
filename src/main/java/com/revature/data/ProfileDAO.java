package com.revature.data;

import java.util.List;

import com.revature.models.Profile;

public interface ProfileDAO {
	public List<Profile> findAll();
	public Profile findProfile(String username);
	public Profile findProfile(String username, String password);
	public boolean updateProfile(Profile profile);
	public boolean addProfile(Profile profile);
	public boolean usernameTaken(String username);
}
