package com.redocode.backend.database;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@Slf4j
@Disabled("Islotating specific test for debugging")
public class UserRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void testFindByUserName() {
        String userNameTest="test";
        User user = User.builder()
                .type(User.USER_TYPE.AUTHENTICATED)
                .email("email"+ UUID.randomUUID() +"@email.com")
                .password("password")
                .nickname(userNameTest)
                .build();



        log.info("user: "+ user);
      //  user=usersRepository.findAll().get(0);
        log.info("user: "+ user);
        usersRepository.save(user);
        User foundUser = usersRepository.findByNickname(userNameTest);
        log.info("all user: "+ Arrays.toString(usersRepository.findAll().toArray()));
        assertNotNull(foundUser);
        assertEquals(userNameTest, foundUser.getNickname());
    }
}
