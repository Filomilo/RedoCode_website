package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.ExecutionResponses.ExecutionChainScheme;
import com.redocode.backend.Messages.ExecutionResponses.ExecutionResponseBase;
import com.redocode.backend.Messages.ExecutionResponses.ExecutionResponseStatusUpdate;
import com.redocode.backend.Messages.Handleres.ChainNodeStatusMessage;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.RequstHandling.Requests.CodeTestRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.database.User;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Setter
@Getter
@Slf4j
@RequiredArgsConstructor
public abstract class BaseRequestHandler implements IRequestHandler{

    @Setter
    private int _nodeLevel;

    BaseRequestHandler nextRequestHandler;
    BiFunction<User, ExecutionResponseBase, Void> messageMethod=(User user, ExecutionResponseBase executionResponseBase)->{
        log.info("massage to user: "+user+" of contentt "+executionResponseBase );
        return null;
    };
    public BaseRequestHandler setNextRequestHandler(BaseRequestHandler requestHandler)
    {
        nextRequestHandler=requestHandler;
        return this;
    }

    @SneakyThrows
    public BaseRequestHandler clone()
    {
        try {


            BaseRequestHandler iterator = this;
            BaseRequestHandler activeHandler = this.getClass().getConstructor().newInstance();
            BaseRequestHandler startingHandler = activeHandler;
            while (iterator.nextRequestHandler != null) {
                BaseRequestHandler tmp = this.nextRequestHandler.getClass().getConstructor().newInstance();
                activeHandler.setNextRequestHandler(tmp);
                activeHandler = activeHandler.getNextRequestHandler();
                iterator=iterator.getNextRequestHandler();
            }
            return startingHandler;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


     public void setMehthodForChain(BiFunction<User, ExecutionResponseBase, Void> messageMethod)
    {
        this.messageMethod=messageMethod;
        if(nextRequestHandler!=null)
        {
            nextRequestHandler.setMehthodForChain(messageMethod);
        }
    }


   public List<ChainNodeInfo> getChainList()
    {
        List<ChainNodeInfo> list;
        if(nextRequestHandler!=null)
        {
            list=nextRequestHandler.getChainList();
        }
        else
        {
            list=new ArrayList<ChainNodeInfo>();
        }
        list.add(0,ChainNodeInfo.builder()
                        .processingMessage("Pending")
                        .status(ChainNodeInfo.CHAIN_NODE_STATUS.PENDING)
                        .nodeName(getChainNodeName())
                .build());
        return list;
    }
    abstract String getChainNodeName();
    abstract RequestBase  handle(RequestBase request)throws RequestHadndlingException;
    abstract void exceptionHandling(Exception exception);
    @Override
    public boolean next(RequestBase request)  {
        log.info("next hadnler");
        try{
        RequestBase result=handle(request);
        log.info(getClass().getName()+ " request resutl: " + result);
        if(result!=null && nextRequestHandler!=null) {
            log.info("  return nextRequestHandler.next(request);");
            boolean res= nextRequestHandler.next(result);
//            log.info( nextRequestHandler.getClass().getName()+ " handling result of "+ (CodeTestRequest) request+ " resulted in "+ res);
            return res;
        }
        else{
            log.info("  return  result; "+ result);
            return  request!=null;
        }
    }
        catch (RequestHadndlingException e)
        {
            log.info("expectpppion; "+e.getMessage());
            exceptionHandling(e);
        }

            return false;

        }

        protected void nodeUpdate(RequestBase request, String updateMessage, ChainNodeInfo.CHAIN_NODE_STATUS status)
        {
            messageMethod.apply (request.getUser(),
                    ExecutionResponseStatusUpdate.builder()
                            .message(updateMessage)
                            .stepUpdate(this._nodeLevel)
                            .lvlStatus(status)
                            .build()
            );
        }


    public void startChain(RequestBase request) {

        log.info("start chain response");
        messageMethod.apply (request.getUser(),
                ExecutionChainScheme.builder()
                        .messageType(ExecutionResponseBase.EXECUTION_RESPONSE_TYPE.STATUS_UPDATE)
                        .levels(getChainList())
                        .messageType(ExecutionResponseBase.EXECUTION_RESPONSE_TYPE.CHAIN_SCHEME)
                        .build()
        );
        next(request);
    }

    private BaseRequestHandler getNextRequestHandler()
    {
        return nextRequestHandler;
    }
}
