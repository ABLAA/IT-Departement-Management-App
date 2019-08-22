package com.alro.zoo.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alro.zoo.login.LoginService;




@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	public UserRepository repository;
	
	@Autowired
	public LoginService loginService;
	
	@PostMapping(path = "")
	public @ResponseBody User insertUser(@Valid @RequestBody User newUser) {
		repository.save(newUser);
		return newUser;
	}
	
	@GetMapping()
	public @ResponseBody User retireiveUser(){
		return loginService.getConnectedUser();
	}
}
