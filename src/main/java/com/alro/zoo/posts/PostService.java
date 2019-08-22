package com.alro.zoo.posts;


import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.alro.zoo.login.LoginService;
import com.alro.zoo.posts.dtos.PostDTO;
import com.alro.zoo.shared.GenericService;

@Service
@Transactional
public class PostService extends GenericService<Post, PostRepository> {

	@Autowired
	private PostRepository repo;
	
	@Autowired
	private LoginService loginService;
	
	@Override
	public PostRepository getRepo() {
		return repo;
	}
	
	@Override
	public String getPrefix() {
		return Post.prefix;
	}
	
    
    public Byte[] createImageByteObject( MultipartFile file) {

        try {

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }
            
            return byteObjects;


        } catch (IOException e) {
            System.err.println("Error occurred" + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "cannot create image byte");
        }
    }
    
    public Post savePost(PostDTO dto, MultipartFile file) {
    	Post post = new Post();
    	post.setCode(generateNewCode());
    	post.setAuthor(loginService.getConnectedUser());
    	post.setTitle(dto.title);
    	post.setCreatedAt(new Date());
    	post.setImage(createImageByteObject(file));
    	return repo.save(post);
    }
    
    
    
}
