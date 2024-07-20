package com.redocode.backend.VmAcces.vmConnection;

import com.redocode.backend.VmAcces.CodeRunners.ConsoleOutput;
import com.redocode.backend.VmAcces.VmStatus;

import java.util.List;

public abstract class VmConnector {

public abstract String createVm(String vmName,int ramMb);


    abstract void pullImageSync(String Image) throws InterruptedException;
    public abstract void startVm(String id);
    public abstract void stopVm(String id);
    public abstract void destroyVm(String id);
    abstract public VmStatus getVmStatus(String id);
    abstract List<String> getRunningVmList();
    public abstract List<String> getVmList();
    public abstract ConsoleOutput executeCommandInVm(String id, String... command);
    public abstract ConsoleOutput executeCommandInVmWithInput(String id, String command,String input);
    public abstract int getContainerRamInMb(String id) throws Exception;;


    abstract public void close();
}
