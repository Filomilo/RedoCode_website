package com.redocode.backend.RequstHandling.Handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.RequstHandling.Requests.CodeTestRequest;
import com.redocode.backend.RequstHandling.Requests.PorgramReusltsSendRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.SpringContextUtil;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.VmAcces.CodeRunners.ConsoleOutput;
import com.redocode.backend.VmAcces.CodeRunners.Program.Factory.ProgramFactory;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunners.Program.SolutionProgram;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.VmAcces.CodeRunnersController;
import com.redocode.backend.database.ExerciseTests;
import com.redocode.backend.database.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class CodeTestHandler extends  BaseRequestHandler {

   protected static final CodeRunnersController codeRunnersController= (CodeRunnersController) SpringContextUtil.getApplicationContext().getBean(CodeRunnersController.class);


    protected  ProgramResult checkTest(ExerciseTests test,CodeTestRequest request,CodeRunner codeRunner ) throws RequestHadndlingException {
        log.info("Testing: " + test);

        try {
            log.info("TEST inpupt: "+ test.getParsedInput(request.getInputType()));
            SolutionProgram solutionProgram = ProgramFactory
                    .createSolutionProgram()
                    .setSolutionCodeRunner(request.getCodeRunnerType())
                    .setInputVaraiable(test.getParsedInput(request.getInputType()))
                    .setOutputBase(request.getOutputType())
                    .setSolutionCode(request.getSolutionCodes().get(request.getCodeRunnerType()== CODE_RUNNER_TYPE.UNIDENTIFIED?CODE_RUNNER_TYPE.UNIDENTIFIED: codeRunner.getType()))
                    .setTimeout(request.getTimeForExecution())
                    .build();
            log.info("solution program being tested: "+ solutionProgram.getInput());
            ProgramResult result=codeRunner.runProgram(solutionProgram);
            Variables recived=result.getVariables();
            if(test.getExpectedOutput()=="")
                return result;
            Variables expcected=test.getParsedOutput(request.getOutputType());
            log.info("program resuult: "+recived);
            log.info("expected program resuult: "+ expcected);
            if(recived==null || !recived.equals(expcected))
            {
                throw new RequestHadndlingException("expected: "+ test.getParsedOutput(request.getOutputType()).getValue()+" but recived: " +result.getVariables()!=null?result.getVariables().getValue().toString():"null");
            }
            return result;
        }
        catch (JsonProcessingException ex)
        {
            log.error("json procesign error: "+ ex.getMessage());
            throw new RequestHadndlingException("unable to prooces json format");
        }
        catch (NullPointerException ex)
        {
            log.error("varaible NULL: "+ ex.getMessage());
            throw new RequestHadndlingException("returned value is null_ "+ ex.getMessage());
        }
        catch (Exception ex)
        {
            log.error("varaible NULL: "+ ex.getMessage());
            throw new RequestHadndlingException("Error while executing tests: "+ ex.getMessage());

        }



    }

    @Override
    String getChainNodeName() {
        return "testing code";
    }

    @Override
    RequestBase handle(RequestBase request) throws RequestHadndlingException {
        List<ProgramResult> programResults=new ArrayList<>();
        if(!(request instanceof CodeTestRequest))
        {

            try{
                CodeTestRequest codeTestRequest= (CodeTestRequest) request;

            }
            catch (Exception ex)
            {
                log.error("Excpetion: "+ ex.getMessage());
            }
            throw new RequestHadndlingException("Wrong reguest was privided to handler");
        }
        CodeTestRequest codeTestRequest= (CodeTestRequest) request;
        this.nodeUpdate(request,"running "+codeTestRequest.getCodeRunnerType()+" tests", ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING);;
        CodeRunner codeRunner= codeRunnersController.getUserCodeRunner(request.getUser());
        log.info("Staring handler: CodeTestHandler+"+"for " + codeTestRequest );
        int i=0;
        List<ExerciseTests> tests = new LinkedList<ExerciseTests>();
        tests.addAll(codeTestRequest.getTestsToRun());
    if(codeTestRequest.getAutotestsToRun()!=null) {
        tests.addAll(codeTestRequest.getAutotestsToRun());
    }
        for (ExerciseTests exTest:tests
        ) {
            try {
                programResults.add(  checkTest(exTest,codeTestRequest,codeRunner));
            }
            catch (RequestHadndlingException ex)
            {
                programResults.add(
                        ProgramResult.builder()
                                .consoleOutput(
                                        ConsoleOutput.builder()
                                                .errorOutput(ex.getMessage())
                                                .build()
                                )
                                .build()
                );
                throw new RequestHadndlingException("Test "+ i+ " failed: "+ex.getMessage());
            }
            i++;
        }
      log.info("CodeTestHandler handles: "+ codeTestRequest);
        this.nodeUpdate(request,"correct "+codeTestRequest.getCodeRunnerType()+" tests", ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS);
        PorgramReusltsSendRequest porgramReusltsSendRequest=PorgramReusltsSendRequest.builder()
                .programResults(programResults)
                .user(request.getUser())
                .build();
      return porgramReusltsSendRequest;
    }

    @Override
    void exceptionHandling(Exception exception) {

    }
}
