package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ICodeResultsRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.IExerciseIdRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ISolutionCodesRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.SpringContextUtil;
import com.redocode.backend.Tools.RedoCodeObjectMapper;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.database.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveExerciseSolutionHandler extends MessageRequestHandler {

  private static final SolutionProgramsRepository solutionProgramsRepository =
      SpringContextUtil.getApplicationContext().getBean(SolutionProgramsRepository.class);
  private static final ExerciseRepository exerciseRepository =
      SpringContextUtil.getApplicationContext().getBean(ExerciseRepository.class);
  private static final ProgrammingLanguageRepository programmingLanguageRepository =
      SpringContextUtil.getApplicationContext().getBean(ProgrammingLanguageRepository.class);
  private static final UsersRepository userRepository =
      SpringContextUtil.getApplicationContext().getBean(UsersRepository.class);
  private static final Logger log = LoggerFactory.getLogger(SaveExerciseSolutionHandler.class);

  @Override
  String getChainNodeName() {
    return "Saving solution to database";
  }

  @Override
  RequestBase handle(RequestBase request) throws RequestHadndlingException {

    this.nodeUpdate(
        request, "Saving solution to database", ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING);

    if (request.getUser().getId() == null) {
      this.nodeUpdate(
          request,
          "Not saving result without authentication",
          ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS);

      return request; // not saving for unathenticated
    }
    assert request instanceof IExerciseIdRequest;
    assert request instanceof ISolutionCodesRequest;
    assert request instanceof ICodeResultsRequest;

    IExerciseIdRequest exerciseIdRequest = (IExerciseIdRequest) request;
    ISolutionCodesRequest solutionCodesRequest = (ISolutionCodesRequest) request;
    ICodeResultsRequest codeResultsRequest = (ICodeResultsRequest) request;
    log.info("Saving exercise for user: " + request.getUser());

    CODE_RUNNER_TYPE codeRunnerType =
        solutionCodesRequest.getSolutionCodes().keySet().stream().findFirst().get();
    String code = solutionCodesRequest.getSolutionCodes().get(codeRunnerType);
    ProgrammingLanguage programmingLanguage =
        programmingLanguageRepository.findByName(
            RedoCodeObjectMapper.CodeRunnerToDataBaseLanguageName(codeRunnerType));

    Long avgExecutionTime =
        (long)
            Math.ceil(
                codeResultsRequest.getProgramResults().get(codeRunnerType).stream()
                    .mapToLong(x -> x.getExecutionTime().longValue())
                    .average()
                    .orElse(-1.0));

    SolutionPrograms solutionProgram =
        SolutionPrograms.builder()
            .code(code)
            .language(programmingLanguage)
            .excersize(exerciseRepository.getReferenceById(exerciseIdRequest.getIdOfExercise()))
            .avgExecutionTime(avgExecutionTime)
            .solutionAuthor(request.getUser())
            .build();
    solutionProgramsRepository.save(solutionProgram);
    this.nodeUpdate(request, "Saved solution", ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS);
    return request;
  }
}
