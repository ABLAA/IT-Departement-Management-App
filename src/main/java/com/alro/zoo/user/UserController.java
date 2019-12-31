package com.alro.zoo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alro.zoo.Login.LoginService;




@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	public UserService service;
	
	@Autowired
	public LoginService loginService;
	
	@GetMapping()
	public ResponseEntity<User> retireiveUser(){
		return service.findConnectedUser();
	}
}
