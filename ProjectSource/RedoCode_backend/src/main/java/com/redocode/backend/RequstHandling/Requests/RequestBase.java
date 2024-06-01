package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.Auth.User;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;


@Slf4j
@Getter
@SuperBuilder
@ToString
public class RequestBase {
   protected User user ;
    protected Date requestTime;


    public RequestBase(User user, Date requestTime) {
        this.user = user;
        this.requestTime = requestTime;
    }
}
