package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.Auth.User;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.database.ExerciseTests;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CodeTestRequest extends RawCodeRunRequest {

    ExerciseTests[] testsToRun;



}
