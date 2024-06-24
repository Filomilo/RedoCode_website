package com.redocode.backend.RequstHandling;

import com.redocode.backend.RequstHandling.Handlers.*;

public class ResponsibilityChainRepository {


    public static final BaseRequestHandler testSingleCode=
            new CodeRunnerAccesValidationHandler()
            .setNextRequestHandler(
                    new CodeTestHandler()
            );
//todo create builder for chain request hadnlers
    public static final BaseRequestHandler createNewExercise=
            new ExerciseInfoValidation().setNextRequestHandler(
                    new AutoTestConfValidationHandler().setNextRequestHandler(
                        new AutoTestGeneratorHandler().setNextRequestHandler(
                                new MergeTestHandler().setNextRequestHandler(
                                        new MultipleCodeTestHandler().setNextRequestHandler(
                                                new SaveNewExerciseHandler()
                                        )
                                )
                        )
                    )
            );
}
