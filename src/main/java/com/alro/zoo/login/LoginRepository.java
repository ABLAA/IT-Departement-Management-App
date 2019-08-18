package com.alro.zoo.login;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alro.zoo.user.User;

public interface LoginRepository extends JpaRepository<Login, String> {
	Optional<Login> findOneByPseudo(String pseudo);
	Optional<Login> findOneByEmail(String email);
	
}
