package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.RequstHandling.Requests.CodeRunnerRequest;
import com.redocode.backend.RequstHandling.Requests.CodeTestRequest;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.RequstHandling.Requests.SingleDatabaseExerciseTestRequest;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.database.Excersize;
import com.redocode.backend.database.ExerciseRepository;
import com.redocode.backend.database.ExerciseTests;
import com.redocode.backend.database.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Loader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class GetExerciseTestsFromDataBaseTest {

    @Autowired
    private ExerciseRepository exerciseRepository;


    @Test
    @SneakyThrows
    void handle() {
        Excersize excersize= exerciseRepository.findAll().get(0);
        List<ExerciseTests>exerciseTests=  excersize.getExerciseTests();

        User user = User.builder().email("test").build();

        HashMap solutionCodes = new HashMap();
        solutionCodes.put(CODE_RUNNER_TYPE.CPP_RUNNER,"test");
        CodeRunnerRequest codeRunnerRequest= CodeRunnerRequest.builder()
                .user(user)
                .codeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER)
                .build();
        CodeRunnerAccesValidationHandler codeRunnerAccesValidationHandler = new CodeRunnerAccesValidationHandler();
        codeRunnerAccesValidationHandler.handle(codeRunnerRequest);

        SingleDatabaseExerciseTestRequest singleDatabaseExerciseTestRequest= SingleDatabaseExerciseTestRequest.builder()
                .idOfExercise(excersize.getId())
                .user(user)
                .timeForExecution(1000L)
                .timeForTaskMin(100L)
                .solutionCodes(solutionCodes)
                .build()
                ;
        GetExerciseTestsFromDataBase getExerciseTestsFromDataBaseTest= new GetExerciseTestsFromDataBase();
        singleDatabaseExerciseTestRequest.getIdOfExercise();
        CodeTestRequest codeTestRequest= (CodeTestRequest) getExerciseTestsFromDataBaseTest.handle(singleDatabaseExerciseTestRequest);
        for (int i=0;i<exerciseTests.size();i++)
        {
            assertEquals(exerciseTests.get(i).getInput(),codeTestRequest.getTestsToRun().get(i).getInput());
            assertEquals(exerciseTests.get(i).getExpectedOutput(),codeTestRequest.getTestsToRun().get(i).getExpectedOutput());
        }



    log.info(Arrays.toString(exerciseTests.toArray()));
    }
}