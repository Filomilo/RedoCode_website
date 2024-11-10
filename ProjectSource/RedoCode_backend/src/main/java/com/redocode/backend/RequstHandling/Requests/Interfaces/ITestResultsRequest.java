package com.redocode.backend.RequstHandling.Requests.Interfaces;

import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Program.TestResults;

import java.util.List;
import java.util.Map;

public interface ITestResultsRequest {
  public Map<CODE_RUNNER_TYPE, List<TestResults>> getProgramResults();

  public void setProgramResults(Map<CODE_RUNNER_TYPE, List<TestResults>> list);
}
