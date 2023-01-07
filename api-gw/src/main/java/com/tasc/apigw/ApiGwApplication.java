package com.tasc.apigw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.web.ZuulController;
import org.springframework.context.annotation.Bean;
import vn.tass.microservice.redis.repository.UserLoginRepository;

import java.util.Collection;
import java.util.List;

@SpringBootApplication
@EnableZuulProxy
public class ApiGwApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGwApplication.class, args);
    }

}
