package com.alro.zoo.login;


import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alro.zoo.login.dtos.JwtResponse;
import com.alro.zoo.login.dtos.LoginDTO;
import com.alro.zoo.login.dtos.SignInDTO;

@Controller
public class LoginController {
	
	
	@Autowired
	private LoginService service;
	
	@PostMapping(path = "/sign-in")
	public ResponseEntity<Login> signIn(@Valid @RequestBody(required = true) SignInDTO newUser) {
		return service.signInNewUser(newUser);
	}
	
	@PostMapping(value = "/authenticate")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@Valid @RequestBody LoginDTO authenticationRequest){
		return service.authinticateUser(authenticationRequest);
	}


}
