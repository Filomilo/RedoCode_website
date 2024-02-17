package com.redocode.backend.VmAcces.CodeRunners.Program;


import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Builder
public class RawProgram extends Program {

    String code="";

    public RawProgram(String code) {
        super();
        this.code=code;
    }


    @Override
    public String getProgramCode() {
        return code;
    }
}
