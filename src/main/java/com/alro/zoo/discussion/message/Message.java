package com.alro.zoo.discussion.message;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.Transient;

import com.alro.zoo.discussion.discussion.Discussion;
import com.alro.zoo.shared.GenericEntity;
import com.alro.zoo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Message extends GenericEntity{

	
	@Transient
	public static String prefix = "MSG";
	
	@Id
	@Column(length = 15)
	private String code;
	
	private Date createdAt;
	
	private String message;
	
	@ManyToOne
	private User author;

	@ManyToOne
	@JsonIgnore
	private Discussion discussion;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Discussion getDiscussion() {
		return discussion;
	}

	public void setDiscussion(Discussion discussion) {
		this.discussion = discussion;
	}
	
	

	

}
