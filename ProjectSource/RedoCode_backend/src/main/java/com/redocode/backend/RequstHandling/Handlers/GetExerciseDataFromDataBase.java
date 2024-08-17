package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.RequstHandling.Requests.Interfaces.IExerciseIdRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ISolutionCodesRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.RequstHandling.Requests.SpecifiedSingleDatabaseExerciseTestRequest;
import com.redocode.backend.SpringContextUtil;
import com.redocode.backend.database.Excersize;
import com.redocode.backend.database.ExerciseRepository;

import java.util.ArrayList;

/**
 * reqyest handler for chain of resposiblity, which job is to retrive manaual tests from databse and
 * add them to the request <br>
 * <br>
 * Request handled by this handler must implements interfaces {@link IExerciseIdRequest
 * IExerciseIdRequest}, {@link ISolutionCodesRequest ISolutionCodesRequest}
 */
public class GetExerciseDataFromDataBase extends MessageRequestHandler {

  protected static final ExerciseRepository exerciseRepository =
      (ExerciseRepository)
          SpringContextUtil.getApplicationContext().getBean(ExerciseRepository.class);

  @Override
  String getChainNodeName() {
    return "Retrieving tests from DataBase";
  }

  @Override
  RequestBase handle(RequestBase request) throws RequestHadndlingException {
    this.nodeUpdate(
        request, "reterving tests from database", ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING);
    if (!(request instanceof IExerciseIdRequest) && !(request instanceof ISolutionCodesRequest)) {
      throw new RequestHadndlingException(
          "Request must implement IExerciseIdRequest,ISolutionCodesRequest");
    }

    IExerciseIdRequest exerciseIdRequest = (IExerciseIdRequest) request;
    ISolutionCodesRequest solutionCodesRequest = (ISolutionCodesRequest) request;
    Excersize excersize = exerciseRepository.getReferenceById(exerciseIdRequest.getIdOfExercise());

    SpecifiedSingleDatabaseExerciseTestRequest specifiedSingleDatabaseExerciseTestRequest =
        SpecifiedSingleDatabaseExerciseTestRequest.builder()
            .TestsToRun(excersize.getExerciseTests())
            .ram(excersize.getRam_mb())
            .inputType(excersize.getInputType())
            .outputType(excersize.getOutputType())
            .idOfExercise(excersize.getId())
            .AutotestsToRun(new ArrayList<>())
            .timeForExecution(excersize.getMaxExecutionTimeMS())
            .solutionCodes(solutionCodesRequest.getSolutionCodes())
            .user(request.getUser())
            .amountOfAutoTests(excersize.getAmountOfAutoTests())
            .lengthRange(
                new Range(excersize.getValueLengthRangeMin(), excersize.getValueLengthRangeMax()))
            .xArrayRange(
                excersize.getInputType().isArray() || excersize.getInputType().isDoubleArray()
                    ? new Range(
                        excersize.getArrayXLengthRangeMin(), excersize.getArrayXLengthRangeMax())
                    : null)
            .yArrayRange(
                excersize.getInputType().isDoubleArray()
                    ? new Range(
                        excersize.getArrayYLengthRangeMin(), excersize.getArrayYLengthRangeMax())
                    : null)
            .build();

    this.nodeUpdate(
        request, "retrived testFrom data base", ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS);

    return specifiedSingleDatabaseExerciseTestRequest;
  }
}
