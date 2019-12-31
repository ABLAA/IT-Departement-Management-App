package com.alro.zoo.UniversitairyYear.StudentClass.DTO;

import javax.validation.constraints.NotEmpty;

public class StudentClassDTO {

	@NotEmpty
	public String sectionCode;
	
	@NotEmpty
	public String universitairyYear;
}
