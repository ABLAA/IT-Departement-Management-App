package com.alro.zoo.login.dtos;

import javax.validation.constraints.NotEmpty;

public class LoginDTO {
	@NotEmpty
	public String emailOrPseudo;
	@NotEmpty
	public String password;

}
