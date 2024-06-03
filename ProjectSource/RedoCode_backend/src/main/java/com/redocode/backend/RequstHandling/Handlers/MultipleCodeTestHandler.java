package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.CodeTestRequest;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.RequstHandling.ResponsibilityChainRepository;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class MultipleCodeTestHandler extends MessageRequestHandler{



    @Override
    boolean handle(RequestBase request) throws RequestHadndlingException {
        ExerciseCreationRequest exerciseCreationRequest= (ExerciseCreationRequest) request;

        for (Map.Entry<CODE_RUNNER_TYPE,String> entry : exerciseCreationRequest.getSolutionCodes().entrySet()
             ) {
            try {
                log.info("ruuning test on code runner " + entry.getKey() + " WIth  code : \n " + entry.getValue() + "\n");
                CodeTestRequest codeTestRequest = exerciseCreationRequest;
                codeTestRequest.setCodeRunnerType(entry.getKey());
                codeTestRequest.setCode(entry.getValue());
                boolean res=ResponsibilityChainRepository.testSingleCode.next(codeTestRequest);
                log.info("ResponsibilityChainRepository result: "+ res);
                if(!res)
                    throw new RequestHadndlingException("test faliure");
            }
            catch (RequestHadndlingException ex)
            {
                throw new RequestHadndlingException(entry.getKey()+"run failed: "+ ex.getMessage());
            }
        }
        return true;
    }


}
