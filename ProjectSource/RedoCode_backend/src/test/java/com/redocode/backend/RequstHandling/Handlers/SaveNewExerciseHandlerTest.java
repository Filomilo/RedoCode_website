package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Auth.AuthenticatedUser;
import com.redocode.backend.Auth.User;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.database.Excersize;
import com.redocode.backend.database.ExerciseRepository;
import com.redocode.backend.database.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ContextConfiguration
@Slf4j
class SaveNewExerciseHandlerTest {
    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    UsersRepository usersRepository;
    SaveNewExerciseHandler saveNewExerciseHandler;

    @BeforeEach
    void prepareHadnler()
    {
        saveNewExerciseHandler=new SaveNewExerciseHandler();
    }


    @Test
    void handleSaving() {

        int amountOfExercisesinBegning=exerciseRepository.findAll().size();
        AuthenticatedUser user= new AuthenticatedUser("1",1);
        int ram=1024;
        int amountOfAutoTest=10;


        ExerciseCreationRequest exerciseCreationRequest=ExerciseCreationRequest.builder()
                .ram(ram)
                .user(user)
                .amountOfAutoTests(amountOfAutoTest)
                .build();
        assertDoesNotThrow(()->{
            assertTrue( saveNewExerciseHandler.handle(exerciseCreationRequest));
        });

        int amountOfExercisesinAfter=exerciseRepository.findAll().size();
        log.info("exercise list: "+         exerciseRepository.findAll().toString());

        Excersize lastAdded=exerciseRepository.findAll().get(amountOfExercisesinAfter-1);

        assertEquals(ram,lastAdded.getRam_mb());
        assertEquals(user.getDatabaseUserEntry(),lastAdded.getAuthor());
        assertEquals(amountOfAutoTest,lastAdded.getAmountOfAutoTests());
        assertEquals(amountOfExercisesinBegning+1,amountOfExercisesinAfter);
    }
}