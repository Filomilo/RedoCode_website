package com.redocode.backend.ConnectionCotrollers;

import com.redocode.backend.Auth.User;
import com.redocode.backend.Messages.CodeRunnerRequestMessage;
import com.redocode.backend.Messages.CodeToRunMessage;
import com.redocode.backend.RedoCodeController;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunnerRequest;
import com.redocode.backend.VmAcces.CodeRunnersController;
import com.redocode.backend.VmAcces.VmStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@Slf4j
public class CodeRunnersConnectionController {

    private final SimpMessagingTemplate template;
    public static final String  codeRunnerStateEndPoint="/topic/codeRunnerState";
    public static final String  codeRunnerResultEndPoint="/topic/codeRunnerResults";

    @Autowired
    private CodeRunnersController codeRunnersController;

    @Autowired
    private RedoCodeController redoCodeController;

    @Autowired
    CodeRunnerSender codeRunnerSender;
    @Autowired
    CodeRunnersConnectionController(SimpMessagingTemplate template){
        this.template = template;
    }
    @MessageMapping("/codeRunnerRequest")
    public void test(Principal principal, CodeRunnerRequestMessage requestMessageSource) throws Exception{
        String userId=principal.getName();
        log.info("code runner reuqest from: "+ userId+" : "+requestMessageSource);
        CodeRunnerRequest req= new CodeRunnerRequest(userId, requestMessageSource);
        codeRunnersController.requestVm(req);
    }


    @MessageMapping("/codeRunnerState")
    @SendToUser
    public VmStatus updateStatuss(Principal principal)
    {
        String userId=principal.getName();
        VmStatus status = codeRunnersController.getUserVmStatus(redoCodeController.getUserById(userId));
        log.info("user: "+ userId+" requested status: "+ status);
        codeRunnerSender.sendToUser(userId, codeRunnerStateEndPoint,status);
        return status;
    }

    @MessageMapping("/CodeToRun")
    public void runCode(Principal principal, CodeToRunMessage codeToRunMessage)
    {
        String userId=principal.getName();
        log.info("user: "+ userId +" runs: "+codeToRunMessage);
        codeRunnersController.runCode(

                redoCodeController.getUserById(userId),
                codeToRunMessage
        );
    }


}
