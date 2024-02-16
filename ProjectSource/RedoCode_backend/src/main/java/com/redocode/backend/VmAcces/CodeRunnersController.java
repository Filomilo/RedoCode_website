package com.redocode.backend.VmAcces;

import com.redocode.backend.Auth.User;
import com.redocode.backend.ConnectionCotrollers.CodeRunnerSender;
import com.redocode.backend.ConnectionCotrollers.CodeRunnersConnectionController;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunnerBuilder;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunnerRequest;
import jakarta.annotation.PreDestroy;
import lombok.Cleanup;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

@Slf4j
@Component
public class CodeRunnersController {

    @Getter
    private static CodeRunnersController instance =new CodeRunnersController();
    private CodeRunnersController() {
    }
    @Autowired
    private CodeRunnerSender codeRunnerSender;

    static final int maxAmountOfVm=5;

    private Map<User, CodeRunner> usersCodeRunenrs=new Hashtable<>(maxAmountOfVm);
    private Set<CodeRunnerRequest> requestMessageSet=new HashSet<>();
    PriorityBlockingQueue<CodeRunnerRequest> requestQueue=new  PriorityBlockingQueue<>();


    public VmStatus getUserVmStatus(User user)
    {
        CodeRunner codeRunner= this.usersCodeRunenrs.get(user);
        if(codeRunner!=null)
        {
            return codeRunner.getStatus();
        }
        if(requestMessageSet.contains(new CodeRunnerRequest(user, CodeRunner.CoderunnerTypes.JS_RUNNER)))
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
            CodeRunnerRequest rq= requestQueue.poll();
            this.requestMessageSet.remove(rq);
            this.createNewVm(rq);
        }
    }


    public void deregisterUser(User user)
    {
        CodeRunner codeRunner= this.usersCodeRunenrs.get(user);
        if(codeRunner!=null)
            codeRunner.destroy();


        Optional<CodeRunnerRequest> requestMessage=requestMessageSet
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
    private void addToQueue(CodeRunnerRequest codeRunnerRequest)
    {
        log.info("adding to rquest queue: "+ codeRunnerRequest);
        requestMessageSet.add(codeRunnerRequest);
        requestQueue.add(codeRunnerRequest);
        log.info("added to queue: "+ codeRunnerRequest);
        updateCodeRunnerState(codeRunnerRequest.getUserRequesting());
    }


    @Synchronized
    private void createNewVm(CodeRunnerRequest codeRunnerRequest)
    {
        log.info("creating new vm per request: "+ codeRunnerRequest);
       CodeRunner codeRunner= CodeRunnerBuilder.build(codeRunnerRequest);
       this.usersCodeRunenrs.put(codeRunnerRequest.getUserRequesting(),codeRunner);
       codeRunner.start();
        log.info("created new vm per request: "+ codeRunnerRequest);
       updateCodeRunnerState(codeRunnerRequest.getUserRequesting());
    }


    public void requestVm(CodeRunnerRequest codeRunnerRequest)
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

    public void updateCodeRunnerState(User user)
    {
    VmStatus status=this.getUserVmStatus(user);
    if(codeRunnerSender!=null)
        codeRunnerSender.sendMessageToUser(CodeRunnersConnectionController.codeRunnerStateEndPoint,status,user);
    }




//    testing purpioses only
    @PreDestroy
    public void reset() {
        requestMessageSet.clear();
        requestQueue.clear();
        usersCodeRunenrs.clear();
    }
    // TODO: 14/02/2024 Wokr on proper synchornizaion aroudn collenction 
}
