package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.database.ExerciseTests;
import com.redocode.backend.database.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Time;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
@ContextConfiguration
class MultipleCodeTestHandlerTest {

    ExerciseTests[] tests;
    @BeforeEach
    void prepareTests()
    {
        tests= new ExerciseTests[]{
                ExerciseTests.builder()
                        .id(1l)
                        .expectedOutput("{\"value\": [1,2]}")
                        .input("{\"value\": [1,2]}")
                        .excersize(null)
                        .build(),
                ExerciseTests.builder()
                        .id(2l)
                        .expectedOutput("{\"value\": [1,2,3]}")
                        .input("{\"value\": [1,2,3]}")
                        .excersize(null)
                        .build(),
                ExerciseTests.builder()
                        .id(3l)
                        .expectedOutput("{\"value\": [1,2,3,4]}")
                        .input("{\"value\": [1,2,3,4]}")
                        .excersize(null)
                        .build(),
                ExerciseTests.builder()
                        .id(4l)
                        .expectedOutput("{\"value\": [1,2,3,4,5]}")
                        .input("{\"value\": [1,2,3,4,5]}")
                        .excersize(null)
                        .build(),
        };
    }

    @Test
    void handleCorrect() {
        User user=new User("@2");
        ExerciseCreationRequest exerciseCreationRequest= ExerciseCreationRequest.builder()
                .ram(1024)
                .Title("test")
                .Description("desription")
                .user(user)
                .testsToRun(tests)
                .inputType(Variables.VARIABLES_TYPES.ARRAY_OF_INTEGERS)
                .outputType(Variables.VARIABLES_TYPES.ARRAY_OF_INTEGERS)
                .timeForTask(new Time(1000))
                .timeForExecution(1000L)
                .solutionCodes(
                        new HashMap<CODE_RUNNER_TYPE, String>() {{
                            put(CODE_RUNNER_TYPE.CPP_RUNNER, "#include <iostream>\n" +
                                    "#include <vector>\n" +
                                    "\n" +
                                    "std::vector<int> solution(std::vector<int> in)\n" +
                                    "{\n" +
                                    "    return in;\n" +
                                    "}");
                            put(CODE_RUNNER_TYPE.JS_RUNNER, "function solution(array){return array;}");
                        }}
                )
               .build();


        MultipleCodeTestHandler multipleCodeTestHandler=new MultipleCodeTestHandler();
        assertDoesNotThrow(()->{
             multipleCodeTestHandler.handle(exerciseCreationRequest);
        });

    }


}