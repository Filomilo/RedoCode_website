import CodeRunnerType from "@/types/CodeRunnerTypes";
import VarType,{isTypeArray, isTypeDoubleArray, isTypeFloat, isTypeInt, isTypeString} from "@/types/VarType";



const generateJsFunction=(): string=>{
    return "function solution(x){\n //your solution \n}"
}

const getVarCpVarType=(type: VarType):string=>{
    let typ="";

    if(isTypeArray(type))
    {
        typ+="std::vector<"
    }
    else if(isTypeDoubleArray(type))
    {
        typ+="std::vector<std::vector<"
    }
    

    if(isTypeInt(type))
    {
        typ+="int"
    }
    else if(isTypeString(type))
    {
        typ+="std::string" 
    }
    else if(isTypeFloat(type))
    {
        typ+="float" 
    }


    if(isTypeArray(type))
    {
        typ+=">"
    }
    else if(isTypeDoubleArray(type))
    {
        typ+=">>"
    }


    return typ;
}


const getCppImport=(inputVaraible: VarType, outputVariable: VarType): string=>{
    let imports="";  
    
    if(isTypeArray(inputVaraible)|| isTypeDoubleArray(inputVaraible) || isTypeArray(outputVariable)|| isTypeDoubleArray(outputVariable))
{
    imports+="#include <vector>\n"
}
    if(isTypeString(inputVaraible)|| isTypeString(outputVariable))
{
    imports+="#include <string>\n"
}
if(imports.length!=0){

}

return imports;
}
const generateCppFunction=(inputVaraible: VarType, outputVariable: VarType): string=>{
    let func="";
    func+=getCppImport(inputVaraible,outputVariable)
    +getVarCpVarType(outputVariable)+" solution("+getVarCpVarType(inputVaraible)+" x){\n //your solution \n}";

    return func;

}




export default function generateStartingFunction(codeRunnerType: CodeRunnerType, inputVariable: VarType, outputVariable: VarType){

    switch (codeRunnerType){
        case CodeRunnerType.CPP_RUNNER: return  generateCppFunction(inputVariable,outputVariable);
        case CodeRunnerType.JS_RUNNER: return generateJsFunction();
        default: return "//your code here";
    }

}