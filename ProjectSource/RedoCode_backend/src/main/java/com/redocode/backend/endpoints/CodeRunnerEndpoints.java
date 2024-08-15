package com.redocode.backend.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redocode.backend.Messages.ExcerciseDataMessage;
import com.redocode.backend.Secuirity.JwtService;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.VmAcces.CodeRunnersController;
import com.redocode.backend.database.Excersize;
import com.redocode.backend.database.User;
import com.redocode.backend.database.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
@RequiredArgsConstructor
@RequestMapping("/public/coderunner")
public class CodeRunnerEndpoints {

    @Autowired
    JwtService jwtService;
    @Autowired
    CodeRunnersController codeRunnersController;
    @Autowired
    UsersRepository usersRepository;

    @PostMapping("/state")
    public CodeRunner getCodeRUnnerState(String token)
    {
        log.info("token: "+ token+" tires getting code runner state");
        if(token==null || token.isEmpty())
        {
            return null;
        }
        String email= jwtService.extractUsername(token);
        User user=usersRepository.findByEmail(email);
        if(user==null)
        {
            user=new User(token);
        }
        CodeRunner codeRunner= codeRunnersController.getUserCodeRunner(user);
        return codeRunner;
    }

}
