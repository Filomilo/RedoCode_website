package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.Auth.User;
import com.redocode.backend.Messages.CodeRunnerRequestMessage;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Getter
@Setter
@SuperBuilder
@ToString
public class CodeRunnerRequest extends RequestBase implements Comparable {

    protected CODE_RUNNER_TYPE codeRunnerType;


    public CodeRunnerRequest(User user, CODE_RUNNER_TYPE codeRunnerType) {
        super(user);
        this.codeRunnerType = codeRunnerType;
    }


    public CodeRunnerRequest(User user, CodeRunnerRequestMessage requestMessageSource) {
        super(user);
        log.info("handling code runner request: "+ requestMessageSource );
       switch (requestMessageSource.getCodeRunnerType())
       {
           case "cpp": this.codeRunnerType= CODE_RUNNER_TYPE.CPP_RUNNER; break;
           case "js": this.codeRunnerType= CODE_RUNNER_TYPE.JS_RUNNER; break;
           default: throw new RuntimeException("Wrong code runner specified: "+requestMessageSource.getCodeRunnerType() );
       }

       this.user=user;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeRunnerRequest that = (CodeRunnerRequest) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public int compareTo( Object o) {
        CodeRunnerRequest crm=(CodeRunnerRequest)o;
        if(this.user.getUserType()==crm.getUser().getUserType() && this.requestTime!=null && crm.requestTime!=null) {
//            log.info("Comapring "+this.requestTime.getTime()+" with "+ crm.getRequestTime()+": "+ this.requestTime.compareTo(crm.getRequestTime()));
            if( this.requestTime.compareTo(crm.getRequestTime())==0)
                return (int) (this.requestTime.getTime()-crm.getRequestTime().getTime());
            return this.requestTime.compareTo(crm.getRequestTime());
        }
        return -1*this.user.compareTo(crm.getUser());
    }


}