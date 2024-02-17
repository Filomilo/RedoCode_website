


import type CodeResultsType from "./CodeResultsType";

export default interface CodeRunnerResultsCallBack {
    (results: CodeResultsType[]): void;
  }