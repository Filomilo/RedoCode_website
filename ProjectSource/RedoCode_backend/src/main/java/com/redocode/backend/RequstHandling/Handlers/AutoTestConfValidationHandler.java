package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AutoTestConfValidationHandler extends MessageRequestHandler{
    @Override
    boolean handle(RequestBase request) throws RequestHadndlingException {
        if(!(request instanceof ExerciseCreationRequest))
        {

            try{
                ExerciseCreationRequest exerciseCretionRequest= (ExerciseCreationRequest) request;
            }
            catch (Exception ex)
            {
                log.error("Excpetion: "+ ex.getMessage());
            }
            throw new RequestHadndlingException("Wrong reguest was privided to handler");
        }

        ExerciseCreationRequest exerciseCretionRequest= (ExerciseCreationRequest) request;

        if(exerciseCretionRequest.getRam()<128 || exerciseCretionRequest.getRam()>2048) //todo repplace wiht global conifg
        {
            throw new RequestHadndlingException("Wrong amount o ram");
        }

        if(exerciseCretionRequest.getAmountOfAutoTests()<1  || exerciseCretionRequest.getAmountOfAutoTests()>10)
        {
            throw new RequestHadndlingException("Wrong amount o auto test");
        }

        // todo Fill with test based o creatro panel in gui
        return true;
    }
}
