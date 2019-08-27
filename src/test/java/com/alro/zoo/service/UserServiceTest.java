package com.alro.zoo.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.alro.zoo.discussion.discussion.DiscussionService;
import com.alro.zoo.login.LoginService;
import com.alro.zoo.user.UserRepository;
import com.alro.zoo.user.UserService;

public class UserServiceTest {
	@Mock
	private UserRepository repo;
	
	@Mock
	private LoginService loginService;
	
	private UserService service;
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        service = new UserService(repo, loginService);
    }

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
