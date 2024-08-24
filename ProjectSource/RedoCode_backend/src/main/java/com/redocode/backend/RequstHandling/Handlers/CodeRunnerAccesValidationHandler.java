package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
// import com.redocode.backend.RequstHandling.Requests.CodeRunnerRequest;
import com.redocode.backend.RequstHandling.Requests.CodeRunnerRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ICodeRunnerRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.SpringContextUtil;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.VmAcces.CodeRunnersController;
import lombok.extern.slf4j.Slf4j;

/**
 * Rquest handler for valdiating user acces to a specifed code runner, <br>
 * request needs to implements interface {@link ICodeRunnerRequest ICodeRunnerRequest }
 */
@Slf4j
public class CodeRunnerAccesValidationHandler extends MessageRequestHandler {
  private static final CodeRunnersController codeRunnersController =
      SpringContextUtil.getApplicationContext().getBean(CodeRunnersController.class);

  @Override
  String getChainNodeName() {
    return "Validating access to coderunner";
  }

  boolean validateCodeRunner(RequestBase request) {
    ICodeRunnerRequest codeRunnerRequest = (ICodeRunnerRequest) request;

    CodeRunner currentCodeRunner = codeRunnersController.getUserCodeRunner(request.getUser());
    if (codeRunnerRequest.getCodeRunnerType() == CODE_RUNNER_TYPE.UNIDENTIFIED
        && currentCodeRunner != null) {
      return true;
    }

    if (currentCodeRunner != null
        && currentCodeRunner.getType() == ((ICodeRunnerRequest) request).getCodeRunnerType()
        && currentCodeRunner.getRamMb() == ((ICodeRunnerRequest) request).getRam()) {
      return true;
    }
    return false;
  }

  @Override
  RequestBase handle(RequestBase request) throws RequestHadndlingException {
    if (!(request instanceof ICodeRunnerRequest)) {
      throw new RequestHadndlingException("Request must implement ICodeRunnerRequest");
    }
    ICodeRunnerRequest codeRunnerRequest = (ICodeRunnerRequest) request;

    this.nodeUpdate(
        request,
        "validating access to " + codeRunnerRequest.getCodeRunnerType(),
        ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING);

    log.info(
        "CodeRunnerAccesValidationHandler hadnling: " + request + " from " + request.getUser());
    CodeRunner currentCodeRunner = codeRunnersController.getUserCodeRunner(request.getUser());

    if (!validateCodeRunner(request)) {
      codeRunnersController.requestVm(
          CodeRunnerRequest.builder()
              .codeRunnerType(((ICodeRunnerRequest) request).getCodeRunnerType())
              .user(request.getUser())
              .ram(((ICodeRunnerRequest) request).getRam())
              .build());
    }

    if (validateCodeRunner(request)) {
      log.info("CodeRunnerAccesValidationHandler: succses");
      this.nodeUpdate(
          request,
          "Validated access to " + codeRunnerRequest.getCodeRunnerType(),
          ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS);

      return request;
    } else {
      log.info("CodeRunnerAccesValidationHandler: FAilure");
      return null;
    }
  }
}
