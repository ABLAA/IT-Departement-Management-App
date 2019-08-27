package com.alro.zoo.discussion.message;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alro.zoo.discussion.discussion.Discussion;
import com.alro.zoo.login.LoginService;
import com.alro.zoo.shared.GenericService;

@Service
@Transactional
public class MessageService extends GenericService<Message, MessageRepository>{

	@Autowired
	private MessageRepository repo;
	
	@Autowired
	private LoginService loginService;

	@Override
	public MessageRepository getRepo() {
		return repo;
	}

	@Override
	public String getPrefix() {
		return Message.prefix;
	}
	
	public Message createNewMessge ( String message, Discussion discussion) {
		
		Message newMessage = new Message();
		
		newMessage.setAuthor(loginService.getConnectedUser());
		newMessage.setCode(generateNewCode());
		newMessage.setCreatedAt(new Date());
		newMessage.setMessage(message);
		newMessage.setDiscussion(discussion);
		
		return repo.save(newMessage);
		
	}
	
}
