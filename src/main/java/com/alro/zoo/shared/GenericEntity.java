package com.alro.zoo.shared;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.annotation.Transient;

@Entity
public class GenericEntity {
	
	@Transient
	public static String prefix = "";
	
	@Id
	@Column(length = 15 )
	private String code;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	

}
