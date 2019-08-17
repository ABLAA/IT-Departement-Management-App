package com.alro.zoo.login;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Login {
	
	@Id
	private Integer userId;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	private String password;


	


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



}
