import ExerciseCreatorController from '@/controllers/CodeRunner/ExerciseCreatorController'
import ExerciseSolverController from '@/controllers/CodeRunner/ExerciseSolverController'
import ExerciseIdToRunMessage from '@/types/ApiMessages/ExerciseIdToRunMessage'
import ExerciseTestToRunMessage from '@/types/ApiMessages/ExerciseTestToRunMessage'
import CodeRunnerType from '@/types/CodeRunnerTypes'
import ExerciseTest from '@/types/ExerciseTest'
import VarType from '@/types/VarType'
import ExerciseCreatorValidationMessage from '@/types/ApiMessages/ExerciseCreatorValidationMessage'

namespace MessageCreator {
  // eslint-disable-next-line no-inner-declarations
  function testStringParser(
    tests: ExerciseTest[],
    inputType: VarType,
    outputType: VarType
  ): ExerciseTest[] {
    const newTest: ExerciseTest[] = []
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
        executionTime: -1,
      })
    })

    return newTest
  }

  export function createExerciseTestToRunMessage(
    exerciseCreatorController: ExerciseCreatorController,
    type: CodeRunnerType
  ): ExerciseTestToRunMessage {
    console.log(
      'exerciseCreatorController: ' + JSON.stringify(exerciseCreatorController)
    )
    const exerciseCreatorControllerCopy: ExerciseCreatorController =
      exerciseCreatorController 
    return {
      code: exerciseCreatorControllerCopy.solutionCodes[type]!,
      manualTests: testStringParser(
        exerciseCreatorControllerCopy.getSingleRowOfManualTests,
        exerciseCreatorControllerCopy.inputType,
        exerciseCreatorControllerCopy.outputType
      ),
      inputType: exerciseCreatorControllerCopy.inputType,
      outputType: exerciseCreatorControllerCopy.outputType,
      amountOfAutoTests: exerciseCreatorController.amountOfAutoTests,
      autoTestMinValue: exerciseCreatorControllerCopy.autoTestMinValue,
      autoTestMaxValue: exerciseCreatorControllerCopy.autoTestMaxValue,
      lengthRange: exerciseCreatorControllerCopy.lengthRange,
      xArrayRange: exerciseCreatorControllerCopy.xArrayRange,
      yArrayRange: exerciseCreatorControllerCopy.yArrayRange,
      upperCaseInput: exerciseCreatorControllerCopy.upperCaseInput,
      lowerCaseInput: exerciseCreatorControllerCopy.lowerCaseInput,
      numberInput: exerciseCreatorControllerCopy.numberInput,
      specialCharacterInput:
        exerciseCreatorControllerCopy.specialCharacterInput,
      breakCharacterInput: exerciseCreatorControllerCopy.breakCharacterInput,
      spaceInput: exerciseCreatorControllerCopy.spaceInput,
      executionTime: exerciseCreatorControllerCopy.timeForExecutionMs,
    }
  }

  export function createExerciseCreatorValidationMessage(
    exerciseCreatorController: ExerciseCreatorController
  ): ExerciseCreatorValidationMessage {
    const parsedTests: ExerciseTest[] = testStringParser(
      exerciseCreatorController.getSingleRowOfManualTests,
      exerciseCreatorController.inputType,
      exerciseCreatorController.outputType
    )
    console.log('parsedTests: \n' + JSON.stringify(parsedTests))

    const exerciseCreatorValidationMessage: ExerciseCreatorValidationMessage = {
      title: exerciseCreatorController.title,
      description: exerciseCreatorController.desc,
      ram: exerciseCreatorController.ram,
      inputType: exerciseCreatorController.inputType,
      outputType: exerciseCreatorController.outputType,
      amountOfAutoTests: exerciseCreatorController.amountOfAutoTests,
      autoTestMinValue: exerciseCreatorController.autoTestMinValue,
      autoTestMaxValue: exerciseCreatorController.autoTestMaxValue,
      lengthRange: exerciseCreatorController.lengthRange,
      xArrayRange: exerciseCreatorController.xArrayRange,
      yArrayRange: exerciseCreatorController.yArrayRange,
      solutionCodes: exerciseCreatorController.solutionCodes,
      timeForExecutionMs: exerciseCreatorController.timeForExecutionMs,
      manualTests: parsedTests,
      upperCaseInput: exerciseCreatorController.upperCaseInput,
      lowerCaseInput: exerciseCreatorController.lowerCaseInput,
      numberInput: exerciseCreatorController.numberInput,
      specialCharacterInput: exerciseCreatorController.specialCharacterInput,
      breakCharacterInput: exerciseCreatorController.breakCharacterInput,
      spaceInput: exerciseCreatorController.spaceInput,
      executionTime: exerciseCreatorController.timeForExecutionMs,
    }
    return exerciseCreatorValidationMessage
  }

  export function createExerciseIdToRunMessage(
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
