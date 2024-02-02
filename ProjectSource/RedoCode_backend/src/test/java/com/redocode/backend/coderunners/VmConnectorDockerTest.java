package com.redocode.backend.coderunners;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class VmConnectorDockerTest {

    static Logger logger = LoggerFactory.getLogger(VmConnectorDockerTest.class);
    static VmConnector vmConnectorDocker;

    static final String testContainer="hello-world";
    static final String testContainerNeverEnding="nginx";
    static final String testProgramInput="filipredocode/redocode:inputoutput";
    @BeforeAll
    static void initializeVmDockerConnector()
    {
        logger.info("before all");
        assertDoesNotThrow(()-> {
            vmConnectorDocker = VmConnectorFactory.getVmConnector();
            assertNotNull(vmConnectorDocker);
        });
    }


    @Test
    void createDestroyVm() {
        assertDoesNotThrow(()->{
            int amtOfConatinersBefore= vmConnectorDocker.getVmList().size();
            String id= vmConnectorDocker.createVm(testContainer);
//            Thread.sleep(1000);
            int amtOfConatinersAfterCreation= vmConnectorDocker.getVmList().size();
            vmConnectorDocker.destroyVm(id);
//            Thread.sleep(10000);
            int amtOfConatinersAfterestrcution= vmConnectorDocker.getVmList().size();
            assertEquals(amtOfConatinersBefore+1,amtOfConatinersAfterCreation,"New Vm failed to be created");
            assertEquals(amtOfConatinersBefore,amtOfConatinersAfterestrcution,"New Vm failed to bew removed after creation");
        });

    }

    @Test
    void startVm(){
        assertDoesNotThrow(()->{
            int amtOfConatinersBefore= vmConnectorDocker.getVmList().size();
            String id= vmConnectorDocker.createVm(testContainer);
            int amtOfConatinersAfterCreation= vmConnectorDocker.getVmList().size();
            vmConnectorDocker.destroyVm(id);
            int amtOfConatinersAfterestrcution= vmConnectorDocker.getVmList().size();
            assertEquals(amtOfConatinersBefore+1,amtOfConatinersAfterCreation,"New Vm failed to be created");
            assertEquals(amtOfConatinersBefore,amtOfConatinersAfterestrcution,"New Vm failed to bew removed after creation");
        });
    }

    @Test
    void startStopContainer()
    {
        assertDoesNotThrow(()->{
            int amtOfConatinersBefore= vmConnectorDocker.getVmList().size();
            String id= vmConnectorDocker.createVm(testContainerNeverEnding);
            String containerStatusBeforeRunnginh= vmConnectorDocker.getVmStatus(id);
            vmConnectorDocker.startVm(id);
            String containerStatusAfterRunning= vmConnectorDocker.getVmStatus(id);
            vmConnectorDocker.stopVm(id);
            String contianerstatusAfterStopping= vmConnectorDocker.getVmStatus(id);
            vmConnectorDocker.destroyVm(id);
            int amtOfConatinersAfterestrcution= vmConnectorDocker.getVmList().size();

            // TODO: 02/02/2024 create namespaces for stastus shared between docker and kubernetes management 
            //assertEquals("Up",containerStatusAfterRunning.split(" ",2)[0],"Container has a wrong status after starting");
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
            int amtOfConatinersBefore= vmConnectorDocker.getVmList().size();
            String id= vmConnectorDocker.createVm(testContainerNeverEnding);
            vmConnectorDocker.startVm(id);

            String checkpharase="Hello, execute";

            String res= vmConnectorDocker.executeCommandInVm(id,"echo",checkpharase);


            vmConnectorDocker.destroyVm(id);
            int amtOfConatinersAfterestrcution= vmConnectorDocker.getVmList().size();


            assertEquals(checkpharase,res,"Command execution result does not much provided echo phraseL "+ checkpharase);
            assertEquals(amtOfConatinersBefore,amtOfConatinersAfterestrcution,"New Vm failed to bew removed after creation");

        });

    }

    @Test
    void executeProgramInputInVMInVm() {

        assertDoesNotThrow(()->{
            int amtOfConatinersBefore= vmConnectorDocker.getVmList().size();
            String id= vmConnectorDocker.createVm(testProgramInput);
            vmConnectorDocker.startVm(id);

            String argumentsInput="Test\nTest2\nTest3\n1\n2\n3\n4\n5\n6\nexit\n";

            String res= vmConnectorDocker.executeCommandInVmWithInput(id,"/inputOutput",argumentsInput);

            vmConnectorDocker.destroyVm(id);
            int amtOfConatinersAfterestrcution= vmConnectorDocker.getVmList().size();


assertEquals(argumentsInput.trim(),res,"Testing program output did not match provided arguments");
            assertEquals(amtOfConatinersBefore,amtOfConatinersAfterestrcution,"New Vm failed to bew removed after creation");

        });

    }




}