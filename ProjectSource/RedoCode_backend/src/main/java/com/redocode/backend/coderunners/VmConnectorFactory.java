package com.redocode.backend.coderunners;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
public class VmConnectorFactory {

    static Logger logger= LoggerFactory.getLogger(VmConnectorFactory.class);

    static VmConnector instance=null;
    static VmConnector getVmConnector()
    {
        if(instance!=null)
            return instance;
        String vmMangager=System.getenv("CONTAINER_MANGER");
        logger.info("CONTAINER_MANGER: "+ vmMangager);
        if(vmMangager==null)
        {
            logger.error("No container manager specfied in evenaromnet varaibles");
            throw  new RuntimeException("No container manager specfied in evenaromnet varaibles");
            // TODO: 31/01/2024 replace with custom exception
        }
        switch (vmMangager)
        {
            case "docker": return instance=new VmConnectorDocker();
            case "kubernetes": return instance=new VmConnectorKubernetes();
        }
        logger.error("didn't recognize container manager: "+ vmMangager);
        throw  new RuntimeException("didn't recognize container manager: "+ vmMangager);
        // TODO: 31/01/2024 replace with custom excpetion 
    }

}
