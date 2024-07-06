package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AutoTestGeneratorHandler extends  MessageRequestHandler{



    @Override
    String getChainNodeName() {
        return "Generating automatic tests";
    }

    @Override
    RequestBase handle(RequestBase request) throws RequestHadndlingException {
        this.nodeUpdate(request,"generation", ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING);

        ExerciseCreationRequest exerciseCreationRequest= (ExerciseCreationRequest) request;
                log.error("AutoTestGeneratorHandler NOT IMPLEMENTED");
        //todo: auto test genrator
        this.nodeUpdate(request,"generated", ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS);

                return request;
    }
}
