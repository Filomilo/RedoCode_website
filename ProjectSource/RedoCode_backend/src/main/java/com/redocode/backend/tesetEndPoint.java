package com.redocode.backend;

import com.redocode.backend.database.User;
import com.redocode.backend.database.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class tesetEndPoint {
    @Autowired
    UsersRepository usersRepository;

    Logger logger= LoggerFactory.getLogger(tesetEndPoint.class);

    @GetMapping("/hello")
    public String hello()
    {
        // health check
        return "hello";
    }
    @GetMapping("/users")
    public List<User> users()
    {
        logger.info("Requesting user data");
        List<User> users=usersRepository.findAll();
        return users;
    }

}
