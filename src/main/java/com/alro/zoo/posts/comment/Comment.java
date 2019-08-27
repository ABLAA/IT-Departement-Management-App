package com.alro.zoo.posts.comment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.Transient;

import com.alro.zoo.posts.Post;
import com.alro.zoo.shared.GenericEntity;
import com.alro.zoo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Comment extends GenericEntity{

	@Transient
	public static String prefix = "COMM";
	
	@Id
	@Column(length = 15)
	private String code;
	
	private Date createdAt;
	
	private String comment;
	
	@ManyToOne
	private User author;

	@ManyToOne
	@JsonIgnore
	private Post post;
	
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

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

}
