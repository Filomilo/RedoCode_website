package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MergeTestHandler extends BaseRequestHandler{

    @Override
    String getChainNodeName() {
        return "Preparing tests";
    }

    @Override
    RequestBase handle(RequestBase request) throws RequestHadndlingException {
        this.nodeUpdate(request,"preparation", ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING);

        log.error("MergeTestHandler: NOT IMPLEMENTED");
        this.nodeUpdate(request,"prepared tests", ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS);

        return request;
    }

    @Override
    void exceptionHandling(Exception exception) {

    }
}
