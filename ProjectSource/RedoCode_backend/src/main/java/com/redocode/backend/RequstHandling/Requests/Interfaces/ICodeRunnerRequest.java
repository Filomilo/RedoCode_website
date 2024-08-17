package com.redocode.backend.RequstHandling.Requests.Interfaces;

import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;

public interface ICodeRunnerRequest {
  /**
   * gives type of codeRunner requestesd
   *
   * @return {@link CODE_RUNNER_TYPE code Runner type}
   */
  public CODE_RUNNER_TYPE getCodeRunnerType();

  /**
   * get amount ram in megabytes that requested coede runner shoudl have
   *
   * @return amount of ram in Mb
   */
  public int getRam();
}
