package com.alro.zoo.posts;


import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.alro.zoo.login.LoginService;
import com.alro.zoo.posts.comment.CommentService;
import com.alro.zoo.posts.dtos.CommentRequestDTO;
import com.alro.zoo.posts.dtos.PostDTO;
import com.alro.zoo.shared.GenericService;
import com.alro.zoo.user.User;
import com.alro.zoo.user.UserService;

@Service
@Transactional
public class PostService extends GenericService<Post, PostRepository> {

	@Autowired
	private PostRepository repo;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentService;
	
	@Override
	public PostRepository getRepo() {
		return repo;
	}
	
	
	public PostService() {
	}
	
	public PostService(PostRepository repo) {
		this.repo = repo;
	}

	public PostService(PostRepository repo, LoginService loginService, UserService userService,
			CommentService commentService) {
		super();
		this.repo = repo;
		this.loginService = loginService;
		this.userService = userService;
		this.commentService = commentService;
	}



	@Override
	public String getPrefix() {
		return Post.prefix;
	}
	
	
	public ResponseEntity<Post> savePost(PostDTO dto, MultipartFile file) {
    	Post post = new Post();
    	post.setCode(generateNewCode());
    	post.setAuthor(loginService.getConnectedUser());
    	post.setTitle(dto.title);
    	post.setCreatedAt(new Date());
    	post.setImage(createImageByteObject(file));
    	return ResponseEntity.created(null).body(repo.save(post));
    }
	
    private Byte[] createImageByteObject( MultipartFile file) {
        try {
            Byte[] byteObjects = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }
            return byteObjects;
        } catch (IOException e) {
            System.err.println("Error occurred" + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "cannot create image Byte");
        }
    }
    
    
    
    
    public ResponseEntity<byte[]> restoreImageFromDataBase(String postCode){
    	Post recipeCommand = findById(postCode);
        if (recipeCommand.getImage() != null) {
        	return ResponseEntity
        			.accepted()
        			.contentType(MediaType.IMAGE_JPEG)
        			.body(wrapByteArray(recipeCommand.getImage()));
        }
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found with post code: " + postCode);
    }
    
    private byte[] wrapByteArray(Byte[] imageBytes){
    	byte[] byteArray = new byte[imageBytes.length];
        int i = 0;
        for (Byte wrappedByte : imageBytes){
            byteArray[i++] = wrappedByte;
        }       
        return byteArray;
    }
    
    public ResponseEntity<List<Post>> findPostsByUser(String userCode){
    	User user = userService.findById(userCode);
    	return ResponseEntity.ok().body(repo.findAllByAuthor(user));
    }
    
    public ResponseEntity<List<Post>> findPostsByConnectedUser(){
    	User user = loginService.getConnectedUser();
    	return ResponseEntity.ok().body(repo.findAllByAuthor(user));
    }
    
    public ResponseEntity<Post> createNewCommentOfAPost(CommentRequestDTO request){
    	Post post = findById(request.postCode);
    	commentService.createNewComment(request.comment, post);
    	return ResponseEntity.created(null).body(post);
    }
    
    public ResponseEntity<List<Post>> findALlPosts(){
    	return ResponseEntity.ok().body(repo.findAll());
    }
    
    
    
}
