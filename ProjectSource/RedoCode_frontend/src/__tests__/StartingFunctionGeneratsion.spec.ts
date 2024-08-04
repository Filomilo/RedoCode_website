import generateStartingFunction from "@/tools/StartingFunctionGenerator";
import CodeRunnerType from "@/types/CodeRunnerTypes";
import { describe, it, expect } from 'vitest'

describe('generate starting function',()=>{
    it("generate java script starting fucntion",()=>{
        expect(generateStartingFunction(CodeRunnerType.JS_RUNNER, "SINGLE_STRING","SINGLE_STRING"))
        .toBe("function solution(x){\n \\\\your solution \n}")
    })

    it("generate cpp single int input, single int output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_INTEGER","SINGLE_INTEGER"))
        .toBe("int solution(int x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single float input, single int output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_FLOAT","SINGLE_INTEGER"))
        .toBe("int solution(float x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single string input, single int output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_STRING","SINGLE_INTEGER"))
        .toBe("#include <string>\nint solution(std::string x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of integers input, single int output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_OF_INTEGERS","SINGLE_INTEGER"))
        .toBe("#include <vector>\nint solution(std::vector<int> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of floats input, single int output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_OF_FLOATS","SINGLE_INTEGER"))
        .toBe("#include <vector>\nint solution(std::vector<float> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of strings input, single int output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_STRINGS","SINGLE_INTEGER"))
        .toBe("#include <vector>\n#include <string>\nint solution(std::vector<std::string> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of integers input, single int output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_INTEGERS","SINGLE_INTEGER"))
        .toBe("#include <vector>\nint solution(std::vector<std::vector<int>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of floats input, single int output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_FLOATS","SINGLE_INTEGER"))
        .toBe("#include <vector>\nint solution(std::vector<std::vector<float>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of strings input, single int output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_STRINGS","SINGLE_INTEGER"))
        .toBe("#include <vector>\n#include <string>\nint solution(std::vector<std::vector<std::string>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single int input, single float output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_INTEGER","SINGLE_FLOAT"))
        .toBe("float solution(int x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single float input, single float output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_FLOAT","SINGLE_FLOAT"))
        .toBe("float solution(float x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single string input, single float output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_STRING","SINGLE_FLOAT"))
        .toBe("#include <string>\nfloat solution(std::string x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of integers input, single float output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_OF_INTEGERS","SINGLE_FLOAT"))
        .toBe("#include <vector>\nfloat solution(std::vector<int> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of floats input, single float output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_OF_FLOATS","SINGLE_FLOAT"))
        .toBe("#include <vector>\nfloat solution(std::vector<float> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of strings input, single float output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_STRINGS","SINGLE_FLOAT"))
        .toBe("#include <vector>\n#include <string>\nfloat solution(std::vector<std::string> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of integers input, single float output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_INTEGERS","SINGLE_FLOAT"))
        .toBe("#include <vector>\nfloat solution(std::vector<std::vector<int>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of floats input, single float output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_FLOATS","SINGLE_FLOAT"))
        .toBe("#include <vector>\nfloat solution(std::vector<std::vector<float>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of strings input, single float output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_STRINGS","SINGLE_FLOAT"))
        .toBe("#include <vector>\n#include <string>\nfloat solution(std::vector<std::vector<std::string>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single int input, single string output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_INTEGER","SINGLE_STRING"))
        .toBe("#include <string>\nstd::string solution(int x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single float input, single string output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_FLOAT","SINGLE_STRING"))
        .toBe("#include <string>\nstd::string solution(float x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single string input, single string output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_STRING","SINGLE_STRING"))
        .toBe("#include <string>\nstd::string solution(std::string x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of integers input, single string output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_OF_INTEGERS","SINGLE_STRING"))
        .toBe("#include <vector>\n#include <string>\nstd::string solution(std::vector<int> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of floats input, single string output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_OF_FLOATS","SINGLE_STRING"))
        .toBe("#include <vector>\n#include <string>\nstd::string solution(std::vector<float> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of strings input, single string output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_STRINGS","SINGLE_STRING"))
        .toBe("#include <vector>\n#include <string>\nstd::string solution(std::vector<std::string> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of integers input, single string output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_INTEGERS","SINGLE_STRING"))
        .toBe("#include <vector>\n#include <string>\nstd::string solution(std::vector<std::vector<int>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of floats input, single string output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_FLOATS","SINGLE_STRING"))
        .toBe("#include <vector>\n#include <string>\nstd::string solution(std::vector<std::vector<float>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of strings input, single string output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_STRINGS","SINGLE_STRING"))
        .toBe("#include <vector>\n#include <string>\nstd::string solution(std::vector<std::vector<std::string>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single int input, array of integers output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_INTEGER","ARRAY_OF_INTEGERS"))
        .toBe("#include <vector>\nstd::vector<int> solution(int x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single float input, array of integers output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_FLOAT","ARRAY_OF_INTEGERS"))
        .toBe("#include <vector>\nstd::vector<int> solution(float x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single string input, array of integers output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_STRING","ARRAY_OF_INTEGERS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<int> solution(std::string x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of integers input, array of integers output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_OF_INTEGERS","ARRAY_OF_INTEGERS"))
        .toBe("#include <vector>\nstd::vector<int> solution(std::vector<int> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of floats input, array of integers output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_OF_FLOATS","ARRAY_OF_INTEGERS"))
        .toBe("#include <vector>\nstd::vector<int> solution(std::vector<float> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of strings input, array of integers output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_STRINGS","ARRAY_OF_INTEGERS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<int> solution(std::vector<std::string> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of integers input, array of integers output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_INTEGERS","ARRAY_OF_INTEGERS"))
        .toBe("#include <vector>\nstd::vector<int> solution(std::vector<std::vector<int>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of floats input, array of integers output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_FLOATS","ARRAY_OF_INTEGERS"))
        .toBe("#include <vector>\nstd::vector<int> solution(std::vector<std::vector<float>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of strings input, array of integers output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_STRINGS","ARRAY_OF_INTEGERS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<int> solution(std::vector<std::vector<std::string>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single int input, array of floats output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_INTEGER","ARRAY_OF_FLOATS"))
        .toBe("#include <vector>\nstd::vector<float> solution(int x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single float input, array of floats output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_FLOAT","ARRAY_OF_FLOATS"))
        .toBe("#include <vector>\nstd::vector<float> solution(float x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single string input, array of floats output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_STRING","ARRAY_OF_FLOATS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<float> solution(std::string x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of integers input, array of floats output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_OF_INTEGERS","ARRAY_OF_FLOATS"))
        .toBe("#include <vector>\nstd::vector<float> solution(std::vector<int> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of floats input, array of floats output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_OF_FLOATS","ARRAY_OF_FLOATS"))
        .toBe("#include <vector>\nstd::vector<float> solution(std::vector<float> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of strings input, array of floats output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_STRINGS","ARRAY_OF_FLOATS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<float> solution(std::vector<std::string> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of integers input, array of floats output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_INTEGERS","ARRAY_OF_FLOATS"))
        .toBe("#include <vector>\nstd::vector<float> solution(std::vector<std::vector<int>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of floats input, array of floats output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_FLOATS","ARRAY_OF_FLOATS"))
        .toBe("#include <vector>\nstd::vector<float> solution(std::vector<std::vector<float>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of strings input, array of floats output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_STRINGS","ARRAY_OF_FLOATS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<float> solution(std::vector<std::vector<std::string>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single int input, array of strings output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_INTEGER","ARRAY_STRINGS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::string> solution(int x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single float input, array of strings output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_FLOAT","ARRAY_STRINGS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::string> solution(float x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single string input, array of strings output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_STRING","ARRAY_STRINGS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::string> solution(std::string x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of integers input, array of strings output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_OF_INTEGERS","ARRAY_STRINGS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::string> solution(std::vector<int> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of floats input, array of strings output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_OF_FLOATS","ARRAY_STRINGS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::string> solution(std::vector<float> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of strings input, array of strings output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_STRINGS","ARRAY_STRINGS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::string> solution(std::vector<std::string> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of integers input, array of strings output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_INTEGERS","ARRAY_STRINGS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::string> solution(std::vector<std::vector<int>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of floats input, array of strings output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_FLOATS","ARRAY_STRINGS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::string> solution(std::vector<std::vector<float>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of strings input, array of strings output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_STRINGS","ARRAY_STRINGS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::string> solution(std::vector<std::vector<std::string>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single int input, double array of integers output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_INTEGER","DOUBLE_ARRAY_OF_INTEGERS"))
        .toBe("#include <vector>\nstd::vector<std::vector<int>> solution(int x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single float input, double array of integers output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_FLOAT","DOUBLE_ARRAY_OF_INTEGERS"))
        .toBe("#include <vector>\nstd::vector<std::vector<int>> solution(float x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single string input, double array of integers output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_STRING","DOUBLE_ARRAY_OF_INTEGERS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::vector<int>> solution(std::string x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of integers input, double array of integers output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_OF_INTEGERS","DOUBLE_ARRAY_OF_INTEGERS"))
        .toBe("#include <vector>\nstd::vector<std::vector<int>> solution(std::vector<int> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of floats input, double array of integers output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_OF_FLOATS","DOUBLE_ARRAY_OF_INTEGERS"))
        .toBe("#include <vector>\nstd::vector<std::vector<int>> solution(std::vector<float> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of strings input, double array of integers output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_STRINGS","DOUBLE_ARRAY_OF_INTEGERS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::vector<int>> solution(std::vector<std::string> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of integers input, double array of integers output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_INTEGERS","DOUBLE_ARRAY_OF_INTEGERS"))
        .toBe("#include <vector>\nstd::vector<std::vector<int>> solution(std::vector<std::vector<int>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of floats input, double array of integers output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_FLOATS","DOUBLE_ARRAY_OF_INTEGERS"))
        .toBe("#include <vector>\nstd::vector<std::vector<int>> solution(std::vector<std::vector<float>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of strings input, double array of integers output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_STRINGS","DOUBLE_ARRAY_OF_INTEGERS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::vector<int>> solution(std::vector<std::vector<std::string>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single int input, double array of floats output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_INTEGER","DOUBLE_ARRAY_OF_FLOATS"))
        .toBe("#include <vector>\nstd::vector<std::vector<float>> solution(int x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single float input, double array of floats output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_FLOAT","DOUBLE_ARRAY_OF_FLOATS"))
        .toBe("#include <vector>\nstd::vector<std::vector<float>> solution(float x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single string input, double array of floats output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_STRING","DOUBLE_ARRAY_OF_FLOATS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::vector<float>> solution(std::string x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of integers input, double array of floats output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_OF_INTEGERS","DOUBLE_ARRAY_OF_FLOATS"))
        .toBe("#include <vector>\nstd::vector<std::vector<float>> solution(std::vector<int> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of floats input, double array of floats output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_OF_FLOATS","DOUBLE_ARRAY_OF_FLOATS"))
        .toBe("#include <vector>\nstd::vector<std::vector<float>> solution(std::vector<float> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of strings input, double array of floats output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_STRINGS","DOUBLE_ARRAY_OF_FLOATS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::vector<float>> solution(std::vector<std::string> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of integers input, double array of floats output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_INTEGERS","DOUBLE_ARRAY_OF_FLOATS"))
        .toBe("#include <vector>\nstd::vector<std::vector<float>> solution(std::vector<std::vector<int>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of floats input, double array of floats output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_FLOATS","DOUBLE_ARRAY_OF_FLOATS"))
        .toBe("#include <vector>\nstd::vector<std::vector<float>> solution(std::vector<std::vector<float>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of strings input, double array of floats output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_STRINGS","DOUBLE_ARRAY_OF_FLOATS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::vector<float>> solution(std::vector<std::vector<std::string>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single int input, double array of strings output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_INTEGER","DOUBLE_ARRAY_OF_STRINGS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::vector<std::string>> solution(int x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single float input, double array of strings output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_FLOAT","DOUBLE_ARRAY_OF_STRINGS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::vector<std::string>> solution(float x){\n \\\\your solution \n}")
    })
    
    it("generate cpp single string input, double array of strings output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "SINGLE_STRING","DOUBLE_ARRAY_OF_STRINGS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::vector<std::string>> solution(std::string x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of integers input, double array of strings output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_OF_INTEGERS","DOUBLE_ARRAY_OF_STRINGS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::vector<std::string>> solution(std::vector<int> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of floats input, double array of strings output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_OF_FLOATS","DOUBLE_ARRAY_OF_STRINGS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::vector<std::string>> solution(std::vector<float> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp array of strings input, double array of strings output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "ARRAY_STRINGS","DOUBLE_ARRAY_OF_STRINGS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::vector<std::string>> solution(std::vector<std::string> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of integers input, double array of strings output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_INTEGERS","DOUBLE_ARRAY_OF_STRINGS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::vector<std::string>> solution(std::vector<std::vector<int>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of floats input, double array of strings output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_FLOATS","DOUBLE_ARRAY_OF_STRINGS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::vector<std::string>> solution(std::vector<std::vector<float>> x){\n \\\\your solution \n}")
    })
    
    it("generate cpp double array of strings input, double array of strings output",()=>{
        expect(generateStartingFunction(CodeRunnerType.CPP_RUNNER, "DOUBLE_ARRAY_OF_STRINGS","DOUBLE_ARRAY_OF_STRINGS"))
        .toBe("#include <vector>\n#include <string>\nstd::vector<std::vector<std::string>> solution(std::vector<std::vector<std::string>> x){\n \\\\your solution \n}")
    })
    
})