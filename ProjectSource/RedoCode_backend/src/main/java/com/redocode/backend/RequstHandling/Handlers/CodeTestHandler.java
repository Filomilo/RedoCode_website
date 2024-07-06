package com.redocode.backend.RequstHandling.Handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.RequstHandling.Requests.CodeTestRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.SpringContextUtil;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
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

@Slf4j
public class CodeTestHandler extends  BaseRequestHandler {

   protected static final CodeRunnersController codeRunnersController= (CodeRunnersController) SpringContextUtil.getApplicationContext().getBean(CodeRunnersController.class);


    protected  void checkTest(ExerciseTests test,CodeTestRequest request,CodeRunner codeRunner ) throws RequestHadndlingException {
        log.info("Testing: " + test);

        try {
            log.info("TEST inpupt: "+ test.getParsedInput(request.getInputType()));
            SolutionProgram solutionProgram = ProgramFactory
                    .createSolutionProgram()
                    .setSolutionCodeRunner(request.getCodeRunnerType())
                    .setInputVaraiable(test.getParsedInput(request.getInputType()))
                    .setOutputBase(request.getOutputType())
                    .setSolutionCode(request.getCode())
                    .build();
            log.info("solution program being tested: "+ solutionProgram.getInput());
            ProgramResult result=codeRunner.runProgram(solutionProgram);
            Variables recived=result.getVariables();
            Variables expcected=test.getParsedOutput(request.getOutputType());
            log.info("program resuult: "+recived);
            log.info("expected program resuult: "+ expcected);
            if(!recived.equals(expcected))
            {
                throw new RequestHadndlingException("expected: "+ test.getParsedOutput(request.getOutputType()).getValue()+" but recived: " + result.getVariables().getValue());
            }
        }
        catch (JsonProcessingException ex)
        {
            log.error("json procesign error: "+ ex.getMessage());
            throw new RequestHadndlingException("unable to prooces json format");
        }
        catch (NullPointerException ex)
        {
            log.error("varaible NULL: "+ ex.getMessage());
            throw new RequestHadndlingException("returned value is null_");
        }



    }

    @Override
    String getChainNodeName() {
        return "testing code";
    }

    @Override
    RequestBase handle(RequestBase request) throws RequestHadndlingException {

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
        for (ExerciseTests exTest: codeTestRequest.getTestsToRun()
        ) {
            try {
                checkTest(exTest,codeTestRequest,codeRunner);
            }
            catch (RequestHadndlingException ex)
            {
                throw new RequestHadndlingException("Test "+ i+ " failed: "+ex.getMessage());
            }
            i++;
        }
      log.info("CodeTestHandler handles: "+ codeTestRequest);
        this.nodeUpdate(request,"correct "+codeTestRequest.getCodeRunnerType()+" tests", ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS);

      return request;
    }

    @Override
    void exceptionHandling(Exception exception) {

    }
}
