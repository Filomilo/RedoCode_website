package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.RedoCodeController;
import com.redocode.backend.RequstHandling.Requests.CodeRunnerRequest;
import com.redocode.backend.RequstHandling.Requests.CodeTestRequest;
import com.redocode.backend.RequstHandling.Requests.SingleDatabaseExerciseTestRequest;
import com.redocode.backend.Tools.RedoCodeObjectMapper;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Variables.SingleInteger;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.VmAcces.CodeRunnersController;
import com.redocode.backend.database.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class UnsolvedDatabaseTestsHandlerTest {
    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    CodeRunnersController codeRunnersControlle;

    @AfterEach
    void clearCodeRunners()
    {
        codeRunnersControlle.reset();
    }


    @Test
    @SneakyThrows
    void handle() {
        Excersize excersize= exerciseRepository.findAll().get(0);
        List<ExerciseTests> exerciseTests=  excersize.getExerciseTests();

        User user = User.builder().email("test").build();

        HashMap solutionCodes = new HashMap();
        solutionCodes.put(CODE_RUNNER_TYPE.CPP_RUNNER,"int solution(int x){return x+1;}");
        int[] arrayOfInputs={1,2,3,4,5};
        int[] arrayOfExpectedOuputs={2,3,4,5,6};

        List<ExerciseTests> tests=new ArrayList<>();
        for(int i=0;i<arrayOfInputs.length;i++){
            tests.add(ExerciseTests.builder()
                            .input(RedoCodeObjectMapper.VarAsString(new SingleInteger(arrayOfInputs[i])))
                            .expectedOutput(null)
                    .build());
        }


        SingleDatabaseExerciseTestRequest singleDatabaseExerciseTestRequest= (SingleDatabaseExerciseTestRequest) SingleDatabaseExerciseTestRequest.builder()
                .idOfExercise(excersize.getId())
                .user(user)
                .timeForExecution(1000L)
                .timeForTaskMin(100L)
                .solutionCodes(solutionCodes)
                .inputType(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
                .outputType(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
                .build();
        singleDatabaseExerciseTestRequest.setAutotestsToRun(tests);
        log.info("request: "+ singleDatabaseExerciseTestRequest);

        CodeRunnerAccesValidationHandler codeRunnerAccesValidationHandler= new CodeRunnerAccesValidationHandler();
        codeRunnerAccesValidationHandler.handle(CodeRunnerRequest.builder().ram(256).user(user).codeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER).build());

        UnsolvedDatabaseTestsHandler unsolvedDatabaseTests= new UnsolvedDatabaseTestsHandler();
        SingleDatabaseExerciseTestRequest resultingRequest= (SingleDatabaseExerciseTestRequest) unsolvedDatabaseTests.handle(singleDatabaseExerciseTestRequest);

        log.info("resultingRequest: "+ Arrays.toString(resultingRequest.getAutotestsToRun().toArray()));


        for (int i = 0; i < arrayOfInputs.length ; i++) {
            log.info("checking test: "+resultingRequest.getAutotestsToRun().get(i) );
            int valueINputarrayOfInputs=
                    (int) resultingRequest.getAutotestsToRun().get(i).getParsedInput(Variables.VARIABLES_TYPES.SINGLE_INTEGER).getValue();

            assertNotNull( resultingRequest.getAutotestsToRun().get(i).getParsedOutput(Variables.VARIABLES_TYPES.SINGLE_INTEGER));
            assertEquals(valueINputarrayOfInputs+1,
                    resultingRequest.getAutotestsToRun().get(i).getParsedOutput(Variables.VARIABLES_TYPES.SINGLE_INTEGER).getValue()
            );
        }

//        GetExerciseTestsFromDataBase getExerciseTestsFromDataBaseTest= new GetExerciseTestsFromDataBase();
//        singleDatabaseExerciseTestRequest.getIdOfExercise();
//        CodeTestRequest codeTestRequest= (CodeTestRequest) getExerciseTestsFromDataBaseTest.handle(singleDatabaseExerciseTestRequest);
//        for (int i=0;i<exerciseTests.size();i++)
//        {
//            assertEquals(exerciseTests.get(i).getInput(),codeTestRequest.getTestsToRun().get(i).getInput());
//            assertEquals(exerciseTests.get(i).getExpectedOutput(),codeTestRequest.getTestsToRun().get(i).getExpectedOutput());
//        }



        log.info(Arrays.toString(exerciseTests.toArray()));
    }
}