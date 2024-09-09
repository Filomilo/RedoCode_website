package com.redocode.backend.ConnectionCotrollers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redocode.backend.Messages.Authentication.Authentication;
import com.redocode.backend.Messages.CodeRunnerRequestMessage;
import com.redocode.backend.Messages.CodeRunningMessages.ExerciseCreatorValidationMessage;
import com.redocode.backend.Messages.CodeRunningMessages.ExerciseTestToRunMesseage;
import com.redocode.backend.Messages.CodeRunningMessages.ProgramResultsMessage;
import com.redocode.backend.Messages.CodeRunningMessages.RawCodeToRunMessage;
import com.redocode.backend.Messages.ExecutionResponses.ExecutionChainScheme;
import com.redocode.backend.Messages.ExecutionResponses.ExecutionResponseBase;
import com.redocode.backend.Messages.ExecutionResponses.ExecutionResponseStatusUpdate;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.RedoCodeController;
import com.redocode.backend.Secuirity.JwtService;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.ConsoleOutput;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunners.Variables.SingleInteger;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.VmAcces.CodeRunnersController;
import com.redocode.backend.WebSocketTestBase;
import com.redocode.backend.database.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.redocode.backend.ConnectionCotrollers.ConnectionTargets.*;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled("tet do not owtk when run along sie other for currently uknonwn reason")
// @RunWith(SpringRunner.class)
// @DirtiesContext
// @DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CodeRunHandlerStompTest extends WebSocketTestBase {

  @LocalServerPort int port;

  @Override
  protected String getWebSocketUri() {
    return getWebSocketUri(port);
  }

  @Autowired ExerciseRepository exerciseRepository;
  @Autowired UsersRepository usersRepository;
  @Autowired RedoCodeController redoCodeController;
  @Autowired CodeRunnersController codeRunnersController;
  @Autowired JwtService jwtService;
  static final String WEBSOCKET_TOPIC_DESTIN = "/public/app" + INrunExerciseCreatorValidationCode;
  static final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeAll
  void setUp() {
    assertDoesNotThrow(
        () -> {
          super.setup();
        });
  }

  @Test
  void runExerciseCreatorValidationCode() throws InterruptedException, JsonProcessingException {

    redoCodeController.addConnectedUser(usersRepository.getReferenceById(1l));
    User user = redoCodeController.getUserByConnectionByID(1l);

    session.send(
        "/public/app//tokenAuth",
        mapper.writeValueAsBytes(Authentication.builder().token(jwtService.generateToken(user))));

    subscribe("/user/public/topic/ExecutionResponses");
    Long userId = 1L;
    Variables.VARIABLES_TYPES inputType = Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_STRINGS;
    Variables.VARIABLES_TYPES ouptutType = Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_STRINGS;
    int ram = 1024;
    String title = "Exercise";
    String decritpion = "Descritpion";
    int amountOfAutoTests = 8;
    boolean breakCharacterInput = true;
    Range lengthRange = new Range(0F, 20F);
    boolean lowerCaseInput = true;
    boolean numberInput = true;
    boolean spaceInput = true;
    boolean specialCharacterInput = true;
    boolean upperCaseInput = true;
    Range xArrayRange = new Range(1F, 20F);
    Range yArrayRange = new Range(1F, 20F);
    long timeForTask = 60L;
    Long maxExecutionTimeMS = 1000L;

    // exercise chain template
    List<ChainNodeInfo> correctNewWxecisehainList = new ArrayList<>();
    correctNewWxecisehainList.add(
        ChainNodeInfo.builder()
            .nodeName("Validating user permissions")
            .processingMessage("Pending")
            .status(ChainNodeInfo.CHAIN_NODE_STATUS.PENDING)
            .build());
    correctNewWxecisehainList.add(
        ChainNodeInfo.builder()
            .nodeName("Validating exercise information")
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
            .nodeName("Preparing tests")
            .processingMessage("Pending")
            .status(ChainNodeInfo.CHAIN_NODE_STATUS.PENDING)
            .build());
    correctNewWxecisehainList.add(
        ChainNodeInfo.builder()
            .nodeName("Code testing")
            .processingMessage("Pending")
            .status(ChainNodeInfo.CHAIN_NODE_STATUS.PENDING)
            .build());
    correctNewWxecisehainList.add(
        ChainNodeInfo.builder()
            .nodeName("Saving exercise")
            .processingMessage("Pending")
            .status(ChainNodeInfo.CHAIN_NODE_STATUS.PENDING)
            .build());

    HashMap<CODE_RUNNER_TYPE, String> solutionCodes =
        new HashMap<>() {
          {
            put(
                CODE_RUNNER_TYPE.CPP_RUNNER,
                "#include <iostream>\n"
                    + "#include <vector>\n"
                    + "#include <string>\n"
                    + "\n"
                    + "std::vector<std::vector<std::string>>"
                    + " solution(std::vector<std::vector<std::string>> in)\n"
                    + "{\n"
                    + "    return in;\n"
                    + "}");
            put(CODE_RUNNER_TYPE.JS_RUNNER, "function solution(array){return array;}");
          }
        };
    ExerciseTests[] tests =
        new ExerciseTests[] {
          ExerciseTests.builder()
              .expectedOutput("{\"value\": [[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"]]}")
              .input("{\"value\": [[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"]]}")
              .excersize(null)
              .build(),
          ExerciseTests.builder()
              .expectedOutput(
                  "{\"value\":"
                      + " [[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"],[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"]]}")
              .input(
                  "{\"value\":"
                      + " [[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"],[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"]]}")
              .excersize(null)
              .build(),
        };

    int amountOfExeciseBeforeAdding = exerciseRepository.findAll().size();

    ExerciseCreatorValidationMessage creatorValidationMessage =
        ExerciseCreatorValidationMessage.builder()
            .title(title)
            .description(decritpion)
            .inputType(inputType)
            .outputType(ouptutType)
            .amountOfAutoTests(amountOfAutoTests)
            .lengthRange(lengthRange)
            .xArrayRange(xArrayRange)
            .yArrayRange(yArrayRange)
            .upperCaseInput(upperCaseInput)
            .lowerCaseInput(lowerCaseInput)
            .numberInput(numberInput)
            .specialCharacterInput(specialCharacterInput)
            .breakCharacterInupt(breakCharacterInput)
            .spaceInupt(spaceInput)
            .timeForTaskMin(timeForTask)
            .timeForExecutionMs(maxExecutionTimeMS)
            .solutionCodes(solutionCodes)
            .manualTests(Arrays.stream(tests).toList())
            .ram(ram)
            .build();

    session.send(WEBSOCKET_TOPIC_DESTIN, mapper.writeValueAsBytes(creatorValidationMessage));
    log.info(
        "messge send to "
            + WEBSOCKET_TOPIC_DESTIN
            + " OF content: "
            + mapper.writeValueAsString(creatorValidationMessage));

    Thread.sleep(2000);

    // checing messages
    assertEquals(
        ExecutionChainScheme.builder()
            .levels(correctNewWxecisehainList)
            .messageType(ExecutionResponseBase.EXECUTION_RESPONSE_TYPE.CHAIN_SCHEME)
            .build(),
        (ExecutionChainScheme)
            objectMapper.readValue(blockingQueue.poll(20, SECONDS), ExecutionChainScheme.class));

    /// todo: siplyfy await form
    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              assertEquals(
                  ExecutionResponseStatusUpdate.builder()
                      .message("Checking user type")
                      .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING)
                      .stepUpdate(0)
                      .build(),
                  objectMapper.readValue(
                      blockingQueue.poll(200, SECONDS), ExecutionResponseStatusUpdate.class));
            });

    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              assertEquals(
                  ExecutionResponseStatusUpdate.builder()
                      .message("Correct user type")
                      .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS)
                      .stepUpdate(0)
                      .build(),
                  objectMapper.readValue(
                      blockingQueue.poll(200, SECONDS), ExecutionResponseStatusUpdate.class));
            });

    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              assertEquals(
                  ExecutionResponseStatusUpdate.builder()
                      .message("Checking exercise information")
                      .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING)
                      .stepUpdate(1)
                      .build(),
                  objectMapper.readValue(
                      blockingQueue.poll(200, SECONDS), ExecutionResponseStatusUpdate.class));
            });

    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              assertEquals(
                  ExecutionResponseStatusUpdate.builder()
                      .message("Correct exercise setup")
                      .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS)
                      .stepUpdate(1)
                      .build(),
                  objectMapper.readValue(
                      blockingQueue.poll(200, SECONDS), ExecutionResponseStatusUpdate.class));
            });
    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              assertEquals(
                  ExecutionResponseStatusUpdate.builder()
                      .message("generation")
                      .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING)
                      .stepUpdate(2)
                      .build(),
                  objectMapper.readValue(
                      blockingQueue.poll(200, SECONDS), ExecutionResponseStatusUpdate.class));
            });
    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              assertEquals(
                  ExecutionResponseStatusUpdate.builder()
                      .message("generated")
                      .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS)
                      .stepUpdate(2)
                      .build(),
                  objectMapper.readValue(
                      blockingQueue.poll(200, SECONDS), ExecutionResponseStatusUpdate.class));
            });

    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              assertEquals(
                  ExecutionResponseStatusUpdate.builder()
                      .message("preparation")
                      .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING)
                      .stepUpdate(3)
                      .build(),
                  objectMapper.readValue(
                      blockingQueue.poll(200, SECONDS), ExecutionResponseStatusUpdate.class));
            });

    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              assertEquals(
                  ExecutionResponseStatusUpdate.builder()
                      .message("prepared tests")
                      .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS)
                      .stepUpdate(3)
                      .build(),
                  objectMapper.readValue(
                      blockingQueue.poll(200, SECONDS), ExecutionResponseStatusUpdate.class));
            });
    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              assertEquals(
                  ExecutionResponseStatusUpdate.builder()
                      .message("validating access to CPP_RUNNER")
                      .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING)
                      .stepUpdate(4)
                      .build(),
                  objectMapper.readValue(
                      blockingQueue.poll(200, SECONDS), ExecutionResponseStatusUpdate.class));
            });
    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              assertEquals(
                  ExecutionResponseStatusUpdate.builder()
                      .message("Validated access to CPP_RUNNER")
                      .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING)
                      .stepUpdate(4)
                      .build(),
                  objectMapper.readValue(
                      blockingQueue.poll(200, SECONDS), ExecutionResponseStatusUpdate.class));
            });

    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              assertEquals(
                  ExecutionResponseStatusUpdate.builder()
                      .message("running CPP_RUNNER tests")
                      .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING)
                      .stepUpdate(4)
                      .build(),
                  objectMapper.readValue(
                      blockingQueue.poll(200, SECONDS), ExecutionResponseStatusUpdate.class));
            });

    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              assertEquals(
                  ExecutionResponseStatusUpdate.builder()
                      .message("correct CPP_RUNNER tests")
                      .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING)
                      .stepUpdate(4)
                      .build(),
                  objectMapper.readValue(
                      blockingQueue.poll(200, SECONDS), ExecutionResponseStatusUpdate.class));
            });
    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              assertEquals(
                  ExecutionResponseStatusUpdate.builder()
                      .message("validating access to JS_RUNNER")
                      .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING)
                      .stepUpdate(4)
                      .build(),
                  objectMapper.readValue(
                      blockingQueue.poll(200, SECONDS), ExecutionResponseStatusUpdate.class));
            });
    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              assertEquals(
                  ExecutionResponseStatusUpdate.builder()
                      .message("Validated access to JS_RUNNER")
                      .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING)
                      .stepUpdate(4)
                      .build(),
                  objectMapper.readValue(
                      blockingQueue.poll(200, SECONDS), ExecutionResponseStatusUpdate.class));
            });
    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              assertEquals(
                  ExecutionResponseStatusUpdate.builder()
                      .message("running JS_RUNNER tests")
                      .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING)
                      .stepUpdate(4)
                      .build(),
                  objectMapper.readValue(
                      blockingQueue.poll(200, SECONDS), ExecutionResponseStatusUpdate.class));
            });
    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              assertEquals(
                  ExecutionResponseStatusUpdate.builder()
                      .message("correct JS_RUNNER tests")
                      .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING)
                      .stepUpdate(4)
                      .build(),
                  objectMapper.readValue(
                      blockingQueue.poll(200, SECONDS), ExecutionResponseStatusUpdate.class));
            });
    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              assertEquals(
                  ExecutionResponseStatusUpdate.builder()
                      .message("tests finished correctly")
                      .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS)
                      .stepUpdate(4)
                      .build(),
                  objectMapper.readValue(
                      blockingQueue.poll(200, SECONDS), ExecutionResponseStatusUpdate.class));
            });
    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              assertEquals(
                  ExecutionResponseStatusUpdate.builder()
                      .message("saving to database")
                      .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING)
                      .stepUpdate(5)
                      .build(),
                  objectMapper.readValue(
                      blockingQueue.poll(200, SECONDS), ExecutionResponseStatusUpdate.class));
            });
    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              assertEquals(
                  ExecutionResponseStatusUpdate.builder()
                      .message("saved to database")
                      .lvlStatus(ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS)
                      .stepUpdate(5)
                      .build(),
                  objectMapper.readValue(
                      blockingQueue.poll(100, SECONDS), ExecutionResponseStatusUpdate.class));
            });

    int amountOfExeciseAfterdding = exerciseRepository.findAll().size();
    Excersize lastAdded = exerciseRepository.findAll().get(exerciseRepository.findAll().size() - 1);

    //        assertEquals(amountOfExeciseBeforeAdding+1,amountOfExeciseAfterdding,"no new exercsie
    // was added");
    //        assertEquals(title,lastAdded.getExcersizeName());
    //        assertEquals(inputType,lastAdded.getInputType());
    //        assertEquals(ouptutType,lastAdded.getOutputType());
    //        assertEquals(ram,lastAdded.getRam_mb());
    //        assertEquals(decritpion,lastAdded.getDescription());
    //        assertEquals(userId,lastAdded.getAuthor().getId());
    //
    // assertEquals(Arrays.stream(tests).toList(),lastAdded.getExerciseTests().stream().toList());
    //
    //
    //        assertEquals(breakCharacterInput,lastAdded.getBreakCharacterInput());
    //        assertEquals(lowerCaseInput,lastAdded.getLowerCaseInput());
    //        assertEquals(breakCharacterInput,lastAdded.getLowerCaseInput());
    //        assertEquals(numberInput,lastAdded.getNumberInput());
    //        assertEquals(spaceInput,lastAdded.getSpaceInput());
    //        assertEquals(specialCharacterInput,lastAdded.getSpecialCharacterInput());
    //        assertEquals(upperCaseInput,lastAdded.getUpperCaseInput());
    //
    //        assertEquals(lengthRange,lastAdded.getLengthRange());
    //        assertEquals(xArrayRange,lastAdded.getXArrayRange());
    //        assertEquals(yArrayRange,lastAdded.getYArrayRange());
    //
    //        assertEquals(amountOfAutoTests,lastAdded.getAmountOfAutoTests());
    //        assertEquals(timeForTask,lastAdded.getTimeForTaskMin());
    //
    //        assertEquals(maxExecutionTimeMS,lastAdded.getMaxExecutionTimeMS());

  }

  @Test
  void rawCppHelloWorld() throws InterruptedException, JsonProcessingException {
    subscribe("/user/public/topic/codeRunnerResults");
    CodeRunnerRequestMessage codeRunnerRequestMessage =
        CodeRunnerRequestMessage.builder().CodeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER).build();

    RawCodeToRunMessage rawCodeToRunMessage =
        RawCodeToRunMessage.builder()
            .code("#include <iostream>\n" + "int main(){\n" + "std::cout<<\"Hello world\";\n" + "}")
            .build();

    session.send(
        "/public/app/codeRunnerRequest", mapper.writeValueAsBytes(codeRunnerRequestMessage));
    TimeUnit.SECONDS.sleep(2);
    session.send("/public/app" + INrunRawCode, mapper.writeValueAsBytes(rawCodeToRunMessage));
    log.info("messge send to " + "/app" + INrunRawCode);

    ProgramResult correctResults =
        ProgramResult.builder()
            .consoleOutput(
                ConsoleOutput.builder().errorOutput("").exitCode(0).output("Hello world").build())
            .variables(null)
            .build();

    await()
        .atMost(60, SECONDS)
        .untilAsserted(
            () -> {
              ProgramResultsMessage result =
                  objectMapper.readValue(
                      blockingQueue.poll(100, SECONDS), ProgramResultsMessage.class);
              ;

              assertEquals(1, result.getResults().size());
              assertEquals(correctResults, result.getResults().get(0));
            });
  }

  @Test
  void rawJsHelloWorld() throws InterruptedException, JsonProcessingException {
    subscribe("/user/public/topic/codeRunnerResults");
    CodeRunnerRequestMessage codeRunnerRequestMessage =
        CodeRunnerRequestMessage.builder().CodeRunnerType(CODE_RUNNER_TYPE.JS_RUNNER).build();

    RawCodeToRunMessage rawCodeToRunMessage =
        RawCodeToRunMessage.builder().code("console.log(\"Hello world\")").build();

    session.send(
        "/public/app/codeRunnerRequest", mapper.writeValueAsBytes(codeRunnerRequestMessage));
    log.info(
        "messge send to /public/app/codeRunnerRequest with content: "
            + mapper.writeValueAsString(codeRunnerRequestMessage));

    TimeUnit.SECONDS.sleep(20);
    session.send("/public/app" + INrunRawCode, mapper.writeValueAsBytes(rawCodeToRunMessage));
    log.info(
        "messge send to "
            + "/public/app"
            + INrunRawCode
            + " with content: "
            + mapper.writeValueAsString(rawCodeToRunMessage));

    //        Thread.sleep(3000);

    ProgramResult correctResults =
        ProgramResult.builder()
            .consoleOutput(
                ConsoleOutput.builder().errorOutput("").exitCode(0).output("Hello world").build())
            .variables(null)
            .build();

    await()
        .atMost(50, SECONDS)
        .untilAsserted(
            () -> {
              ProgramResultsMessage result =
                  objectMapper.readValue(
                      blockingQueue.poll(60, SECONDS), ProgramResultsMessage.class);
              ;

              assertEquals(1, result.getResults().size());
              assertEquals(correctResults, result.getResults().get(0));
            });
  }

  @ParameterizedTest
  @MethodSource(
      "com.redocode.backend.DataProviders.ValuesProvider#multipleDoubleArrayFloatProvider")
  void ruNExerciseTestCodesJsReturnTheSame(List<Float[][]> inputs)
      throws InterruptedException, JsonProcessingException {

    int amountOfAutoTests = 4;
    List<ExerciseTests> tests = new ArrayList<>();
    for (int i = 0; i < inputs.size(); i++) {
      tests.add(
          ExerciseTests.builder()
              .input(objectMapper.writeValueAsString(inputs.get(i)))
              .expectedOutput(objectMapper.writeValueAsString(inputs.get(i)))
              .build());
    }

    TimeUnit.SECONDS.sleep(2);

    subscribe("/user/public/topic/codeRunnerResults");
    CodeRunnerRequestMessage codeRunnerRequestMessage =
        CodeRunnerRequestMessage.builder().CodeRunnerType(CODE_RUNNER_TYPE.JS_RUNNER).build();

    ExerciseTestToRunMesseage exerciseTestToRunMesseage =
        ExerciseTestToRunMesseage.builder()
            .code("function solution(x)\n{\nreturn x;\n}")
            .amountOfAutoTests(amountOfAutoTests)
            .lengthRange(new Range(-3, 6))
            .inputType(String.valueOf(Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_FLOATS))
            .manualTests(tests)
            .executionTime(200L)
            .outputType(String.valueOf(Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_FLOATS))
            .xArrayRange(new Range(1, 7))
            .yArrayRange(new Range(4, 8))
            .lengthRange(new Range(-444, 555))
            .build();

    session.send(
        "/public/app/codeRunnerRequest", mapper.writeValueAsBytes(codeRunnerRequestMessage));
    log.info(
        "messge send to /public/app/codeRunnerRequest with content: "
            + mapper.writeValueAsString(codeRunnerRequestMessage));

    String reqMes = mapper.writeValueAsString(codeRunnerRequestMessage);
    log.info("reqMes: \n" + reqMes);
    TimeUnit.SECONDS.sleep(2);

    String mess = mapper.writeValueAsString(exerciseTestToRunMesseage);
    log.info("Messeage: \n" + mess);

    session.send(
        "/public/app" + INrunExercsieTestsCode,
        mapper.writeValueAsBytes(exerciseTestToRunMesseage));
    log.info(
        "messge send to "
            + "/public/app"
            + INrunRawCode
            + " with content: "
            + mapper.writeValueAsString(exerciseTestToRunMesseage));

    //        Thread.sleep(3000);

    String results = blockingQueue.poll(60, SECONDS);

    assertNotNull(results);
    assertTrue(results != "");

    log.info("results: \n\n\n" + results + "\n\n\n\n\n");

    int i = 0;
    Pattern p = Pattern.compile("consoleOutput");
    Matcher m = p.matcher(results);
    while (m.find()) {
      i++;
    }
    log.info("input size: " + inputs.size());
    assertEquals(amountOfAutoTests + inputs.size(), i);
  }

  @DirtiesContext
  @Test
  void ruNExerciseTestCodesJsReturnOneINcorrect()
      throws InterruptedException, JsonProcessingException {

    int amountOfAutoTests = 4;
    List<ExerciseTests> tests = new ArrayList<>();

    tests.add(
        ExerciseTests.builder()
            .input(objectMapper.writeValueAsString(new SingleInteger(1)))
            .expectedOutput(objectMapper.writeValueAsString(new SingleInteger(1)))
            .build());
    tests.add(
        ExerciseTests.builder()
            .input(objectMapper.writeValueAsString(new SingleInteger(1)))
            .expectedOutput(objectMapper.writeValueAsString(new SingleInteger(1)))
            .build());
    tests.add(
        ExerciseTests.builder()
            .input(objectMapper.writeValueAsString(new SingleInteger(1)))
            .expectedOutput(objectMapper.writeValueAsString(new SingleInteger(1)))
            .build());
    tests.add(
        ExerciseTests.builder()
            .input(objectMapper.writeValueAsString(new SingleInteger(1)))
            .expectedOutput(objectMapper.writeValueAsString(new SingleInteger(1)))
            .build());
    tests.add(
        ExerciseTests.builder()
            .input(objectMapper.writeValueAsString(new SingleInteger(2)))
            .expectedOutput(objectMapper.writeValueAsString(new SingleInteger(2)))
            .build());
    tests.add(
        ExerciseTests.builder()
            .input(objectMapper.writeValueAsString(new SingleInteger(1)))
            .expectedOutput(objectMapper.writeValueAsString(new SingleInteger(1)))
            .build());

    TimeUnit.SECONDS.sleep(2);

    subscribe("/user/public/topic/codeRunnerResults");
    CodeRunnerRequestMessage codeRunnerRequestMessage =
        CodeRunnerRequestMessage.builder().CodeRunnerType(CODE_RUNNER_TYPE.JS_RUNNER).build();

    ExerciseTestToRunMesseage exerciseTestToRunMesseage =
        ExerciseTestToRunMesseage.builder()
            .code("function solution(x)\n{\nreturn 1;\n}")
            .amountOfAutoTests(amountOfAutoTests)
            .lengthRange(new Range(-3, 6))
            .inputType(String.valueOf(Variables.VARIABLES_TYPES.SINGLE_INTEGER))
            .manualTests(tests)
            .executionTime(200L)
            .outputType(String.valueOf(Variables.VARIABLES_TYPES.SINGLE_INTEGER))
            .xArrayRange(new Range(1, 7))
            .yArrayRange(new Range(4, 8))
            .lengthRange(new Range(-444, 555))
            .build();

    session.send(
        "/public/app/codeRunnerRequest", mapper.writeValueAsBytes(codeRunnerRequestMessage));
    log.info(
        "messge send to /public/app/codeRunnerRequest with content: "
            + mapper.writeValueAsString(codeRunnerRequestMessage));

    String reqMes = mapper.writeValueAsString(codeRunnerRequestMessage);
    log.info("reqMes: \n" + reqMes);
    TimeUnit.SECONDS.sleep(2);

    String mess = mapper.writeValueAsString(exerciseTestToRunMesseage);
    log.info("Messeage: \n" + mess);

    session.send(
        "/public/app" + INrunExercsieTestsCode,
        mapper.writeValueAsBytes(exerciseTestToRunMesseage));
    log.info(
        "messge send to "
            + "/public/app"
            + INrunRawCode
            + " with content: "
            + mapper.writeValueAsString(exerciseTestToRunMesseage));

    //        Thread.sleep(3000);

    String results = blockingQueue.poll(60, SECONDS);

    assertNotNull(results);
    assertTrue(results != "");

    log.info("results: \n\n\n" + results + "\n\n\n\n\n");

    int i = 0;
    Pattern p = Pattern.compile("consoleOutput");
    Matcher m = p.matcher(results);
    while (m.find()) {
      i++;
    }
    log.info("input size: " + tests.size());
    assertEquals(tests.size() - 1, i);
  }

  @SneakyThrows
  @Override
  @AfterAll
  protected void tearDown() {
    super.tearDown();
    redoCodeController.reset();
  }

  @AfterEach
  @SneakyThrows
  public void resetController() {
    this.codeRunnersController.reset();
    Thread.sleep(1000);
  }
}
