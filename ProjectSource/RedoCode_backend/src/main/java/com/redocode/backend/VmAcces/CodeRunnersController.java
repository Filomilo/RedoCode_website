package com.redocode.backend.VmAcces;

import com.redocode.backend.Auth.User;
import com.redocode.backend.ConnectionCotrollers.CodeRunnerSender;
import com.redocode.backend.ConnectionCotrollers.CodeRunnersConnectionController;
import com.redocode.backend.Messages.CodeToRunMessage;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunnerBuilder;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunnerRequest;
import com.redocode.backend.VmAcces.CodeRunners.Program.Program;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunners.Program.RawProgram;
import jakarta.annotation.PreDestroy;
import javassist.compiler.ast.Variable;
import lombok.Cleanup;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

@Slf4j
@Component
@Scope("singleton")
public class CodeRunnersController {


    CodeRunnersController() {
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
        if(requestMessageSet.contains(new CodeRunnerRequest(user, CODE_RUNNER_TYPE.JS_RUNNER)))
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
        if(usersCodeRunenrs.containsKey(codeRunnerRequest.getUserRequesting()))
        {
            deregisterUser(codeRunnerRequest.getUserRequesting());
        }

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
        log.info("retriving code runner from: "+ user);
        log.info("code runners: "+this.usersCodeRunenrs.size()+" ---- "+
                Arrays.toString(this.usersCodeRunenrs.entrySet().toArray())+
                "------"+
                Arrays.toString(this.usersCodeRunenrs.keySet().toArray())
        );
        return this.usersCodeRunenrs.get(user);
    }

    public void updateCodeRunnerState(User user)
    {
    VmStatus status=this.getUserVmStatus(user);
    if(codeRunnerSender!=null)
        codeRunnerSender.sendMessageToUser(CodeRunnersConnectionController.codeRunnerStateEndPoint,status,user);
    }




//    testing purpioses only

    public void reset() {
        requestMessageSet.clear();
        requestQueue.clear();
        usersCodeRunenrs.clear();
    }

    public void runCode(User user, CodeToRunMessage codeToRunMessage) {
        CodeRunner codeRunner= this.getUserCodeRunner(user);
        if(codeRunner==null)
            throw  new RuntimeException("user doesnt have code runner");
        Program pr=new RawProgram("");
        List<Variable> variablesInput=new ArrayList<>();
        if(codeToRunMessage.getExercise_id()==null)
        {

            pr=new RawProgram(codeToRunMessage.getCode());
        }
        log.info("program to run: "+ pr);
        List<ProgramResult> results= codeRunner.runProgram(pr,variablesInput);
        this.sendResults(user,results);

    }

    public void sendResults(User user, List<ProgramResult> results)
    {
        log.info("sending resutls: "+ Arrays.toString(results.toArray())+" to user "+ user);
        this.codeRunnerSender.sendMessageToUser(CodeRunnersConnectionController.codeRunnerResultEndPoint,results,user);
    }

    // TODO: 14/02/2024 Wokr on proper synchornizaion aroudn collenction 
}
