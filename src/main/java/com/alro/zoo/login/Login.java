package com.alro.zoo.login;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.Transient;

import com.alro.zoo.shared.GenericEntity;
import com.alro.zoo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Login extends GenericEntity {
	
	
	@Transient
	public static String prefix = "LOG";
	
	@OneToOne
	private User user;
	@JsonIgnore
	private String password;
	@Column(length = 100 ,unique = true)
	private String email;
	@Column(length = 24 ,unique = true)
	private String pseudo; 



	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



}
