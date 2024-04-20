package com.redocode.backend.VmAcces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redocode.backend.Auth.User;
import com.redocode.backend.ConnectionCotrollers.CodeRunnerSender;
import com.redocode.backend.ConnectionCotrollers.CodeRunnersConnectionController;
import com.redocode.backend.Messages.CodeToRunMessage;
import com.redocode.backend.Messages.CoderunnerStateMessage;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunnerBuilder;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunnerRequest;
import com.redocode.backend.VmAcces.CodeRunners.Program.Factory.ProgramFactory;
import com.redocode.backend.VmAcces.CodeRunners.Program.Program;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunners.Program.RawProgram;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.VmAcces.CodeRunners.Variables.VariablesFactory;
import com.redocode.backend.database.Excersize;
import com.redocode.backend.database.ExerciseRepository;
import com.redocode.backend.database.ExerciseTests;
import javassist.compiler.ast.Variable;
import lombok.extern.slf4j.Slf4j;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
    @Autowired
    ExerciseRepository exerciseRepository;
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
        updateCodeRunnerState(codeRunnerRequest.getUserRequesting());

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
    {
        CodeRunner userCodeRunner=getUserCodeRunner(user);
        CodeRunnerState state;
        switch (status)
        {
            case RUNNING_MACHINE -> state=CodeRunnerState.ACTIVE;
            case AWAITING_ACCES -> state=CodeRunnerState.AWAITING;
            case DESTROYING_MACHINE -> state=CodeRunnerState.CLOSING;
            default -> state=CodeRunnerState.INACTIVE;
        }

        CoderunnerStateMessage coderunnerStateMessage=   CoderunnerStateMessage.builder()
                .state(state)
                .codeRunnerType(userCodeRunner==null?CODE_RUNNER_TYPE.UNIDENTIFIED :userCodeRunner.getType())
                .build();
        log.info("user: "+ user+" requested status: "+ coderunnerStateMessage);
        codeRunnerSender.sendToUser(user.getId(),  CodeRunnersConnectionController.codeRunnerStateEndPoint,coderunnerStateMessage);
    }
    }




//    testing purpioses only

    public void reset() {
        requestMessageSet.clear();
        requestQueue.clear();
        usersCodeRunenrs.clear();
    }

    public List<ProgramResult> runCode(User user, CodeToRunMessage codeToRunMessage) {
        CodeRunner codeRunner= this.getUserCodeRunner(user);
        if(codeRunner==null)
            throw  new RuntimeException("user doesnt have code runner");

        log.info("running program form meesage: "+ codeToRunMessage);
        List<ProgramResult> results=runProgramFromMessage(codeRunner,codeToRunMessage);
        this.sendResults(user,results);
        return results;
    }
    // running raw program based on message send by user

    public List<ProgramResult> runProgramFromMessage(CodeRunner codeRunner, CodeToRunMessage codeToRunMessage)
    {
        List<ProgramResult> results=new ArrayList<>();
        if(codeToRunMessage.getExercise_id()==null)
        {
            results=this.runRawProgramFromMessage(codeRunner,codeToRunMessage);
        }
        else {
            results=this.runExerciseSoultionFromMessage(codeRunner,codeToRunMessage);
        }

        return results;
    }

    private List<ProgramResult> runRawProgramFromMessage(CodeRunner codeRunner, CodeToRunMessage codeToRunMessage)
    {
        Program pr;
        pr=new RawProgram(codeToRunMessage.getCode());
        List<Variable> variablesInput=new ArrayList<>();
        List<ProgramResult> programResults=new ArrayList<>();
        programResults.add(codeRunner.runProgram(pr));
        return programResults;
    }
    // running exercise program based on message send by user

    private List<ProgramResult> runExerciseSoultionFromMessage(CodeRunner codeRunner, CodeToRunMessage codeToRunMessage)
    {
        List<ProgramResult> results=new ArrayList<>();
        log.info("Ruunnign code to run on exercise of id: "+codeToRunMessage.getExercise_id() );
        Excersize exercise= exerciseRepository.findById(Long.parseLong(codeToRunMessage.getExercise_id())).orElse(null);
    List<ExerciseTests>     tests=exercise.getExerciseTests();
        log.info("Exercise Tests: "+ Arrays.toString(tests.toArray()));

        //MANULA TES INPUT
        try {
        for (ExerciseTests test: tests
             ) {
            Variables input = (test.getParsedInput(exercise.getInputType()));
            Variables output = (test.getParsedOutput(exercise.getOutputType()));
            Program program= ProgramFactory
                    .createSolutionProgram()
                    .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                    .setOutputBase(VariablesFactory.getVeraibleFromType(exercise.getOutputType()))
                    .setInputVaraiable(input)
                    .setSolutionCode(codeToRunMessage.getCode())
                    .build();
            log.info("Ruunign test: "+ program);
            ProgramResult result=codeRunner.runProgram(program);
            results.add(result);
            if(result.getVariables()== null || result.getVariables().getValue()!=output.getValue())
            {
                log.info("wrong result so stopping : "+result.getVariables()+" != "+ output);
                break;
            }

        }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

//todo: add outomatic test if all manual ereuslt were correct


    return results;
    }

    public void sendResults(User user, List<ProgramResult> results)
    {
        log.info("sending resutls: "+ Arrays.toString(results.toArray())+" to user "+ user);
        this.codeRunnerSender.sendMessageToUser(CodeRunnersConnectionController.codeRunnerResultEndPoint,results,user);
    }

    // TODO: 14/02/2024 Wokr on proper synchornizaion aroudn collenction 
}
