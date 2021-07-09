package com.revature.data;

import java.util.List;

import com.revature.models.Profile;

public interface ProfileDAO {
	public List<Profile> findAll();
	public Profile findProfile(String username);
	public boolean updateProfile(Profile profile);
	public boolean addProfile(Profile profile);
}
