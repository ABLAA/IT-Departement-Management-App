package com.alro.zoo.Department.dtos;

import javax.validation.constraints.NotEmpty;

public class SectionDTO {
	@NotEmpty
	public String department;
	
	@NotEmpty
	public String name;

}
