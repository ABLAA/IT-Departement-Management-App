package com.alro.zoo.discussion;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alro.zoo.discussion.discussion.Discussion;
import com.alro.zoo.discussion.discussion.DiscussionService;
import com.alro.zoo.discussion.message.DTO.newMessageRequestDto;

@Controller
public class DiscussionController {

	@Autowired
	private DiscussionService service;
	
	@GetMapping(path = "/discussions")
	public ResponseEntity<List<Discussion>> getConnectedUserDiscussions(){
		return service.retrieveConnectedUserDiscussion();
	}
	
	@GetMapping(path = "/discussion/{discussionCode}")
	public ResponseEntity<Discussion> getDiscussionByCode(@PathVariable String discussionCode){
		return service.retrieveDiscussionByCode(discussionCode);
	}
	
	@GetMapping(path = "/discussion/user/{userCode}")
	public ResponseEntity<Discussion> getDiscussionBetweenCurrentUserAndUserPassedInPath(@PathVariable String userCode){
		return service.retrieveDiscussionByConnectedUserAndAnOtherUser(userCode);
	}
	
	@PostMapping(path = "/discussion/{userCode}")
	public ResponseEntity<Discussion> createDiscusionBetweenConnectedUserAndUserPassedInPath(@PathVariable String userCode) {
		return service.createNewDiscussionByConnectedUserAndAnOtherUser(userCode);
	}
	
	@PostMapping(path = "/discussion-message")
	public ResponseEntity<Discussion> addNewMessageToDiscussion(@Valid @RequestBody newMessageRequestDto requestDto){
		return service.createNewMessageAndAddItToDiscussion(requestDto);
	}
	
	
}
