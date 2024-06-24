package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AutoTestGeneratorHandler extends  MessageRequestHandler{

    @Override
    boolean handle(RequestBase request) throws RequestHadndlingException {
        ExerciseCreationRequest exerciseCreationRequest= (ExerciseCreationRequest) request;
                log.error("AutoTestGeneratorHandler NOT IMPLEMENTED");

                return true;
    }
}
