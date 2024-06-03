package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExerciseInfoValidation extends MessageRequestHandler{
    @Override
    boolean handle(RequestBase request) throws RequestHadndlingException {
        ExerciseCreationRequest exerciseCretionRequest= (ExerciseCreationRequest) request;


        if(exerciseCretionRequest.getTitle().length()>5
                &&exerciseCretionRequest.getTitle().length()<20) //todo: replace check with global config
        {
            return true;
        }
        else {
            throw  new RequestHadndlingException("Exercise title is to short or too long");
        }
    }

    @Override
    void exceptionHandling(Exception exception) {

    }
}
