package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.database.User;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.Date;


@Slf4j
@Getter
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestBase {

    @NotNull
    protected User user;
    @NotNull
                                     protected final Date requestTime = new Date();

}
