package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.RequstHandling.Requests.Interfaces.IExerciseIdRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ISolutionCodesRequest;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * Message request that is used mainly to run chain of resposiblity in situation when user runs tests on his code without yet submiting solution
 */
@Getter
@SuperBuilder
@Setter
public class SingleDatabaseExerciseTestRequest extends RequestBase implements IExerciseIdRequest, ISolutionCodesRequest {
    protected Long idOfExercise;
    protected Map<CODE_RUNNER_TYPE,String> solutionCodes;


}
