package com.playground.kinesis.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.setProperty("com.amazonaws.sdk.disableCbor", "true");
        SpringApplication.run(Application.class, args);
    }

}
