package com.redocode.backend.VmAcces.Messages;

import com.redocode.backend.Auth.User;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@Builder
public class CodeRunnerRequestMessage implements Comparable {

    private User userRequesting;
    private CodeRunner.CoderunnerTypes codeRunnerType;
    private Date requestTime;



    public CodeRunnerRequestMessage(User userRequesting, CodeRunner.CoderunnerTypes codeRunnerType) {
        this.userRequesting = userRequesting;
        this.codeRunnerType = codeRunnerType;
        requestTime=new Date();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeRunnerRequestMessage that = (CodeRunnerRequestMessage) o;
        return Objects.equals(userRequesting, that.userRequesting);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRequesting);
    }

    @Override
    public int compareTo( Object o) {
        CodeRunnerRequestMessage crm=(CodeRunnerRequestMessage)o;
        if(this.userRequesting.getUserType()==crm.getUserRequesting().getUserType() && this.requestTime!=null && crm.requestTime!=null) {
            log.info("Comapring "+this.requestTime.getTime()+" with "+ crm.getRequestTime()+": "+ this.requestTime.compareTo(crm.getRequestTime()));
            if( this.requestTime.compareTo(crm.getRequestTime())==0)
                return (int) (this.requestTime.getTime()-crm.getRequestTime().getTime());
            return this.requestTime.compareTo(crm.getRequestTime());
        }
        return -1*this.userRequesting.compareTo(crm.getUserRequesting());
    }
}
