package com.redocode.backend.coderunners;

import java.util.List;

public abstract class VmConnector {

abstract String createVm(String vmName);


    abstract void pullImageSync(String Image) throws InterruptedException;
    abstract void startVm(String id);
    abstract void stopVm(String id);
    abstract void destroyVm(String id);
    abstract String getVmStatus(String id);
    abstract List<String> getRunningVmList();
    abstract List<String> getVmList();
    abstract String executeCommandInVm(String id, String command);


    abstract public void close();
}
