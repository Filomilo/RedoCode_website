package com.redocode.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.redocode.backend.database")
public class RedoCodeBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedoCodeBackendApplication.class, args);
    }

}
