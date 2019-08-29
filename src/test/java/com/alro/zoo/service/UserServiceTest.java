package com.alro.zoo.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.alro.zoo.login.LoginService;
import com.alro.zoo.login.dtos.SignInDTO;
import com.alro.zoo.shared.GeneralMethods;
import com.alro.zoo.user.User;
import com.alro.zoo.user.UserRepository;
import com.alro.zoo.user.UserService;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {
	@Mock
	private UserRepository repo;
	
	@Mock
	private LoginService loginService;
	
	@Mock
	private GeneralMethods methods;
	
	private UserService service;
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        service = new UserService(repo, loginService);
        service.setMethods(methods);
    }

	@Test
	public void testGenerateNewCode() {
		//given
		when(methods.generateAnId(service.getPrefix())).thenReturn(service.getPrefix() + "7217679" , service.getPrefix() + "0153698");
		User user = new User();
		user.setCode(service.getPrefix() + "7217679");
		
		when(repo.findById(service.getPrefix() + "7217679")).thenReturn(Optional.of(user));
		when(repo.findById(service.getPrefix() + "0153698")).thenReturn(Optional.empty());
		
		//when
		
		String code = service.generateNewCode();
		
		//then
		assertEquals(code, service.getPrefix() + "0153698");
		verify(methods , times(2)).generateAnId(anyString());
		verify(repo , times(2)).findById(anyString());
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
		User user = new User();
		user.setCode(code);
		when(repo.findById(code)).thenReturn(Optional.of(user));
		//when
		User result = service.findById(code);
		
		//then
		assertEquals(result, user);
	}
	
	@Test(expected = Exception.class)
	public void testSaveNewUserTwice() {
		String code = service.getPrefix() + "7217679";
		User user = new User();
		user.setCode(code);
		when(repo.save(user)).thenReturn(user ).thenThrow(Exception.class);
		service.saveNewUser(user);
		service.saveNewUser(user);
	}
	
	@Test
	public void testSaveNewUser() {
		String code = service.getPrefix() + "7217679";
		User user = new User();
		user.setCode(code);
		when(repo.save(user)).thenReturn(user );
		User result = service.saveNewUser(user);
		assertEquals(user, result);
	}
	
	@Test
	public void testCreateUserObjectFromDTO() {
		String code = service.getPrefix() + "7217679";
		SignInDTO dto = new SignInDTO();
		
		dto.firstName = "Attia";
		dto.lastName = "Alla Eddine";
		Date birthDate = new Date(700000);
		dto.birthDate = birthDate;
	
		User user = new User();
		user.setCode(code);
		user.setBirthDate(birthDate);
		user.setFirstName("Attia");
		user.setLastName("Alla Eddine");
		
		//given
		when(methods.generateAnId(service.getPrefix())).thenReturn(code);
		
		//when
		User result = service.createUserObject(dto);
		
		//then
		assertEquals(user.getCode(), result.getCode());
		assertEquals(user.getBirthDate().getTime(), result.getBirthDate().getTime());
		assertEquals(user.getFirstName(), user.getFirstName());
		assertEquals(user.getLastName(), user.getLastName());
		
	}
	
	@Test
	public void testConnectedUserAuthenticated() {
		//given
		User user = new User();
		when(loginService.getConnectedUser()).thenReturn(user);
		
		//when
		ResponseEntity<User> response = service.findConnectedUser();
		
		//then
		assertEquals(response, ResponseEntity.ok().body(user));
		
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testConnectedUserNotAuthenticated() {
		when(loginService.getConnectedUser()).thenThrow(ResponseStatusException.class);
		
		service.findConnectedUser();
	}
	
	
	

	
	
	

}
