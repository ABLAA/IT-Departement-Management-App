package com.alro.zoo.posts;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.Transient;

import com.alro.zoo.shared.GenericEntity;
import com.alro.zoo.user.User;

@Entity
public class Post extends GenericEntity {
	
	@Transient
	public static String prefix = "POST";
	
	@Id
	@Column(length = 15)
	private String code;
	
	private Date createdAt;
	
	private String title;
	
	@ManyToOne
	private User author;
	
	@Lob
    private Byte[] image;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Byte[] getImage() {
		return image;
	}

	public void setImage(Byte[] image) {
		this.image = image;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	
	
}
