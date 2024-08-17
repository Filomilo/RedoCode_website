package com.redocode.backend.ConnectionCotrollers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redocode.backend.Messages.CodeRunnerRequestMessage;
import com.redocode.backend.Messages.CodeRunningMessages.ExerciseIdToRunMessage;
import com.redocode.backend.Messages.CodeRunningMessages.ProgramResultsMessage;
import com.redocode.backend.Messages.CodeRunningMessages.RawCodeToRunMessage;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.ConsoleOutput;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.WebSocketTestBase;
import com.redocode.backend.database.ProgrammingLanguageRepository;
import com.redocode.backend.database.SolutionProgramsRepository;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.redocode.backend.ConnectionCotrollers.CodeRunHandlerStompTest.objectMapper;
import static com.redocode.backend.ConnectionCotrollers.ConnectionTargets.INrunExerciseById;
import static com.redocode.backend.ConnectionCotrollers.ConnectionTargets.INrunRawCode;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisabledOnOs(OS.LINUX)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Disabled("tet do not owtk when run along sie other for currently uknonwn reason")
// @RunWith(SpringRunner.class)
// @DirtiesContext
// @DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CodeRunExerciseIDSingleStompTest extends WebSocketTestBase {
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

@Data
    public static class MockingMessageAnwser{
        @Data
        public static class MockingMessageAnwserPart{
            ConsoleOutput consoleOutput;
            int variables;
            int variablesInput;
        }

        List<MockingMessageAnwserPart> results;
    }
    MockingMessageAnwser result;
    @SneakyThrows
    @Override
    @AfterAll
    protected void tearDown() {
        super.tearDown();
    }
    @Test
    //todo: shoudl be replaced b writer a proper parser for Vairables class


    void runFibonachiExercise() throws InterruptedException, JsonProcessingException {
        subscribe("/user/public/topic/codeRunnerResults");
        CodeRunnerRequestMessage codeRunnerRequestMessage =
                CodeRunnerRequestMessage.builder().CodeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER).build();



        session.send(
                "/public/app/codeRunnerRequest", mapper.writeValueAsBytes(codeRunnerRequestMessage));
        TimeUnit.SECONDS.sleep(2);

        ExerciseIdToRunMessage exerciseIdToRunMessage= ExerciseIdToRunMessage.builder()
                .exercise_id(1L)
                .code(solutionProgramsRepository.findByLanguageIdAndExerciseId(
                        programmingLanguageRepository.findByName("cpp").getId()
                        , 1L
                ).getCode())
                .build();

        session.send("/public/app" + INrunExerciseById, mapper.writeValueAsBytes(exerciseIdToRunMessage));
        log.info("messge send to " + "/app" + INrunExerciseById);

//        ProgramResult correctResults =
//                ProgramResult.builder()
//                        .consoleOutput(
//                                ConsoleOutput.builder().errorOutput("").exitCode(0).output("Hello world").build())
//                        .variables(null)
//                        .build();
//

        await()
                .atMost(600, SECONDS)
                .untilAsserted(
                        () -> {
                            result =objectMapper.readValue(
                                    blockingQueue.poll(600, SECONDS), MockingMessageAnwser.class
                            ) ;
                            ;
                                log.info("CodeRunExerciseIDSingleStompTest results: "+ result);


                        });
        assertNotNull(result);
        for (MockingMessageAnwser.MockingMessageAnwserPart part: result.results) {
            assertEquals("",part.consoleOutput.getErrorOutput());
            assertEquals("",part.consoleOutput.getOutput());
            assertEquals(fibonacci(part.variablesInput),part.variables);
        }
    }

    public static int fibonacci(int val) {
        if (val <= 0) {
            return 0;
        }

        int[] arr = new int[val];

        if (val >= 1)
            arr[0] = 0;
        if (val >= 2)
            arr[1] = 1;

        for (int i = 2; i < val; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }

        return arr[val - 1];
    }


}
