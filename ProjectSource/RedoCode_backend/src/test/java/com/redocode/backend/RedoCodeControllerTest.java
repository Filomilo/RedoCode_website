package com.redocode.backend;

import com.redocode.backend.Auth.UnauthenticatedUser;
import com.redocode.backend.Auth.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedoCodeControllerTest {

    @Autowired
    RedoCodeController redoCodeController;
    @BeforeEach
    void setUp() {
        redoCodeController.reset();
    }

    @Test
    void getInstance() {
        RedoCodeController redoCodeController=RedoCodeController.getInstance();
        RedoCodeController redoCodeController1=RedoCodeController.getInstance();
        assertEquals(redoCodeController,redoCodeController1);
    }

    @Test
    void addConnectedUser() {
        UnauthenticatedUser user1=new UnauthenticatedUser("1");
        UnauthenticatedUser user2= new UnauthenticatedUser("2");

        int amountOfUsersBeforeAdding=redoCodeController.connectedUsers.size();
        redoCodeController.addConnectedUser(user1);
        int aomuntAfterFristAdditon=redoCodeController.connectedUsers.size();
        redoCodeController.addConnectedUser(user2);
        int amountAfterSecondAddition=redoCodeController.connectedUsers.size();
        redoCodeController.addConnectedUser(user1);
        redoCodeController.addConnectedUser(user2);
        int amountAfterAdingTheSame=redoCodeController.connectedUsers.size();

        assertEquals(0,amountOfUsersBeforeAdding);
        assertEquals(1,aomuntAfterFristAdditon);
        assertEquals(2,amountAfterSecondAddition);
        assertEquals(2,amountAfterAdingTheSame);
        assertTrue(redoCodeController.connectedUsers.keySet().stream().anyMatch(user -> (user.equals(user1))));
        assertTrue(redoCodeController.connectedUsers.keySet().stream().anyMatch(user -> (user.equals(user2))));
    }

    @Test
    void removeConnectedUser() {
        UnauthenticatedUser user1=new UnauthenticatedUser("1");
        UnauthenticatedUser user2= new UnauthenticatedUser("2");
        redoCodeController.addConnectedUser(user1);
        redoCodeController.addConnectedUser(user2);
        int amountOfUsersBeforeRemoving=redoCodeController.connectedUsers.size();
        redoCodeController.removeConnectedUser(user1);
        int amountOfUsersAfterRemoving=redoCodeController.connectedUsers.size();
        Boolean doesContainUser1=redoCodeController.connectedUsers.keySet().stream().anyMatch(user -> (user.equals(user1)));
        Boolean doesContainUser2=redoCodeController.connectedUsers.keySet().stream().anyMatch(user -> (user.equals(user2)));
        redoCodeController.removeConnectedUser("2");
        Boolean doesContainUser2AfterRemoval=redoCodeController.connectedUsers.keySet().stream().anyMatch(user -> (user.equals(user2)));
        int amountOfUsersAfterRemovingSecond=redoCodeController.connectedUsers.size();

        assertEquals(2,amountOfUsersBeforeRemoving);
        assertEquals(1,amountOfUsersAfterRemoving);
        assertTrue(doesContainUser2);
        assertFalse(doesContainUser1);
        assertFalse(doesContainUser2AfterRemoval);
        assertEquals(0,amountOfUsersAfterRemovingSecond);
    }



    @Test
    void getUserById() {
        UnauthenticatedUser user1=new UnauthenticatedUser("1");
        UnauthenticatedUser user2= new UnauthenticatedUser("2");
        redoCodeController.addConnectedUser(user1);
        redoCodeController.addConnectedUser(user2);
        User user1Recived=redoCodeController.getUserById("1");
        User user2Recived=redoCodeController.getUserById("2");
        assertEquals(user1,user1Recived);
        assertEquals(user2,user2Recived);
    }


}