package com.redocode.backend.RequstHandling.Handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.CodeTestRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ICodeRunSpecificationParametersRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.IExerciseIdRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ISolutionCodesRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ITestsToRunRequest;
import com.redocode.backend.RequstHandling.Requests.PorgramReusltsSendRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.SpringContextUtil;
import com.redocode.backend.Tools.RedoCodeObjectMapper;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.database.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Chain of resposibilty handler whcich purpose is to solve All tests in a given requests using a
 * solutin saved in database in a given language <br>
 * <br>
 * this handler requires request to implement interfaces {@link ISolutionCodesRequest
 * ISolutionCodesRequest}, {@link ICodeRunSpecificationParametersRequest
 * ICodeRunSpecificationParametersRequest}, {@link
 * com.redocode.backend.RequstHandling.Requests.Interfaces.IExerciseIdRequest IExerciseIdRequest}
 * and {@link com.redocode.backend.RequstHandling.Requests.Interfaces.ITestsToRunRequest
 * ITestsToRunRequest}
 */
@Slf4j
public class UnsolvedDatabaseTestsHandler extends MessageRequestHandler {

  protected static final ExerciseRepository exerciseRepository =
      (ExerciseRepository)
          SpringContextUtil.getApplicationContext().getBean(ExerciseRepository.class);
  protected static final SolutionProgramsRepository solutionProgramsRepository =
      (SolutionProgramsRepository)
          SpringContextUtil.getApplicationContext().getBean(SolutionProgramsRepository.class);
  protected static final ProgrammingLanguageRepository programmingLanguageRepository =
      (ProgrammingLanguageRepository)
          SpringContextUtil.getApplicationContext().getBean(ProgrammingLanguageRepository.class);

  @Override
  String getChainNodeName() {
    return "Solving automated exercises";
  }

  @Override
  RequestBase handle(RequestBase request) throws RequestHadndlingException {
    if (!(request instanceof IExerciseIdRequest)
        && !(request instanceof ITestsToRunRequest)
        && !(request instanceof ISolutionCodesRequest)
        && !(request instanceof ICodeRunSpecificationParametersRequest)) {
      throw new RequestHadndlingException(
          "Request must implement ISolutionCodesRequest,IExerciseIdRequest and ITestsToRunRequest");
    }
    ITestsToRunRequest testsToRunRequest = (ITestsToRunRequest) request;
    IExerciseIdRequest exerciseIdRequest = (IExerciseIdRequest) request;
    ISolutionCodesRequest solutionCodesRequest = (ISolutionCodesRequest) request;
    ICodeRunSpecificationParametersRequest specificationParametersRequest =
        (ICodeRunSpecificationParametersRequest) request;

    log.info(
        "tests to be filled: " + Arrays.toString(testsToRunRequest.getAutotestsToRun().toArray()));

    List<ExerciseTests> testsToGenerateExpectedOutput =
        testsToRunRequest.getAutotestsToRun().stream()
            .filter(x -> x.getExpectedOutput() == null)
            .toList();
    CODE_RUNNER_TYPE currentlySolvingCodeRunnerr =
        (CODE_RUNNER_TYPE) solutionCodesRequest.getSolutionCodes().keySet().toArray()[0];
    String correctSolution =
        solutionProgramsRepository
            .findByLanguageIdAndExerciseId(
                programmingLanguageRepository
                    .findByName(
                        RedoCodeObjectMapper.CodeRunnerToDataBaseLanguageName(
                            currentlySolvingCodeRunnerr))
                    .getId(),
                exerciseIdRequest.getIdOfExercise())
            .getCode();
    if (correctSolution == null || correctSolution.isEmpty()) {
      throw new RequestHadndlingException(
          "Solution code not found, please report problem with exercise");
    }
    Map<CODE_RUNNER_TYPE, String> solutions = new HashMap<>();
    solutions.put(currentlySolvingCodeRunnerr, correctSolution);

    CodeTestRequest codeTestRequest =
        CodeTestRequest.builder()
            .solutionCodes(solutions)
            .testsToRun(testsToGenerateExpectedOutput)
            .inputType(testsToRunRequest.getInputType())
            .outputType(testsToRunRequest.getOutputType())
            .user(request.getUser())
            .timeForExecution(specificationParametersRequest.getTimeForExecution())
            .codeRunnerType(currentlySolvingCodeRunnerr)
            .build();

    CodeTestHandler codeTestHandler = new CodeTestHandler();
    PorgramReusltsSendRequest resultsfromCodeTest =
        (PorgramReusltsSendRequest) codeTestHandler.handle(codeTestRequest);

    log.info("resultsfromCodeTest: " + resultsfromCodeTest);
    final int[] i = {0};

    testsToRunRequest.getAutotestsToRun().stream()
        .filter(x -> x.getExpectedOutput() == null)
        .forEach(
            x -> {
              try {
                log.info(
                    "UnsolvedDatabaseTestsHandler results: "
                        + resultsfromCodeTest.getProgramResults().get(i[0]));
                x.setExpectedOutput(
                    RedoCodeObjectMapper.VarAsString(
                        resultsfromCodeTest.getProgramResults().get(i[0]).getVariables()));
                x.setInput(
                    RedoCodeObjectMapper.VarAsString(
                        resultsfromCodeTest.getProgramResults().get(i[0]).getVariablesInput()));
                i[0]++;
              } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
              }
            });

    return request;
  }
}
