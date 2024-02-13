package com.redocode.backend.VmAcces;

import com.redocode.backend.RedoCodeController;
import org.junit.jupiter.api.*;
import com.redocode.backend.Auth.User
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodeRunnersControllerTest {


    CodeRunnersController codeRunnersController= CodeRunnersController.getInstance();
    RedoCodeController redoCodeController=RedoCodeController.getInstance();
    List<User> unathenicatedUsers=new ArrayList<User>();
    List<User> athenicatedUsers=new ArrayList<User>();
    List<User> premiumUsers=new ArrayList<User>();
    List<User> adminsUsers=new ArrayList<User>();



    @BeforeEach
    void setUp() {



        for (:
             ) {
            
        }
    }

    @AfterEach
    void tearDown() {
        
    }

    @Test
    void getUserVmStatus() {
    }

    @Test
    void destroyMachine() {
    }

    @Test
    void deregisterUser() {
    }

    @Test
    void requestVm() {
    }

    @Test
    void getInstnace() {
        CodeRunnersController codeRunnersController1= CodeRunnersController.getInstance();
        CodeRunnersController codeRunnersController2= CodeRunnersController.getInstance();
        assertEquals(this.codeRunnersController,codeRunnersController1);
        assertEquals(this.codeRunnersController,codeRunnersController2 );
    }
}