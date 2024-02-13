package com.redocode.backend.VmAcces.vmConnection;

import com.redocode.backend.VmAcces.CodeRunners.ConsoleOutput;

import java.util.List;

public abstract class VmConnector {

public abstract String createVm(String vmName);


    abstract void pullImageSync(String Image) throws InterruptedException;
    public abstract void startVm(String id);
    public abstract void stopVm(String id);
    public abstract void destroyVm(String id);
    abstract String getVmStatus(String id);
    abstract List<String> getRunningVmList();
    abstract List<String> getVmList();
    public abstract ConsoleOutput executeCommandInVm(String id, String... command);
    public abstract ConsoleOutput executeCommandInVmWithInput(String id, String command,String input);

    abstract public void close();
}
