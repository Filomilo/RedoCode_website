package com.redocode.backend.RequstHandling.Handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redocode.backend.Excpetions.CodeErroeException;
import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.RequstHandling.Requests.Interfaces.*;
import com.redocode.backend.RequstHandling.Requests.PorgramReusltsSendRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.SpringContextUtil;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.VmAcces.CodeRunners.ConsoleOutput;
import com.redocode.backend.VmAcces.CodeRunners.Program.Factory.ProgramFactory;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunners.Program.SolutionProgram;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.VmAcces.CodeRunnersController;
import com.redocode.backend.database.ExerciseTests;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Code test hadnler used hadnle running test on code on only one code runner, <br>
 * it needs to implement:<br>
 * {@link ISolutionCodesRequest ISolutionCodesRequest}<br>
 * {@link ITestsToRunRequest ITestsToRunRequest }<br>
 * {@link ICodeRunnerRequest ICodeRunnerRequest }<br>
 * {@link ICodeRunSpecificationParametersRequest ICodeRunSpecificationParametersRequest }<br>
 * <br>
 * and returns results in form of {@link PorgramReusltsSendRequest PorgramReusltsSendRequest }
 */
@Slf4j
public class CodeTestHandler extends BaseRequestHandler {

  protected static final CodeRunnersController codeRunnersController =
      (CodeRunnersController)
          SpringContextUtil.getApplicationContext().getBean(CodeRunnersController.class);

  protected ProgramResult checkTest(ExerciseTests test, RequestBase request, CodeRunner codeRunner)
      throws RequestHadndlingException, CodeErroeException {

    ISolutionCodesRequest solutionCodesRequest = (ISolutionCodesRequest) request;
    ITestsToRunRequest testsToRunRequest = (ITestsToRunRequest) request;
    ICodeRunnerRequest codeRunnerRequest = (ICodeRunnerRequest) request;
    ICodeRunSpecificationParametersRequest codeRunSpecificationParametersRequest =
        (ICodeRunSpecificationParametersRequest) request;
    log.info("Testing: " + test);

    try {
      log.info("TEST inpupt: " + test.getParsedInput(testsToRunRequest.getInputType()));
      SolutionProgram solutionProgram =
          ProgramFactory.createSolutionProgram()
              .setSolutionCodeRunner(codeRunnerRequest.getCodeRunnerType())
              .setInputVaraiable(test.getParsedInput(testsToRunRequest.getInputType()))
              .setOutputBase(testsToRunRequest.getOutputType())
              .setSolutionCode(
                  solutionCodesRequest
                      .getSolutionCodes()
                      .get(
                          codeRunnerRequest.getCodeRunnerType() == CODE_RUNNER_TYPE.UNIDENTIFIED
                              ? CODE_RUNNER_TYPE.UNIDENTIFIED
                              : codeRunner.getType()))
              .setTimeout(codeRunSpecificationParametersRequest.getTimeForExecution())
              .build();
      log.info("solution program being tested: " + solutionProgram.getInput());
      ProgramResult result = codeRunner.runProgram(solutionProgram);
      log.info(
          "program: \n\n" + solutionProgram.getProgramCode() + "\n\nresulted in: \n\n\n" + result);
      if (result.getConsoleOutput().getErrorOutput().length() > 0) {
        throw new CodeErroeException(result.getConsoleOutput().getErrorOutput());
      }
      Variables recived = result.getVariables();
      if (test.getExpectedOutput() == null) return result;
      Variables expcected = test.getParsedOutput(testsToRunRequest.getOutputType());
      log.info("program resuult: " + recived);
      log.info("expected program resuult: " + expcected);
      if (recived == null || !recived.equals(expcected)) {
        throw new RequestHadndlingException(
            "expected: "
                        + test.getParsedOutput(testsToRunRequest.getOutputType()).getValue()
                        + " but recived: "
                        + result.getVariables()
                    != null
                ? result.getVariables().getValue().toString()
                : "null");
      }
      return result;
    } catch (JsonProcessingException ex) {
      log.error("json procesign error: " + ex.getMessage());
      throw new RequestHadndlingException("unable to prooces json format");
    } catch (NullPointerException ex) {
      log.error("varaible NULL: " + ex.getMessage());
      throw new RequestHadndlingException("returned value is null ");
    }
  }

  @Override
  String getChainNodeName() {
    return "testing code";
  }

  @Override
  RequestBase handle(RequestBase request) throws RequestHadndlingException {
    assert request instanceof ISolutionCodesRequest;
    assert request instanceof ITestsToRunRequest;
    assert request instanceof ICodeRunnerRequest;
    assert request instanceof ICodeRunSpecificationParametersRequest;
    ISolutionCodesRequest solutionCodesRequest = (ISolutionCodesRequest) request;
    ITestsToRunRequest testsToRunRequest = (ITestsToRunRequest) request;
    ICodeRunnerRequest codeRunnerRequest = (ICodeRunnerRequest) request;
    List<ProgramResult> programResults = new ArrayList<>();

    this.nodeUpdate(
        request,
        "running " + codeRunnerRequest.getCodeRunnerType() + " tests",
        ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING);
    ;
    CodeRunner codeRunner = codeRunnersController.getUserCodeRunner(request.getUser());
    if (codeRunner == null) throw new RequestHadndlingException("Could not acces code runner");

    log.info("Staring handler: CodeTestHandler+" + "for " + request.getUser());
    int i = 0;
    List<ExerciseTests> tests = new LinkedList<ExerciseTests>();
    tests.addAll(testsToRunRequest.getTestsToRun());
    if (testsToRunRequest.getAutotestsToRun() != null) {
      tests.addAll(testsToRunRequest.getAutotestsToRun());
    }
    for (ExerciseTests exTest : tests) {
      try {
        programResults.add(checkTest(exTest, request, codeRunner));
      } catch (Exception ex) {
        programResults.add(
            ProgramResult.builder()
                .consoleOutput(ConsoleOutput.builder().errorOutput(ex.getMessage()).build())
                .build());
        if (!is_continueOnError())
          throw new RequestHadndlingException("Test " + i + " failed: " + ex.getMessage());
        else break;
      }
      i++;
    }
    log.info("CodeTestHandler handles: " + request);
    this.nodeUpdate(
        request,
        "correct " + codeRunnerRequest.getCodeRunnerType() + " tests",
        ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS);
    PorgramReusltsSendRequest porgramReusltsSendRequest =
        PorgramReusltsSendRequest.builder()
            .programResults(programResults)
            .user(request.getUser())
            .build();
    return porgramReusltsSendRequest;
  }

  @Override
  void exceptionHandling(Exception exception) {}
}
