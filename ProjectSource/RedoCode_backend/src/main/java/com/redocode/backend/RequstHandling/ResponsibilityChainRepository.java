package com.redocode.backend.RequstHandling;

import com.redocode.backend.RequstHandling.Handlers.BaseRequestHandler;
import com.redocode.backend.RequstHandling.Handlers.CodeRunnerAccesValidationHandler;
import com.redocode.backend.RequstHandling.Handlers.CodeTestHandler;

public class ResponsibilityChainRepository {


    public static final BaseRequestHandler testSingleCode=
            new CodeRunnerAccesValidationHandler()
            .setNextRequestHandler(
                    new CodeTestHandler()
            );


}
