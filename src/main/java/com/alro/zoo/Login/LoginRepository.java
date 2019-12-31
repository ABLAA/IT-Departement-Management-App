package com.alro.zoo.Login;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, String> {
	Optional<Login> findOneByPseudo(String pseudo);
	Optional<Login> findOneByEmail(String email);
	
}
