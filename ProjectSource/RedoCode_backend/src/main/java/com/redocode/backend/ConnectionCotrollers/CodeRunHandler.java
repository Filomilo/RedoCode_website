package com.redocode.backend.ConnectionCotrollers;

import com.redocode.backend.Messages.CodeRunningMessages.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@Slf4j
public class CodeRunHandler {
    @MessageMapping("/CodeRun/ExerciseIdRun") //todo:: consider possibluty of mapping global configuaraiton like languegs encpoint etcc to soem global config
    public void runExerciseIdCode(Principal principal, ExerciseIdToRunMessage exerciseIdToRunMessage)
    {
        String userId=principal.getName();
        log.info("user: "+ userId +" runs exerciseIdToRunMessage: "+ exerciseIdToRunMessage);
//        codeRunnersController.runCode(
//                redoCodeController.getUserById(userId),
//                exerciseIdToRunMessage
//        );
    }
    @MessageMapping("/CodeRun/RawCodeRun") //todo:: consider possibluty of mapping global configuaraiton like languegs encpoint etcc to soem global config
    public void runRawCode(Principal principal, RawCodeToRunMessage rawCodeToRunMessage)
    {
        String userId=principal.getName();
        log.info("user: "+ userId +" runs rawCodeToRunMessage: "+ rawCodeToRunMessage);
    }
    @MessageMapping("/CodeRun/ExerciseIdValidation") //todo:: consider possibluty of mapping global configuaraiton like languegs encpoint etcc to soem global config
    public void runExercsieIdValidationCode(Principal principal, ExerciseIdToRunMessage exerciseIdValidationMessage)
    {
        String userId=principal.getName();
        log.info("user: "+ userId +" runs runExercsieIdValidationCode: "+ exerciseIdValidationMessage);
    }
    @MessageMapping("/CodeRun/ExerciseCodeTests") //todo:: consider possibluty of mapping global configuaraiton like languegs encpoint etcc to soem global config
    public void runExercsieTestsCode(Principal principal, ExerciseTestToRunMesseage exerciseTestToRunMesseage)
    {
        String userId=principal.getName();
        log.info("user: "+ userId +" runs runExercsieIdValidationCode: "+ exerciseTestToRunMesseage);
    }

    @MessageMapping("/CodeRun/ExerciseCreationValidation") //todo:: consider possibluty of mapping global configuaraiton like languegs encpoint etcc to soem global config
    public void runExerciseCreatorValidationCode(Principal principal, ExerciseCreatorValidationMessage exerciseCreatorValidationMessage)
    {
        String userId=principal.getName();
        log.info("user: "+ userId +" runs exerciseCreatorValidationMessage: "+ exerciseCreatorValidationMessage);
    }
}
