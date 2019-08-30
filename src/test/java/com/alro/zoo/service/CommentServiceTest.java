package com.alro.zoo.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import com.alro.zoo.discussion.discussion.Discussion;
import com.alro.zoo.discussion.discussion.DiscussionService;
import com.alro.zoo.discussion.message.Message;
import com.alro.zoo.login.LoginService;
import com.alro.zoo.posts.Post;
import com.alro.zoo.posts.comment.Comment;
import com.alro.zoo.posts.comment.CommentRepository;
import com.alro.zoo.posts.comment.CommentService;
import com.alro.zoo.shared.GeneralMethods;
import com.alro.zoo.user.User;

public class CommentServiceTest {

	@Mock
	private CommentRepository repo;
	
	@Mock
	private LoginService loginService;
	
	@Mock
	private GeneralMethods methods;
	
	private CommentService service;
	
	private AdditionalMatchers AdditionalMatchers;
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        service = new CommentService(repo, loginService);
        service.setMethods(methods);
    }
	
	
	
	@Test
	public void testGenerateNewCode() {
		Comment comment = new Comment();
		comment.setCode(service.getPrefix() + "7217679");
		//given
		when(methods.generateAnId(service.getPrefix())).thenReturn(service.getPrefix() + "7217679" ,service.getPrefix() + "7217679",service.getPrefix() + "7217679", service.getPrefix() + "0153698");
		when(repo.findById(service.getPrefix() + "7217679")).thenReturn(Optional.of(comment));
		when(repo.findById(service.getPrefix() + "0153698")).thenReturn(Optional.empty());
		//when
		
		String code = service.generateNewCode();
		
		//then
		assertEquals(code, service.getPrefix() + "0153698");
		verify(methods , times(4)).generateAnId(anyString());
		verify(repo , times(4)).findById(anyString());
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
		String commentCode = service.getPrefix() + "7217679";
		when(methods.generateAnId(service.getPrefix())).thenReturn(commentCode);
		
		User user = new User();
		user.setCode("currentUser");
		when(loginService.getConnectedUser()).thenReturn(user);
		
		Post post = new Post();
		post.setCode("postCode");
		
		when(repo.save(any(Comment.class))).thenAnswer(i -> (Comment)i.getArgument(0));
		//when
		String comment = "hello";
		Comment result = service.createNewComment(comment, post);
		//then
		assertEquals(result.getAuthor(), user);
		assertEquals(result.getCode(), commentCode);
		assertEquals(result.getComment(), comment);
		assertEquals(result.getPost(), post);
		
	}

}
