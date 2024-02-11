package com.redocode.backend.coderunners.vmConnection;

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
    abstract String executeCommandInVm(String id, String... command);
abstract String executeCommandInVmWithInput(String id, String command,String input);

    abstract public void close();
}
