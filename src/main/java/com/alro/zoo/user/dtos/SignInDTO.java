package com.alro.zoo.user.dtos;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class SignInDTO {
	
	@NotEmpty
	@Size(min = 2)
	public String firstName;
	@NotEmpty
	@Size(min = 2)
	public String lastName;
	@NotEmpty
	@Size(min = 2)
	public String departmentName;
	@Past
	public Date birthDate;

}
