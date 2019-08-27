package com.alro.zoo.posts.comment;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alro.zoo.login.LoginService;
import com.alro.zoo.posts.Post;
import com.alro.zoo.shared.GenericService;

@Service
@Transactional
public class CommentService extends GenericService<Comment, CommentRepository>{

	@Autowired
	private CommentRepository repo;
	
	@Autowired
	private LoginService loginService;
	
	@Override
	public CommentRepository getRepo() {
		return repo;
	}

	public CommentService() {
		super();
	}
	
	public CommentService(CommentRepository repo) {
		super();
		this.repo = repo;
	}

	
	public CommentService(CommentRepository repo, LoginService loginService) {
		super();
		this.repo = repo;
		this.loginService = loginService;
	}



	@Override
	public String getPrefix() {
		return Comment.prefix;
	}
	
	public Comment createNewComment(String comment, Post post) {
		Comment newComment = new Comment();
		
		newComment.setAuthor(loginService.getConnectedUser());
		newComment.setCode(generateNewCode());
		newComment.setComment(comment);
		newComment.setCreatedAt(new Date());
		newComment.setPost(post);
		
		return repo.save(newComment);
	}

}
