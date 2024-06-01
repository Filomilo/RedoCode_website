package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.Auth.User;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.HashMap;


@SuperBuilder
public class RawCodeRunRequest extends CodeRunnerRequest {
    String Code;


    public RawCodeRunRequest(User user, Date requesttime, CODE_RUNNER_TYPE codeRunnerType, String code) {
        super(user,requesttime,codeRunnerType);
        Code = code;
    }
}
