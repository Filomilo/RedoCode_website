package com.redocode.backend.RequstHandling;

import com.redocode.backend.ConnectionCotrollers.MessageSender;
import com.redocode.backend.Messages.ExecutionResponses.ExecutionResponseBase;
import com.redocode.backend.RequstHandling.Handlers.*;
import com.redocode.backend.RequstHandling.Requests.ResposibilityChainBuilder;
import com.redocode.backend.SpringContextUtil;
import com.redocode.backend.database.User;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiFunction;

@Slf4j
public class ResponsibilityChainRepository {

  private static final MessageSender messageSender =
      SpringContextUtil.getApplicationContext().getBean(MessageSender.class);

  public static BiFunction<User, ExecutionResponseBase, Void> sendExecutionMessage =
      (User user, ExecutionResponseBase responseBase) -> {
        log.info("sending chian repsonse: " + responseBase + " to " + user);
        messageSender.sendMessage(user, "/public/topic/ExecutionResponses", responseBase);
        return null;
      };
    /**
     * Chian resposible for tesing just a single code on multiple tests provided
     * <lu>
     *     <li>{@link CodeRunnerAccesValidationHandler validates user acces to requried code runner}</li>,
     *     <li>{@link CodeTestHandler tests code using CodeTestHandler}</li>
     * </lu>
     * @see MultipleCodeTestHandler use as  part of MultipleCodeTestHandler test handler
     */
  public static final BaseRequestHandler testSingleCode =
      new ResposibilityChainBuilder()
          .setMessagehandler(sendExecutionMessage)
          .setSteps()
          .next(new CodeRunnerAccesValidationHandler())
          .next(new CodeTestHandler())
          .build();
    /**
     * Chain reposibile for handling request to save new exercise to database
     * <ul>
     *     <li>{@link  AuthenticatedUserValidationHandler Checks if user is authenticated so he can solve} </li>,
     *     <li>{@link ExerciseInfoValidation Checks if infomration like title descirption nad and settings are corret and meet requiermetns} </li>,
     *     <li>{@link AutoTestGeneratorHandler  Generate automated tests, mailny to check if there arent any compilation or run errors becouse currently it is not konw what sould be anwser to those automated tests}</li>,
     *     <li> {@link MergeTestHandler Merges automated tests with manual tests to check all of them at once}</li>,
     *     <li>{@link MultipleCodeTestHandler runs all of the test to check if code is correct}  </li>,
     *      <li>{@link SaveNewExerciseHandler if everything is correct saves exercise to database}  </li>
     * </ul>
     * @see com.redocode.backend.ConnectionCotrollers.CodeRunHandler#runExerciseCreatorValidationCode
     * used only CodeRun handler for operating on user request
     */
  public static final BaseRequestHandler createNewExercise =
      new ResposibilityChainBuilder()
          .setMessagehandler(sendExecutionMessage)
          .setSteps()
          .next(new AuthenticatedUserValidationHandler())
          .next(new ExerciseInfoValidation())
          .next(new AutoTestGeneratorHandler())
          .next(new MergeTestHandler())
          .next(new MultipleCodeTestHandler())
          .next(new SaveNewExerciseHandler())
          .build();
  public static final BaseRequestHandler runCodeWithSelfTest =
      new ResposibilityChainBuilder()
          .setMessagehandler(sendExecutionMessage)
          .setSteps()
          .next(new CodeRunnerAccesValidationHandler())
          .next(new CodeTestHandler())
          .build();
    /**
     * Chain responsible for running single language tests druring exercise creation proces before hiting submit
     * @see com.redocode.backend.ConnectionCotrollers.CodeRunHandler#runExerciseTestsCode
     * Mainly used in CodeRunHandler to handle user request
     */
  public static final BaseRequestHandler runExercisesTests =
      new ResposibilityChainBuilder()
          .setSteps()
          .next(new CodeRunnerAccesValidationHandler())
          .next(new AutoTestGeneratorHandler())
          .next(new CodeTestHandler())
          .setContinueOnError(true)
          .next(new SendProgramResultsHandler())
          .build();
    public static final BaseRequestHandler runDataBaseTestCode =
            new ResposibilityChainBuilder()
                    .setSteps()
                    .next(new CodeRunnerAccesValidationHandler())
                    .next(new AutoTestGeneratorHandler())
                    .next(new CodeTestHandler())
                    .setContinueOnError(true)
                    .next(new SendProgramResultsHandler())
                    .build();

  public static final BaseRequestHandler runRawCode =
      new ResposibilityChainBuilder()
          .setSteps()
          .next(new CodeRunnerAccesValidationHandler())
          .next(new RawCodeRunHandler())
          .next(new SendProgramResultsHandler())
          .build();
    /**
     * chain reposible for running code tests for exercise code run from databse before submiting
     * <br/> chian steps aee
     * <ul>
     *     <li>{@link CodeRunnerAccesValidationHandler validating acces to code required code runner}</li>
     *     <li>{@link GetExerciseDataFromDataBase retrives exercsie data from database  }</li>
     *     <li>{@link AutoTestGeneratorHandler generate automatic test  } </li>
     *     <li>{@link UnsolvedDatabaseTestsHandler run solution code From data base to get expected ouput for automatic tests}</li>
     *     <li>{@link CodeTestHandler run tests for to check if solution is correct }</li>
     *     <li>{@link SendProgramResultsHandler send results to user}</li>
     * </ul>
     */
  public static final BaseRequestHandler runExerciseIdCode=
          new ResposibilityChainBuilder()
                  .setSteps()
                  .next(new CodeRunnerAccesValidationHandler())
                  .next(new GetExerciseDataFromDataBase())
                  .next(new AutoTestGeneratorHandler())
                  .next(new UnsolvedDatabaseTestsHandler())
                  .next(new CodeTestHandler())
                  .setContinueOnError(true)
                  .next(new SendProgramResultsHandler())
                  .build();
}
