package com.redocode.backend.ConnectionCotrollers;

import com.redocode.backend.Messages.CodeRunningMessages.*;
import com.redocode.backend.Messages.CoderunnerStateMessage;
import com.redocode.backend.RedoCodeController;
import com.redocode.backend.RequstHandling.Requests.CodeTestRequest;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.RequstHandling.Requests.RawCodeRunRequest;
import com.redocode.backend.RequstHandling.Requests.SingleDatabaseExerciseTestRequest;
import com.redocode.backend.RequstHandling.ResponsibilityChainRepository;
import com.redocode.backend.VmAcces.CodeRunnerState;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.VmAcces.CodeRunnersController;
import com.redocode.backend.database.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import com.redocode.backend.Tools.RedoCodeObjectMapper;

import java.security.Principal;

import static com.redocode.backend.RequstHandling.ResponsibilityChainRepository.runExerciseIdCode;

@Controller
@Slf4j
public class CodeRunHandler {

  @Autowired RedoCodeController redoCodeController;
  @Autowired CodeRunnersController codeRunnersController;
  @Autowired MessageSender messageSender;

  @MessageMapping({ConnectionTargets.INrunExerciseById})
  // todo:: consider possibluty of mapping global configuaraiton like languegs encpoint etcc to soem
  // global config
  public void runExerciseIdCode(
      Principal principal, ExerciseIdToRunMessage exerciseIdToRunMessage) {
    String useruuid = principal.getName();
    User user = redoCodeController.getUserByConnectionUUID(useruuid);

    SingleDatabaseExerciseTestRequest singleDatabaseExerciseTestRequest=
    RedoCodeObjectMapper.toSingleDatabaseExerciseTestRequest(
            exerciseIdToRunMessage
            , user
            ,codeRunnersController.getUserCodeRunner(user).getType()
    );
    runExerciseIdCode.startChain(singleDatabaseExerciseTestRequest);

    //    log.info("user: " + userId + " runs runExerciseIdCode: " + exerciseIdToRunMessage);
//    //        codeRunnersController.runCode(
//    //                redoCodeController.getUserById(userId),
//    //                exerciseIdToRunMessage
//    //        );
//
//    messageSender.sendMessage(
//        principal.getName(),
//        CodeRunnersConnectionController.codeRunnerStateEndPoint,
//        CoderunnerStateMessage.builder()
//            .state(CodeRunnerState.ACTIVE)
//            .codeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER)
//            .build());
  }

  @MessageMapping({ConnectionTargets.INrunRawCode})
  // todo:: consider possibluty of mapping global configuaraiton like languegs encpoint etcc to soem
  // global config
  public void runRawCode(Principal principal, RawCodeToRunMessage rawCodeToRunMessage) {
    String useruuid = principal.getName();
    log.info("user: " + useruuid + " runs runRawCode: " + rawCodeToRunMessage);
    User user = redoCodeController.getUserByConnectionUUID(useruuid);
    RawCodeRunRequest rawCodeRunRequest =
        RedoCodeObjectMapper.toRunRawCodeRequest(
            rawCodeToRunMessage, user, codeRunnersController.getUserCodeRunner(user).getType());
    log.info("runRawCode chain start");
    ResponsibilityChainRepository.runRawCode.startChain(rawCodeRunRequest);
  }

  @MessageMapping({ConnectionTargets.INrunExercsieIdValidationCode})
  // todo:: consider possibluty of mapping global configuaraiton like languegs encpoint etcc to soem
  // global config
  public void runExercsieIdValidationCode(
      Principal principal, ExerciseIdToRunMessage exerciseIdValidationMessage) {
    String userId = principal.getName();
    log.info(
        "user: " + userId + " runs runExercsieIdValidationCode: " + exerciseIdValidationMessage);
  }

  @MessageMapping({ConnectionTargets.INrunExercsieTestsCode})
  // todo:: consider possibluty of mapping global configuaraiton like languegs encpoint etcc to soem
  // global config
  public void runExerciseTestsCode(Principal principal, ExerciseTestToRunMesseage codeTestRequest) {
    String useruuid = principal.getName();
    log.info("user: " + useruuid + " runs runExercsieTestsCode: " + codeTestRequest);
    User user = redoCodeController.getUserByConnectionUUID(useruuid);
    CodeRunner activeCodeRunner = codeRunnersController.getUserCodeRunner(user);
    CodeTestRequest exerciseCreationRequest =
        RedoCodeObjectMapper.toExerciseTestsRunRequest(
            codeTestRequest, user, activeCodeRunner.getType());

    log.info("runExerciseTestsCode chain start");
    ResponsibilityChainRepository.runExercisesTests.startChain(exerciseCreationRequest);
  }

  @MessageMapping({ConnectionTargets.INrunExerciseCreatorValidationCode})
  // todo:: consider possibluty of mapping global configuaraiton like languegs encpoint etcc to soem
  // global config
  public void runExerciseCreatorValidationCode(
      Principal principal, ExerciseCreatorValidationMessage exerciseCreatorValidationMessage) {
    String useruuid = principal.getName();
    log.info(
        "user: "
            + useruuid
            + " runs runExerciseCreatorValidationCode: "
            + exerciseCreatorValidationMessage);
    User user = redoCodeController.getUserByConnectionUUID(useruuid);
    ExerciseCreationRequest exerciseCreationRequest =
        RedoCodeObjectMapper.toExerciseCreationRequest(exerciseCreatorValidationMessage, user);
    log.info("createNewExercise chain start");
    ResponsibilityChainRepository.createNewExercise.startChain(exerciseCreationRequest);
  }
}
