package com.redocode.backend.databse;

import com.redocode.backend.database.User;
import com.redocode.backend.database.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@ContextConfiguration
public class UserRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void testFindByUserName() {
        String userNameTest="test";
        User user = new User(userNameTest);
        usersRepository.save(user);
        User foundUser = usersRepository.findByUserName(userNameTest);
        assertNotNull(foundUser);
        assertEquals(userNameTest, foundUser.getUserName());
    }
}
