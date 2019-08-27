package com.alro.zoo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alro.zoo.login.LoginService;
import com.alro.zoo.login.dtos.SignInDTO;
import com.alro.zoo.shared.GenericService;

@Service
public class UserService extends GenericService<User, UserRepository> {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private LoginService loginService;
	
	
	
	public UserService(UserRepository repo, LoginService loginService) {
		super();
		this.repo = repo;
		this.loginService = loginService;
	}

	public UserService(UserRepository repo) {
		this.repo = repo;
	}
	
	public UserService() {

	}
	
	@Override 
	public UserRepository getRepo() {
		return repo;
	}
	
	@Override
	public String getPrefix() {
		return User.prefix;
	}
	
	public ResponseEntity<User> findConnectedUser(){
		return ResponseEntity.ok().body(loginService.getConnectedUser());
	}
	
	public User saveNewUser(User user) {
		return repo.save(user);
	}
	
	public User createUserObject(SignInDTO dto) {
		User user = new User();
		String userCode = generateNewCode();
		user.setCode(userCode);
		user.setBirthDate(dto.birthDate);
		user.setFirstName(dto.firstName);
		user.setLastName(dto.lastName);
		return user;
	}
	
}
