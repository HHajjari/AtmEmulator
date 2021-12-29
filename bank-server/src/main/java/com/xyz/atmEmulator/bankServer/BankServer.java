package com.xyz.atmEmulator.bankServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class BankServer {
    public static void main(String[] args) {
        SpringApplication.run(BankServer.class, args);
    }
}