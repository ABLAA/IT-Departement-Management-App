package com.alro.zoo.user.dtos;

import javax.validation.constraints.NotEmpty;

public class LoginDTO {
	@NotEmpty
	public String emailOrPseudo;
	@NotEmpty
	public String password;

}
