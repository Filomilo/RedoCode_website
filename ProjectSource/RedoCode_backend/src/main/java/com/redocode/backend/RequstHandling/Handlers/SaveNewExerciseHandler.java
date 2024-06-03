package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Auth.AuthenticatedUser;
import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.SpringContextUtil;
import com.redocode.backend.database.Excersize;
import com.redocode.backend.database.ExerciseRepository;

public class SaveNewExerciseHandler extends MessageRequestHandler{

    static final ExerciseRepository  exerciseRepository= SpringContextUtil.getApplicationContext().getBean(ExerciseRepository.class);

    @Override
    boolean handle(RequestBase request) throws RequestHadndlingException {
        ExerciseCreationRequest exerciseCreationRequest= (ExerciseCreationRequest) request;

        Excersize excersize= Excersize.builder()
                .excersizeName(exerciseCreationRequest.getTitle())
                .ram_mb(exerciseCreationRequest.getRam())
                .outputType(exerciseCreationRequest.getOutputType())
                .inputType(exerciseCreationRequest.getInputType())
                .amountOfAutoTests(exerciseCreationRequest.getAmountOfAutoTests())
                .author(((AuthenticatedUser) exerciseCreationRequest.getUser()).getDatabaseUserEntry())
                .build();
       exerciseRepository.save(excersize);
       return true;
    }
}
