package com.redocode.backend.RequstHandling.Requests.Interfaces;

import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;

import java.util.List;
import java.util.Map;

public interface ICodeResultsRequest {
  public Map<CODE_RUNNER_TYPE, List<ProgramResult>> getProgramResults();

  public void setProgramResults(Map<CODE_RUNNER_TYPE, List<ProgramResult>> list);
}
