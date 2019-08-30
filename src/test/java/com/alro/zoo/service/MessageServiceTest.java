package com.alro.zoo.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.mockito.AdditionalMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import com.alro.zoo.discussion.discussion.Discussion;
import com.alro.zoo.discussion.discussion.DiscussionService;
import com.alro.zoo.discussion.discussion.DisussionRepository;
import com.alro.zoo.discussion.message.Message;
import com.alro.zoo.discussion.message.MessageRepository;
import com.alro.zoo.discussion.message.MessageService;
import com.alro.zoo.login.LoginService;
import com.alro.zoo.shared.GeneralMethods;
import com.alro.zoo.user.User;
import com.alro.zoo.user.UserService;

public class MessageServiceTest {

	@Mock
	private MessageRepository repo;
	
	@Mock
	private LoginService loginService;
	
	@Mock
	private GeneralMethods methods;
	
	private MessageService service;
	
	private AdditionalMatchers AdditionalMatchers;
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        service = new MessageService(repo, loginService);
        
        service.setMethods(methods);
    }
	
	@Test
	public void testGenerateNewCode() {
		Message message = new Message();
		message.setCode(service.getPrefix() + "7217679");
		//given
		when(methods.generateAnId(service.getPrefix())).thenReturn(service.getPrefix() + "7217679",service.getPrefix() + "7217679" ,service.getPrefix() + "7217679",service.getPrefix() + "7217679", service.getPrefix() + "0153698");
		when(repo.findById(service.getPrefix() + "7217679")).thenReturn(Optional.of(message));
		when(repo.findById(service.getPrefix() + "0153698")).thenReturn(Optional.empty());
		//when
		
		String code = service.generateNewCode();
		
		//then
		assertEquals(code, service.getPrefix() + "0153698");
		verify(methods , times(5)).generateAnId(anyString());
		verify(repo , times(5)).findById(anyString());
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
	public void test() {
		//given
		String messageCode = service.getPrefix() + "7217679";
		when(methods.generateAnId(service.getPrefix())).thenReturn(messageCode);
		
		User user = new User();
		user.setCode("currentUser");
		when(loginService.getConnectedUser()).thenReturn(user);
		
		Discussion discussion = new Discussion();
		discussion.setCode("postCode");
		
		when(repo.save(any(Message.class))).thenAnswer(i -> (Message)i.getArgument(0));
		//when
		String message = "hello";
		Message result = service.createNewMessge(message, discussion);
		//then
		assertEquals(result.getAuthor(), user);
		assertEquals(result.getCode(), messageCode);
		assertEquals(result.getMessage(), message);
		assertEquals(result.getDiscussion(), discussion);
		
	}

}
