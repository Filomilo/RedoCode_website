package com.redocode.backend.coderunners;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class VmConnectorDockerTest {

    static Logger logger = LoggerFactory.getLogger(VmConnectorDockerTest.class);
    static VmConnectorDocker vmConnectorDocker;

    static final String testContainer="hello-world";
    static final String testContainerNeverEnding="nginx";
    static final String testProgramInput="filipredocode/redocode:inputoutput";
    @BeforeAll
    static void initializeVmDockerConnector()
    {
        logger.info("before all");
        assertDoesNotThrow(()-> {
            vmConnectorDocker = new VmConnectorDocker();
            assertNotNull(vmConnectorDocker);
        });
    }


    @Test
    void createDestroyVm() {
        assertDoesNotThrow(()->{
            int amtOfConatinersBefore=this.vmConnectorDocker.getVmList().size();
            String id= this.vmConnectorDocker.createVm(testContainer);
            int amtOfConatinersAfterCreation=this.vmConnectorDocker.getVmList().size();
            this.vmConnectorDocker.destroyVm(id);
            int amtOfConatinersAfterestrcution=this.vmConnectorDocker.getVmList().size();
            assertEquals(amtOfConatinersBefore+1,amtOfConatinersAfterCreation,"New Vm failed to be created");
            assertEquals(amtOfConatinersBefore,amtOfConatinersAfterestrcution,"New Vm failed to bew removed after creation");
        });

    }

    @Test
    void startVm(){
        assertDoesNotThrow(()->{
            int amtOfConatinersBefore=this.vmConnectorDocker.getVmList().size();
            String id= this.vmConnectorDocker.createVm(testContainer);
            int amtOfConatinersAfterCreation=this.vmConnectorDocker.getVmList().size();
            this.vmConnectorDocker.destroyVm(id);
            int amtOfConatinersAfterestrcution=this.vmConnectorDocker.getVmList().size();
            assertEquals(amtOfConatinersBefore+1,amtOfConatinersAfterCreation,"New Vm failed to be created");
            assertEquals(amtOfConatinersBefore,amtOfConatinersAfterestrcution,"New Vm failed to bew removed after creation");
        });
    }

    @Test
    void startStopContainer()
    {
        assertDoesNotThrow(()->{
            int amtOfConatinersBefore=this.vmConnectorDocker.getVmList().size();
            String id= this.vmConnectorDocker.createVm(testContainerNeverEnding);
            String containerStatusBeforeRunnginh=this.vmConnectorDocker.getVmStatus(id);
            this.vmConnectorDocker.startVm(id);
            String containerStatusAfterRunning=this.vmConnectorDocker.getVmStatus(id);
            this.vmConnectorDocker.stopVm(id);
            String contianerstatusAfterStopping=this.vmConnectorDocker.getVmStatus(id);
            this.vmConnectorDocker.destroyVm(id);
            int amtOfConatinersAfterestrcution=this.vmConnectorDocker.getVmList().size();

            assertEquals("Up",containerStatusAfterRunning.split(" ",2)[0],"Container has a wrong status after starting");
            assertEquals(amtOfConatinersBefore,amtOfConatinersAfterestrcution,"New Vm failed to bew removed after creation");

        });
    }



    @AfterAll
    static void closeVmConnection()
    {
        assertDoesNotThrow(()-> {
            vmConnectorDocker.close();
        });
    }

    @Test
    void executeCommandInVm() {

        assertDoesNotThrow(()->{
            int amtOfConatinersBefore=this.vmConnectorDocker.getVmList().size();
            String id= this.vmConnectorDocker.createVm(testContainerNeverEnding);
            this.vmConnectorDocker.startVm(id);

            String checkpharase="Hello, execute";

            String res= vmConnectorDocker.executeCommandInVm(id,"echo",checkpharase);


            vmConnectorDocker.destroyVm(id);
            int amtOfConatinersAfterestrcution=this.vmConnectorDocker.getVmList().size();


            assertEquals(checkpharase,res,"Command execution result does not much provided echo phraseL "+ checkpharase);
            assertEquals(amtOfConatinersBefore,amtOfConatinersAfterestrcution,"New Vm failed to bew removed after creation");

        });

    }

    @Test
    void executeProgramInputInVMInVm() {

        assertDoesNotThrow(()->{
            int amtOfConatinersBefore=this.vmConnectorDocker.getVmList().size();
            String id= this.vmConnectorDocker.createVm(testProgramInput);
            this.vmConnectorDocker.startVm(id);

            String argumentsInput="Test\nTest2\nTest3\n1\n2\n3\n4\n5\n6\nexit\n";

            String res= vmConnectorDocker.executeCommandInVmWithInput(id,"/inputOutput",argumentsInput);

            vmConnectorDocker.destroyVm(id);
            int amtOfConatinersAfterestrcution=this.vmConnectorDocker.getVmList().size();


assertEquals(argumentsInput.trim(),res,"Testing program output did not match provided arguments");
            assertEquals(amtOfConatinersBefore,amtOfConatinersAfterestrcution,"New Vm failed to bew removed after creation");

        });

    }




}