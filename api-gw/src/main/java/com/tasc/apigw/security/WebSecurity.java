package com.tasc.apigw.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import vn.tass.microservice.redis.repository.UserLoginRepository;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Override
    public void configure(
            org.springframework.security.config.annotation.web.builders.WebSecurity web)
            throws Exception {
        web.ignoring().antMatchers( "/user/register" , "/login");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        HttpSecurity httpSecurity = http.headers().disable()
                .cors()
                .and()
                .requestCache().disable()
                .csrf().disable().authorizeRequests().and();

        BasicAuthenticationFilter filter = new Oauth2AuthorizationFilter(authenticationManager() , userLoginRepository);
        httpSecurity.addFilterBefore(filter, BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling();

        http.authorizeRequests().anyRequest().authenticated();
    }

}
