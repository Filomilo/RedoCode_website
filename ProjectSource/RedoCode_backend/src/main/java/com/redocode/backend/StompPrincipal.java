package com.redocode.backend;

import lombok.ToString;

import java.security.Principal;

@ToString
class StompPrincipal implements Principal {
    String name;

    StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }


}