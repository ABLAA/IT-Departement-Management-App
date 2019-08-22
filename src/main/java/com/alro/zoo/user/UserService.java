package com.alro.zoo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alro.zoo.shared.GenericService;

@Service
public class UserService extends GenericService<User, UserRepository> {
	
	@Autowired
	private UserRepository repo;
	
	public UserService(UserRepository repo) {
		// TODO Auto-generated constructor stub
	}
	
	@Override 
	public UserRepository getRepo() {
		return repo;
	}
	
	@Override
	public String getPrefix() {
		return User.prefix;
	}
	
	
	
}
