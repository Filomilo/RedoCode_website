package com.redocode.backend.VmAcces.CodeRunners.Program;

import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuperBuilder
@NoArgsConstructor
public class JsSolutionProgram extends SolutionProgram {
    @Override
    String getImports() {
        return "";
    }

    @Override
    void validate() {
        log.error("not impelementet");
    }

    @Override
    public String getInputGeneratorCode() {
        String inputCodeGenerationExpected=
                "function "+this.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        getReturnInputVar(getInput())+
                        "\n}";
        return inputCodeGenerationExpected;
    }

    @Override
    public String getOutputGeneratorCode() {
        String code=
                " const "+getOutputGeneratorFunctionName()+"_fs = require('fs');\n" +
                        "function "+this.getOutputGeneratorFunctionName()+"(a)\n" +
                        "{\n"+
                        "let str=\"err\";\n" +
                        "if(!Array.isArray(a)){\n" +
                        "str = a.toString().replace(/\\\\/g, \"\\\\\\\\\").replace(/\\n/g, \"\\\\n\").replace(/\\t/g, \"\\\\t\");\n" +
                        "}\n" +
                        "else{\n" +
                        "    if(!Array.isArray(a[0]))\n" +
                        "        {\n" +
                        "            a.forEach(element => {element.toString().replace(/\\\\/g, \"\\\\\\\\\").replace(/\\n/g, \"\\\\n\").replace(/\\t/g, \"\\\\t\")});\n" +
                        "            str=a.join(\"\\t\");\n" +
                        "        }\n" +
                        "        else{\n" +
                        "            a=a.map(element => {\n" +
                        "                   element= element.map(x=>{return x.toString().replace(/\\\\/g, \"\\\\\\\\\").replace(/\\n/g, \"\\\\n\").replace(/\\t/g, \"\\\\t\")})\n" +
                        "                return element= element.join(\"\\t\")\n" +
                        "            });\n" +
                        "            str=a.join(\"\\n\")\n" +
                        "        }\n" +
                        "}"+
        getOutputGeneratorFunctionName()+"_fs.writeFileSync(\""+getOutputFileName()+"\",str);\n"+
                "}";
        return code;
    }
    private String getReturnInputVar(Variables var)
    {
        String returnfunc=switch (var.getType()){
            case ARRAY_STRINGS,ARRAY_OF_FLOATS,ARRAY_OF_INTEGERS -> {
                Object[] arr=(Object[])var.getValue();
                String tmp="return "
                        +"[";
                for (int i = 0; i < arr.length; i++) {
                    tmp+=getValueString(arr[i]);
                    if(i<arr.length-1)
                        tmp+=", ";
                }
                tmp+="];";
                yield tmp;

            }
            case SINGLE_FLOAT,SINGLE_INTEGER,SINGLE_STRING ->  {
                yield "return "+ getValueString(var)+";";
            }
            case DOUBLE_ARRAY_OF_FLOATS,DOUBLE_ARRAY_OF_INTEGERS,DOUBLE_ARRAY_OF_STRINGS ->  {
                Object[][] arr=(Object[][])var.getValue();
                String tmp= "return [";
                for (int i = 0; i <arr.length ; i++) {
                    tmp+="[";
                    for (int j = 0; j < arr[0].length; j++) {
                        tmp+=getValueString(arr[i][j]);
                        if(j<arr[0].length-1)
                            tmp+=", ";
                    }
                    tmp+="]";
                    if(i<arr.length-1)
                        tmp+=", ";

                }
                tmp+="];";
                yield  tmp;
            }
        };
        return returnfunc;
    }
    @Override
    public String getActivationFunction() {
        String code=   this.getOutputGeneratorFunctionName() +"(solution(";
        log.info("activation: \n"+ code);
        if(this.getInput()!=null)
        {
            code+=this.getInputGeneratorFunctionName()+"()";
        }
        code+="));";




        return code;
    }
}
