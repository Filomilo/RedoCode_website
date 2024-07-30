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

    private static final MessageSender messageSender= SpringContextUtil.getApplicationContext().getBean(MessageSender.class);

    public static BiFunction<User, ExecutionResponseBase, Void> sendExecutionMessage=(User user, ExecutionResponseBase responseBase)->
    {
        log.info("sending chian repsonse: "+responseBase+" to "+user);
        messageSender.sendMessage(user,"/public/topic/ExecutionResponses",responseBase);
        return null;
    };



    public static final BaseRequestHandler testSingleCode=
            new ResposibilityChainBuilder()
                    .setMessagehandler(sendExecutionMessage)
                    .setSteps()
                    .next(new CodeRunnerAccesValidationHandler())
                    .next(new CodeTestHandler())
                    .build();

    public  static final BaseRequestHandler createNewExercise=
        new ResposibilityChainBuilder()
                .setMessagehandler(sendExecutionMessage)
                .setSteps()
                .next(new AuthenticatedUserValidationHandler())
                .next(new ExerciseInfoValidation())
                .next(new AutoTestGeneratorHandler())
                .next(new MergeTestHandler())
                .next( new MultipleCodeTestHandler())
                .next(new SaveNewExerciseHandler())
                .build();
    public  static final BaseRequestHandler runCodeWithSelfTest=
            new ResposibilityChainBuilder()
                    .setMessagehandler(sendExecutionMessage)
                    .setSteps()
                    .next(new CodeRunnerAccesValidationHandler())
                    .next(new CodeTestHandler())
                    .build();

    public  static final BaseRequestHandler runExercisesTests=
            new ResposibilityChainBuilder()
                    .setSteps()
                    .next(new CodeRunnerAccesValidationHandler())
                    .next(new AutoTestGeneratorHandler())
                    .next(new CodeTestHandler())
                    .setContinueOnError(true)
                    .next(new SendProgramResultsHandler())
                    .build();


    public  static final BaseRequestHandler runRawCode=
            new ResposibilityChainBuilder()
                    .setSteps()
                    .next(new CodeRunnerAccesValidationHandler())
                    .next(new RawCodeRunHandler())
                    .next(new SendProgramResultsHandler())
                    .build();


}
