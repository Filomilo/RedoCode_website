package com.redocode.backend.VmAcces.CodeRunners.Program;

import com.redocode.backend.Tools.StringFormatter;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import javassist.compiler.ast.Variable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CppSolutionProgram extends SolutionProgram {

    @Override
    String getImports() {
        log.error("not impelementet");
        return null;
    }

    @Override
    void validate() {
        log.error("not impelementet");
    }

    private String getVarName(Variables.VARIABLES_TYPES type){
        String output=switch (type){
            case SINGLE_INTEGER -> "int";
            case SINGLE_STRING -> "std::string";
            case SINGLE_FLOAT -> "float";
            case ARRAY_OF_INTEGERS -> "int*";
            case ARRAY_STRINGS -> "std::string*";
            case ARRAY_OF_FLOATS -> "float*";
            case DOUBLE_ARRAY_OF_INTEGERS -> "int**";
            case DOUBLE_ARRAY_OF_FLOATS -> "float**";
            case DOUBLE_ARRAY_OF_STRINGS -> "std::string**";
        };
        return output;
    }


    private String getReturnInputVar(Variables var)
    {
        String returnfunc=switch (var.getType()){
            case ARRAY_STRINGS,ARRAY_OF_FLOATS,ARRAY_OF_INTEGERS -> {
                Object[] arr=(Object[])var.getValue();
                String tmp="return new "+
                        getVarName(var.getType()).replace("*","["+arr.length+"]")
                        +" {";
                for (int i = 0; i < arr.length; i++) {
                    tmp+=getValueString(arr[i]);
                    if(i<arr.length-1)
                        tmp+=", ";
                }
                tmp+="};";
                yield tmp;

            }
            case SINGLE_FLOAT,SINGLE_INTEGER,SINGLE_STRING ->  {
                yield "return "+ getValueString(var)+";";
            }
            case DOUBLE_ARRAY_OF_FLOATS,DOUBLE_ARRAY_OF_INTEGERS,DOUBLE_ARRAY_OF_STRINGS ->  {
                Object[][] arr=(Object[][])var.getValue();
                String singleVar=getVarName(var.getType()).substring(0,getVarName(var.getType()).length()-2);
              String tmp=   getVarName(var.getType())+ " arr = new "+
                      singleVar+"*"+
                "["+arr.length+"];\n";

                for (int i = 0; i <arr.length ; i++) {
                    tmp+="arr["+i+"]"+" = new "+singleVar+"["+arr[0].length+"];\n";
                }
                for (int i = 0; i <arr.length ; i++) {
                    for (int j = 0; j < arr[0].length; j++) {
                        tmp+="arr["+i+"]["+j+"]="+getValueString(arr[i][j])+";\n";
                    }

                }
                tmp+="return arr;";
                yield  tmp;
            }
        };
        return returnfunc;
    }

    @Override
    String getInputGeneratorCode() {
        String inputCodeGenerationExpected=
                "#include <iostream>\n"+
                getVarName(getInput().getType())+" "+this.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        getReturnInputVar(getInput())+
                        "\n}";
        return inputCodeGenerationExpected;
    }

    @Override
    String getOutputGeneratorCode() {
        log.error("not impelementet");
        return null;
    }

    @Override
    String getActivationFunction() {
        log.error("not impelementet");
        return null;
    }
}
