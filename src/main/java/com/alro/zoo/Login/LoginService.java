package com.alro.zoo.Login;



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

import com.alro.zoo.Login.dtos.JwtResponse;
import com.alro.zoo.Login.dtos.LoginDTO;
import com.alro.zoo.Login.dtos.SignInDTO;
import com.alro.zoo.configuration.jwt.JwtTokenUtil;
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
	
	private User connectedUser;
	

	public LoginService() {
		super();
	}
	public LoginService(LoginRepository repo) {
		super();
		this.repo = repo;
	}
	public LoginService(UserService userService, LoginRepository repo, JwtTokenUtil jwtTokenUtil,
			AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder) {
		super();
		this.userService = userService;
		this.repo = repo;
		this.jwtTokenUtil = jwtTokenUtil;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	public User getConnectedUser() {
		if(connectedUser!= null)
			return connectedUser;
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "There is no Connected User");
	}

	public void setConnectedUser(User connectedUser) {
		this.connectedUser = connectedUser;
	}
	
	public void setConnectedUserByLoginCode(String loginCode) {
		this.connectedUser = findById(loginCode).getUser();
	}

	@Override
	public LoginRepository getRepo() {
		return repo;
	}
	
	@Override
	public String getPrefix() {
		return Login.prefix;
	}
	
	
	
	
	public ResponseEntity<Login> signInNewUser(SignInDTO dto){
		User user = userService.createUserObject(dto);
		Login login = createLoginObject(dto);
		Login requestBody = saveUserInDataBase(user,login);
		return ResponseEntity.created(null).body(requestBody);
	}
	
	private Login createLoginObject(SignInDTO dto) {
		Login log = new Login();
		log.setPassword(passwordEncoder.encode(dto.password));
		log.setEmail(dto.email);
		log.setPseudo(dto.pseudo);
		log.setCode(generateNewCode());
		return log ;
	}
	
	private Login saveUserInDataBase(User user, Login login){
		User savedUser = userService.saveNewUser(user);
		login.setUser(savedUser);
		return tryToSaveLogin(login);
	}
	
	private Login tryToSaveLogin(Login login) {
		try {
			return repo.save(login);
		} catch (Exception e) {
			userService.getRepo().delete(login.getUser());
			handleTryToSaveLoginException(e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
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

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Login details = getLoginByEmailOrPseudo(username);
		return new org.springframework.security.core.userdetails.User(details.getCode(), details.getPassword(), new ArrayList<>());
	}
	
	private Login getLoginByEmailOrPseudo(String emailOrPseudo) throws UsernameNotFoundException {
		Optional<Login> details = repo.findOneByEmail(emailOrPseudo);
		if(!details.isPresent()) {
			details = repo.findOneByPseudo(emailOrPseudo);	
		}
		if(!details.isPresent()) {
			throw new UsernameNotFoundException("User not found with username: " + emailOrPseudo);
		}
		Login login = details.get();
		if(login.getCode() == null || login.getPassword() == null) {
			throw new UsernameNotFoundException("Login details corrupted");
		}
		return details.get();
		
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
