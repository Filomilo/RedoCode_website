package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.ExecutionResponses.ExecutionResponseBase;
import com.redocode.backend.Messages.ExecutionResponses.ExecutionResponseStatusUpdate;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.RequstHandling.Requests.CodeTestRequest;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.RequstHandling.ResponsibilityChainRepository;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.database.User;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@Slf4j
@SpringBootTest
@ContextConfiguration
public class MultipleCodeTestHandler extends MessageRequestHandler{




    @Override
    String getChainNodeName() {
        return "Code testing";
    }

    @Override
    RequestBase handle(RequestBase request) throws RequestHadndlingException {

        BaseRequestHandler  codeTester=ResponsibilityChainRepository.testSingleCode.clone();
        codeTester.setMehthodForChain((User user, ExecutionResponseBase executionResponseBase)->{
            ExecutionResponseStatusUpdate statusUpdate= (ExecutionResponseStatusUpdate) executionResponseBase;
            nodeUpdate(request, statusUpdate.getMessage(), ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING );
            return null;
        });


//        this.nodeUpdate(request,"Checking codes", ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING);

        ExerciseCreationRequest exerciseCreationRequest= (ExerciseCreationRequest) request;
        List<Map.Entry<CODE_RUNNER_TYPE, String>> lists = new ArrayList<>(exerciseCreationRequest.getSolutionCodes().entrySet());          lists.sort(Comparator.comparing(Map.Entry::getValue));
        for (Map.Entry<CODE_RUNNER_TYPE,String> entry : lists
             ) {
            try {
                log.info("ruuning test on code runner " + entry.getKey() + " WIth  code : \n " + entry.getValue() + "\n");
                CodeTestRequest codeTestRequest = exerciseCreationRequest;
                codeTestRequest.setCodeRunnerType(entry.getKey());
                codeTestRequest.setCode(entry.getValue());
                boolean res=codeTester.next(codeTestRequest);
                log.info("ResponsibilityChainRepository result: "+ res);
                if(!res)
                    throw new RequestHadndlingException("test faliure");
            }
            catch (RequestHadndlingException ex)
            {
                throw new RequestHadndlingException(entry.getKey()+"run failed: "+ ex.getMessage());
            }
        }
        this.nodeUpdate(request,"tests finished correctly", ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS);

        return request;
    }


}
