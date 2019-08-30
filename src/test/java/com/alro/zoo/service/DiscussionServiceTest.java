package com.alro.zoo.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;
import static org.mockito.ArgumentMatchers.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import com.alro.zoo.discussion.discussion.Discussion;
import com.alro.zoo.discussion.discussion.DiscussionService;
import com.alro.zoo.discussion.discussion.DisussionRepository;
import com.alro.zoo.discussion.message.Message;
import com.alro.zoo.discussion.message.MessageService;
import com.alro.zoo.discussion.message.DTO.newMessageRequestDto;
import com.alro.zoo.login.LoginService;
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
	
	private AdditionalMatchers AdditionalMatchers;
	
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
		String discussionCode = service.getPrefix() + "7217679";
		newMessageRequestDto dto = new newMessageRequestDto();
		dto.message = "comment";
		dto.discussionCode = discussionCode;
		Discussion discussion = new Discussion();
		discussion.setCode(discussionCode);
		
		
		when(repo.findById(discussionCode)).thenReturn(Optional.of(discussion));
		Answer answer = new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				 Object[] args = invocation.getArguments();
	             Message message = new Message();
	             message.setMessage((String)args[0]);
	             Discussion discussion = (Discussion) args[1];
	             List<Message> messages = discussion.getMessages();
	             messages.add(message);
	             discussion.setMessages(messages);
	             return message ;
			}
	     }
		;
		when(messageService.createNewMessge(dto.message, discussion)).thenAnswer(answer);
		Discussion result = service.createNewMessageAndAddItToDiscussion(dto).getBody();
		assertEquals(result.getMessages().get(result.getMessages().size()-1).getMessage(), dto.message);
	}
	
	@Test
	public void testRetrieveConnectedUserDiscussion() {
		User currentUser = new User();
		currentUser.setCode("currentUser");
		User otherUser1 = new User();
		otherUser1.setCode("otherUser1");
		User otherUser2 = new User();
		otherUser2.setCode("otherUser2");

		Discussion discussion1 = new Discussion();
		discussion1.setCode("code");
		discussion1.setFirstInterlocutor(currentUser);
		discussion1.setSecondInterlocutor(otherUser1);
		
		Discussion discussion2 = new Discussion();
		discussion2.setCode("code");
		discussion2.setFirstInterlocutor(otherUser2);
		discussion2.setSecondInterlocutor(currentUser);

		
		when(userService.findById("otherUser1")).thenReturn(otherUser1);
		when(userService.findById("otherUser2")).thenReturn(otherUser2);
		when(loginService.getConnectedUser()).thenReturn(currentUser);
		when(repo.findAllBySecondInterlocutor(currentUser)).thenReturn(Arrays.asList(discussion2));
		when(repo.findAllByFirstInterlocutor(currentUser)).thenReturn(Arrays.asList(discussion1));
		
		List<Discussion> result = service.retrieveConnectedUserDiscussion().getBody();
		
		assertThat(result, contains(discussion1,discussion2));
		assertThat(result.size(), is(2));
		
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testRetrieveNonExistantDiscussionByConnectedUserAndAnOtherUser() {
		User currentUser = new User();
		currentUser.setCode("currentUser");
		User otherUser = new User();
		otherUser.setCode("otherUser");
		
		
		when(loginService.getConnectedUser()).thenReturn(currentUser);
		when(userService.findById("otherUser")).thenReturn(otherUser);
		
		when(repo.findOneByFirstInterlocutorAndSecondInterlocutor(currentUser, otherUser)).thenReturn(Optional.empty());
		when(repo.findOneByFirstInterlocutorAndSecondInterlocutor(otherUser, currentUser)).thenReturn(Optional.empty());
		
		service.retrieveDiscussionByConnectedUserAndAnOtherUser("otherUser");
	}
	
	@Test()
	public void testRetrieveDiscussionByConnectedUserAndAnOtherUser() {
		User currentUser = new User();
		currentUser.setCode("currentUser");
		User otherUser = new User();
		otherUser.setCode("otherUser");
		
		Discussion discussion = new Discussion();
		discussion.setCode("code");
		discussion.setFirstInterlocutor(otherUser);
		discussion.setSecondInterlocutor(currentUser);
		when(loginService.getConnectedUser()).thenReturn(currentUser);
		when(userService.findById("otherUser")).thenReturn(otherUser);
		
		when(repo.findOneByFirstInterlocutorAndSecondInterlocutor(currentUser, otherUser)).thenReturn(Optional.empty());
		when(repo.findOneByFirstInterlocutorAndSecondInterlocutor(otherUser, currentUser)).thenReturn(Optional.of(discussion));
		
		Discussion result = service.retrieveDiscussionByConnectedUserAndAnOtherUser("otherUser").getBody();
		assertEquals(result, discussion);
		
	}
	
	@Test
	public void testCreateNewDiscussionByConnectedUserAndAnOtherUser() {
		User currentUser = new User();
		currentUser.setCode("currentUser");
		User otherUser = new User();
		otherUser.setCode("otherUser");
		
		when(loginService.getConnectedUser()).thenReturn(currentUser);
		when(userService.findById("otherUser")).thenReturn(otherUser);
		
		when(repo.save(any(Discussion.class))).thenAnswer(i -> i.getArgument(0));
		when(methods.generateAnId(service.getPrefix())).thenReturn("DiscussionCode");
		
		when(repo.findOneByFirstInterlocutorAndSecondInterlocutor(currentUser, otherUser)).thenReturn(Optional.empty());
		when(repo.findOneByFirstInterlocutorAndSecondInterlocutor(otherUser, currentUser)).thenReturn(Optional.empty());
		
		Discussion result = service.createNewDiscussionByConnectedUserAndAnOtherUser("otherUser").getBody();
		
		assertEquals(result.getCode(), "DiscussionCode");
		assertEquals(result.getFirstInterlocutor(), currentUser);
		assertEquals(result.getSecondInterlocutor(), otherUser);
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testTryToCreateNewDiscussionBetweenConnectedUserAndAnOtherUserHowAlreadyHaveDiscussion() {
		User currentUser = new User();
		currentUser.setCode("currentUser");
		User otherUser = new User();
		otherUser.setCode("otherUser");
		
		when(loginService.getConnectedUser()).thenReturn(currentUser);
		when(userService.findById("otherUser")).thenReturn(otherUser);
		
		when(repo.save(any(Discussion.class))).thenAnswer(i -> i.getArgument(0));
		when(methods.generateAnId(service.getPrefix())).thenReturn("DiscussionCode");
		
		when(repo.findOneByFirstInterlocutorAndSecondInterlocutor(currentUser, otherUser)).thenReturn(Optional.empty());
		when(repo.findOneByFirstInterlocutorAndSecondInterlocutor(otherUser, currentUser)).thenReturn(Optional.of(new Discussion()));
		
		service.createNewDiscussionByConnectedUserAndAnOtherUser("otherUser").getBody();
		

	}
	
	

}
