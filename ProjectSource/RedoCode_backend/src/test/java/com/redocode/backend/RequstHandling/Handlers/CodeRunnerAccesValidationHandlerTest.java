package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.database.User;
import com.redocode.backend.RedoCodeBackendApplication;
import com.redocode.backend.RedoCodeController;
import com.redocode.backend.RequstHandling.Requests.RawCodeRunRequest;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.VmAcces.CodeRunnersController;
import com.redocode.backend.VmAcces.VmStatus;
import com.redocode.backend.database.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Disabled("Not wokrking in gihtub pipleine eveneroment")
class CodeRunnerAccessValidationHandlerTest {

        @Autowired
           RedoCodeController redoCodeController;
@Autowired
    CodeRunnersController codeRunnersController;
@Autowired
    UsersRepository usersRepository;
    CodeRunnerAccesValidationHandler codeRunnerAccesValidationHandler;
User user;
User authorizedUser;
    @BeforeEach
    public void registerUser()
    {
        codeRunnersController.reset();
        user=new User("555"+ UUID.randomUUID());
        redoCodeController.addConnectedUser(user);

        authorizedUser=usersRepository.getReferenceById(1L);
        authorizedUser.setSessionID("222222222"+ UUID.randomUUID());
        redoCodeController.addConnectedUser(authorizedUser);

    }
    @BeforeEach
    public void cerateHandler()
    {
      codeRunnerAccesValidationHandler=new CodeRunnerAccesValidationHandler();
    }
    @AfterEach
    public void deregisterUser()
    {
        redoCodeController.removeConnectedUser(user);
        redoCodeController.removeConnectedUser(authorizedUser);
    }

    @Test
    void handleCppRequest() {

        assertNotNull(codeRunnerAccesValidationHandler);
        VmStatus statusOnBeging = codeRunnersController.getUserVmStatus(user);
        RawCodeRunRequest rawCodeRunRequest= (RawCodeRunRequest) RawCodeRunRequest
                .builder()
                .codeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER)
                .user(user)
                .build();
        assertDoesNotThrow(()->{
            codeRunnerAccesValidationHandler.handle(rawCodeRunRequest);
        });
        VmStatus statusAfter = codeRunnersController.getUserVmStatus(user);
        CodeRunner codeRunner= codeRunnersController.getUserCodeRunner(user);
        assertEquals(VmStatus.NOT_REQUESTED, statusOnBeging);
        assertEquals(VmStatus.RUNNING_MACHINE, statusAfter);
        assertEquals(CODE_RUNNER_TYPE.CPP_RUNNER,codeRunner.getType());
    }


    @Test
    void handleJsRequest() {
        assertNotNull(codeRunnerAccesValidationHandler);
        VmStatus statusOnBeging = codeRunnersController.getUserVmStatus(user);
        RawCodeRunRequest rawCodeRunRequest= (RawCodeRunRequest) RawCodeRunRequest
                .builder()
                .codeRunnerType(CODE_RUNNER_TYPE.JS_RUNNER)
                .user(user)
                .build();
        assertDoesNotThrow(()->{
            codeRunnerAccesValidationHandler.handle(rawCodeRunRequest);
        });
        VmStatus statusAfter = codeRunnersController.getUserVmStatus(user);
        CodeRunner codeRunner= codeRunnersController.getUserCodeRunner(user);
        assertEquals(VmStatus.NOT_REQUESTED, statusOnBeging);
        assertEquals(VmStatus.RUNNING_MACHINE, statusAfter);
        assertEquals(CODE_RUNNER_TYPE.JS_RUNNER,codeRunner.getType());
    }

    @Test
    void handleAlreadyHavingCodeRunnerRequest() {
        assertNotNull(codeRunnerAccesValidationHandler);



        VmStatus statusOnBeging = codeRunnersController.getUserVmStatus(user);
        RawCodeRunRequest rawCodeRunRequest= (RawCodeRunRequest) RawCodeRunRequest
                .builder()
                .codeRunnerType(CODE_RUNNER_TYPE.JS_RUNNER)
                .user(user)
                .build();
        assertDoesNotThrow(()->{
            codeRunnerAccesValidationHandler.handle(rawCodeRunRequest);
        });
        VmStatus statusAfter = codeRunnersController.getUserVmStatus(user);
        CodeRunner codeRunnerFirst= codeRunnersController.getUserCodeRunner(user);

        assertDoesNotThrow(()->{
            codeRunnerAccesValidationHandler.handle(rawCodeRunRequest);
        });
        VmStatus statusAfterSecond = codeRunnersController.getUserVmStatus(user);
        CodeRunner codeRunnerSecond= codeRunnersController.getUserCodeRunner(user);
        assertEquals(VmStatus.NOT_REQUESTED, statusOnBeging);
        assertEquals(VmStatus.RUNNING_MACHINE, statusAfter);
        assertEquals(VmStatus.RUNNING_MACHINE, statusAfterSecond);
        assertEquals(CODE_RUNNER_TYPE.JS_RUNNER,codeRunnerFirst.getType());
        assertEquals(CODE_RUNNER_TYPE.JS_RUNNER,codeRunnerSecond.getType());
        assertEquals(codeRunnerFirst,codeRunnerSecond);
    }

    @Test
    void handleSwitchingRequest() {
        assertNotNull(codeRunnerAccesValidationHandler);
        VmStatus statusOnBeging = codeRunnersController.getUserVmStatus(user);
        RawCodeRunRequest rawCodeRunRequest= (RawCodeRunRequest) RawCodeRunRequest
                .builder()
                .codeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER)
                .user(user)
                .build();
        assertDoesNotThrow(()->{
            codeRunnerAccesValidationHandler.handle(rawCodeRunRequest);
        });
        VmStatus statusAfterCpp = codeRunnersController.getUserVmStatus(user);
        CodeRunner codeRunner= codeRunnersController.getUserCodeRunner(user);
        CODE_RUNNER_TYPE typeAfterCpp=codeRunner.getType();

        RawCodeRunRequest rawCodeRunRequestJS= (RawCodeRunRequest) RawCodeRunRequest
                .builder()
                .codeRunnerType(CODE_RUNNER_TYPE.JS_RUNNER)
                .user(user)
                .build();
        assertDoesNotThrow(()->{
            codeRunnerAccesValidationHandler.handle(rawCodeRunRequestJS);
        });
        VmStatus statusAfterJs = codeRunnersController.getUserVmStatus(user);
        codeRunner= codeRunnersController.getUserCodeRunner(user);
        CODE_RUNNER_TYPE typeAfterJs=codeRunner.getType();


        assertEquals(VmStatus.NOT_REQUESTED, statusOnBeging);
        assertEquals(VmStatus.RUNNING_MACHINE, statusAfterCpp);
        assertEquals(CODE_RUNNER_TYPE.CPP_RUNNER,typeAfterCpp);
        assertEquals(VmStatus.RUNNING_MACHINE, statusAfterJs);
        assertEquals(CODE_RUNNER_TYPE.JS_RUNNER,typeAfterJs);
    }



    @Test
    void handleCppRequestAuthirzed() {

        assertNotNull(codeRunnerAccesValidationHandler);
        VmStatus statusOnBeging = codeRunnersController.getUserVmStatus(authorizedUser);
        RawCodeRunRequest rawCodeRunRequest= (RawCodeRunRequest) RawCodeRunRequest
                .builder()
                .codeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER)
                .user(authorizedUser)
                .build();
        assertDoesNotThrow(()->{
            codeRunnerAccesValidationHandler.handle(rawCodeRunRequest);
        });
        VmStatus statusAfter = codeRunnersController.getUserVmStatus(authorizedUser);
        CodeRunner codeRunner= codeRunnersController.getUserCodeRunner(authorizedUser);
        assertEquals(VmStatus.NOT_REQUESTED, statusOnBeging);
        assertEquals(VmStatus.RUNNING_MACHINE, statusAfter);
        assertEquals(CODE_RUNNER_TYPE.CPP_RUNNER,codeRunner.getType());
    }

    @Test
    void handleJsRequestAuthorized() {
        assertNotNull(codeRunnerAccesValidationHandler);
        VmStatus statusOnBeging = codeRunnersController.getUserVmStatus(authorizedUser);
        RawCodeRunRequest rawCodeRunRequest= (RawCodeRunRequest) RawCodeRunRequest
                .builder()
                .codeRunnerType(CODE_RUNNER_TYPE.JS_RUNNER)
                .user(authorizedUser)
                .build();
        assertDoesNotThrow(()->{
            codeRunnerAccesValidationHandler.handle(rawCodeRunRequest);
        });
        VmStatus statusAfter = codeRunnersController.getUserVmStatus(authorizedUser);
        CodeRunner codeRunner= codeRunnersController.getUserCodeRunner(authorizedUser);
        assertEquals(VmStatus.NOT_REQUESTED, statusOnBeging);
        assertEquals(VmStatus.RUNNING_MACHINE, statusAfter);
        assertEquals(CODE_RUNNER_TYPE.JS_RUNNER,codeRunner.getType());
    }

    @Test
    void handleSwitchingRequestAuthirzed() {
        assertNotNull(codeRunnerAccesValidationHandler);
        VmStatus statusOnBeging = codeRunnersController.getUserVmStatus(authorizedUser);
        RawCodeRunRequest rawCodeRunRequest= (RawCodeRunRequest) RawCodeRunRequest
                .builder()
                .codeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER)
                .user(authorizedUser)
                .build();
        assertDoesNotThrow(()->{
            codeRunnerAccesValidationHandler.handle(rawCodeRunRequest);
        });
        VmStatus statusAfterCpp = codeRunnersController.getUserVmStatus(authorizedUser);
        CodeRunner codeRunner= codeRunnersController.getUserCodeRunner(authorizedUser);
        CODE_RUNNER_TYPE typeAfterCpp=codeRunner.getType();

        RawCodeRunRequest rawCodeRunRequestJS= (RawCodeRunRequest) RawCodeRunRequest
                .builder()
                .codeRunnerType(CODE_RUNNER_TYPE.JS_RUNNER)
                .user(authorizedUser)
                .build();
        assertDoesNotThrow(()->{
            codeRunnerAccesValidationHandler.handle(rawCodeRunRequestJS);
        });
        VmStatus statusAfterJs = codeRunnersController.getUserVmStatus(authorizedUser);
        codeRunner= codeRunnersController.getUserCodeRunner(authorizedUser);
        CODE_RUNNER_TYPE typeAfterJs=codeRunner.getType();


        assertEquals(VmStatus.NOT_REQUESTED, statusOnBeging);
        assertEquals(VmStatus.RUNNING_MACHINE, statusAfterCpp);
        assertEquals(CODE_RUNNER_TYPE.CPP_RUNNER,typeAfterCpp);
        assertEquals(VmStatus.RUNNING_MACHINE, statusAfterJs);
        assertEquals(CODE_RUNNER_TYPE.JS_RUNNER,typeAfterJs);
    }

}