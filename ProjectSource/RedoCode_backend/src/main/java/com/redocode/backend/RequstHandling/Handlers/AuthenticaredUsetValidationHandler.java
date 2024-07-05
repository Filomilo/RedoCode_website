package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.RequstHandling.Requests.RequestBase;

public class AuthenticaredUsetValidationHandler extends MessageRequestHandler{


    @Override
    String getChainNodeName() {
        return "Validating user permissions";
    }

    @Override
    boolean handle(RequestBase request) throws RequestHadndlingException {
        this.nodeUpdate(request,"Checking user type", ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING);
        this.nodeUpdate(request,"Correct user type", ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS);
        return true; //todo: add validations
    }
}
