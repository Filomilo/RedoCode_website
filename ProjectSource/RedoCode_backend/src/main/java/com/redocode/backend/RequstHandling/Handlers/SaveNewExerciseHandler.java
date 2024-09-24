package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.RequstHandling.Requests.Interfaces.*;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.RequstHandling.Requests.SaveExerciseSolutionRequest;
import com.redocode.backend.SpringContextUtil;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.database.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveNewExerciseHandler extends MessageRequestHandler {

  static final ExerciseRepository exerciseRepository =
      SpringContextUtil.getApplicationContext().getBean(ExerciseRepository.class);
  static final UsersRepository usersRepository =
      SpringContextUtil.getApplicationContext().getBean(UsersRepository.class);

  @Override
  String getChainNodeName() {
    return "Saving exercise";
  }

  @Override
  RequestBase handle(RequestBase request) throws RequestHadndlingException {
    this.nodeUpdate(request, "saving to database", ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING);

    assert request instanceof IExerciseInfoRequest;
    assert request instanceof ITestsToRunRequest;
    assert request instanceof ICodeRunnerRequest;
    assert request instanceof ICodeResultsRequest;
    assert request instanceof ICodeRunSpecificationParametersRequest;
    assert request instanceof ISolutionCodesRequest;

    IExerciseInfoRequest exerciseInfoRequest = (IExerciseInfoRequest) request;
    ITestsToRunRequest testsToRunRequest = (ITestsToRunRequest) request;
    ICodeRunnerRequest codeRunnerRequest = (ICodeRunnerRequest) request;
    ICodeRunSpecificationParametersRequest codeRunSpecificationParametersRequest =
        (ICodeRunSpecificationParametersRequest) request;
    ICodeResultsRequest codeResultsRequest = (ICodeResultsRequest) request;
    ISolutionCodesRequest codeSolutionsRequest = (ISolutionCodesRequest) request;

    Excersize excersize =
        Excersize.builder()
            .excersizeName(exerciseInfoRequest.getTitle())
            .ram_mb(codeRunnerRequest.getRam())
            .outputType(testsToRunRequest.getOutputType())
            .inputType(testsToRunRequest.getInputType())
            .amountOfAutoTests(testsToRunRequest.getAmountOfAutoTests())
            .author(request.getUser())
            .description(exerciseInfoRequest.getDescription())
            .exerciseTests(testsToRunRequest.getTestsToRun())
            .breakCharacterInput(testsToRunRequest.isBreakCharacterInput())
            .lowerCaseInput(testsToRunRequest.isBreakCharacterInput())
            .numberInput(testsToRunRequest.isNumberInput())
            .spaceInput(testsToRunRequest.isSpaceInput())
            .specialCharacterInput(testsToRunRequest.isSpecialCharacterInput())
            .upperCaseInput(testsToRunRequest.isUpperCaseInput())
            .valueLengthRangeMin(testsToRunRequest.getLengthRange().getMin())
            .valueLengthRangeMax(testsToRunRequest.getLengthRange().getMax())
            .maxExecutionTimeMS(codeRunSpecificationParametersRequest.getTimeForExecution())
            .build();
    if (testsToRunRequest.getXArrayRange() != null) {
      excersize.setArrayXLengthRangeMax(testsToRunRequest.getXArrayRange().getMax().intValue());
      excersize.setArrayXLengthRangeMin(testsToRunRequest.getXArrayRange().getMin().intValue());
    }
    if (testsToRunRequest.getYArrayRange() != null) {
      excersize.setArrayYLengthRangeMax(testsToRunRequest.getYArrayRange().getMax().intValue());
      excersize.setArrayYLengthRangeMin(testsToRunRequest.getYArrayRange().getMin().intValue());
    }

    for (ExerciseTests test : testsToRunRequest.getTestsToRun()) {
      test.setExcersize(excersize);
    }

    Excersize savedExercise = exerciseRepository.save(excersize);

    this.nodeUpdate(
        request, "saving Solutions to database", ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING);

    SaveExerciseSolutionHandler saveExerciseSolutionHandler = new SaveExerciseSolutionHandler();
    for (CODE_RUNNER_TYPE codeRunnerType : codeResultsRequest.getProgramResults().keySet()) {
      Map<CODE_RUNNER_TYPE, List<ProgramResult>> resultOfSepcifCodeRunner = new HashMap<>();
      resultOfSepcifCodeRunner.put(
          codeRunnerType, codeResultsRequest.getProgramResults().get(codeRunnerType));

      Map<CODE_RUNNER_TYPE, String> solutionOfSepcifCodeRunner = new HashMap<>();
      solutionOfSepcifCodeRunner.put(
          codeRunnerType, codeSolutionsRequest.getSolutionCodes().get(codeRunnerType));

      SaveExerciseSolutionRequest saveExerciseSolutionRequest =
          SaveExerciseSolutionRequest.builder()
              .user(request.getUser())
              .idOfExercise(savedExercise.getId())
              .solutionCodes(solutionOfSepcifCodeRunner)
              .programResults(resultOfSepcifCodeRunner)
              .timeForExecution(codeRunSpecificationParametersRequest.getTimeForExecution())
              .inputType(testsToRunRequest.getInputType())
              .outputType(testsToRunRequest.getOutputType())
              .build();
      saveExerciseSolutionHandler.handle(saveExerciseSolutionRequest);
    }

    this.nodeUpdate(request, "saved to database", ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS);

    return request;
  }
}
