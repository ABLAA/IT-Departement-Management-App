package com.alro.zoo.login;



import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.alro.zoo.shared.GeneralMethods;
import com.alro.zoo.user.User;
import com.alro.zoo.user.UserRepository;

@Service
public class LoginService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private GeneralMethods methods;

	@Autowired
	private LoginRepository repo;
	
	@Autowired
	private UserRepository userRepo;
	
	
	
	public ResponseEntity<Login> signInNewUser(SignInDTO dto){
		User user = createUserObject(dto);
		Login login = createLoginObject(dto);
		Login requestBody = saveUserInDataBase(user,login);
		return ResponseEntity.created(null).body(requestBody);
	}
	
	private User createUserObject(SignInDTO dto) {
		User user = new User();
		String userCode = methods.generateAnId(User.prefix);
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
		log.setCode(methods.generateAnId(Login.prefix));
		return log ;
	}
	
	private Login saveUserInDataBase(User user, Login log){
		User savedUser = userRepo.save(user);
		log.setUser(savedUser);
		return tryToSaveLogin(log);
	}
	
	private Login tryToSaveLogin(Login log) {
		try {
			return repo.save(log);
		} catch (Exception e) {
			userRepo.delete(log.getUser());
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
	
	
	public ResponseEntity<Login> authinticateUser(LoginDTO loginCoordiantes){
		Login login = getLoginByEmailOrPseudo(loginCoordiantes.emailOrPseudo);
		if (passwordEncoder.matches(loginCoordiantes.password, login.getPassword())) 
			return ResponseEntity.accepted().body(login);
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, " unvalid cordinates");
	}
	
	private Login getLoginByEmailOrPseudo(String emailOrPseudo) {
		Optional<Login> login = repo.findOneByPseudo(emailOrPseudo);
		if (! login.isPresent())
			login = repo.findOneByEmail(emailOrPseudo);
		if (! login.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, " unvalid cordinates");
		return login.get(); 
	}
	
}
