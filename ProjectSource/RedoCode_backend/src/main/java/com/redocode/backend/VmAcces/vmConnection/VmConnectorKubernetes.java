package com.redocode.backend.VmAcces.vmConnection;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.redocode.backend.VmAcces.CodeRunners.ConsoleOutput;
import com.redocode.backend.VmAcces.VmStatus;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.api.model.apps.DeploymentList;
import io.fabric8.kubernetes.client.*;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.dsl.ExecWatch;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Slf4j
public class VmConnectorKubernetes  extends VmConnector{


    KubernetesClient kubernetesClient =null;

    final String codeRunnersNamespace="coderunners";

    Logger logger= LoggerFactory.getLogger(VmConnectorFactory.class);
    private Object TimeUnit;

    public VmConnectorKubernetes() {
        try {
            String kubernetesHost=System.getenv("KUBERNETES_HOST");
            if(kubernetesHost==null)
                kubernetesClient=new KubernetesClientBuilder().build();
            else {
                Config config = new ConfigBuilder().withMasterUrl("https://mymaster.com").build();
                kubernetesClient = new KubernetesClientBuilder().withConfig(config).build();
            }
            Namespace namespace= kubernetesClient.namespaces().withName(codeRunnersNamespace).get();
            System.out.println("namespace: "+namespace);
try {
// TODO: 31/01/2024 repplace try catch with more reliable way of checking whether or namespace already exist 
    Namespace codeRunnerNameSpace = new NamespaceBuilder().withNewMetadata().withName(codeRunnersNamespace).endMetadata().build();
    kubernetesClient.namespaces().resource(codeRunnerNameSpace).create();
}
catch (Exception ex)
{
    
}
        
        }
        catch (Exception ex)
        {
            logger.error("Couldnt create api connetion to kubernetes: "+ ex.getMessage());
            ex.printStackTrace();
            throw  new RuntimeException("Couldnt create api connetion to kubernetes: "+ ex.getMessage());
            // TODO: 31/01/2024 create custom exception
        }


    }

    @Override
    public String createVm(String image, int ramMb) {
        String name=UUID.randomUUID().toString();
        //TODO add ram handling
        Deployment deployment = new DeploymentBuilder()
                .withNewMetadata().withName(name).endMetadata()
                .withNewSpec()
                .withNewSelector()
                .withMatchLabels(Collections.singletonMap("app", name))
                .endSelector()
                .withNewTemplate()
                .withNewMetadata().addToLabels("app", name).endMetadata()
                .withNewSpec()
                .addNewContainer()
                .withName(name)
                .withImage(image)
                .addNewPort().withContainerPort(80).endPort()
                .endContainer()
                .endSpec()
                .endTemplate()
                .endSpec()

                .build();
            logger.info("name: "+ name);
        Deployment dep= kubernetesClient.apps().deployments().inNamespace(codeRunnersNamespace).resource(deployment).create();
        logger.info("Created vm with id: "+ dep.getMetadata().getName());
        return name;
    }

    @Override
    void pullImageSync(String Image) throws InterruptedException {

    }

    @Override
    public void startVm(String id) {

    }

    @Override
    public void stopVm(String id) {

    }

   Pod getPodFromID(String id)
   {
       logger.info(Arrays.toString(kubernetesClient.pods().inNamespace(codeRunnersNamespace).list().getItems().toArray()));
       return kubernetesClient
               .pods()
               .inNamespace(codeRunnersNamespace)
               .withLabel("app",id)
               .list()
               .getItems()
               .get(0);
   }


 Deployment  getDeploymentFromId(String id)
 {
     return this.kubernetesClient.apps().deployments().inNamespace(codeRunnersNamespace).withName(id).get();
 }

    @Override
    public void destroyVm(String id) {



        List<StatusDetails> listStastu= this.kubernetesClient
                .apps()
                .deployments()
                .inNamespace(codeRunnersNamespace)
                .withName(id)

                .withGracePeriod(0)
                .delete();

        this.kubernetesClient
        .apps()
                .deployments()
                .inNamespace(codeRunnersNamespace)
                .withName(id)
                        .waitUntilCondition((deployment )-> {

                            DeploymentList DepLisy =  kubernetesClient.apps()
                                    .deployments().inNamespace(codeRunnersNamespace)
                                    .list();

                            List<Deployment> deployments = DepLisy.getItems();
//                            logger.info("test: "+deployments.get(0).getMetadata().getName());
                            return deployments.stream().noneMatch(d-> Objects.equals(d.getMetadata().getName(), id));
                        },30, java.util.concurrent.TimeUnit.SECONDS);


    }

    @Override
    public VmStatus getVmStatus(String id) {
//     return   getDeploymentFromId(id).getStatus().toString();
       return null;
        // TODO: 14/02/2024 create enum statsu hadnler for kubernetes
    }

    @Override
    List<String> getRunningVmList() {
        DeploymentList list= this.kubernetesClient.apps().deployments().inNamespace(codeRunnersNamespace).list();
        return list.getItems().stream().map(deployment -> deployment.getMetadata().getName()).toList();
    }

    @Override
    public List<String> getVmList() {
       return kubernetesClient.apps()
               .deployments()
               .inNamespace(codeRunnersNamespace)
               .list()
               .getItems()
               .stream()
               .map(Deployment::getMetadata)
               .map(ObjectMeta::getUid)
               .toList();


    }

    @Override
    public ConsoleOutput executeCommandInVm(String id, String... command) {
        logger.info("Executign command in "+ id+" : \n"+Arrays.toString(Arrays.stream(command).toArray()));


try {
    Pod pod=getPodFromID(id);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream outputError = new ByteArrayOutputStream();

    ExecWatch exec = kubernetesClient
            .pods()
            .inNamespace(codeRunnersNamespace)
            .withName(pod.getMetadata().getName())
            .writingOutput(outputStream)
            .writingError(outputError)
            .exec(command);
    //latch.await();
    int exitCode=exec.exitCode().get();  //todo: exit code might be useful or not in the future, might need delete, need closelook in  later stages of development
    logger.info("EXIT CODE: "+ exitCode);

 // String  outputString = outputStream.toString();
    return new ConsoleOutput(exitCode,outputStream.toString().trim(),outputError.toString().trim());
}
catch (Exception ex)
{
    ex.printStackTrace();
    // TODO: 02/02/2024 add better exception handling
    return null;
}
    }

    @Override
    public ConsoleOutput executeCommandInVmWithInput(String id, String command, String input) {
        logger.info("Executign command in "+ id+" : \n"+command+" with input: "+ input);

        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        try {
            Pod pod = getPodFromID(id);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream outputError = new ByteArrayOutputStream();

            ExecWatch exec = kubernetesClient
                    .pods()
                    .inNamespace(codeRunnersNamespace)
                    .withName(pod.getMetadata().getName())
                    .redirectingInput()
                    .writingOutput(outputStream)
                    .writingError(outputError)
                    .exec(command);

            //latch.await();

//            ExecWatch exec3 = kubernetesClient
//                    .pods()
//                    .inNamespace(codeRunnersNamespace)
//                    .withName(pod.getMetadata().getName())
//                    .attach();
            exec.getInput().write(input.getBytes());
            exec.getInput().flush();
            int exitCode = exec.exitCode().get();  //todo: exit code might be useful or not in the future, might need delete, need closelook in  later stages of development
            logger.info("EXIT CODE: " + exitCode);

           // String outputString = outputStream.toString();
            return new ConsoleOutput(exitCode,outputStream.toString().trim(),outputError.toString().trim());
        }
        catch(Exception ex)
            {
                ex.printStackTrace();
                // TODO: 02/02/2024 neeed better eexception
            }

        return  null;
    }

    @Override
    public int getContainerRamInMb(String id)  {
       log.error("Not implemented yet");
        return -1;
    }

    void destroyEveryThing()
    {
        this.kubernetesClient.apps().deployments().inNamespace(codeRunnersNamespace).delete();
        this.kubernetesClient.pods().inNamespace(codeRunnersNamespace).delete();
    }


    @Override
    public void close() {
        destroyEveryThing();
this.kubernetesClient.close();
    }
}
