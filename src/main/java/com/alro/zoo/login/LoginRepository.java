package com.alro.zoo.login;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alro.zoo.user.User;

public interface LoginRepository extends JpaRepository<Login, String> {

}
