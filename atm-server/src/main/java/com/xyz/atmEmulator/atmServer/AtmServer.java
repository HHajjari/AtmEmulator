package com.xyz.atmEmulator.atmServer;

//import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAdminServer
public class AtmServer {
    public static void main(String[] args) {
        SpringApplication.run(AtmServer.class, args);
    }
}