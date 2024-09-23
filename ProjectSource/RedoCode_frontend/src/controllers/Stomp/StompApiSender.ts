import RawCodeToRunMessage from '@/types/ApiMessages/RawCodeToRunMessage'
import StompApiConnection from './StompApiConnection'
import CodeRunnerRequestMessage from '@/types/CodeRunnerRequestMessage'
import AuthenticationResponse from '@/types/ApiMessages/Authentication/AuthenticationResponse'
import ExerciseIdToRunMessage from '@/types/ApiMessages/ExerciseIdToRunMessage'
import ExerciseTestToRunMessage from '@/types/ApiMessages/ExerciseTestToRunMessage'
import ExerciseCreatorValidationMessage from '@/types/ApiMessages/ExerciseCreatorValidationMessage'

class StompApiSender {
  private _stompApiConnection: StompApiConnection

  public constructor(stompApiConnection: StompApiConnection) {
    this._stompApiConnection = stompApiConnection
  }

  //#region ApiAuthentication

  public readonly authenticationStomp = (message: AuthenticationResponse) => {
    this._stompApiConnection.sendMessage('/public/app/tokenAuth', message)
  }

  //#endregion

  //#region  CodeRunHandler

  public readonly runExerciseIdCode = (message: ExerciseIdToRunMessage) => {
    this._stompApiConnection.sendMessage(
      '/public/app/CodeRun/ExerciseIdRun',
      message
    )
  }

  public readonly runRawCode = (message: RawCodeToRunMessage) => {
    this._stompApiConnection.sendMessage(
      '/public/app/CodeRun/RawCodeRun',
      message
    )
  }

  public readonly runExerciseIdValidationCode = (
    message: ExerciseIdToRunMessage
  ) => {
    this._stompApiConnection.sendMessage(
      '/public/app/CodeRun/ExerciseIdValidation',
      message
    )
  }

  public readonly runExerciseTestsCode = (
    message: ExerciseTestToRunMessage
  ) => {
    this._stompApiConnection.sendMessage(
      '/public/app/CodeRun/ExerciseCodeTests',
      message
    )
  }

  public readonly runExerciseCreatorValidationCode = (
    message: ExerciseCreatorValidationMessage
  ) => {
    this._stompApiConnection.sendMessage(
      '/public/app/CodeRun/ExerciseCreationValidation',
      message
    )
  }

  //#endregion

  //#region CodeRunner Connection controller

  public readonly codeRunnerRequest = (message: CodeRunnerRequestMessage) => {
    console.log('codeRunnerRequest: ' + JSON.stringify(message))
    this._stompApiConnection.sendMessage(
      '/public/app/codeRunnerRequest',
      message
    )
  }

  //#endregion

  //#region
  public readonly healthCheck = (message: string) => {
    this._stompApiConnection.sendMessage('/public/app/public/Health', message)
  }

  //#endregion
}

export default StompApiSender
