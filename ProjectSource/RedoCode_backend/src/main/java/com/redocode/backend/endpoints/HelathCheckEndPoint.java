package com.redocode.backend.endpoints;

import com.redocode.backend.Messages.Authentication.AuthenticationRequest;
import com.redocode.backend.database.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
public class HelathCheckEndPoint {
    @Autowired
    UsersRepository usersRepository;

    Logger logger = LoggerFactory.getLogger(HelathCheckEndPoint.class);

    @GetMapping("/public/healthcheck/hello")
    public ResponseEntity<String> hello(@RequestHeader Map<String, String> headers) {
        // health check
        log.info("hello");
        return ResponseEntity.ok("hello");
    }


    @GetMapping("/secure/healthcheck/hello")
    public ResponseEntity<String> helloSecured() {
        // health check
        log.info("helloSecured");
        return ResponseEntity.ok("hello secret");
    }

}
