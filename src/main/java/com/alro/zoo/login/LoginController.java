package com.alro.zoo.login;

import java.sql.SQLException;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.alro.zoo.shared.GeneralMethods;
import com.alro.zoo.user.User;
import com.alro.zoo.user.UserRepository;

@Controller
public class LoginController {
	
	
	@Autowired
	private LoginService service;
	
	@PostMapping(path = "/sign-in")
	public ResponseEntity<Login> signIn(@Valid @RequestBody(required = true) SignInDTO newUser) {
		return service.signInNewUser(newUser);

	}
	
	@PostMapping(path = "/login")
	public ResponseEntity<Login> login(@Valid @RequestBody LoginDTO dto) {
		
		return service.authinticateUser(dto);
		
		
	}
	


}
