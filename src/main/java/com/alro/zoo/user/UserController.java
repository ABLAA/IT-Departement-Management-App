package com.alro.zoo.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alro.zoo.user.dtos.SignInDTO;





@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	public UserService service;
	@CrossOrigin
	@PostMapping(path = "/sign-in")
	public ResponseEntity<User> signIn(@Valid @RequestBody(required = true) SignInDTO newUser) {
		return ResponseEntity.ok().body(service.createUserObject(newUser));
	}
}
