package com.alro.zoo.login.dtos;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class SignInDTO {
	
	@NotEmpty
	@Email
	@Size(max = 100)
	public String email;
	@NotEmpty
	@Size(min = 2, max = 24)
	public String pseudo;
	@NotEmpty
	@Size(min = 2)
	public String firstName;
	@NotEmpty
	@Size(min = 8)
	public String password;
	@NotEmpty
	@Size(min = 2)
	public String lastName;
	@Past
	public Date birthDate;

}
