package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.RequstHandling.Requests.CodeRunnerRequest;
import com.redocode.backend.RequstHandling.Requests.CodeTestRequest;
import com.redocode.backend.RequstHandling.Requests.SingleDatabaseExerciseTestRequest;
import com.redocode.backend.RequstHandling.Requests.SpecifiedSingleDatabaseExerciseTestRequest;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.database.Excersize;
import com.redocode.backend.database.ExerciseRepository;
import com.redocode.backend.database.ExerciseTests;
import com.redocode.backend.database.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class GetExerciseDataFromDataBaseTest {

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
                .solutionCodes(solutionCodes)
                .build()
                ;
        GetExerciseDataFromDataBase getExerciseDataFromDataBase= new GetExerciseDataFromDataBase();
        singleDatabaseExerciseTestRequest.getIdOfExercise();
        SpecifiedSingleDatabaseExerciseTestRequest specifiedSingleDatabaseExerciseTestRequest= (SpecifiedSingleDatabaseExerciseTestRequest) getExerciseDataFromDataBase.handle(singleDatabaseExerciseTestRequest);

        assertEquals(excersize.getInputType(), specifiedSingleDatabaseExerciseTestRequest.getInputType());
        assertEquals(excersize.getOutputType(), specifiedSingleDatabaseExerciseTestRequest.getOutputType());
        assertEquals(excersize.getMaxExecutionTimeMS(),specifiedSingleDatabaseExerciseTestRequest.getTimeForExecution());
        assertEquals(excersize.getRam_mb(),specifiedSingleDatabaseExerciseTestRequest.getRam());

        for (int i=0;i<exerciseTests.size();i++)
        {
            assertEquals(exerciseTests.get(i).getInput(),specifiedSingleDatabaseExerciseTestRequest.getTestsToRun().get(i).getInput());
            assertEquals(exerciseTests.get(i).getExpectedOutput(),specifiedSingleDatabaseExerciseTestRequest.getTestsToRun().get(i).getExpectedOutput());
        }



    log.info(Arrays.toString(exerciseTests.toArray()));
    }
}