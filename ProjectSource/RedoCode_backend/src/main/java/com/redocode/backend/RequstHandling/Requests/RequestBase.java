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
@NoArgsConstructor
@AllArgsConstructor
public class RequestBase {
   protected User user ;
    protected  final Date requestTime=new Date();

}
