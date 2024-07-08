package com.redocode.backend;

import lombok.ToString;

import java.security.Principal;

@ToString
public class StompPrincipal implements Principal {
    String name;

    public StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }


}