package com.redocode.backend.VmAcces.CodeRunners.Program;

import com.redocode.backend.Tools.StringFormatter;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import javassist.compiler.ast.Variable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;

import java.util.ArrayList;
import java.util.List;
@SuperBuilder
@Slf4j
@NoArgsConstructor
public class CppSolutionProgram extends SolutionProgram {

    @Override
    String getImports() {
        log.error("not impelementet");
        return "";
    }

    @Override
    void validate() {
        log.error("not impelementet");
    }

    public static String getVarName(Variables.VARIABLES_TYPES type){
        String output=switch (type){
            case SINGLE_INTEGER -> "int";
            case SINGLE_STRING -> "std::string";
            case SINGLE_FLOAT -> "float";
            case ARRAY_OF_INTEGERS -> "std::vector<int>";
            case ARRAY_STRINGS -> "std::vector<std::string>";
            case ARRAY_OF_FLOATS -> "std::vector<float>";
            case DOUBLE_ARRAY_OF_INTEGERS -> "std::vector<std::vector<int>>";
            case DOUBLE_ARRAY_OF_FLOATS -> "std::vector<std::vector<float>>";
            case DOUBLE_ARRAY_OF_STRINGS -> "std::vector<std::vector<std::string>>";
        };
        return output;
    }


    private String getReturnInputVar(Variables var)
    {
        String returnfunc=switch (var.getType()){
            case ARRAY_STRINGS,ARRAY_OF_FLOATS,ARRAY_OF_INTEGERS -> {
                Object[] arr=(Object[])var.getValue();
                String tmp="return "+
                        getVarName(var.getType())
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
                Variables.VARIABLES_TYPES singleArrType=switch (var.getType()){
                    case DOUBLE_ARRAY_OF_FLOATS -> Variables.VARIABLES_TYPES.ARRAY_OF_FLOATS;
                    case DOUBLE_ARRAY_OF_STRINGS -> Variables.VARIABLES_TYPES.ARRAY_STRINGS;
                    case DOUBLE_ARRAY_OF_INTEGERS -> Variables.VARIABLES_TYPES.ARRAY_OF_INTEGERS;
                    default -> throw new IllegalStateException("Unexpected value: " + var.getType());
                };
              String tmp=   getVarName(var.getType())+ " arr;\n";

                for (int i = 0; i <arr.length ; i++) {
                    tmp+="arr.push_back("+getVarName(singleArrType)+"());\n";
                }
                for (int i = 0; i <arr.length ; i++) {
                    for (int j = 0; j < arr[0].length; j++) {
                        tmp+="arr["+i+"].push_back("+getValueString(arr[i][j])+");\n";
                    }

                }
                tmp+="return arr;";
                yield  tmp;
            }
        };
        return returnfunc;
    }

    @Override
    public String getInputGeneratorCode() {
        String inputCodeGenerationExpected=
                "#include <iostream>\n"+
                        "#include <vector>\n"+
                getVarName(getInput().getType())+" "+this.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        getReturnInputVar(getInput())+
                        "\n}";
        return inputCodeGenerationExpected;
    }

    @Override
    public String getOutputGeneratorCode() {
       String code=
               "#include <fstream>\n" +
                       "#include <iostream>\n" +
                       "#include <sstream>\n" +
                       "#include <vector>\n" +
                       "void "+this.getOutputGeneratorFunctionName()+"("+getVarName(this.getOutputType())+" a)\n" +
                       "{\n"+
        "std::ofstream myfile;\n" +
                "myfile.open (\""+this.getOutputFileName()+"\");\n";
        code+=this.getOutputgeneartionBody();
        code+= "myfile.close();\n" +
                "}";
        return code;
    }

    private String getOutputgeneartionBody() {
        String returnfunc=switch (this.getOutputType()){
            case ARRAY_STRINGS,ARRAY_OF_FLOATS,ARRAY_OF_INTEGERS -> {
           yield   "size_t l=a.size();\n" +
                   "for (size_t i = 0; i < l; i++)\n" +
                   "{\n" +
                   "std::stringstream ss;\n" +
                   "ss<< a[i];\n" +
                   "std::string s=ss.str();\n" +
                   "for (size_t j = 0; j < s.size(); j++)\n" +
                   "{\n" +
                   "std::string str;\n" +
                   "switch (s[j])\n" +
                   "{\n" +
                   "case '\\\\':\n" +
                   "str=\"\\\\\\\\\";\n" +
                   "break;\n" +
                   "case '\\n':\n" +
                   "str=\"\\\\n\";\n" +
                   "break;\n" +
                   "case '\\t':\n" +
                   "str= \"\\\\t\";\n" +
                   "break;\n" +
                   "default:\n" +
                   "str=std::string(1,s[j]);\n" +
                   "};\n" +
                   "myfile << str;\n" +
                   "}\n" +
                   "if(j+1<s.size())"+
                   "myfile <<\"\\t\";\n" +
                   "}\n";

            }
            case SINGLE_FLOAT,SINGLE_INTEGER,SINGLE_STRING ->  {
                yield  "std::stringstream ss;\n"+
                        "ss<< a;\n" +
                        "std::string s=ss.str();\n" +
                        "for (size_t i = 0; i < s.size(); i++)\n" +
                        "{\n" +
                        "std::string str;\n" +
                        "switch (s[i])\n" +
                        "{\n" +
                        "case '\\\\':\n" +
                        "str=\"\\\\\\\\\";\n" +
                        "break;\n" +
                        "case '\\n':\n" +
                        "str=\"\\\\n\";\n" +
                        "break;\n" +
                        "case '\\t':\n" +
                        "str= \"\\\\t\";\n" +
                        "break;\n" +
                        "default:\n" +
                        "str=std::string(1,s[i]);\n" +
                        "};\n" +
                        "myfile << str;\n" +
                        "}\n";
            }
            case DOUBLE_ARRAY_OF_FLOATS,DOUBLE_ARRAY_OF_INTEGERS,DOUBLE_ARRAY_OF_STRINGS ->  {

                yield   "size_t w=a[0].size();\n" +
                        "size_t h=a.size();\n" +
                        "for (size_t i = 0; i < h; i++)\n" +
                        "{\n" +
                        "for (size_t j = 0; j < w; j++)\n" +
                        "{\n" +
                        "std::stringstream ss;\n" +
                        "ss<< a[i][j];\n" +
                        "std::string s=ss.str();\n" +
                        "for (size_t k = 0; k < s.size(); k++)\n" +
                        "{\n" +
                        "std::string str;\n" +
                        "switch (s[k])\n" +
                        "{\n" +
                        "case '\\\\':\n" +
                        "str=\"\\\\\\\\\";\n" +
                        "break;\n" +
                        "case '\\n':\n" +
                        "str=\"\\\\n\";\n" +
                        "break;\n" +
                        "case '\\t':\n" +
                        "str= \"\\\\t\";\n" +
                        "break;\n" +
                        "default:\n" +
                        "str=std::string(1,s[k]);\n" +
                        "};\n" +
                        "myfile << str;\n" +
                        "}\n" +
                        "if(j+1<w)\n"+
                        "myfile <<\"\\t\";\n" +
                        "}\n" +
                        "if(i+1<h)"+
                        "myfile <<\"\\n\";\n" +
                        "}\n" ;
            }
        };
        return returnfunc;
    }

    @Override
    public String getActivationFunction() {
        String code=   "int main()\n" +
                "{\n" +
        this.getOutputGeneratorFunctionName() +"(solution(";
        log.info("activation: \n"+ code);
        if(this.getInput()!=null)
        {
            code+=this.getInputGeneratorFunctionName()+"()";
        }


//
        code +="));\n" +
                "return 0;\n" +
                "}";


        return code;
    }
}
