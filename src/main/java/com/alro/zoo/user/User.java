package com.alro.zoo.user;
import java.util.Date;

import javax.persistence.Entity;

import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.Transient;

import com.alro.zoo.shared.GenericEntity;

@Entity

public class User extends GenericEntity{
	

	@Transient
	public static String prefix = "USR";


	private String firstName;
	private String lastName;
	private Date birthDate;
		
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}


	
}
