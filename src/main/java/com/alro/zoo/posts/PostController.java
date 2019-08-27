package com.alro.zoo.posts;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alro.zoo.posts.dtos.CommentRequestDTO;
import com.alro.zoo.posts.dtos.PostDTO;


@Controller
public class PostController {

	@Autowired
	private PostService service;
	
	@PostMapping("/post")
	public ResponseEntity<Post> postAnImage(@RequestParam("title") String title, @RequestParam("imagefile") MultipartFile file) {
		PostDTO postRequest= new PostDTO();
		postRequest.title = title;
		return service.savePost(postRequest, file);
	}
	
	@GetMapping("/posts/{userCode}")
	public ResponseEntity<List<Post>> getPostsByUser(@PathVariable String userCode){
		return service.findPostsByUser(userCode);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<List<Post>> getPostsByUserConnected(){
		return service.findPostsByConnectedUser();
	}
	
	@GetMapping("/all-posts")
	public ResponseEntity<List<Post>> getAllPosts(){
		return service.findALlPosts();
	}
	
	@GetMapping("/post/{postCode}")
	public ResponseEntity<Post> getImageByCode(@PathVariable String postCode){
		System.out.println("\n\nhere "+postCode);
		Post post = service.findById(postCode);
		return ResponseEntity.ok().body(post);
	}
	
	@GetMapping("/post/image/{postCode}")
	public ResponseEntity<byte[]> getImageByPostCode(@PathVariable String postCode){
		return service.restoreImageFromDataBase(postCode);
	}
	
	@PostMapping("/post-comment")
	public ResponseEntity<Post> addNewCommentToAPost(@Valid @RequestBody CommentRequestDTO dto){
		return service.createNewCommentOfAPost(dto);
	}
	
	
	
}
