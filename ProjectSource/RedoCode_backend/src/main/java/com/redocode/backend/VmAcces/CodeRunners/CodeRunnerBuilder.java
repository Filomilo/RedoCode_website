package com.redocode.backend.VmAcces.CodeRunners;

import com.redocode.backend.Excpetions.ContainerException;
import com.redocode.backend.Excpetions.VmControllerException;
import com.redocode.backend.RequstHandling.Requests.CodeRunnerRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class CodeRunnerBuilder {

  CodeRunner instance;

  public CodeRunnerBuilder(CODE_RUNNER_TYPE type, int ram) throws ContainerException, VmControllerException {
    switch (type) {
      case CPP_RUNNER -> instance = new CppCodeRunner(ram);
      case JS_RUNNER -> instance = new JsCodeRunner(ram);
    }
  }

  CodeRunner build() {
    return instance;
  }

  public static CodeRunner build(CodeRunnerRequest codeRunnerRequest) throws ContainerException, VmControllerException {
    return new CodeRunnerBuilder(codeRunnerRequest.getCodeRunnerType(), codeRunnerRequest.getRam())
        .build();
  }
}
