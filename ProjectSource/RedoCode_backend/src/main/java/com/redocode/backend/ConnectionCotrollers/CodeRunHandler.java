package com.redocode.backend.ConnectionCotrollers;

import com.redocode.backend.Messages.CodeRunningMessages.*;
import com.redocode.backend.RedoCodeController;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.RequstHandling.ResponsibilityChainRepository;
import com.redocode.backend.database.User;
import com.redocode.backend.database.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import com.redocode.backend.Tools.ObjectMapper;

import java.security.Principal;

@Controller
@Slf4j
public class CodeRunHandler {

@Autowired
    RedoCodeController redoCodeController;
    @MessageMapping({ConnectionTargets.INrunExerciseById}) //todo:: consider possibluty of mapping global configuaraiton like languegs encpoint etcc to soem global config
    public void runExerciseIdCode(Principal principal, ExerciseIdToRunMessage exerciseIdToRunMessage)
    {
        String userId=principal.getName();
        log.info("user: "+ userId +" runs exerciseIdToRunMessage: "+ exerciseIdToRunMessage);
//        codeRunnersController.runCode(
//                redoCodeController.getUserById(userId),
//                exerciseIdToRunMessage
//        );
    }
    @MessageMapping({ConnectionTargets.INrunRawCode}) //todo:: consider possibluty of mapping global configuaraiton like languegs encpoint etcc to soem global config
    public void runRawCode(Principal principal, RawCodeToRunMessage rawCodeToRunMessage)
    {
        String userId=principal.getName();
        log.info("user: "+ userId +" runs rawCodeToRunMessage: "+ rawCodeToRunMessage);
    }
    @MessageMapping({ConnectionTargets.INrunExercsieIdValidationCode}) //todo:: consider possibluty of mapping global configuaraiton like languegs encpoint etcc to soem global config
    public void runExercsieIdValidationCode(Principal principal, ExerciseIdToRunMessage exerciseIdValidationMessage)
    {
        String userId=principal.getName();
        log.info("user: "+ userId +" runs runExercsieIdValidationCode: "+ exerciseIdValidationMessage);
    }
    @MessageMapping({ConnectionTargets.INrunExercsieTestsCode}) //todo:: consider possibluty of mapping global configuaraiton like languegs encpoint etcc to soem global config
    public void runExercsieTestsCode(Principal principal, ExerciseTestToRunMesseage exerciseTestToRunMesseage)
    {
        String userId=principal.getName();
        log.info("user: "+ userId +" runs runExercsieIdValidationCode: "+ exerciseTestToRunMesseage);
    }

    @MessageMapping({ConnectionTargets.INrunExerciseCreatorValidationCode}) //todo:: consider possibluty of mapping global configuaraiton like languegs encpoint etcc to soem global config
    public void runExerciseCreatorValidationCode(Principal principal, ExerciseCreatorValidationMessage exerciseCreatorValidationMessage)
    {
        String useruuid=principal.getName();
        log.info("user: "+ useruuid +" runs exerciseCreatorValidationMessage: "+ exerciseCreatorValidationMessage);
        User user=redoCodeController.getUserByConnectionUUID(useruuid);
        ExerciseCreationRequest exerciseCreationRequest= ObjectMapper.toExerciseCreationRequest(exerciseCreatorValidationMessage,user);
        ResponsibilityChainRepository.createNewExercise.next(exerciseCreationRequest);
    }
}
