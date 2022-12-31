package com.tasc.apigw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.tass.microservice.redis.repository.UserLoginRepository;

@SpringBootApplication
public class ApiGwApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGwApplication.class, args);
    }

    @Bean
    UserLoginRepository userLoginRepository() { return userLoginRepository();}
}
