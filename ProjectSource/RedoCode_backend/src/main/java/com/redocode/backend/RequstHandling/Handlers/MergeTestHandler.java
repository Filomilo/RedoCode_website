package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MergeTestHandler extends BaseRequestHandler{
    @Override
    boolean handle(RequestBase request) throws RequestHadndlingException {

        log.error("MergeTestHandler: NOT IMPLEMENTED");
        return true;
    }

    @Override
    void exceptionHandling(Exception exception) {

    }
}
