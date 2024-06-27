package com.redocode.backend.RequstHandling;

import com.redocode.backend.RequstHandling.Handlers.*;

public class ResponsibilityChainRepository {


    public static final BaseRequestHandler testSingleCode=
            new ResposibilityChainBuilder(new CodeRunnerAccesValidationHandler())
                    .next(new CodeTestHandler())
                    .build();

    public static final BaseRequestHandler createNewExercise=
        new ResposibilityChainBuilder(new ExerciseInfoValidation())
                .next(new AutoTestGeneratorHandler())
                .next(new MergeTestHandler())
                .next( new MultipleCodeTestHandler())
                .next(new SaveNewExerciseHandler())
                .build();
    public static final BaseRequestHandler runCodeWithSelfTest=
            new ResposibilityChainBuilder(new CodeRunnerAccesValidationHandler())
                    .next(new CodeTestHandler())
                    .build();
}
