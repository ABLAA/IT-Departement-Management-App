package com.alro.zoo.discussion.discussion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alro.zoo.user.User;

public interface DisussionRepository extends JpaRepository<Discussion, String> {
	
	List<Discussion> findAllByFirstInterlocutor(User firstInterlocutor);
	List<Discussion> findAllBySecondInterlocutor(User secondInterlocutor);
	Optional<Discussion> findOneByFirstInterlocutorAndSecondInterlocutor(User firstInterlocutor, User secondInterlocutor);

}
