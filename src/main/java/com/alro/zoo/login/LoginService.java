package com.alro.zoo.login;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.alro.zoo.configuration.jwt.JwtTokenUtil;
import com.alro.zoo.login.dtos.JwtResponse;
import com.alro.zoo.login.dtos.LoginDTO;
import com.alro.zoo.login.dtos.SignInDTO;
import com.alro.zoo.shared.GenericService;
import com.alro.zoo.user.User;
import com.alro.zoo.user.UserService;

@Service
public class LoginService extends GenericService<Login, LoginRepository> implements UserDetailsService{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private LoginRepository repo;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public LoginRepository getRepo() {
		return repo;
	}
	
	@Override
	protected String getPrefix() {
		return Login.prefix;
	}
	
	public ResponseEntity<Login> signInNewUser(SignInDTO dto){
		User user = createUserObject(dto);
		Login login = createLoginObject(dto);
		Login requestBody = saveUserInDataBase(user,login);
		return ResponseEntity.created(null).body(requestBody);
	}
	
	private User createUserObject(SignInDTO dto) {
		User user = new User();
		String userCode = userService.generateNewCode();
		user.setCode(userCode);
		user.setBirthDate(dto.birthDate);
		user.setFirstName(dto.firstName);
		user.setLastName(dto.lastName);
		return user;
	}
	
	private Login createLoginObject(SignInDTO dto) {
		Login log = new Login();
		log.setPassword(passwordEncoder.encode(dto.password));
		log.setEmail(dto.email);
		log.setPseudo(dto.pseudo);
		log.setCode(generateNewCode());
		return log ;
	}
	
	private Login saveUserInDataBase(User user, Login log){
		User savedUser = userService.getRepo().save(user);
		log.setUser(savedUser);
		return tryToSaveLogin(log);
	}
	
	private Login tryToSaveLogin(Login log) {
		try {
			return repo.save(log);
		} catch (Exception e) {
			userService.getRepo().delete(log.getUser());
			handleTryToSaveLoginException(e);
		}
		return log;
	}
	
	private void handleTryToSaveLoginException(Exception e) {
		while (! (e instanceof SQLException) & e != null ) {
			e = (Exception) e.getCause();
		}
		if (e instanceof SQLException) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	public ResponseEntity<JwtResponse> authinticateUser(LoginDTO authenticationRequest){
		authenticate(authenticationRequest.emailOrPseudo, authenticationRequest.password);
		final UserDetails userDetails = loadUserByUsername(authenticationRequest.emailOrPseudo);
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
		

	}
	
	private void authenticate(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"USER_DISABLED");
		} catch (BadCredentialsException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Unvalid Coordonates");
		}
	}
	
	
	private Login getLoginByEmailOrPseudo(String emailOrPseudo) {
		Optional<Login> details = repo.findOneByEmail(emailOrPseudo);
		if(!details.isPresent()) {
			details = repo.findOneByPseudo(emailOrPseudo);	
		}
		if(!details.isPresent()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unvalid Coordonates");
		}
		return details.get();
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Login details = getLoginByEmailOrPseudo(username);
		if(details != null) {
			return new org.springframework.security.core.userdetails.User(details.getCode(), details.getPassword(), new ArrayList<>());
		}else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
	
	public UserDetails loadUserByCode(String code) throws UsernameNotFoundException {
		Optional<Login> details = repo.findById(code);
		if(details.isPresent() ) {
			return new org.springframework.security.core.userdetails.User(details.get().getCode(), details.get().getPassword(), new ArrayList<>());
		}else {
			throw new UsernameNotFoundException("User not found with code: " + code);
		}
	}
	
}
