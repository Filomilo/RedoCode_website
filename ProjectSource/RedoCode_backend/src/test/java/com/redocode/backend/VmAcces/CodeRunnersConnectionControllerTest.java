package com.redocode.backend.VmAcces;

import com.redocode.backend.Auth.*;
import com.redocode.backend.RedoCodeController;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.RequstHandling.Requests.CodeRunnerRequest;
import com.redocode.backend.VmAcces.vmConnection.VmConnectorFactory;
import com.redocode.backend.database.User;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.reflect.Code;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@ContextConfiguration
    @Slf4j
//@Disabled("Islotating specific test for debugging")
class CodeRunnersConnectionControllerTest {



    @Autowired
    CodeRunnersController codeRunnersController;
    @Autowired
    RedoCodeController redoCodeController;
    List<User> unathenicatedUsers=new ArrayList<User>();
    List<User> athenicatedUsers=new ArrayList<User>();
    List<User> premiumUsers=new ArrayList<User>();
    List<User> adminsUsers=new ArrayList<User>();

    List<User> allUsers=new ArrayList<User>();
    Random random= new Random();

    @BeforeEach
    void setUp() {
        log.info("code runner controllwe test");
        int amountOfUnauth=random.nextInt(10,20);
        int authenticated=random.nextInt(10,20);
        int amountPremieum=random.nextInt(10,20);
        int amountAdmin=random.nextInt(10,20);

//        int amountOfUnauth=random.nextInt(1,2);
//        int authenticated=random.nextInt(1,2);
//        int amountPremieum=random.nextInt(1,2);
//        int amountAdmin=random.nextInt(1,2);
        for (int i = 0; i < amountOfUnauth; i++) {
            User user=new User(UUID.randomUUID().toString(),"nick", User.USER_TYPE.UNAUTHENTICATED);
            unathenicatedUsers.add(user);
            redoCodeController.addConnectedUser(user);
            allUsers.add(user);
        }

        for (int i = 0; i < authenticated; i++) {
            User user=new User(UUID.randomUUID().toString(),"1", User.USER_TYPE.AUTHENTICATED);
            athenicatedUsers.add(user);
            redoCodeController.addConnectedUser(user);
            allUsers.add(user);
        }

        for (int i = 0; i < amountPremieum ; i++) {
            User user=new User(UUID.randomUUID().toString(),"1", User.USER_TYPE.PREMIUM);
            premiumUsers.add(user);
            redoCodeController.addConnectedUser(user);
            allUsers.add(user);
        }

        for (int i = 0; i < amountAdmin ; i++) {
            User user=new User(UUID.randomUUID().toString(),"nick", User.USER_TYPE.ADMIN);
            adminsUsers.add(user);
            redoCodeController.addConnectedUser(user);
            allUsers.add(user);
        }
    log.info("finshed setup");

    }

    @AfterEach
    void tearDown() {
        log.info("Startign teradown");
        for (User user: allUsers
             ) {
            redoCodeController.removeConnectedUser(user);
        }

        unathenicatedUsers.clear();
        athenicatedUsers.clear();
        premiumUsers.clear();
        allUsers.clear();
        redoCodeController.reset();
        codeRunnersController.reset();
        log.info("finshed teradown");
    }

    CODE_RUNNER_TYPE getRandomCodeRunnerType()
    {
        int i= random.nextInt(0,2);
        switch (i){
            case 0: return CODE_RUNNER_TYPE.CPP_RUNNER;
            case 1: return CODE_RUNNER_TYPE.JS_RUNNER;
        }
        return CODE_RUNNER_TYPE.CPP_RUNNER;
    }

    @Test
    void requestVmSingle() {
        assertDoesNotThrow(()->{
            log.info(" gett mat of vms before");
            int amountOFVmsBefore= VmConnectorFactory.getVmConnector().getVmList().size();

            User user=allUsers.get(random.nextInt(allUsers.size()));
            log.info("creating vm fo single rnadom ser");
            CodeRunnerRequest codeRunnerRequest = (CodeRunnerRequest) CodeRunnerRequest.builder()
                    .codeRunnerType(getRandomCodeRunnerType())
                    .user(user)
                    .build();


            VmStatus statusBeforeRequest=codeRunnersController.getUserVmStatus(user);
            log.info("requesting vm: "+ codeRunnerRequest);
            codeRunnersController.requestVm(codeRunnerRequest);

            long timeout=5000;
            long sleep=500;
            while (codeRunnersController.getUserVmStatus(user)!=VmStatus.RUNNING_MACHINE)
            {
                Thread.sleep(sleep);
                if((timeout-=sleep)<0)
                    break;
            }
            VmStatus statusAfterRequest=codeRunnersController.getUserVmStatus(user);
            ;
            CodeRunner codeRunner=codeRunnersController.getUserCodeRunner(user);
            codeRunner.stop();

            timeout=5000;
            while (codeRunnersController.getUserVmStatus(user)!=VmStatus.MACHINE_STOPPED)
            {
                Thread.sleep(sleep);
                if((timeout-=sleep)<0)
                    break;
            }
            VmStatus statusAafterStop=codeRunnersController.getUserVmStatus(user);



            codeRunner.start();

            timeout=5000;
            while (codeRunnersController.getUserVmStatus(user)!=VmStatus.RUNNING_MACHINE)
            {
                Thread.sleep(sleep);
                if((timeout-=sleep)<0)
                    break;
            }
            VmStatus statusAafterStartignAgain=codeRunnersController.getUserVmStatus(user);


            codeRunnersController.deregisterUser(user);
            int amountOfUsersAfter= VmConnectorFactory.getVmConnector().getVmList().size();


            assertEquals(VmStatus.NOT_REQUESTED,statusBeforeRequest);
            assertEquals(VmStatus.RUNNING_MACHINE,statusAfterRequest);
            assertEquals(VmStatus.MACHINE_STOPPED,statusAafterStop);
            assertEquals(VmStatus.RUNNING_MACHINE,statusAafterStartignAgain);
            assertEquals(amountOFVmsBefore,amountOfUsersAfter);
        });
    }


    @Test
    void testQueue()
    {
        assertDoesNotThrow(()->{
            List<User> bufferFillUser= new ArrayList<>();
            for (int i = 0; i < CodeRunnersController.maxAmountOfVm; i++) {
                User user=new User(UUID.randomUUID().toString());
                bufferFillUser.add(user);
                redoCodeController.addConnectedUser(user);
                CodeRunnerRequest req=   CodeRunnerRequest.builder()
                        .user(user)
                        .codeRunnerType(getRandomCodeRunnerType())
//                        .requestTime(new Date())
                        .build();
                codeRunnersController.requestVm(req);
                Thread.sleep(100);
            }


            for (User user: unathenicatedUsers
            ) {
                redoCodeController.addConnectedUser(user);
                CodeRunnerRequest req=   CodeRunnerRequest.builder()
                        .user(user)
                        .codeRunnerType(getRandomCodeRunnerType())
//                        .requestTime(new Date())
                        .build();
                codeRunnersController.requestVm(req);
                Thread.sleep(100);
            }

            for (User user: adminsUsers
            ) {
                redoCodeController.addConnectedUser(user);
                CodeRunnerRequest req=   CodeRunnerRequest.builder()
                        .user(user)
                        .codeRunnerType(getRandomCodeRunnerType())
//                        .requestTime(new Date())
                        .build();
                codeRunnersController.requestVm(req);
                Thread.sleep(100);
            }

            for (User user: athenicatedUsers
            ) {
                redoCodeController.addConnectedUser(user);
                CodeRunnerRequest req=   CodeRunnerRequest.builder()
                        .codeRunnerType(getRandomCodeRunnerType())
                        .user(user)
//                        .requestTime(new Date())
                        .build();
                codeRunnersController.requestVm(req);
                Thread.sleep(100);
            }
            for (User user: premiumUsers
            ) {
                redoCodeController.addConnectedUser(user);
                CodeRunnerRequest req=   CodeRunnerRequest.builder()
                        .user(user)
                        .codeRunnerType(getRandomCodeRunnerType())
//                        .requestTime(new Date())
                        .build();
                codeRunnersController.requestVm(req);
                Thread.sleep(100);
            }


            PriorityBlockingQueue<CodeRunnerRequest> queue= codeRunnersController.requestQueue;
            int queueSizeAfterAding=queue.size();




            for (User user: allUsers
            ) {
                VmStatus status= codeRunnersController.getUserVmStatus(user);
                assertEquals(VmStatus.AWAITING_ACCES,status);
            }







//        check order


            for (User user: adminsUsers
            ) {
                User retrivedUser=queue.poll().getUser();
                assertEquals(user,retrivedUser,"expected first added admin");
            }

            for (User user: premiumUsers
            ) {
                CodeRunnerRequest rq=queue.poll();
                assert rq != null;
                User retrivedUser=rq.getUser();
                assertEquals(user,retrivedUser,"expected first added premium: ");
            }

            for (User user: athenicatedUsers
            ) {
                User retrivedUser=queue.poll().getUser();
                assertEquals(user,retrivedUser,"expected first added authenticated user");
            }
            for (User user: unathenicatedUsers
            ) {
                User retrivedUser=queue.poll().getUser();
                assertEquals(user,retrivedUser,"expected first added una unauthenticated user");
            }

            assertEquals(
                    unathenicatedUsers.size()
                            + athenicatedUsers.size()
                            + premiumUsers.size()
                            +adminsUsers.size(),
                    queueSizeAfterAding
            );


        });

    }


    @Test
    void testAutoStartFromQueue()
    {
        List<User> bufferFillUser= new ArrayList<>();
        for (int i = 0; i < CodeRunnersController.maxAmountOfVm; i++) {
            User user=new User(UUID.randomUUID().toString());
            bufferFillUser.add(user);
            redoCodeController.addConnectedUser(user);
            CodeRunnerRequest req=   CodeRunnerRequest.builder()
                    .user(user)
                    .codeRunnerType(getRandomCodeRunnerType())
                    .build();
            codeRunnersController.requestVm(req);

        }

        User user1 = new User(UUID.randomUUID().toString());
        User user2 = new User(UUID.randomUUID().toString());


        redoCodeController.addConnectedUser(user1);
        redoCodeController.addConnectedUser(user2);

        CodeRunnerRequest req1= CodeRunnerRequest.builder()
                .codeRunnerType(getRandomCodeRunnerType())
                .user(user1)
                .build();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        CodeRunnerRequest req2= CodeRunnerRequest.builder()
                .codeRunnerType(getRandomCodeRunnerType())
                .user(user2)
                .build();



        codeRunnersController.requestVm(req1);
        codeRunnersController.requestVm(req2);

        VmStatus statusBeforeStarting1= codeRunnersController.getUserVmStatus(user1);
        VmStatus statusBeforeStarting2= codeRunnersController.getUserVmStatus(user2);

        codeRunnersController.deregisterUser(bufferFillUser.get(0));

        VmStatus statusAfterFirstStartStarting1= codeRunnersController.getUserVmStatus(user1);
        VmStatus statusAfterFirstStartStarting2= codeRunnersController.getUserVmStatus(user2);

        codeRunnersController.deregisterUser(user1);

        VmStatus statusAfterSecondStart1= codeRunnersController.getUserVmStatus(user1);
        VmStatus statusAfterSecondStart2= codeRunnersController.getUserVmStatus(user2);




        assertEquals(VmStatus.AWAITING_ACCES,statusBeforeStarting1);
        assertEquals(VmStatus.AWAITING_ACCES,statusBeforeStarting2);
        assertEquals(VmStatus.RUNNING_MACHINE,statusAfterFirstStartStarting1);
        assertEquals(VmStatus.AWAITING_ACCES,statusAfterFirstStartStarting2);
        assertEquals(VmStatus.NOT_REQUESTED,statusAfterSecondStart1);
        assertEquals(VmStatus.RUNNING_MACHINE,statusAfterSecondStart2);

    }



//    @Test
//    void getInstnace() {
//        CodeRunnersController codeRunnersController1= CodeRunnersController.getInstance();
//        CodeRunnersController codeRunnersController2= CodeRunnersController.getInstance();
//        assertEquals(this.codeRunnersController,codeRunnersController1);
//        assertEquals(this.codeRunnersController,codeRunnersController2 );
//    }
}