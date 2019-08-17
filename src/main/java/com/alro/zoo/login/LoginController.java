package com.alro.zoo.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alro.zoo.shared.GeneralMethods;
import com.alro.zoo.user.User;
import com.alro.zoo.user.UserRepository;

@Controller
public class LoginController {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private GeneralMethods methods;
	
	@Autowired
	private LoginRepository repo;
	
	@Autowired
	private UserRepository userRepo;
	
	@PostMapping(path = "/sign-in")
	public @ResponseBody LoginDTO signIn(@RequestBody LoginDTO newUser) {
		User user = new User();
		int id = methods.generateAnId(null);
		user.setId(id);
		user.setBirthDate(newUser.birthDate);
		user.setEmail(newUser.email);
		user.setFirstName(newUser.firstName);
		user.setLastName(newUser.lastName);
		userRepo.save(user);
		Login log = new Login();
		log.setUserId(id);
		log.setPassword(passwordEncoder.encode(newUser.password));
		repo.save(log);
		newUser.password = log.getPassword();
		return newUser;
	}
	


}
