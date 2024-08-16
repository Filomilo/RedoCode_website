package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.RequstHandling.Requests.Interfaces.IExerciseIdRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Message request that is used mainly to run chain of resposiblity in situation when user runs tests on his code without yet submiting solution
 */
@Getter
@SuperBuilder
public class SingleDatabaseExerciseTestRequest extends CodeTestRequest implements IExerciseIdRequest {
    protected Long idOfExercise;
}
