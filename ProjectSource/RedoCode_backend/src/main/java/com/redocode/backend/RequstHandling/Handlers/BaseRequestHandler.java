package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.CodeTestRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public abstract class BaseRequestHandler implements IRequestHandler{

    BaseRequestHandler nextRequestHandler;

    public BaseRequestHandler setNextRequestHandler(BaseRequestHandler requestHandler)
    {
        nextRequestHandler=requestHandler;
        return this;
    }

    abstract boolean  handle(RequestBase request)throws RequestHadndlingException;
    abstract void exceptionHandling(Exception exception);
    @Override
    public boolean next(RequestBase request)  {
        log.info("next hadnler");
        try{
        boolean result=handle(request);
        log.info(getClass().getName()+ " request resutl: " + result);
        if(result && nextRequestHandler!=null) {
            log.info("  return nextRequestHandler.next(request);");
            boolean res= nextRequestHandler.next(request);
            log.info( nextRequestHandler.getClass().getName()+ " handling result of "+ (CodeTestRequest) request+ " resulted in "+ res);
            return res;
        }
        else{
            log.info("  return  result; "+ result);
            return  result;
        }
    }
        catch (RequestHadndlingException e)
        {
            log.info("expectpppion; "+e.getMessage());
            exceptionHandling(e);
        }

            return false;

        }
}
