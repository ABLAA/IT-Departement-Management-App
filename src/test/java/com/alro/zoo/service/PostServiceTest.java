package com.alro.zoo.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.alro.zoo.login.Login;
import com.alro.zoo.login.LoginService;
import com.alro.zoo.posts.Post;
import com.alro.zoo.posts.PostRepository;
import com.alro.zoo.posts.PostService;
import com.alro.zoo.posts.comment.Comment;
import com.alro.zoo.posts.comment.CommentService;
import com.alro.zoo.posts.dtos.CommentRequestDTO;
import com.alro.zoo.posts.dtos.PostDTO;
import com.alro.zoo.shared.GeneralMethods;
import com.alro.zoo.user.User;
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
	
	@Mock
	private GeneralMethods methods;
	
	private PostService service;
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        service = new PostService(repo, loginService, userService, commentService);
        service.setMethods(methods);
    }
	
	@Test
	public void testGenerateNewCode() {
		Post post = new Post();
		post.setCode("7217679");
		//given
		when(methods.generateAnId(service.getPrefix())).thenReturn(service.getPrefix() + "7217679" ,service.getPrefix() + "7217679", service.getPrefix() + "0153698");
		when(repo.findById(service.getPrefix() + "7217679")).thenReturn(Optional.of(post));
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
	
	@Test()
	public void testFindByIdWhenCodeisValide() {
		//given
		String code = service.getPrefix() + "0153698";
		Post post = new Post();
		post.setCode(code);
		when(repo.findById(code)).thenReturn(Optional.of(post));
		
		//when
		Post result = service.findById(code);
		
		//then
		assertEquals(result, post);
	}
	
	@Test
	public void testSavePost() {
		//given
		String title = "PostTitle";
		String userCode = "USR0000001";
		User user = new User();
		user.setCode(userCode);
		String postCode = service.getPrefix() + "7217679";
		
		MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt" , "text/plain", "Pink Floyd".getBytes());
		
		when(methods.generateAnId(service.getPrefix())).thenReturn(postCode);
		when(repo.findById(postCode)).thenReturn(Optional.empty());
		when(loginService.getConnectedUser()).thenReturn(user);
		PostDTO dto =new PostDTO();
		dto.title = title;
		when(repo.save(any())).thenAnswer(i -> i.getArguments()[0]);
		//when
		Post result = service.savePost(dto, multipartFile).getBody();
		//then

		assertEquals(result.getCode(), postCode);
		assertEquals(result.getTitle(), title);
		assertEquals(result.getAuthor().getCode(), userCode);
		assertEquals(result.getImage().length, "Pink Floyd".getBytes().length);

	}	

	@Test
	public void testRestoreImageFromDataBase() throws IOException {
		
		String title = "PostTitle";
		String userCode = "USR0000001";
		String postCode = service.getPrefix() + "7217679";
		User user = new User();
		user.setCode(userCode);
		Post post = new Post();
		post.setAuthor(user);
		post.setCode(postCode);
		post.setTitle(title);
		MultipartFile file = new MockMultipartFile("imagefile", "testing.txt" , "text/plain", "Pink Floyd".getBytes());
		Byte[] byteObjects;
		byteObjects = new Byte[file.getBytes().length];
		int i = 0;
		for (byte b : file.getBytes()){
			byteObjects[i++] = b;
	      }
		post.setImage(byteObjects);
		when(repo.findById(postCode)).thenReturn(Optional.of(post));
        
		//when
		byte[] result = service.restoreImageFromDataBase(postCode).getBody();
		assertArrayEquals(result, "Pink Floyd".getBytes());
	}

	@Test
	public void testFindPostsByUser() {
		String userCode = "USR0000001";
		String postCode = service.getPrefix() + "7217679";
		User user = new User();
		user.setCode(userCode);
		Post post = new Post();
		post.setAuthor(user);
		post.setCode(postCode);
		List<Post> posts = new ArrayList<Post>();
		posts.add(post);
		when(userService.findById(userCode)).thenReturn(user);
		when(repo.findAllByAuthor(user)).thenReturn(posts);
		List<Post> result = service.findPostsByUser(userCode).getBody();
		
		assertEquals(result, posts);
	}
	
	@Test
	public void testfindPostsByConnectedUser() {
		String userCode = "USR0000001";
		String postCode = service.getPrefix() + "7217679";
		User user = new User();
		user.setCode(userCode);
		Post post = new Post();
		post.setAuthor(user);
		post.setCode(postCode);
		List<Post> posts = new ArrayList<Post>();
		posts.add(post);
		when(loginService.getConnectedUser()).thenReturn(user);
		when(repo.findAllByAuthor(user)).thenReturn(posts);
		List<Post> result = service.findPostsByConnectedUser().getBody();
		
		assertEquals(result, posts);
	}

	@Test
	public void testCreateNewCommentOfAPost() {
		
		String postCode = service.getPrefix() + "7217679";
		CommentRequestDTO dto = new CommentRequestDTO();
		dto.comment = "comment";
		dto.postCode = postCode;
		Post post = new Post();
		when(repo.findById(postCode)).thenReturn(Optional.of(post));
		post.setCode(postCode);
		Answer answer = new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				 Object[] args = invocation.getArguments();
	             Comment comment = new Comment();
	             comment.setComment((String)args[0]);
	             Post post = (Post) args[1];
	             List<Comment> comments = post.getComments();
	             comments.add(comment);
	             post.setComments(comments);
	             return comment ;
			}
	     }
		;
		when(commentService.createNewComment(dto.comment, post)).thenAnswer(answer);
		Post result = service.createNewCommentOfAPost(dto).getBody();
		assertEquals(result.getComments().get(result.getComments().size()-1).getComment(), dto.comment);
		
	}




}
