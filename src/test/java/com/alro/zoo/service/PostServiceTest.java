package com.alro.zoo.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.alro.zoo.login.LoginService;
import com.alro.zoo.posts.PostRepository;
import com.alro.zoo.posts.PostService;
import com.alro.zoo.posts.comment.CommentService;
import com.alro.zoo.user.UserService;

public class PostServiceTest {

	@Mock
	private PostRepository repo;
	
	@Mock
	private CommentService commentService;
	
	@Mock
	private LoginService loginService;
	
	@Mock
	private UserService userService;
	
	private PostService service;
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        service = new PostService(repo, loginService, userService, commentService);
    }
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
