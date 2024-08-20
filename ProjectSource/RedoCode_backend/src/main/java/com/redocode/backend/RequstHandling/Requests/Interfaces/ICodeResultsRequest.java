package com.redocode.backend.RequstHandling.Requests.Interfaces;

import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;

import java.util.List;

public interface ICodeResultsRequest {
  public List<ProgramResult> getProgramResults();

  public void setProgramResults(List<ProgramResult> list);
}
