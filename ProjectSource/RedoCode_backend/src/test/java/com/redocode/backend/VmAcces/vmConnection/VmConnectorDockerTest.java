package com.redocode.backend.VmAcces.vmConnection;

import com.redocode.backend.VmAcces.VmStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
@Disabled("Islotating specific test for debugging")
class VmConnectorDockerTest {


    static Logger logger = LoggerFactory.getLogger(VmConnectorDockerTest.class);
    static VmConnector vmConnectorDocker;

    static final String testContainer="hello-world";
    static final String testContainerNeverEnding="nginx:1.25";
    static final String testProgramInput="filipredocode/redocode:inputoutput";
    @BeforeAll
    static void initializeVmDockerConnector()
    {
        logger.info("before all");
        assertDoesNotThrow(()-> {
            vmConnectorDocker = VmConnectorFactory.getVmConnector();
            logger.info("created vm mangager: "+ vmConnectorDocker);
            assertNotNull(vmConnectorDocker,"vmConnectorDocker is null, could not be initialized");
        });
    }


    @Test
    void createDestroyVm() {
        assertDoesNotThrow(()->{
            int amtOfConatinersBefore= vmConnectorDocker.getVmList().size();
            String id= vmConnectorDocker.createVm(testContainer,128);
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
            String id= vmConnectorDocker.createVm(testContainer,128);
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
            String id= vmConnectorDocker.createVm(testContainerNeverEnding,128);
            VmStatus containerStatusBeforeRunnginh= vmConnectorDocker.getVmStatus(id);
            vmConnectorDocker.startVm(id);
            VmStatus containerStatusAfterRunning= vmConnectorDocker.getVmStatus(id);
            vmConnectorDocker.stopVm(id);
            VmStatus contianerstatusAfterStopping= vmConnectorDocker.getVmStatus(id);
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

    }

    @Test
    void executeCommandInVm() {

        assertDoesNotThrow(()->{
            int amtOfConatinersBefore= vmConnectorDocker.getVmList().size();
            String id= vmConnectorDocker.createVm(testContainerNeverEnding,128);
            vmConnectorDocker.startVm(id);

            String checkpharase="Hello, execute";

            String res= vmConnectorDocker.executeCommandInVm(id,100,"echo",checkpharase).getOutput();


            vmConnectorDocker.destroyVm(id);
            int amtOfConatinersAfterestrcution= vmConnectorDocker.getVmList().size();


            assertEquals(checkpharase,res,"Command execution result does not much provided echo phraseL "+ checkpharase);
            assertEquals(amtOfConatinersBefore,amtOfConatinersAfterestrcution,"New Vm failed to bew removed after creation");

        });

    }
    @Test
    void executeCommandInVmTimeout() {

        assertDoesNotThrow(()->{
            int amtOfConatinersBefore= vmConnectorDocker.getVmList().size();
            String id= vmConnectorDocker.createVm(testContainerNeverEnding,128);
            vmConnectorDocker.startVm(id);

            String checkpharase="Hello, execute";
            assertThrows(Exception.class, ()->{
                vmConnectorDocker.executeCommandInVm(id,1,"echo",checkpharase).getOutput();
            });



            vmConnectorDocker.destroyVm(id);


        });

    }


    @Test
    void executeProgramInputInVMInVm() {

        assertDoesNotThrow(()->{
            int amtOfConatinersBefore= vmConnectorDocker.getVmList().size();
            String id= vmConnectorDocker.createVm(testProgramInput,128);
            vmConnectorDocker.startVm(id);

            String argumentsInput="Test\nTest2\nTest3\n1\n2\n3\n4\n5\n6\nexit\n";

            String res= vmConnectorDocker.executeCommandInVmWithInput(id,"/inputOutput",argumentsInput).getOutput();

            vmConnectorDocker.destroyVm(id);
            int amtOfConatinersAfterestrcution= vmConnectorDocker.getVmList().size();


assertEquals(argumentsInput,res,"Testing program output did not match provided arguments");
            assertEquals(amtOfConatinersBefore,amtOfConatinersAfterestrcution,"New Vm failed to bew removed after creation");

        });

    }
    @ParameterizedTest
    @ValueSource(ints = {6,8,16,32,64,128,256,512,1024,2048,2048*6})
    void getRamFromConatainer(int ram){
        assertDoesNotThrow(()->{
            int amtOfConatinersBefore= vmConnectorDocker.getVmList().size();
            String id= vmConnectorDocker.createVm(testContainer,ram);
            int containerRam= vmConnectorDocker.getContainerRamInMb(id);
            vmConnectorDocker.destroyVm(id);
            assertEquals(ram,containerRam);
        });
    }



}