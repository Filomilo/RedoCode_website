package com.redocode.backend.VmAcces.vmConnection;

import com.redocode.backend.Excpetions.ContainerException;
import com.redocode.backend.VmAcces.CodeRunners.ConsoleOutput;
import com.redocode.backend.VmAcces.VmStatus;

import java.util.List;
import java.util.concurrent.TimeoutException;

public abstract class VmConnector {

  public abstract String createVm(String vmName, int ramMb) throws ContainerException;

  abstract void pullImageSync(String Image) throws InterruptedException;

  public abstract void startVm(String id);

  public abstract void stopVm(String id);

  public abstract void destroyVm(String id);

  public abstract VmStatus getVmStatus(String id);

  abstract List<String> getRunningVmList();

  public abstract List<String> getVmList();

  public abstract ConsoleOutput executeCommandInVm(String id, long timeout, String... command)
      throws TimeoutException;

  public abstract ConsoleOutput executeCommandInVmWithInput(
      String id, String command, String input);

  public abstract int getContainerRamInMb(String id);

  public abstract void close();
}
