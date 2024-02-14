package com.redocode.backend.VmAcces;

import com.redocode.backend.Auth.User;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunnerBuilder;
import com.redocode.backend.VmAcces.Messages.CodeRunnerRequestMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.Synchronized;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

@Slf4j
public class CodeRunnersController {

    @Getter
    private static CodeRunnersController instance =new CodeRunnersController();
    private CodeRunnersController() {
    }

    static final int maxAmountOfVm=5;

    private Map<User, CodeRunner> usersCodeRunenrs=new Hashtable<>(maxAmountOfVm);
    private Set<CodeRunnerRequestMessage> requestMessageSet=new HashSet<>();
    PriorityBlockingQueue<CodeRunnerRequestMessage> requestQueue=new  PriorityBlockingQueue<>();


    public VmStatus getUserVmStatus(User user)
    {
        CodeRunner codeRunner= this.usersCodeRunenrs.get(user);
        if(codeRunner!=null)
        {
            return codeRunner.getStatus();
        }
        if(requestMessageSet.contains(new CodeRunnerRequestMessage(user, CodeRunner.CoderunnerTypes.JS_RUNNER)))
            return  VmStatus.AWAITING_ACCES;
       return VmStatus.NOT_REQUESTED;
    }

    public void destroyMachine(User user)
    {
        CodeRunner codeRunner= this.usersCodeRunenrs.get(user);
        codeRunner.destroy();
        this.usersCodeRunenrs.remove(user);
    }

    @Synchronized
    private void updateQueue()
    {
        log.info("updating queue");
        if(requestQueue.size()>0)
        {
            log.info("removing request from queue and creating new vm");
            CodeRunnerRequestMessage rq= requestQueue.poll();
            this.requestMessageSet.remove(rq);
            this.createNewVm(rq);
        }
    }


    public void deregisterUser(User user)
    {
        CodeRunner codeRunner= this.usersCodeRunenrs.get(user);
        if(codeRunner!=null)
            codeRunner.destroy();


        Optional<CodeRunnerRequestMessage> requestMessage=requestMessageSet
                .stream()
                .filter((req)-> req.getUserRequesting().equals(user))

                .findFirst();

        if(requestMessage.isPresent())
        {
            requestMessageSet.remove(requestMessage.get());
            requestQueue.remove(requestMessage.get());
        }
        updateQueue();
    }

    @Synchronized
    private void addToQueue(CodeRunnerRequestMessage codeRunnerRequestMessage)
    {
        log.info("adding to rquest queue: "+ codeRunnerRequestMessage);
        requestMessageSet.add(codeRunnerRequestMessage);
        requestQueue.add(codeRunnerRequestMessage);
    }


    @Synchronized
    private void createNewVm(CodeRunnerRequestMessage codeRunnerRequestMessage)
    {
        log.info("creating new vm per request: "+ codeRunnerRequestMessage);
       CodeRunner codeRunner= CodeRunnerBuilder.build(codeRunnerRequestMessage);
       this.usersCodeRunenrs.put(codeRunnerRequestMessage.getUserRequesting(),codeRunner);
       codeRunner.start();
    }


    public void requestVm(CodeRunnerRequestMessage codeRunnerRequest)
    {
        if(usersCodeRunenrs.size()<maxAmountOfVm)
        {
            createNewVm(codeRunnerRequest);
        }
        else
        {
            addToQueue(codeRunnerRequest);
        }
    }


    public CodeRunner getUserCodeRunner(User user) {
        return this.usersCodeRunenrs.get(user);
    }
    // TODO: 14/02/2024 Wokr on proper synchornizaion aroudn collenction 
}
