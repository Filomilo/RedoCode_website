package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.CodeTestRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeTestHandler extends  BaseRequestHandler {


    @Override
    boolean handle(RequestBase request) throws RequestHadndlingException {
        CodeTestRequest codeTestRequest= (CodeTestRequest) request;
      log.info("CodeTestHandler handles: "+ codeTestRequest);

      return true;
    }

    @Override
    void exceptionHandling(Exception exception) {

    }
}
