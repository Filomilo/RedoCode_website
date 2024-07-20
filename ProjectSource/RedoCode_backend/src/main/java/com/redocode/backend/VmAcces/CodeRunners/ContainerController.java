package com.redocode.backend.VmAcces.CodeRunners;

import com.github.dockerjava.api.exception.NotFoundException;
import com.redocode.backend.Tools.StringFormatter;
import com.redocode.backend.VmAcces.VmStatus;
import com.redocode.backend.VmAcces.vmConnection.VmConnector;
import com.redocode.backend.VmAcces.vmConnection.VmConnectorFactory;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeoutException;

public class ContainerController {
    static Logger logger= LoggerFactory.getLogger(ContainerController.class);
    private String containerId;
    static final VmConnector vmConnector= VmConnectorFactory.getVmConnector();
    private void createContainer(String image, int ram)
    {
        logger.info("attempting creation of container controller for image "+ image);
        this.containerId=vmConnector.createVm(image,ram);
    }

    public ContainerController(String image, int ram)
    {
        createContainer(image,ram);
    }


    private void stopContainer()
    {
        vmConnector.stopVm(containerId);
    }

    private void startContainer()
    {
        vmConnector.startVm(containerId);
    }

    protected ConsoleOutput executeCommand(long timeout,String... commad) throws TimeoutException {
        return vmConnector.executeCommandInVm(containerId,timeout,commad);
    }
    protected ConsoleOutput executeBash(String command,long timeout) throws TimeoutException {
        return  vmConnector.executeCommandInVm(containerId,timeout,"bash", "-c",command);
    }

    @SneakyThrows
    void createFile(String fileName,String fileContent)
    {
//        String[] fileContentSection=StringFormatter.prepreForFileSaving(fileContent).split("'");
//        for (int i = 0; i <fileContentSection.length ; i++)
//        {
            String command="echo ";
            command+="\""+ StringFormatter.prepreForFileSaving(fileContent)+"\"";
            command+=" > ";
            command+="\""+fileName+"\"";
            logger.info("creating file "+ fileName+ "with content\n"+ fileContent);
            executeBash(command,1000);


    }


    @SneakyThrows
   protected String[] listFiles()
    {

     ConsoleOutput consoleOutput=  executeCommand(500,"ls");
     return consoleOutput.getOutput().split("\n");
    }

    @SneakyThrows
    void removeFile(String fileName)
    {
        logger.info("removing file: "+ fileName);
        executeCommand(500,"unlink",fileName);
    }


    public void start() {
        vmConnector.startVm(containerId);
    }
    @SneakyThrows
    public String getFileContnt(String fileName) {
        logger.info("getting file conent: "+ fileName);
        ConsoleOutput consoleOutput=executeCommand(2000,"cat",fileName);
        return consoleOutput.getOutput();
    }

    public void stop()
    {
        vmConnector.stopVm(containerId);
    }

    public void destroy() {
        try {
            vmConnector.destroyVm(containerId);
        }
        catch (NotFoundException ex)
        {
            logger.warn("Conatiner doesnt exist: "+ ex.getMessage());
        }
    }

    public VmStatus getStatus() {
        return vmConnector.getVmStatus(containerId);
    }

    public int getRamMb(){
        return  vmConnector.getContainerRamInMb(this.containerId);
    }
}
