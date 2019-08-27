package com.alro.zoo.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.alro.zoo.configuration.jwt.JwtTokenUtil;
import com.alro.zoo.login.LoginRepository;
import com.alro.zoo.login.LoginService;
import com.alro.zoo.user.UserService;

public class LoginServiceTest {

	@Mock
	private BCryptPasswordEncoder passwordEncoder;
	
	@Mock 
	private UserService userService;
	
	@Mock
	private LoginRepository repo;
	
	@Mock
	private JwtTokenUtil jwtTokenUtil;
	
	@Mock
	private AuthenticationManager authenticationManager;
	
	private LoginService service;
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        service = new LoginService(userService, repo, jwtTokenUtil, authenticationManager);
    }
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
