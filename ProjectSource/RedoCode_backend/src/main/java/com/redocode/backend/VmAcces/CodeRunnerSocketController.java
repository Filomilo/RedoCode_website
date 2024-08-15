package com.redocode.backend.VmAcces;

import com.redocode.backend.VmAcces.vmConnection.VmConnector;

public class CodeRunnerSocketController {

  private VmConnector connector;
  private static CodeRunnerSocketController instance;

  private CodeRunnerSocketController() {}

  public static CodeRunnerSocketController getInstance() {
    if (instance != null)
      throw new RuntimeException(
          "Code runner controller should be initialized only once with vm connector");
    return instance;
  }
}
