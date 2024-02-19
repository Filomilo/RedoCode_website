package com.redocode.backend.VmAcces.CodeRunners.Program;

import com.redocode.backend.Tools.StringFormatter;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@NoArgsConstructor
@Getter
@Setter
public abstract class SolutionProgram  extends Program{


    private final String inputGeneratorFunctionName= "inputGenerator_"+UUID.randomUUID().toString().replace("-","");
    private final String outputGeneratorFunctionName= "outputGenerator_"+UUID.randomUUID().toString().replace("-","");
    private final String outputFileName= "outputResult_"+UUID.randomUUID().toString().replace("-","");

    private Variables input;
    private Variables output;
    private String SolutionCode;
    private List<String> imports=new ArrayList<>();

    abstract String getImports();
    abstract void validate();
    abstract String getInputGeneratorCode();
    abstract String getOutputGeneratorCode();
    abstract String getActivationFunction();

    @Override
    public String getProgramCode() {
        validate();
        String programCode="";
        programCode+=this.getSolutionCode()+"\n";
        programCode+=getImports()+"\n\n";
        programCode+=getInputGeneratorCode()+"\n\n";
        programCode+=getOutputGeneratorCode()+"\n\n";
        programCode+=getActivationFunction();
        return programCode;
    }
    protected String getValueString(Object o)
    {
        if(o.getClass().isAssignableFrom(String.class))
        {
            return "\""+ StringFormatter.removeWhiteCharacterss(o.toString())+"\"";
        }
        return o.toString();
    }

    @Override
    public Variables.VARIABLES_TYPES getOutuputType() {
        return output.getType();
    }
}
