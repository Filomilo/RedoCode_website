package com.redocode.backend.ConnectionCotrollers;

import com.redocode.backend.Messages.CodeRunnerRequestMessage;
import com.redocode.backend.RedoCodeController;
import com.redocode.backend.RequstHandling.Requests.CodeRunnerRequest;
import com.redocode.backend.VmAcces.CodeRunnersController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@Slf4j
public class CodeRunnersConnectionController {

  private final SimpMessagingTemplate template;
  public static final String codeRunnerStateEndPoint = "public/topic/codeRunnerState";
  public static final String codeRunnerResultEndPoint = "public/topic/codeRunnerResults";

  @Autowired private CodeRunnersController codeRunnersController;

  @Autowired private RedoCodeController redoCodeController;

  @Autowired
  CodeRunnersConnectionController(SimpMessagingTemplate template) {
    this.template = template;
  }

  @MessageMapping("/codeRunnerRequest")
  public void codeRunnerRequest(Principal principal, CodeRunnerRequestMessage requestMessageSource)
      throws Exception {
    try {
      String userId = principal.getName();
      log.info("code runner reuqest from: " + userId + " : " + requestMessageSource);
      CodeRunnerRequest req =
              new CodeRunnerRequest(
                      redoCodeController.getUserByConnectionUUID(userId), requestMessageSource);
      codeRunnersController.requestVm(req);
    }
    catch (Exception e) {
      log.error("Error requesting code runner: "+ e.getMessage());
    }
  }
}
