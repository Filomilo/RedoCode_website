import CodeRunnerRequestMessage from '@/types/CodeRunnerRequestMessage'
import StompApiSender from '../Stomp/StompApiSender'
import CodeRunnerType from '@/types/CodeRunnerTypes'
import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'
import CodeRunnerConnection from './CodeRunnerConnection'
import RawCodeToRunMessage from '@/types/ApiMesseages/RawCodeToRunMessage'
import ExerciseCreatorController from './ExerciseCreatorControlller'
import ExerciseTestToRunMesseage from '@/types/ApiMesseages/ExerciseTestToRunMesseage'
import ExercsieCreatorValidationMesage from '@/types/ApiMesseages/ExercsieCreatorValidationMesage'
import MessageCreator from '@/tools/MessageCreator'
import ExerciseSolverController from '@/controllers/CodeRunner/ExerciseSolverController'
import ExerciseIdToRunMessage from '@/types/ApiMesseages/ExerciseIdToRunMessage'

class CodeRunnerSender {
  private _stompApiSender: StompApiSender
  private _CodeRnnerCOnnection: CodeRunnerConnection
  constructor(
    stompApiSender: StompApiSender,
    codeRunnerConnection: CodeRunnerConnection
  ) {
    this._stompApiSender = stompApiSender
    this._CodeRnnerCOnnection = codeRunnerConnection
  }

  public readonly requestCodeRunner = (codeRunnerName: CodeRunnerType) => {
    this._CodeRnnerCOnnection.setAwaiting()
    console.log('codeRunnerName: ' + JSON.stringify(codeRunnerName))
    const request: CodeRunnerRequestMessage = {
      CodeRunnerType: codeRunnerName,
    }
    console.log('requestCodeRunner: ' + JSON.stringify(request))
    this._stompApiSender.codeRunnerRequest(request)
  }
  runRawCode = (code: string) => {
    const message: RawCodeToRunMessage = {
      code: code,
    }
    console.log('runRawCode: ' + JSON.stringify(message))
    this._stompApiSender.runRawCode(message)
  }

  runSingleExerciseCreationTest = (
    exerciseCreatorController: ExerciseCreatorController,
    type: CodeRunnerType
  ) => {
    const exerciseTestToRunMesseage: ExerciseTestToRunMesseage =
      MessageCreator.createExerciseTestToRunMesseage(
        exerciseCreatorController,
        type
      )
    console.log(
      'runSignleExerciseCreationTest: ' +
        JSON.stringify(exerciseTestToRunMesseage)
    )
    this._stompApiSender.runExerciseTestsCode(exerciseTestToRunMesseage)
  }

  runExerciseCreationValistaion = (
    exerciseCreatorController: ExerciseCreatorController
  ) => {
    console.log(
      'runSignleExerciseCreationTest: ' +
        JSON.stringify(exerciseCreatorController)
    )

    const exercsieCreatorValidationMesage: ExercsieCreatorValidationMesage =
      MessageCreator.createExercsieCreatorValidationMesage(
        exerciseCreatorController
      )

    this._stompApiSender.runExerciseCreatorValidationCode(
      exercsieCreatorValidationMesage
    )
  }

  runExerciseIdCode(exerciseSolverController: ExerciseSolverController) {
    const exerciseIdToRunMessage: ExerciseIdToRunMessage =
      MessageCreator.createExercsieIdToRunMessage(exerciseSolverController)
    this._stompApiSender.runExerciseIdCode(exerciseIdToRunMessage)
  }

  runExercsieIdValidationCode(
    exerciseSolverController: ExerciseSolverController
  ) {
    const exerciseIdToRunMessage: ExerciseIdToRunMessage =
      MessageCreator.createExercsieIdToRunMessage(exerciseSolverController)
    this._stompApiSender.runExercsieIdValidationCode(exerciseIdToRunMessage)
  }
}

export default CodeRunnerSender

// code: exerciseCreatorController.solutionCodes[type]!,
// manualTests: exerciseCreatorController.getSingleRowOfManualTests,
// inputType: exerciseCreatorController.inputType,
// outputType: exerciseCreatorController.outputType,
// amountOfAutoTests: exerciseCreatorController.amountOfAutoTests,
// autoTestminValue: exerciseCreatorController.autoTestminValue,
// autoTestMaxValue: exerciseCreatorController.autoTestMaxValue,
// lengthRange: exerciseCreatorController.lengthRange,
// xArrayRange: exerciseCreatorController.xArrayRange,
// yArrayRange: exerciseCreatorController.yArrayRange,
