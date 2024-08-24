import ExerciseCreatorController from '@/controllers/CodeRunner/ExerciseCreatorControlller'
import ExerciseSolverController from '@/controllers/CodeRunner/ExerciseSolverController'
import ExerciseIdToRunMessage from '@/types/ApiMesseages/ExerciseIdToRunMessage'
import ExerciseTestToRunMesseage from '@/types/ApiMesseages/ExerciseTestToRunMesseage'
import ExercsieCreatorValidationMesage, {
  TestsIndexed,
} from '@/types/ApiMesseages/ExercsieCreatorValidationMesage'
import CodeRunnerType from '@/types/CodeRunnerTypes'
import ExerciseTest from '@/types/ExcericseTest'
import VarType from '@/types/VarType'

namespace MessageCreator {
  function testStringParser(
    tests: ExerciseTest[],
    inputType: VarType,
    outputType: VarType
  ): ExerciseTest[] {
    let newTest: ExerciseTest[] = []
    tests.forEach((x: ExerciseTest) => {
      newTest.push({
        input:
          inputType === 'SINGLE_STRING' ? JSON.stringify(x.input) : x.input,
        output: null,
        expectedOutput:
          outputType === 'SINGLE_STRING'
            ? JSON.stringify(x.expectedOutput)
            : x.expectedOutput,
        errorOutput: '',
        consoleOutput: '',
        isSolved: null,
        uuid: '',
      })
    })

    return newTest
  }

  export function createExerciseTestToRunMesseage(
    exerciseCreatorController: ExerciseCreatorController,
    type: CodeRunnerType
  ): ExerciseTestToRunMesseage {
    const exerciseCreatorControllercopy: ExerciseCreatorController =
      exerciseCreatorController //JSON.parse(JSON.stringify(exerciseCreatorController)) as ExerciseCreatorController;
    return {
      code: exerciseCreatorControllercopy.solutionCodes[type]!,
      manualTests: testStringParser(
        exerciseCreatorControllercopy.getSingleRowOfManualTests,
        exerciseCreatorControllercopy.inputType,
        exerciseCreatorControllercopy.outputType
      ),
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
      specialCharacterInput:
        exerciseCreatorControllercopy.specialCharacterInput,
      breakCharacterInupt: exerciseCreatorControllercopy.breakCharacterInupt,
      spaceInupt: exerciseCreatorControllercopy.spaceInupt,
      executionTime: exerciseCreatorControllercopy.executionTime,
    }
  }

  export function createExercsieCreatorValidationMesage(
    exerciseCreatorController: ExerciseCreatorController
  ): ExercsieCreatorValidationMesage {
    let parsedTests: ExerciseTest[] = testStringParser(
      exerciseCreatorController.getSingleRowOfManualTests,
      exerciseCreatorController.inputType,
      exerciseCreatorController.outputType
    )
    console.log('parsedTests: \n' + JSON.stringify(parsedTests))

    const exercsieCreatorValidationMesage: ExercsieCreatorValidationMesage = {
      title: exerciseCreatorController.title,
      description: exerciseCreatorController.desc,
      ram: exerciseCreatorController.ram,
      inputType: exerciseCreatorController.inputType,
      outputType: exerciseCreatorController.outputType,
      amountOfAutoTests: exerciseCreatorController.amountOfAutoTests,
      autoTestminValue: exerciseCreatorController.autoTestminValue,
      autoTestMaxValue: exerciseCreatorController.autoTestMaxValue,
      lengthRange: exerciseCreatorController.lengthRange,
      xArrayRange: exerciseCreatorController.xArrayRange,
      yArrayRange: exerciseCreatorController.yArrayRange,
      solutionCodes: exerciseCreatorController.solutionCodes,
      timeForTaskMin: exerciseCreatorController.timeForTaskMin,
      timeForExecutionMs: exerciseCreatorController.timeForExecutionMs,
      manualTests: parsedTests,
      upperCaseInput: exerciseCreatorController.upperCaseInput,
      lowerCaseInput: exerciseCreatorController.lowerCaseInput,
      numberInput: exerciseCreatorController.numberInput,
      specialCharacterInput: exerciseCreatorController.specialCharacterInput,
      breakCharacterInupt: exerciseCreatorController.breakCharacterInupt,
      spaceInupt: exerciseCreatorController.spaceInupt,
      executionTime: exerciseCreatorController.executionTime,
    }
    return exercsieCreatorValidationMesage
  }

  export function createExercsieIdToRunMessage(
    exerciseSolverController: ExerciseSolverController
  ): ExerciseIdToRunMessage {
    const exerciseIdToRunMessage: ExerciseIdToRunMessage = {
      code:
        exerciseSolverController.solution == undefined
          ? ''
          : exerciseSolverController.solution,
      exercise_id: exerciseSolverController.id,
    }
    return exerciseIdToRunMessage
  }
}

export default MessageCreator
