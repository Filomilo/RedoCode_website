package com.redocode.backend.VmAcces.CodeRunners;

import com.redocode.backend.Auth.User;
import com.redocode.backend.Messages.CodeRunnerRequestMessage;
import com.redocode.backend.RedoCodeController;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString public class CodeRunnerRequest implements Comparable {

    private User userRequesting;
    private CODE_RUNNER_TYPE codeRunnerType;
    private Date requestTime;


    public CodeRunnerRequest(User userRequesting, CODE_RUNNER_TYPE codeRunnerType) {
        this.userRequesting = userRequesting;
        this.codeRunnerType = codeRunnerType;
        requestTime=new Date();
    }

    public CodeRunnerRequest(User user, CodeRunnerRequestMessage requestMessageSource) {

       switch (requestMessageSource.getCodeRunnerType())
       {
           case "cpp": this.codeRunnerType= CODE_RUNNER_TYPE.CPP_RUNNER; break;
           case "js": this.codeRunnerType= CODE_RUNNER_TYPE.JS_RUNNER; break;
           default: throw new RuntimeException("Wrong code runner specified: "+requestMessageSource.getCodeRunnerType() );
       }

       this.userRequesting=user;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeRunnerRequest that = (CodeRunnerRequest) o;
        return Objects.equals(userRequesting, that.userRequesting);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRequesting);
    }

    @Override
    public int compareTo( Object o) {
        CodeRunnerRequest crm=(CodeRunnerRequest)o;
        if(this.userRequesting.getUserType()==crm.getUserRequesting().getUserType() && this.requestTime!=null && crm.requestTime!=null) {
//            log.info("Comapring "+this.requestTime.getTime()+" with "+ crm.getRequestTime()+": "+ this.requestTime.compareTo(crm.getRequestTime()));
            if( this.requestTime.compareTo(crm.getRequestTime())==0)
                return (int) (this.requestTime.getTime()-crm.getRequestTime().getTime());
            return this.requestTime.compareTo(crm.getRequestTime());
        }
        return -1*this.userRequesting.compareTo(crm.getUserRequesting());
    }


}
