package com.alro.zoo.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.alro.zoo.discussion.discussion.DiscussionService;
import com.alro.zoo.discussion.discussion.DisussionRepository;
import com.alro.zoo.discussion.message.MessageService;
import com.alro.zoo.login.LoginService;
import com.alro.zoo.user.UserService;

public class DiscussionServiceTest {

	@Mock
	private DisussionRepository repo;
	
	@Mock
	private MessageService messageService;
	
	@Mock
	private LoginService loginService;
	
	@Mock
	private UserService userService;
	
	private DiscussionService service;
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        service = new DiscussionService(repo, messageService, loginService, userService);
    }
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
