package com.xyz.atmEmulator.bankServer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                //.antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated();

        http.headers().frameOptions().disable();

    }
}