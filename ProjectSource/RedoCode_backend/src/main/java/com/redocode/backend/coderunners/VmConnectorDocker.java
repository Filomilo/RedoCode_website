package com.redocode.backend.coderunners;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.exception.ConflictException;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.StreamType;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.core.command.AttachContainerResultCallback;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.dockerjava.transport.DockerHttpClient.Request;
import com.github.dockerjava.transport.DockerHttpClient.Response;

import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VmConnectorDocker extends VmConnector {

    static Logger logger= LoggerFactory.getLogger(VmConnectorDocker.class);

   private DockerClientConfig dockerConfiguration;
    private DockerHttpClient httpClient;
    private DockerClient dockerClient;

    public VmConnectorDocker() {
        dockerConfiguration= DefaultDockerClientConfig.createDefaultConfigBuilder()
                        .withDockerHost("tcp://localhost:2375")
                        .withDockerTlsVerify(false)
                        .withApiVersion("1.43")
                        .build();



    logger.info("dockerConfiguration: "+dockerConfiguration.toString());
        httpClient= new ApacheDockerHttpClient.Builder()
                .dockerHost(dockerConfiguration.getDockerHost())
                .sslConfig(dockerConfiguration.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();

        Request request = Request.builder()
                .method(Request.Method.GET)
                .path("/_ping")
                .build();

        Response response = httpClient.execute(request);
        if(response.getStatusCode()!=200)
        {
            logger.error("error establishing connection with docker api: "+response.getBody());
            throw new RuntimeException("error establishing connection with docker api: "+response.getBody());
            //todo: replace with custom exeption
        }
        else {
            logger.info("successfully established connection docker api");
        }
        dockerClient = DockerClientImpl.getInstance(dockerConfiguration, httpClient);

    }

    @Override
    String createVm(String Image) {

        CreateContainerResponse response=null;
        logger.info("attempting to create new vm in docker: "+ Image);
        try {
            response=   dockerClient.createContainerCmd(Image).exec();
        }
        catch (NotFoundException ex)
        {
            logger.warn("Docker image: "+ Image+" locally attempting to pull it from repository");
            try {
                pullImageSync(Image);
                response=dockerClient.createContainerCmd(Image).exec();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
                //todo: add own exception
            }
        }
        String virtualMachineIdentifiaction=response.getId();
        logger.info("Successfully created docker conaitner with image: "+ Image+" with id: "+virtualMachineIdentifiaction);
        return virtualMachineIdentifiaction;

    }

    @Override
    void pullImageSync(String image) throws InterruptedException {
        PullImageCmd pullImageCmd = dockerClient.pullImageCmd(image);
        PullImageResultCallback callback = pullImageCmd.exec(new PullImageResultCallback());
        callback.awaitCompletion();
    }


    @Override
    List<String> getRunningVmList() {
            List<Container> conatinerList= dockerClient.listContainersCmd().exec();
            //todo: switch to trace
            logger.trace("returned this container list: "+ Arrays.toString(conatinerList.toArray()));
            List<String> containersId=contianerListToIdList(conatinerList);
            logger.trace("converted to this list of id: "+ Arrays.toString(conatinerList.toArray()));

            return containersId;
    }


    @Override
    void startVm(String id) {
    this.dockerClient.startContainerCmd(id).exec();
    }

    @Override
    void stopVm(String id) {
        logger.info("stopping container "+ id);
    dockerClient.stopContainerCmd(id).exec();
    }

    @Override
    void destroyVm(String id) {
        logger.info("destroying vm with id: "+ id);
try {
    dockerClient.removeContainerCmd(id).exec();

}
catch (ConflictException ex)
{
    logger.warn("warning, container "+ id+" id still running but will be exited to destroy it");
    stopVm(id);
    dockerClient.removeContainerCmd(id).exec();

}

    }


    Container getContianerFromID(String id)
    {
        return dockerClient.listContainersCmd()
                .withShowAll(true)
                .withFilter("id",new ArrayList<>(Arrays.asList(id)))
                .exec()
                .get(0);
    }


    @Override
    String getVmStatus(String id) {
        return getContianerFromID(id).getStatus();
    }

    List<String> contianerListToIdList(List<Container> contiaenrList)
    {
return        contiaenrList.stream()
                .map(Container::getId).toList();
    }


    @Override
    List<String> getVmList() {
       List<Container> conatinerList= dockerClient.listContainersCmd().withShowAll(true).exec();
        //todo: switch to trace
       logger.trace("returned this container list: "+ Arrays.toString(conatinerList.toArray()));
       List<String> containersId=contianerListToIdList(conatinerList);
        logger.trace("converted to this list of id: "+ Arrays.toString(conatinerList.toArray()));

        return containersId;
    }

    @Override
    public void  close() {
        try {
            dockerClient.close();
        } catch (IOException e) {
            //todo add won exception
            throw new RuntimeException(e);
        }
    }



    private String execCreate(String containerId, String... command) {
        return this.dockerClient.execCreateCmd(containerId)
                .withAttachStdout(true).withAttachStderr(true)
                .withAttachStdin(true).withTty(false)
                .withCmd(command).exec().getId();
    }

    @Override
    String executeCommandInVm(String id, String... command) {
        logger.info("Executing commandL :\n"+ Arrays.toString(Arrays.stream(command).toList().toArray())+"\n in conaitner: "+ id);

        String execResponseId = execCreate(id, command);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStream errorStream = new ByteArrayOutputStream();
        try {
            this.dockerClient.execStartCmd(execResponseId).withDetach(false)
                    .exec(new ExecStartResultCallback(outputStream, errorStream)).awaitCompletion();
        } catch (InterruptedException e) {
            return "";
        }
        logger.info("rturned: "+outputStream.toString().trim());
        return outputStream.toString().trim();



//        return null;

    }
}
