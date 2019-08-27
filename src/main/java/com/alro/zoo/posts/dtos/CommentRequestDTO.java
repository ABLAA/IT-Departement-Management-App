package com.alro.zoo.posts.dtos;

import javax.validation.constraints.NotEmpty;

public class CommentRequestDTO {
	@NotEmpty
	public String comment;
	
	@NotEmpty
	public String postCode;

}
