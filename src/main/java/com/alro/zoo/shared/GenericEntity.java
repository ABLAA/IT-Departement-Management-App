package com.alro.zoo.shared;

import org.springframework.data.annotation.Transient;

public abstract class GenericEntity {
	
	@Transient
	public static String getPrefix() {
		return "";
	}
	public abstract String getCode();
	public abstract void setCode(String code);
	
	

}
