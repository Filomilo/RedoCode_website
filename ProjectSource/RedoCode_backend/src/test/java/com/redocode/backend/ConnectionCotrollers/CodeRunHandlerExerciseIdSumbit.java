package com.redocode.backend.ConnectionCotrollers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.redocode.backend.Messages.CodeRunnerRequestMessage;
import com.redocode.backend.Messages.CodeRunningMessages.ExerciseCreatorValidationMessage;
import com.redocode.backend.Messages.CodeRunningMessages.ExerciseIdToRunMessage;
import com.redocode.backend.Messages.ExecutionResponses.ExecutionChainScheme;
import com.redocode.backend.Messages.ExecutionResponses.ExecutionResponseBase;
import com.redocode.backend.Messages.ExecutionResponses.ExecutionResponseStatusUpdate;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.WebSocketTestBase;
import com.redocode.backend.database.Excersize;
import com.redocode.backend.database.ExerciseTests;
import com.redocode.backend.database.ProgrammingLanguageRepository;
import com.redocode.backend.database.SolutionProgramsRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.redocode.backend.ConnectionCotrollers.CodeRunHandlerStompTest.objectMapper;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisabledOnOs(OS.LINUX)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CodeRunHandlerExerciseIdSumbit extends WebSocketTestBase {
    @Autowired
    SolutionProgramsRepository solutionProgramsRepository;
    @Autowired
    ProgrammingLanguageRepository programmingLanguageRepository;
    @LocalServerPort
    int port;

    @Override
    protected String getWebSocketUri() {
        return getWebSocketUri(port);
    }

    @BeforeAll
    void setUp() {
        assertDoesNotThrow(
                () -> {
                    super.setup();
                });
    }


    @SneakyThrows
    @Override
    @AfterAll
    protected void tearDown() {
        super.tearDown();
    }

    @Test
    @SneakyThrows
    void exerciseIdSumbit() {
        subscribe("/user/public/topic/ExecutionResponses");
        CodeRunnerRequestMessage codeRunnerRequestMessage =
                CodeRunnerRequestMessage.builder().CodeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER).build();

        session.send(
                "/public/app/codeRunnerRequest", mapper.writeValueAsBytes(codeRunnerRequestMessage));
        log.info(
                "messge send to /public/app/codeRunnerRequest with content: "
                        + mapper.writeValueAsString(codeRunnerRequestMessage));

        String reqMes = mapper.writeValueAsString(codeRunnerRequestMessage);
        log.info("reqMes: \n" + reqMes);
        TimeUnit.SECONDS.sleep(2);









        ExerciseIdToRunMessage exerciseIdToRunMessage =
                ExerciseIdToRunMessage.builder()
                        .exercise_id(1L)
                        .code(
                                solutionProgramsRepository
                                        .findFirstByLanguageIdAndExcersizeId(
                                                programmingLanguageRepository.findByName("cpp").getId(), 1L)
                                        .getCode())
                        .build();

            // exercise chain template
            List<ChainNodeInfo> correctNewWxecisehainList = new ArrayList<>();
            correctNewWxecisehainList.add(
                    ChainNodeInfo.builder()
                            .nodeName("Retrieving information from the database")
                            .processingMessage("Pending")
                            .status(ChainNodeInfo.CHAIN_NODE_STATUS.PENDING)
                            .build());
            correctNewWxecisehainList.add(
                    ChainNodeInfo.builder()
                            .nodeName("Validating access to coderunner")
                            .processingMessage("Pending")
                            .status(ChainNodeInfo.CHAIN_NODE_STATUS.PENDING)
                            .build());
            correctNewWxecisehainList.add(
                    ChainNodeInfo.builder()
                            .nodeName("Generating automatic tests")
                            .processingMessage("Pending")
                            .status(ChainNodeInfo.CHAIN_NODE_STATUS.PENDING)
                            .build());
            correctNewWxecisehainList.add(
                    ChainNodeInfo.builder()
                            .nodeName("Solving automated exercises")
                            .processingMessage("Pending")
                            .status(ChainNodeInfo.CHAIN_NODE_STATUS.PENDING)
                            .build());
            correctNewWxecisehainList.add(
                    ChainNodeInfo.builder()
                            .nodeName("Testing code")
                            .processingMessage("Pending")
                            .status(ChainNodeInfo.CHAIN_NODE_STATUS.PENDING)
                            .build());
            correctNewWxecisehainList.add(
                    ChainNodeInfo.builder()
                            .nodeName("Saving solution to database")
                            .processingMessage("Pending")
                            .status(ChainNodeInfo.CHAIN_NODE_STATUS.PENDING)
                            .build());




            int amoutOfSolutionBefore = solutionProgramsRepository.findAll().size();



        session.send(
                "/public/app/CodeRun/ExerciseIdValidation", mapper.writeValueAsBytes(exerciseIdToRunMessage));
        log.info(
                "messge send to /public/app/CodeRun/ExerciseIdValidation with content: "
                        + mapper.writeValueAsString(exerciseIdToRunMessage));




        assertEquals(
                ExecutionChainScheme.builder()
                        .levels(correctNewWxecisehainList)
                        .messageType(ExecutionResponseBase.EXECUTION_RESPONSE_TYPE.CHAIN_SCHEME)
                        .build(),
                (ExecutionChainScheme)
                        objectMapper.readValue(blockingQueue.poll(20, SECONDS), ExecutionChainScheme.class));
//

        await()
                .atMost(120, SECONDS)
                .untilAsserted(
                        () -> {
                            assertEquals(
                                   12,
                                            blockingQueue.size());
                        });


        HashSet<ExecutionResponseStatusUpdate> messages = new HashSet();

        for (int i = 0; i < 12; i++) {
            messages.add(objectMapper.readValue(
                    blockingQueue.poll(200, SECONDS), ExecutionResponseStatusUpdate.class));
        }



            assertTrue(
                    messages.contains( ExecutionResponseStatusUpdate.builder()
                            .message("Loading data")
                            .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING)
                            .stepUpdate(0)
                            .build())
            );

        assertTrue(
                messages.contains( ExecutionResponseStatusUpdate.builder()
                        .message("Data loaded")
                        .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS)
                        .stepUpdate(0)
                        .build())
        );

        assertTrue(
                messages.contains(  ExecutionResponseStatusUpdate.builder()
                        .message("validating access to CPP_RUNNER")
                        .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING)
                        .stepUpdate(1)
                        .build())
        );

        assertTrue(
                messages.contains(   ExecutionResponseStatusUpdate.builder()
                        .message("Validated access to CPP_RUNNER")
                        .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS)
                        .stepUpdate(1)
                        .build())
        );


        assertTrue(
                messages.contains(   ExecutionResponseStatusUpdate.builder()
                        .message("generation")
                        .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING)
                        .stepUpdate(2)
                        .build())
        );
        assertTrue(
                messages.contains(   ExecutionResponseStatusUpdate.builder()
                        .message("generated")
                        .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS)
                        .stepUpdate(2)
                        .build())
        );
        assertTrue(
                messages.contains(    ExecutionResponseStatusUpdate.builder()
                        .message("solving")
                        .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING)
                        .stepUpdate(3)
                        .build())
        );

        assertTrue(
                messages.contains(    ExecutionResponseStatusUpdate.builder()
                        .message("solved")
                        .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS)
                        .stepUpdate(3)
                        .build())
        );
        assertTrue(
                messages.contains(      ExecutionResponseStatusUpdate.builder()
                        .message("running CPP_RUNNER tests")
                        .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING)
                        .stepUpdate(4)
                        .build())
        );

        assertTrue(
                messages.contains(     ExecutionResponseStatusUpdate.builder()
                        .message("correct CPP_RUNNER tests")
                        .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS)
                        .stepUpdate(4)
                        .build())
        );
        assertTrue(
                messages.contains(  ExecutionResponseStatusUpdate.builder()
                        .message("Saving solution to database")
                        .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING)
                        .stepUpdate(5)
                        .build())
        );
        assertTrue(
                messages.contains(  ExecutionResponseStatusUpdate.builder()
                        .message("Saved solution")
                        .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS)
                        .stepUpdate(5)
                        .build())
        );



        int amoutOfSolutionAfter = solutionProgramsRepository.findAll().size();


        assertEquals(amoutOfSolutionBefore+1, amoutOfSolutionAfter);
    }

}
