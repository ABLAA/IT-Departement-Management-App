package com.alro.zoo.posts;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alro.zoo.posts.dtos.PostDTO;


@Controller
public class PostController {

	@Autowired
	private PostService service;
	
	@PostMapping("/post")
	public ResponseEntity<Post> postAnImage(@RequestParam("title") String title, @RequestParam("imagefile") MultipartFile file) {
		PostDTO postRequest= new PostDTO();
		postRequest.title = title;
		Post body = service.savePost(postRequest, file);
		return ResponseEntity.created(null).body(body);
	}
	
	@GetMapping("/posts/{userCode}")
	public ResponseEntity<List<Post>> getImagesByUser(){
		return null;
	}
	
	@GetMapping("/post/{postCode}")
	public ResponseEntity<Post> getImageByCode(@PathVariable String postCode){
		return null;
	}
	
	@GetMapping("/post/image/{postCode}")
	public ResponseEntity<byte[]> getImageByPostCode(@PathVariable String postCode){
		Post recipeCommand = service.findById(postCode);
		

        if (recipeCommand.getImage() != null) {
            byte[] byteArray = new byte[recipeCommand.getImage().length];
            int i = 0;

            for (Byte wrappedByte : recipeCommand.getImage()){
                byteArray[i++] = wrappedByte; //auto unboxing
            }

                      
            return ResponseEntity.accepted().contentType(MediaType.IMAGE_JPEG).body(byteArray);
        }
		return null;
	}
	
	
	
}
