package com.redocode.backend.VmAcces.CodeRunners;

import com.github.dockerjava.api.exception.NotFoundException;
import com.redocode.backend.Tools.StringFormatter;
import com.redocode.backend.VmAcces.VmStatus;
import com.redocode.backend.VmAcces.vmConnection.VmConnector;
import com.redocode.backend.VmAcces.vmConnection.VmConnectorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContainerController {
    static Logger logger= LoggerFactory.getLogger(ContainerController.class);
    private String containerId;
    static final VmConnector vmConnector= VmConnectorFactory.getVmConnector();
    private void createContainer(String image)
    {
        logger.info("attempting creation of container controller for image "+ image);
        this.containerId=vmConnector.createVm(image,128);
    }

    public ContainerController(String image)
    {
        createContainer(image);
    }


    private void stopContainer()
    {
        vmConnector.stopVm(containerId);
    }

    private void startContainer()
    {
        vmConnector.startVm(containerId);
    }

    protected ConsoleOutput executeCommand(String... commad)
    {
        return vmConnector.executeCommandInVm(containerId,commad);
    }
    protected ConsoleOutput executeBash(String command)
    {
        return  vmConnector.executeCommandInVm(containerId,"bash", "-c",command);
    }


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
            executeBash(command);


    }



   protected String[] listFiles()
    {
     ConsoleOutput consoleOutput=  executeCommand("ls");
     return consoleOutput.getOutput().split("\n");
    }


    void removeFile(String fileName)
    {
        logger.info("removing file: "+ fileName);
        executeCommand("unlink",fileName);
    }


    public void start() {
        vmConnector.startVm(containerId);
    }

    public String getFileContnt(String fileName) {
        logger.info("getting file conent: "+ fileName);
        ConsoleOutput consoleOutput=executeCommand("cat",fileName);
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
}
