package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.RequstHandling.Requests.Interfaces.IExerciseIdRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ISolutionCodesRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.SpringContextUtil;
import com.redocode.backend.Tools.RedoCodeObjectMapper;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Program.SolutionProgram;
import com.redocode.backend.VmAcces.CodeRunnersController;
import com.redocode.backend.database.*;

public class SaveExerciseSolutionHandler extends MessageRequestHandler{


    private static final SolutionProgramsRepository solutionProgramsRepository =
            SpringContextUtil.getApplicationContext().getBean(SolutionProgramsRepository.class);
    private static final ExerciseRepository exerciseRepository =
            SpringContextUtil.getApplicationContext().getBean(ExerciseRepository.class);
    private static final ProgrammingLanguageRepository programmingLanguageRepository=
            SpringContextUtil.getApplicationContext().getBean(ProgrammingLanguageRepository.class);



    @Override
    String getChainNodeName() {
        return "Saving solution to database";
    }

    @Override
    RequestBase handle(RequestBase request) throws RequestHadndlingException {
        this.nodeUpdate(
                request, "Saving solution to database", ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING);

        IExerciseIdRequest exerciseIdRequest=(IExerciseIdRequest)request;
        ISolutionCodesRequest  solutionCodesRequest=(ISolutionCodesRequest )request;
        CODE_RUNNER_TYPE codeRunnerType= solutionCodesRequest.getSolutionCodes().keySet().stream().findFirst().get();
        String code=solutionCodesRequest.getSolutionCodes().get(codeRunnerType);
        ProgrammingLanguage programmingLanguage=programmingLanguageRepository.findByName(RedoCodeObjectMapper.CodeRunnerToDataBaseLanguageName(codeRunnerType));


        SolutionPrograms solutionProgram=SolutionPrograms.builder()
                .code(code)
                .language(programmingLanguage)
                .excersize(exerciseRepository.getReferenceById(exerciseIdRequest.getIdOfExercise()))
                .build();
        solutionProgramsRepository.save(solutionProgram);
        this.nodeUpdate(
                request, "Saved solution", ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS);
        return request;
    }


}
