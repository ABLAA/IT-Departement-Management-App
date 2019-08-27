package com.alro.zoo.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.alro.zoo.discussion.discussion.DiscussionService;
import com.alro.zoo.discussion.discussion.DisussionRepository;
import com.alro.zoo.discussion.message.MessageRepository;
import com.alro.zoo.discussion.message.MessageService;
import com.alro.zoo.login.LoginService;
import com.alro.zoo.user.UserService;

public class MessageServiceTest {

	@Mock
	private MessageRepository repo;
	
	@Mock
	private LoginService loginService;
	
	private MessageService service;
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        service = new MessageService(repo, loginService);
    }
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
