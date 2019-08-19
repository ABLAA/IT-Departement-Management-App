package com.alro.zoo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.alro.zoo.configuration.jwt.JwtTokenUtil;
import com.alro.zoo.shared.GeneralMethods;

@SpringBootApplication
public class ZooApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext application = SpringApplication.run(ZooApplication.class, args);
		GeneralMethods methods = (GeneralMethods) application.getBean("GeneralMethods");
		System.out.println(methods.generateAnId("USR"));

	}

}
