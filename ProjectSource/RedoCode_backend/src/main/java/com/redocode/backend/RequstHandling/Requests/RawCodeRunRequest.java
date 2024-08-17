package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.RequstHandling.Requests.Interfaces.ICodeRunSpecificationParametersRequest;
import com.redocode.backend.database.User;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RawCodeRunRequest extends CodeRunnerRequest
    implements ICodeRunSpecificationParametersRequest {
  String Code;
  @NotNull Long timeForExecution;

  public RawCodeRunRequest(
      User user, CODE_RUNNER_TYPE codeRunnerType, String code, long timeForExecution) {
    super(user, codeRunnerType);
    Code = code;
    this.timeForExecution = timeForExecution;
  }
}
