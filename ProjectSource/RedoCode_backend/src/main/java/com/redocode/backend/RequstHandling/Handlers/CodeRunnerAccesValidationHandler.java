package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
//import com.redocode.backend.RequstHandling.Requests.CodeRunnerRequest;
import com.redocode.backend.RequstHandling.Requests.CodeRunnerRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ICodeRunnerRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.IExerciseIdRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ISolutionCodesRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.SpringContextUtil;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.VmAcces.CodeRunnersController;
import com.redocode.backend.VmAcces.VmStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * Rquest handler for valdiating user acces to a specifed code runner, <br/> request needs to implements interface
 * {@link ICodeRunnerRequest ICodeRunnerRequest }
 */
@Slf4j
public class CodeRunnerAccesValidationHandler extends MessageRequestHandler {
  private static final CodeRunnersController codeRunnersController =
      SpringContextUtil.getApplicationContext().getBean(CodeRunnersController.class);

  @Override
  String getChainNodeName() {
    return "validating access to coderunner";
  }

  @Override
  RequestBase handle(RequestBase request) throws RequestHadndlingException {
    if(!(request instanceof ICodeRunnerRequest) ) {
      throw new RequestHadndlingException("Request must implement ICodeRunnerRequest");
    }
    ICodeRunnerRequest codeRunnerRequest = (ICodeRunnerRequest) request;



    this.nodeUpdate(
        request,
        "validating access to " +codeRunnerRequest.getCodeRunnerType(),
        ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING);

    log.info(
        "CodeRunnerAccesValidationHandler hadnling: " + request + " from " + request.getUser());
    CodeRunner currentCodeRunner = codeRunnersController.getUserCodeRunner(request.getUser());

    if (codeRunnerRequest.getCodeRunnerType() == CODE_RUNNER_TYPE.UNIDENTIFIED
        && currentCodeRunner != null) {
      return request;
    }

    if (currentCodeRunner != null
        && currentCodeRunner.getType() == ((ICodeRunnerRequest) request).getCodeRunnerType()
        && currentCodeRunner.getRamMb() == ((ICodeRunnerRequest) request).getRam()) {
      return request;
    }

    codeRunnersController.requestVm(CodeRunnerRequest.builder()
                    .codeRunnerType(((ICodeRunnerRequest) request).getCodeRunnerType())
                    .user(request.getUser())
                    .ram(((ICodeRunnerRequest) request).getRam())
            .build());
    CodeRunner codeRunner = codeRunnersController.getUserCodeRunner(request.getUser());
    log.info("code unner: " + codeRunner);
    CodeRunner userCodeRunner = codeRunnersController.getUserCodeRunner(request.getUser());

    if (userCodeRunner != null
        && userCodeRunner.getType() == codeRunnerRequest.getCodeRunnerType()
        && codeRunner.getStatus() == VmStatus.RUNNING_MACHINE) {
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
