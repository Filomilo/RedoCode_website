package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.SpringContextUtil;
import com.redocode.backend.database.*;

public class SaveNewExerciseHandler extends MessageRequestHandler{

    static final ExerciseRepository  exerciseRepository= SpringContextUtil.getApplicationContext().getBean(ExerciseRepository.class);
    static final UsersRepository usersRepository= SpringContextUtil.getApplicationContext().getBean(UsersRepository.class);


    @Override
    String getChainNodeName() {
        return "Saving exercise";
    }

    @Override
    RequestBase handle(RequestBase request) throws RequestHadndlingException {
        this.nodeUpdate(request,"saving to database", ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING);

        ExerciseCreationRequest exerciseCreationRequest= (ExerciseCreationRequest) request;

        Excersize excersize= Excersize.builder()
                .excersizeName(exerciseCreationRequest.getTitle())
                .ram_mb(exerciseCreationRequest.getRam())
                .outputType(exerciseCreationRequest.getOutputType())
                .inputType(exerciseCreationRequest.getInputType())
                .amountOfAutoTests(exerciseCreationRequest.getAmountOfAutoTests())
                .author( exerciseCreationRequest.getUser())
                .description(exerciseCreationRequest.getDescription())
                .exerciseTests(exerciseCreationRequest.getTestsToRun())
                .breakCharacterInput(exerciseCreationRequest.isBreakCharacterInput())
                .lowerCaseInput(exerciseCreationRequest.isBreakCharacterInput())
                .numberInput(exerciseCreationRequest.isNumberInput())
                .spaceInput(exerciseCreationRequest.isSpaceInput())
                .specialCharacterInput(exerciseCreationRequest.isSpecialCharacterInput())
                .upperCaseInput(exerciseCreationRequest.isUpperCaseInput())
                .valueLengthRangeMin(exerciseCreationRequest.getLengthRange().getMin())
                .valueLengthRangeMax(exerciseCreationRequest.getLengthRange().getMax())
                .timeForTaskMin(exerciseCreationRequest.getTimeForTaskMin())
                .maxExecutionTimeMS(exerciseCreationRequest.getTimeForExecution())
                .build();
        if(exerciseCreationRequest.getXArrayRange()!=null)
        {
            excersize.setArrayXLengthRangeMax(exerciseCreationRequest.getXArrayRange().getMax().intValue());
            excersize.setArrayXLengthRangeMin(exerciseCreationRequest.getXArrayRange().getMin().intValue());
        }
        if(exerciseCreationRequest.getYArrayRange()!=null)
        {
            excersize.setArrayYLengthRangeMax(exerciseCreationRequest.getYArrayRange().getMax().intValue());
            excersize.setArrayYLengthRangeMin(exerciseCreationRequest.getYArrayRange().getMin().intValue());
        }

        for (ExerciseTests test: exerciseCreationRequest.getTestsToRun()
        ) {
            test.setExcersize(excersize);
        }

        //todo: tempoery adding new user to save
        // exercise this shoudln be happenign and
        // should be checkd in user autheinteacted handler
        // it is tepory soultuion to test chain

        User user=exerciseCreationRequest.getUser();
        if(user.getNickname()==null) {
            user.setNickname("tmp");
            user.setEmail("email@emial.com");
            user.setPassword("passs");
            usersRepository.save(user);

        }
        exerciseRepository.save(excersize);
        this.nodeUpdate(request,"saved to database", ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS);

        return request;
    }
}
