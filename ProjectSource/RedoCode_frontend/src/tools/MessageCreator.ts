import ExerciseCreatorController from "@/controllers/CodeRunner/ExerciseCreatorControlller";
import ExerciseTestToRunMesseage from "@/types/ApiMesseages/ExerciseTestToRunMesseage";
import CodeRunnerType from "@/types/CodeRunnerTypes";
import ExerciseTest from "@/types/ExcericseTest";
import VarType from "@/types/VarType";





namespace MessageCreator{


    function testStringParser(tests: ExerciseTest[], inputType: VarType, outputType: VarType): ExerciseTest[]{
            let newTest:ExerciseTest[]=[];
            tests.forEach((x:ExerciseTest )=>{
                newTest.push({
                    input: inputType==='SINGLE_STRING'?JSON.stringify(x.input):x.input,
                    output: null,
                    expectedOutput: outputType==='SINGLE_STRING'?JSON.stringify(x.expectedOutput):x.expectedOutput,
                    errorOutput: "",
                    consoleOutput: "",
                    isSolved: null,
                    uuid: ""
                })
            })

            return newTest;
    }

    

    export function createExerciseTestToRunMesseage(exerciseCreatorController: ExerciseCreatorController, type: CodeRunnerType): ExerciseTestToRunMesseage{

        const exerciseCreatorControllercopy: ExerciseCreatorController=exerciseCreatorController;//JSON.parse(JSON.stringify(exerciseCreatorController)) as ExerciseCreatorController;
        return {
            code: exerciseCreatorControllercopy.solutionCodes[type]!,
            manualTests: testStringParser(exerciseCreatorControllercopy.getSingleRowOfManualTests,exerciseCreatorControllercopy.inputType,exerciseCreatorControllercopy.outputType),
            inputType: exerciseCreatorControllercopy.inputType,
            outputType: exerciseCreatorControllercopy.outputType,
            amountOfAutoTests: exerciseCreatorController.amountOfAutoTests,
            autoTestminValue: exerciseCreatorControllercopy.autoTestminValue,
            autoTestMaxValue: exerciseCreatorControllercopy.autoTestMaxValue,
            lengthRange: exerciseCreatorControllercopy.lengthRange,
            xArrayRange: exerciseCreatorControllercopy.xArrayRange,
            yArrayRange: exerciseCreatorControllercopy.yArrayRange,
            upperCaseInput: exerciseCreatorControllercopy.upperCaseInput,
            lowerCaseInput: exerciseCreatorControllercopy.lowerCaseInput,
            numberInput: exerciseCreatorControllercopy.numberInput,
            specialCharacterInput: exerciseCreatorControllercopy.specialCharacterInput,
            breakCharacterInupt: exerciseCreatorControllercopy.breakCharacterInupt,
            spaceInupt: exerciseCreatorControllercopy.spaceInupt,
            executionTime: exerciseCreatorControllercopy.executionTime
          }
    }
}

export default MessageCreator;