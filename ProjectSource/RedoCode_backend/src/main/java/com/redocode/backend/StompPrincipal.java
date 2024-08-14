package com.redocode.backend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.security.Principal;

@AllArgsConstructor
@ToString
@Getter
public class StompPrincipal implements Principal {
    String name;


}