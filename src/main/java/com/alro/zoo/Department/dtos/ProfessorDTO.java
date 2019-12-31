package com.alro.zoo.Department.dtos;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class ProfessorDTO {
	@NotEmpty
	@Size(min = 2)
	public String firstName;
	@NotEmpty
	@Size(min = 2)
	public String lastName;
	@Past
	public Date birthDate;
	@NotEmpty
	public String departmentName;
}

