package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.database.User;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.database.ExerciseTests;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Setter
public class CodeTestRequest extends RawCodeRunRequest {

    List<ExerciseTests> testsToRun;
    Variables.VARIABLES_TYPES inputType;
    Variables.VARIABLES_TYPES outputType;




}
