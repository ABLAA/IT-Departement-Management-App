package com.alro.zoo.discussion.discussion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.alro.zoo.discussion.message.MessageService;
import com.alro.zoo.discussion.message.DTO.newMessageRequestDto;
import com.alro.zoo.login.LoginService;
import com.alro.zoo.shared.GenericService;
import com.alro.zoo.user.User;
import com.alro.zoo.user.UserService;

@Service
@Transactional
public class DiscussionService extends GenericService<Discussion, DisussionRepository>{

	@Autowired
	private DisussionRepository repo;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserService userService;
	
	
	
	public DiscussionService() {
		super();
	}
	

	public DiscussionService(DisussionRepository repo) {
		super();
		this.repo = repo;
	}

	public DiscussionService(DisussionRepository repo, MessageService messageService, LoginService loginService,
			UserService userService) {
		super();
		this.repo = repo;
		this.messageService = messageService;
		this.loginService = loginService;
		this.userService = userService;
	}

	@Override
	public String getPrefix() {
		return Discussion.prefix;
	}

	@Override
	public DisussionRepository getRepo() {
		return repo;
	}
	
	public ResponseEntity<List<Discussion>> retrieveConnectedUserDiscussion() {
		User connectedUser = loginService.getConnectedUser();
		List<Discussion> body = findDiscussionsByUser(connectedUser);
		return ResponseEntity.ok().body(body);
	}
	
	private List<Discussion> findDiscussionsByUser(User user){
		List <Discussion> result = new ArrayList<Discussion>();
		result.addAll( repo.findAllByFirstInterlocutor(user));
		result.addAll(repo.findAllBySecondInterlocutor(user));
		return result;
	}
	
	
	
	public ResponseEntity<Discussion> retrieveDiscussionByCode(String discussionCode){
		Discussion body = findById(discussionCode);
		return ResponseEntity.ok().body(body);
	}
	
	
	
	public ResponseEntity<Discussion> retrieveDiscussionByConnectedUserAndAnOtherUser(String otherUserCode){
		User connectedUser = loginService.getConnectedUser();
		User otherUser = userService.findById(otherUserCode);
		Discussion body = findDiscussionByUsers(connectedUser, otherUser);
		return ResponseEntity.ok().body(body);
	}
	
	private Discussion findDiscussionByUsers(User firstUser, User secondUser){
		
		Optional<Discussion> result = repo.findOneByFirstInterlocutorAndSecondInterlocutor(firstUser, secondUser);
		if (! result.isPresent()) 
			result = repo.findOneByFirstInterlocutorAndSecondInterlocutor(secondUser, firstUser);
		if ( result.isPresent())
			return result.get();
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no discussion between the two users with code: "
				+ firstUser.getCode() + " , " +secondUser.getCode()
				);
	}
	
	
	
	
	public ResponseEntity<Discussion> createNewDiscussionByConnectedUserAndAnOtherUser(String otherUserCode){
		User connectedUser = loginService.getConnectedUser();
		User otherUser = userService.findById(otherUserCode);
		Discussion body = tryCreateDiscussion(connectedUser , otherUser);
		return ResponseEntity.ok().body(body);
	}
	
	private Discussion tryCreateDiscussion(User firstUser, User secondUser) {
		if ( !checkExistenceByUsers(firstUser, secondUser)) {
			return repo.save( createDiscussion(firstUser, secondUser) );
		}
		else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "there is discussion between the two users with code: "
					+ firstUser.getCode() + " , " +secondUser.getCode()
					);
		}
	}
	
	private boolean checkExistenceByUsers( User firstUser, User secondUser) {
		try {
			findDiscussionByUsers(firstUser, secondUser);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	private Discussion createDiscussion(User firstUser, User secondUser) {
		Discussion newDiscussion = new Discussion();
		newDiscussion.setCode(generateNewCode());
		newDiscussion.setFirstInterlocutor(firstUser);
		newDiscussion.setSecondInterlocutor(secondUser);
		return newDiscussion;
	}
	
	
	
	
	public ResponseEntity<Discussion> createNewMessageAndAddItToDiscussion(newMessageRequestDto request){
		Discussion discussion = findById(request.discussionCode);
		messageService.createNewMessge(request.message, discussion);
		return ResponseEntity.created(null).body(discussion);
	}

	

}
