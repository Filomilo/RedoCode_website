package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.RequstHandling.Requests.Interfaces.*;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.Tools.ExerciseTestFactory;
import com.redocode.backend.database.ExerciseTests;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class AutoTestGeneratorHandler extends MessageRequestHandler {

  @Override
  String getChainNodeName() {
    return "Generating automatic tests";
  }

  @Override
  RequestBase handle(RequestBase request) throws RequestHadndlingException {

    if (!(request instanceof ITestsToRunRequest)) {
      throw new RequestHadndlingException(
          "Request must implement IExerciseIdRequest,ISolutionCodesRequest");
    }
    ITestsToRunRequest testsToRunRequest = (ITestsToRunRequest) request;
    this.nodeUpdate(request, "generation", ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING);

    ExerciseTests[] tests =
        new ExerciseTestFactory()
            .setAmount(testsToRunRequest.getAmountOfAutoTests())
            .setInputType(testsToRunRequest.getInputType())
            .setLengthRange(testsToRunRequest.getLengthRange())
            .setXArrayRange(testsToRunRequest.getXArrayRange())
            .setYArrayRange(testsToRunRequest.getYArrayRange())
            .setCapitalLetters(testsToRunRequest.isUpperCaseInput())
            .setSpaceCharacters(testsToRunRequest.isSpaceInput())
            .setUnderscoreLetters(testsToRunRequest.isLowerCaseInput())
            .setBreakCharacters(testsToRunRequest.isBreakCharacterInput())
            .setNumbers(testsToRunRequest.isNumberInput())
            .setSpaceCharacters(testsToRunRequest.isSpecialCharacterInput())
            .build();

    testsToRunRequest.setAutotestsToRun(Arrays.stream(tests).toList());

    this.nodeUpdate(request, "generated", ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS);

    return request;
  }
}
