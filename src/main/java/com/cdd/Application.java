package com.cdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.security.Principal;

import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;

@EnableTransactionManagement
@ComponentScan(basePackages = {"com.cdd"})
@SpringBootApplication
public class Application /*extends SpringBootServletInitializer*/{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @RequestMapping("/validateUser")
	public Principal user(Principal user) {
		return user;
	}

}