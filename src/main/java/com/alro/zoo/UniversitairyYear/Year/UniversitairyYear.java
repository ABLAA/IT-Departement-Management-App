package com.alro.zoo.UniversitairyYear.Year;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.annotation.Transient;



@Entity

public class UniversitairyYear{
	@Transient
	@Id
	@Column(length = 15)
	private String year;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	
	
	
	
	
}