package com.example.streamfunctiondemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StreamFunctiondemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamFunctiondemoApplication.class, args);
    }

}
