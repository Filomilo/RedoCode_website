package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.RequstHandling.Requests.Interfaces.IExerciseIdRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ITestsToRunRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.SpringContextUtil;
import com.redocode.backend.database.ExerciseRepository;
import com.redocode.backend.database.ExerciseTests;

import java.util.List;

/**
 * reqyest handler for chain of resposiblity, which job is to retrive manaual tests from databse and add them to the request
 * <br/><br/>
 * Request handled by this handler must implements interfaces <b>IExerciseIdRequest</b> and <b>ITestsToRunRequest</b>
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
        this.nodeUpdate(request, "reterving tests from database", ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING);
        if(!(request instanceof IExerciseIdRequest) && !(request instanceof ITestsToRunRequest)) {
            throw new RequestHadndlingException("Request must implement IExerciseIdRequest and ITestsToRunRequest");
        }

        IExerciseIdRequest exerciseIdRequest= (IExerciseIdRequest) request;

        List<ExerciseTests> tests =exerciseRepository.getReferenceById(((IExerciseIdRequest) request).getIdOfExercise()).getExerciseTests();

        ITestsToRunRequest codeTestRequest = (ITestsToRunRequest) request;
        codeTestRequest.setTestsToRun(tests);

        this.nodeUpdate(request, "retrived testFrom data base", ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS);

        return request;
    }
}
