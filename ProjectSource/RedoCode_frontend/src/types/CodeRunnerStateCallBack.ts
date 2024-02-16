import type CoderunnerState from "./CodeRunnerState";

export default interface CodeRunnerStateCallBack {
    (state: CoderunnerState): void;
  }