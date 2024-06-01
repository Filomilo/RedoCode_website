package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseRequestHandler implements IRequestHandler{

    BaseRequestHandler nextRequestHandler;

    abstract boolean  handle(RequestBase request)throws RequestHadndlingException;
    abstract void exceptionHandling(Exception exception);
    @Override
    public boolean next(RequestBase request)  {
        try{
        boolean result=handle(request);
        if(result && nextRequestHandler!=null) {
            return nextRequestHandler.next(request);
        }
        else{
            return  result;
        }
    }
        catch (RequestHadndlingException e)
        {
            exceptionHandling(e);
        }
        finally {
            return false;
        }
        }
}
