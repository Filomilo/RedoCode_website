package com.redocode.backend;

import com.redocode.backend.database.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration
@Slf4j
//@Disabled("Islotating specific test for debugging")
class RedoCodeControllerTest {

    @Autowired
    RedoCodeController redoCodeController;

    @BeforeEach
    void setUp() {
        redoCodeController.reset();
    }

//    @Test
//    void getInstance() {
//        RedoCodeController redoCodeController=RedoCodeController.getInstance();
//        RedoCodeController redoCodeController1=RedoCodeController.getInstance();
//        assertEquals(redoCodeController,redoCodeController1);
//    }

    @Test
    void addConnectedUser() {
        User user1 = new User("1");
        User user2 = new User("2");

        int amountOfUsersBeforeAdding = redoCodeController.connectedUsers.size();
        redoCodeController.addConnectedUser(user1);
        int aomuntAfterFristAdditon = redoCodeController.connectedUsers.size();
        redoCodeController.addConnectedUser(user2);
        int amountAfterSecondAddition = redoCodeController.connectedUsers.size();
        redoCodeController.addConnectedUser(user1);
        redoCodeController.addConnectedUser(user2);
        int amountAfterAdingTheSame = redoCodeController.connectedUsers.size();

        assertEquals(0, amountOfUsersBeforeAdding);
        assertEquals(1, aomuntAfterFristAdditon);
        assertEquals(2, amountAfterSecondAddition);
        assertEquals(2, amountAfterAdingTheSame);
        assertTrue(redoCodeController.connectedUsers.values().stream().anyMatch(user -> (user.equals(user1))));
        assertTrue(redoCodeController.connectedUsers.values().stream().anyMatch(user -> (user.equals(user2))));
    }

    @Test
    void removeConnectedUser() {
        User user1 = new User("1");
        User user2 = new User("2");
        redoCodeController.addConnectedUser(user1);
        redoCodeController.addConnectedUser(user2);
        int amountOfUsersBeforeRemoving = redoCodeController.connectedUsers.size();
        redoCodeController.removeConnectedUser(user1);
        int amountOfUsersAfterRemoving = redoCodeController.connectedUsers.size();
        log.info("checking doesContainUser1: "+ Arrays.toString(redoCodeController.connectedUsers.keySet().toArray()));
        Boolean doesContainUser1 = redoCodeController.connectedUsers.values().stream().anyMatch(user -> (user.equals(user1)));
        log.info("checking doesContainUser2: "+ Arrays.toString(redoCodeController.connectedUsers.keySet().toArray()));

        Boolean doesContainUser2 = redoCodeController.connectedUsers.values().stream().anyMatch(user -> (user.equals(user2)));
        redoCodeController.removeConnectedUser("2");
        Boolean doesContainUser2AfterRemoval = redoCodeController.connectedUsers.keySet().stream().anyMatch(user -> (user.equals(user2)));
        int amountOfUsersAfterRemovingSecond = redoCodeController.connectedUsers.size();

        assertEquals(2, amountOfUsersBeforeRemoving);
        assertEquals(1, amountOfUsersAfterRemoving);
        assertTrue(doesContainUser2);
        assertFalse(doesContainUser1);
        assertFalse(doesContainUser2AfterRemoval);
        assertEquals(0, amountOfUsersAfterRemovingSecond);
    }


    @Test
    void getUserById() {
        User user1 = new User("1");
        User user2 = new User("2");
        redoCodeController.addConnectedUser(user1);
        redoCodeController.addConnectedUser(user2);
        User user1Recived = redoCodeController.getUserByConnectionUUID("1");
        User user2Recived = redoCodeController.getUserByConnectionUUID("2");
        assertEquals(user1, user1Recived);
        assertEquals(user2, user2Recived);
    }

}