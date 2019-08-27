package com.alro.zoo.discussion.discussion;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.Transient;

import com.alro.zoo.discussion.message.Message;
import com.alro.zoo.shared.GenericEntity;
import com.alro.zoo.user.User;
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"first_interlocutor" , "second_interlocutor"}))
public class Discussion extends GenericEntity {
	
	@Transient
	public static String prefix = "DIS";

	@Id
	@Column(length = 15)
	private String code;
	
	@ManyToOne
	@JoinColumn(name = "first_interlocutor")
	private User firstInterlocutor;
	
	@ManyToOne
	@JoinColumn(name = "second_interlocutor")
	private User secondInterlocutor;
	
	@OneToMany(mappedBy = "discussion")
	private List<Message> messages = new ArrayList<Message>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public User getFirstInterlocutor() {
		return firstInterlocutor;
	}

	public void setFirstInterlocutor(User firstInterlocutor) {
		this.firstInterlocutor = firstInterlocutor;
	}

	public User getSecondInterlocutor() {
		return secondInterlocutor;
	}

	public void setSecondInterlocutor(User secondInterlocutor) {
		this.secondInterlocutor = secondInterlocutor;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	
	
	
}
