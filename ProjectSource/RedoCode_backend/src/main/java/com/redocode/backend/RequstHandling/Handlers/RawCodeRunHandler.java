package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.PorgramReusltsSendRequest;
import com.redocode.backend.RequstHandling.Requests.RawCodeRunRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.SpringContextUtil;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.VmAcces.CodeRunners.Program.Program;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunners.Program.RawProgram;
import com.redocode.backend.VmAcces.CodeRunnersController;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RawCodeRunHandler extends  BaseRequestHandler{


    protected static final CodeRunnersController codeRunnersController= (CodeRunnersController) SpringContextUtil.getApplicationContext().getBean(CodeRunnersController.class);


    @Override
    String getChainNodeName() {
        return "run raw code";
    }

    @Override
    RequestBase handle(RequestBase request) throws RequestHadndlingException {
        log.info("RawCodeRunHadnler");
        RawCodeRunRequest requestRawCodeRunRequest = (RawCodeRunRequest) request;

        CodeRunner codeRunner=codeRunnersController.getUserCodeRunner(request.getUser());
        ProgramResult results=
        codeRunner.runProgram(
                RawProgram.builder()
                        .code(requestRawCodeRunRequest.getCode())
                        .build()
        );

        request= PorgramReusltsSendRequest.builder()
                .programResults(List.of(new ProgramResult[]{results}))
                .user(requestRawCodeRunRequest.getUser())
                .build();


        return request;
    }

    @Override
    void exceptionHandling(Exception exception) {

    }
}
