package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.database.User;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.HashMap;


@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RawCodeRunRequest extends CodeRunnerRequest {
    String Code;


    public RawCodeRunRequest(User user, CODE_RUNNER_TYPE codeRunnerType, String code) {
        super(user,codeRunnerType);
        Code = code;
    }
}
