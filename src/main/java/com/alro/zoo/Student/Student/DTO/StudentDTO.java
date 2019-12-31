package com.alro.zoo.Student.Student.DTO;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class StudentDTO {
	@NotEmpty
	@Size(min = 2)
	public String firstName;
	@NotEmpty
	@Size(min = 2)
	public String lastName;
	@Past
	public Date birthDate;
	@NotEmpty
	public String classCode;
}

