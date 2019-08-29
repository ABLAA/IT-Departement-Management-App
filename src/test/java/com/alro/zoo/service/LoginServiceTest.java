package com.alro.zoo.service;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.not;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.CryptoPrimitive;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.alro.zoo.configuration.jwt.JwtTokenUtil;
import com.alro.zoo.login.Login;
import com.alro.zoo.login.LoginRepository;
import com.alro.zoo.login.LoginService;
import com.alro.zoo.login.dtos.JwtResponse;
import com.alro.zoo.login.dtos.LoginDTO;
import com.alro.zoo.login.dtos.SignInDTO;
import com.alro.zoo.shared.GeneralMethods;
import com.alro.zoo.user.User;
import com.alro.zoo.user.UserService;

public class LoginServiceTest {

	@Mock
	private BCryptPasswordEncoder passwordEncoder;
	
	@Mock 
	private UserService userService;
	
	@Mock
	private LoginRepository repo;
	
	@Mock
	private JwtTokenUtil jwtTokenUtil;
	
	@Mock
	private AuthenticationManager authenticationManager;
	
	@Mock
	private GeneralMethods methods;
	
	private LoginService service;
	
	private AdditionalMatchers AdditionalMatchers;
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        service = new LoginService(userService, repo, jwtTokenUtil, authenticationManager,passwordEncoder);
        service.setMethods(methods);
    }
	
	@Test
	public void testGenerateNewCode() {
		//given
		when(methods.generateAnId(service.getPrefix())).thenReturn(service.getPrefix() + "7217679" , service.getPrefix() + "0153698");
		when(repo.findById(service.getPrefix() + "7217679")).thenReturn(Optional.empty());
		
		//when
		
		String code = service.generateNewCode();
		
		//then
		assertEquals(code, service.getPrefix() + "7217679");
		verify(methods , times(1)).generateAnId(anyString());
		verify(repo , times(1)).findById(anyString());
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
		Login login = new Login();
		login.setCode(code);
		when(repo.findById(code)).thenReturn(Optional.of(login));
		
		//when
		Login result = service.findById(code);
		
		//then
		assertEquals(result, login);
	}
	
	@Test
	public void testGetConnectedUserNotNull () {
		//given 
		User user = new User();
		service .setConnectedUser(user);
		User result = service.getConnectedUser();
		assertEquals(user, result);
		
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testGetConnectedUserNull () {
		service .setConnectedUser(null);
		service.getConnectedUser();
	}
	
	@Test
	public void testSetConnectedUserByValidCode () {
		//given
		String code = "log1234567";
		User user = new User ();		
		Login login = new Login();
		login.setCode(code);
		login.setUser(user);
		when(repo.findById(code)).thenReturn(Optional.of(login));
		when(repo.findById(AdditionalMatchers.not(eq(code)))).thenThrow(ResponseStatusException.class);
		service.setConnectedUserByLoginCode(code);
		User result = service.getConnectedUser();
		
		assertEquals(result, user);
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testSetConnectedUserByANonValidCode () {
		//given
		String code = service.getPrefix() + "1234567";
		User user = new User ();		
		Login login = new Login();
		login.setCode(code);
		login.setUser(user);
		when(repo.findById(code)).thenReturn(Optional.of(login));
		when(repo.findById(AdditionalMatchers.not(eq(code)))).thenThrow(ResponseStatusException.class);
		//then
		service.setConnectedUserByLoginCode(service.getPrefix() + "1234568");
	}
	
	@Test
	public void testSignInNewUser() {
		//given
		String userCode = "USR7217679";
		SignInDTO dto = new SignInDTO();
		
		dto.firstName = "Attia";
		dto.lastName = "Alla Eddine";
		Date birthDate = new Date(700000);
		dto.birthDate = birthDate;
		dto.email = "a@gmail.com";
		dto.pseudo = "alro36";
		dto.password = "password";
		
		User user = new User();
		user.setCode(userCode);
		user.setBirthDate(birthDate);
		user.setFirstName("Attia");
		user.setLastName("Alla Eddine");
		
		String loginCode = service.getPrefix() + "1234567";
		Login login = new Login();
		login.setCode(loginCode);
		login.setEmail("a@gmail.com");
		login.setPseudo("alro36");
		login.setUser(user);
		login.setPassword("crypted Password");
		
		
		when(userService.createUserObject(dto)).thenReturn(user);
		when(passwordEncoder.encode("password")).thenReturn("crypted Password");
		when(userService.saveNewUser(user)).thenReturn(user);
		when(repo.save(any())).thenAnswer(i -> i.getArguments()[0]);
		when(methods.generateAnId(service.getPrefix())).thenReturn(loginCode);
		
		//when
		ResponseEntity<Login> response= service.signInNewUser(dto);
		Login result = response.getBody();
		if(result == null) {
			fail( "null");
		}
		assertEquals(login.getCode(), result.getCode());
		assertEquals(login.getEmail(), result.getEmail());
		assertEquals(login.getPassword(), result.getPassword());
		assertEquals(login.getPseudo(), result.getPseudo());
		assertEquals(login.getUser(), result.getUser());
		
	}
	
	@Test
	public void testLoadUserByValidCode() {
		//given
		String code = service.getPrefix() + "1234567";	
		String password = "password";
		Login login = new Login();
		login.setCode(code);
		login.setPassword(password);
		when(repo.findById(code)).thenReturn(Optional.of(login));
		
		//when
		UserDetails result =  service.loadUserByCode(code);
		
		//then
		assertEquals(result.getUsername(), code);
		assertEquals(result.getPassword(), password);
		
	}

	@Test(expected = UsernameNotFoundException.class)
	public void testLoadUserByNonValidCode() {
		//given
		String code = service.getPrefix() + "1234567";	
		String password = "password";
		Login login = new Login();
		login.setCode(code);
		login.setPassword(password);
		when(repo.findById(code)).thenReturn(Optional.of(login));
		when(repo.findById(AdditionalMatchers.not(eq(code)))).thenThrow(UsernameNotFoundException.class);
		
		//then
		service.loadUserByCode(service.getPrefix() + "1234568");
		
	}
	
	@Test
	public void testLoadUserByUserNameValidPseudo() {
		String pseudo = "alro36";
		String email = "a@gmail.com";
		String password = "crypted Password";
		String loginCode = service.getPrefix() + "1234567";
		Login login = new Login();
		login.setCode(loginCode);
		login.setEmail(email);
		login.setPseudo(pseudo);
		login.setUser(null);
		login.setPassword(password);
		
		when(repo.findOneByEmail(email)).thenReturn(Optional.of(login));
		when(repo.findOneByPseudo(pseudo)).thenReturn(Optional.of(login));
		when(repo.findOneByEmail(AdditionalMatchers.not(eq(email)))).thenReturn(Optional.empty());
		when(repo.findOneByPseudo(AdditionalMatchers.not(eq(pseudo)))).thenReturn(Optional.empty());
		
		//when
		UserDetails result = service.loadUserByUsername(pseudo);
		
		//then
		assertEquals(result.getUsername(), loginCode);
		assertEquals(result.getPassword(), password);
		
		
	}
	
	@Test
	public void testLoadUserByUserNameValidEmail() {
		String pseudo = "alro36";
		String email = "a@gmail.com";
		String password = "crypted Password";
		String loginCode = service.getPrefix() + "1234567";
		Login login = new Login();
		login.setCode(loginCode);
		login.setEmail(email);
		login.setPseudo(pseudo);
		login.setUser(null);
		login.setPassword(password);
		
		when(repo.findOneByEmail(email)).thenReturn(Optional.of(login));
		when(repo.findOneByPseudo(pseudo)).thenReturn(Optional.of(login));
		when(repo.findOneByEmail(AdditionalMatchers.not(eq(email)))).thenReturn(Optional.empty());
		when(repo.findOneByPseudo(AdditionalMatchers.not(eq(pseudo)))).thenReturn(Optional.empty());
		
		//when
		UserDetails result = service.loadUserByUsername(pseudo);
		
		//then
		assertEquals(result.getUsername(), loginCode);
		assertEquals(result.getPassword(), password);
		
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void testLoadUserByUserNameNonValidUserName() {
		String pseudo = "alro36";
		String email = "a@gmail.com";
		String password = "crypted Password";
		String loginCode = service.getPrefix() + "1234567";
		Login login = new Login();
		login.setCode(loginCode);
		login.setEmail(email);
		login.setPseudo(pseudo);
		login.setUser(null);
		login.setPassword(password);
		
		when(repo.findOneByEmail(email)).thenReturn(Optional.of(login));
		when(repo.findOneByPseudo(pseudo)).thenReturn(Optional.of(login));
		when(repo.findOneByEmail(AdditionalMatchers.not(eq(email)))).thenReturn(Optional.empty());
		when(repo.findOneByPseudo(AdditionalMatchers.not(eq(pseudo)))).thenReturn(Optional.empty());
		
		//when
		UserDetails result = service.loadUserByUsername("something");
		
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void testLoadUserByUserNameValidUserNameCorruptedLoginCode() {
		String pseudo = "alro36";
		String email = "a@gmail.com";
		String password = "crypted Password";
		Login login = new Login();
		
		login.setEmail(email);
		login.setPseudo(pseudo);
		login.setPassword(password);
		
		when(repo.findOneByEmail(email)).thenReturn(Optional.of(login));
		when(repo.findOneByPseudo(pseudo)).thenReturn(Optional.of(login));
		when(repo.findOneByEmail(AdditionalMatchers.not(eq(email)))).thenReturn(Optional.empty());
		when(repo.findOneByPseudo(AdditionalMatchers.not(eq(pseudo)))).thenReturn(Optional.empty());
		
		//when
		UserDetails result = service.loadUserByUsername("something");
		
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void testLoadUserByUserNameValidUserNameCorruptedLoginPassword() {
		String pseudo = "alro36";
		String email = "a@gmail.com";
		String loginCode = service.getPrefix() + "1234567";
		Login login = new Login();
		login.setCode(loginCode);
		login.setEmail(email);
		login.setPseudo(pseudo);
		
		when(repo.findOneByEmail(email)).thenReturn(Optional.of(login));
		when(repo.findOneByPseudo(pseudo)).thenReturn(Optional.of(login));
		when(repo.findOneByEmail(AdditionalMatchers.not(eq(email)))).thenReturn(Optional.empty());
		when(repo.findOneByPseudo(AdditionalMatchers.not(eq(pseudo)))).thenReturn(Optional.empty());
		
		//when
		UserDetails result = service.loadUserByUsername("something");
		
	}
	
	@Test
	public void testAuthenticateUser() {
		String pseudo = "validusername";
		String email = "a@gmail.com";
		String password = "password";
		String loginCode = service.getPrefix() + "1234567";
		Login login = new Login();
		login.setCode(loginCode);
		login.setEmail(email);
		login.setPseudo(pseudo);
		login.setUser(null);
		login.setPassword(password);
		when(repo.findOneByPseudo(pseudo)).thenReturn(Optional.of(login));
		LoginDTO dto = new LoginDTO();
		dto.emailOrPseudo = pseudo;
		dto.password = password;
		when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(pseudo, password)))
		.thenReturn(new Authentication() {
			
			@Override
			public String getName() {
				return pseudo;
			}
			
			@Override
			public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isAuthenticated() {
				return true;
			}
			
			@Override
			public Object getPrincipal() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getDetails() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getCredentials() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenAnswer(i -> 
		((UserDetails) i.getArguments()[0]).getUsername() + "-" + ((UserDetails) i.getArguments()[0]).getPassword());
		
		String[] result = service.authinticateUser(dto).getBody().getToken().split("-");
		
		assertEquals(result[0], loginCode);
		assertEquals(result[1], password);
	}
	
	
	
}
