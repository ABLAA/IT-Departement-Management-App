package com.alro.zoo.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.alro.zoo.discussion.discussion.DiscussionService;
import com.alro.zoo.login.LoginService;
import com.alro.zoo.posts.comment.CommentRepository;
import com.alro.zoo.posts.comment.CommentService;
import com.alro.zoo.shared.GeneralMethods;

public class CommentServiceTest {

	@Mock
	private CommentRepository repo;
	
	@Mock
	private LoginService loginService;
	
	@Mock
	private GeneralMethods methods;
	
	private CommentService service;
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        service = new CommentService(repo, loginService);
    }
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
