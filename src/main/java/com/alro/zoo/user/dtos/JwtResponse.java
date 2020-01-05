package com.alro.zoo.user.dtos;

import java.io.Serializable;

public class JwtResponse implements Serializable {
	public static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	
	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}
