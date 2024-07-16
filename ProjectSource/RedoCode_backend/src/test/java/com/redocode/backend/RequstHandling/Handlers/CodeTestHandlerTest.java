package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.CodeRunnerRequest;
import com.redocode.backend.RequstHandling.Requests.CodeTestRequest;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.VmAcces.CodeRunnersController;
import com.redocode.backend.database.ExerciseTests;
import com.redocode.backend.database.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Disabled("not working under guthub pipilene")
class CodeTestHandlerTest {

    @Autowired CodeRunnersController codeRunnersController;
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
    void handleCpp() throws RequestHadndlingException {
        CodeRunnerAccesValidationHandler codeRunnerAccesValidationHandler= new CodeRunnerAccesValidationHandler();
        CodeTestHandler codeTestHandler=new CodeTestHandler();

        User user=new User("1234"+ UUID.randomUUID());


        CodeTestRequest codeTestRequest=CodeTestRequest.builder()
                .testsToRun(Arrays.stream(this.tests).toList())
                .codeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER)
                .Code("#include <iostream>\n" +
                        "#include <vector>\n" +
                        "\n" +
                        "std::vector<int> solution(std::vector<int> in)\n" +
                        "{\n" +
                        "    return in;\n" +
                        "}")
                .user(user)
                .inputType(Variables.VARIABLES_TYPES.ARRAY_OF_INTEGERS)
                .outputType(Variables.VARIABLES_TYPES.ARRAY_OF_INTEGERS)
                .build();


        assertNotNull( codeRunnerAccesValidationHandler.handle(CodeRunnerRequest.builder()
                .codeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER)
                .user(user)
                .build()
        ));
        CodeRunner codeRunner = codeRunnersController.getUserCodeRunner(user);
        assertNotNull(codeRunner);




        assertNotNull(codeTestHandler.handle(codeTestRequest));


    }

    @AfterEach
    void removePoteniatlDockerContaierd(){
        codeRunnersController.reset();
    }
}