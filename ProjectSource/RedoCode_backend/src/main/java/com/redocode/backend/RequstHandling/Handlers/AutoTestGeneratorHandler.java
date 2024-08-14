package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.RequstHandling.Requests.CodeTestRequest;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.Tools.ExerciseTestFactory;
import com.redocode.backend.database.ExerciseTests;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class AutoTestGeneratorHandler extends MessageRequestHandler {


    @Override
    String getChainNodeName() {
        return "Generating automatic tests";
    }

    @Override
    RequestBase handle(RequestBase request) throws RequestHadndlingException {
        this.nodeUpdate(request, "generation", ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING);

        CodeTestRequest codeTestRequest = (CodeTestRequest) request;

        ExerciseTests[] tests = new ExerciseTestFactory()
                .setAmount(codeTestRequest.getAmountOfAutoTests())
                .setInputType(codeTestRequest.getInputType())
                .setLengthRange(codeTestRequest.getLengthRange())
                .setXArrayRange(codeTestRequest.getXArrayRange())
                .setYArrayRange(codeTestRequest.getYArrayRange())
                .setCapitalLetters(codeTestRequest.isUpperCaseInput())
                .setSpaceCharacters(codeTestRequest.isSpaceInput())
                .setUnderscoreLetters(codeTestRequest.isLowerCaseInput())
                .setBreakCharacters(codeTestRequest.isBreakCharacterInput())
                .setNumbers(codeTestRequest.isNumberInput())
                .setSpaceCharacters(codeTestRequest.isSpecialCharacterInput())
                .build();


        codeTestRequest.setAutotestsToRun(Arrays.stream(tests).toList());


        this.nodeUpdate(request, "generated", ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS);

        return request;
    }
}
