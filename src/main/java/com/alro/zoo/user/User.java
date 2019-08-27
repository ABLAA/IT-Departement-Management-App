package com.alro.zoo.user;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

import com.alro.zoo.posts.Post;
import com.alro.zoo.shared.GenericEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "User")
@Table(name = "user")
public class User extends GenericEntity implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	public static String prefix = "USR";

	@Id
	@Column(length = 15)
	private String code;
	private String firstName;
	private String lastName;
	private Date birthDate;
	
	@OneToMany(mappedBy = "author")
	@JsonIgnore
	private List<Post> posts = new ArrayList<>();
	
	
		
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}


	
	
}
