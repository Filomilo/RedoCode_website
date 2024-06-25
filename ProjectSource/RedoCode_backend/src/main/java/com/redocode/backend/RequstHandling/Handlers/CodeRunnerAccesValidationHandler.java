package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.CodeRunnerRequest;
import com.redocode.backend.RequstHandling.Requests.CodeTestRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.SpringContextUtil;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.VmAcces.CodeRunnersController;
import com.redocode.backend.VmAcces.VmStatus;
import com.redocode.backend.database.ExerciseTests;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Slf4j
public class CodeRunnerAccesValidationHandler extends MessageRequestHandler {
     private static final CodeRunnersController codeRunnersController= SpringContextUtil.getApplicationContext().getBean(CodeRunnersController.class);


    @Override
    boolean handle(RequestBase request) throws RequestHadndlingException {
        log.info("messegeSender: " + this.messageSender);
        log.info("CodeRunnerAccesValidationHandler hadnling: "+ request+" from "+request.getUser());

        codeRunnersController.requestVm((CodeRunnerRequest) request);
        CodeRunner codeRunner= codeRunnersController.getUserCodeRunner(request.getUser());
        log.info("code unner: "+ codeRunner);
        CodeRunner userCodeRunner=codeRunnersController.getUserCodeRunner(request.getUser());

        if(userCodeRunner!=null &&
                userCodeRunner.getType()==((CodeRunnerRequest) request).getCodeRunnerType() &&
                codeRunner.getStatus()== VmStatus.RUNNING_MACHINE)
        {
            log.info("CodeRunnerAccesValidationHandler: succses");
            return  true;
        }
            else {
            log.info("CodeRunnerAccesValidationHandler: FAilure");
            return false;
        }
    }
}
