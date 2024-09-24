import CodeRunnerRequestMessage from '@/types/CodeRunnerRequestMessage'
import StompApiSender from '../Stomp/StompApiSender'
import CodeRunnerType from '@/types/CodeRunnerTypes'
import CodeRunnerConnection from './CodeRunnerConnection'
import RawCodeToRunMessage from '@/types/ApiMessages/RawCodeToRunMessage'
import ExerciseCreatorController from './ExerciseCreatorController'
import ExerciseTestToRunMessage from '@/types/ApiMessages/ExerciseTestToRunMessage'
import ExerciseCreatorValidationMessage from '@/types/ApiMessages/ExerciseCreatorValidationMessage'
import MessageCreator from '@/tools/MessageCreator'
import ExerciseSolverController from '@/controllers/CodeRunner/ExerciseSolverController'
import ExerciseIdToRunMessage from '@/types/ApiMessages/ExerciseIdToRunMessage'

class CodeRunnerSender {
  private _stompApiSender: StompApiSender
  private _CodeRunnerCOnnection: CodeRunnerConnection
  constructor(
    stompApiSender: StompApiSender,
    codeRunnerConnection: CodeRunnerConnection
  ) {
    this._stompApiSender = stompApiSender
    this._CodeRunnerCOnnection = codeRunnerConnection
  }

  public readonly requestCodeRunner = (codeRunnerName: CodeRunnerType) => {
    this._CodeRunnerCOnnection.setAwaiting()
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
    const exerciseTestToRunMessage: ExerciseTestToRunMessage =
      MessageCreator.createExerciseTestToRunMessage(
        exerciseCreatorController,
        type
      )
    console.log(
      'runSingleExerciseCreationTest: ' +
        JSON.stringify(exerciseTestToRunMessage)
    )
    this._stompApiSender.runExerciseTestsCode(exerciseTestToRunMessage)
  }

  runExerciseCreationValidation = (
    exerciseCreatorController: ExerciseCreatorController
  ) => {
    console.log(
      'runSingleExerciseCreationTest: ' +
        JSON.stringify(exerciseCreatorController)
    )

    const exerciseCreatorValidationMessage: ExerciseCreatorValidationMessage =
      MessageCreator.createExerciseCreatorValidationMessage(
        exerciseCreatorController
      )

    this._stompApiSender.runExerciseCreatorValidationCode(
      exerciseCreatorValidationMessage
    )
  }

  runExerciseIdCode(exerciseSolverController: ExerciseSolverController) {
    const exerciseIdToRunMessage: ExerciseIdToRunMessage =
      MessageCreator.createExerciseIdToRunMessage(exerciseSolverController)
    this._stompApiSender.runExerciseIdCode(exerciseIdToRunMessage)
  }

  runExorciseIdValidationCode(
    exerciseSolverController: ExerciseSolverController
  ) {
    const exerciseIdToRunMessage: ExerciseIdToRunMessage =
      MessageCreator.createExerciseIdToRunMessage(exerciseSolverController)
    this._stompApiSender.runExerciseIdValidationCode(exerciseIdToRunMessage)
  }
}

export default CodeRunnerSender
