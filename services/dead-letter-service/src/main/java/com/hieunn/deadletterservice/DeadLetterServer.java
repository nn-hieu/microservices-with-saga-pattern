package com.hieunn.deadletterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeadLetterServer {

    public static void main(String[] args) {
        SpringApplication.run(DeadLetterServer.class, args);
    }

}
