package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.CodeRunnerRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.VmAcces.CodeRunnersController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@Slf4j
public class CodeRunnerAccesValidationHandler extends MessageRequestHandler {
    @Autowired private CodeRunnersController codeRunnersController;

    @Override
    boolean handle(RequestBase request) throws RequestHadndlingException {
        log.info("messegeSender: " + this.messageSender);
        log.info("CodeRunnerAccesValidationHandler hadnling: "+ request+" from "+request.getUser());
        CodeRunner codeRunner= codeRunnersController.getUserCodeRunner(request.getUser());
        codeRunnersController.requestVm((CodeRunnerRequest) request);
        log.info("code unner: "+ codeRunner);
        return false;
    }
}
