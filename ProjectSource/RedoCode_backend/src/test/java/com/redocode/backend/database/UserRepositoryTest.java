package com.redocode.backend.database;

import com.redocode.backend.database.User;
import com.redocode.backend.database.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@ContextConfiguration
@Slf4j
public class UserRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void testFindByUserName() {
        String userNameTest="test";
        User user = new User("session",userNameTest, User.USER_TYPE.AUTHENTICATED);
        log.info("user: "+ user);
      //  user=usersRepository.findAll().get(0);
        log.info("user: "+ user);
        usersRepository.save(user);
        User foundUser = usersRepository.findByUserName(userNameTest);
        log.info("all user: "+ Arrays.toString(usersRepository.findAll().toArray()));
        assertNotNull(foundUser);
        assertEquals(userNameTest, foundUser.getUserName());
    }
}
