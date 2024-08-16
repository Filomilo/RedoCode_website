package com.redocode.backend.VmAcces.CodeRunners;

public class CodeRunnersData {

  public static CODE_RUNNER_TYPE getCodeRunnerByLanuageName(String name) {
    switch (name) {
      case "cpp":
        return CODE_RUNNER_TYPE.CPP_RUNNER;
      case "js":
        return CODE_RUNNER_TYPE.JS_RUNNER;
      default:
        return CODE_RUNNER_TYPE.UNIDENTIFIED;
    }
  }
}
