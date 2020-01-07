package com.alro.zoo.Student.Absence.DTO;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;



public class AbsenceDTO {

	@NotEmpty
	public String studentCode;
	
	@PastOrPresent
	public Date date;
}
