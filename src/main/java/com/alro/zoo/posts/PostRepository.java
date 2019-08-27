package com.alro.zoo.posts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alro.zoo.user.User;

public interface PostRepository extends JpaRepository<Post, String> {

	List<Post> findAllByAuthor(User author);
	
}
