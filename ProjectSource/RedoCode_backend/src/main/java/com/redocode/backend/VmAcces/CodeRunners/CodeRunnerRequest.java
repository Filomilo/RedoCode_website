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
@ToString
public class CodeRunnerRequest implements Comparable {

    private User userRequesting;
    private CodeRunner.CoderunnerTypes codeRunnerType;
    private Date requestTime;


    public CodeRunnerRequest(User userRequesting, CodeRunner.CoderunnerTypes codeRunnerType) {
        this.userRequesting = userRequesting;
        this.codeRunnerType = codeRunnerType;
        requestTime=new Date();
    }

    public CodeRunnerRequest(String userId, CodeRunnerRequestMessage requestMessageSource) {

       switch (requestMessageSource.getCodeRunnerType())
       {
           case "Cpp": this.codeRunnerType= CodeRunner.CoderunnerTypes.CPP_RUNNER; break;
           case "Java script": this.codeRunnerType= CodeRunner.CoderunnerTypes.JS_RUNNER; break;
           default: throw new RuntimeException("Wrong code runner specified");
       }

       this.userRequesting= RedoCodeController.getInstance().getUserById(userId);

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