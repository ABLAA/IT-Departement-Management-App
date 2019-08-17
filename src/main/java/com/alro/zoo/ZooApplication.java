package com.alro.zoo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.alro.zoo.configuration.Configuration;
import com.alro.zoo.shared.GeneralMethods;

@SpringBootApplication
public class ZooApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext application = SpringApplication.run(ZooApplication.class, args);
		GeneralMethods methods = (GeneralMethods) application.getBean("GeneralMethods");
		System.out.println(methods.generateAnId("USR"));
	}

}
