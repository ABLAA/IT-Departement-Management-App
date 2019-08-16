package com.alro.zoo.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;




@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository repository;
	
	@PostMapping(path = "")
	public @ResponseBody User insertUser(@RequestBody User newUser) {
		repository.save(newUser);
		return newUser;
	}
	
	@GetMapping()
	public @ResponseBody List<User> retireiveAllUsers(){
		return repository.findAll();
	}
}
