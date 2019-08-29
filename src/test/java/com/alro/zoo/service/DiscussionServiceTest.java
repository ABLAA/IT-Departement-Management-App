package com.alro.zoo.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import com.alro.zoo.discussion.discussion.Discussion;
import com.alro.zoo.discussion.discussion.DiscussionService;
import com.alro.zoo.discussion.discussion.DisussionRepository;
import com.alro.zoo.discussion.message.MessageService;
import com.alro.zoo.login.LoginService;
import com.alro.zoo.posts.Post;
import com.alro.zoo.shared.GeneralMethods;
import com.alro.zoo.user.User;
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
	
	@Mock
	private GeneralMethods methods;
	
	private DiscussionService service;
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new DiscussionService(repo, messageService, loginService, userService);
        service.setMethods(methods);
    }
	@Test
	public void testGenerateNewCode() {
		Discussion discussion = new Discussion();
		discussion.setCode("7217679");
		//given
		when(methods.generateAnId(service.getPrefix())).thenReturn(service.getPrefix() + "7217679" ,service.getPrefix() + "7217679", service.getPrefix() + "0153698");
		when(repo.findById(service.getPrefix() + "7217679")).thenReturn(Optional.of(discussion));
		when(repo.findById(service.getPrefix() + "0153698")).thenReturn(Optional.empty());
		//when
		
		String code = service.generateNewCode();
		
		//then
		assertEquals(code, service.getPrefix() + "0153698");
		verify(methods , times(3)).generateAnId(anyString());
		verify(repo , times(3)).findById(anyString());
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testFindByIdWhenCodeNotValide() {
		//given
		String code = service.getPrefix() + "0153698";
		when(repo.findById(code)).thenReturn(Optional.empty());
		//then
		service.findById(code);
	}
	
	
	
	@Test
	public void testCreateNewMessageAndAddItToDiscussion() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testRetrieveDiscussionByCode() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testRetrieveConnectedUserDiscussion() {
		fail("Not yet implemented");
	}
	
	
	
	@Test
	public void testCreateNewDiscussionByConnectedUserAndAnOtherUser() {
		fail("Not yet implemented");
	}
	
	
	
	@Test
	public void testRetrieveDiscussionByConnectedUserAndAnOtherUser() {
		User currentUser = new User();
		
	}

}
