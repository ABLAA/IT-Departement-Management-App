package com.alro.zoo.discussion.message.DTO;

import javax.validation.constraints.NotEmpty;

public class newMessageRequestDto {
	@NotEmpty
	public String discussionCode;
	@NotEmpty
	public String message;
}
