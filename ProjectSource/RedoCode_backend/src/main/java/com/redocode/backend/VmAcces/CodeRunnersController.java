package com.redocode.backend.VmAcces;

import com.redocode.backend.Auth.User;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.VmAcces.Messages.CodeRunnerRequestMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class CodeRunnersController {

    @Getter
    private static CodeRunnersController instance =new CodeRunnersController();
    private CodeRunnersController() {
    }

    private Map<User, CodeRunner> usersCodeRunenrs=new Hashtable<>();
    private Set<CodeRunnerRequestMessage> requestMessageSet=new HashSet<>();
    private PriorityQueue<CodeRunnerRequestMessage> requestQueue=new PriorityQueue<>();


    public CodeRunner.STATUS getUserVmStatus(User user)
    {
       return CodeRunner.STATUS.NOT_REQUESTED;
    }

    public void destroyMachine(User user)
    {
        CodeRunner codeRunner= this.usersCodeRunenrs.get(user);
        codeRunner.destroy();
        this.usersCodeRunenrs.remove(user);
    }

    public void deregisterUser(User user)
    {
        CodeRunner codeRunner= this.usersCodeRunenrs.get(user);

    }

    public void requestVm(User user, CodeRunnerRequestMessage codeRunnerRequest)
    {

    }





}
