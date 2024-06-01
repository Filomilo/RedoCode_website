package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.Auth.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@AllArgsConstructor
@SuperBuilder
public class RequestBase {
   protected User user;
    protected Date requestTime;

    public RequestBase(User user ) {
        this.user = user;
        requestTime=new Date();;
    }
}
