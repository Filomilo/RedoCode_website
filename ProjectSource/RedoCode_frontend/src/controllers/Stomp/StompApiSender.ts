import RawCodeToRunMessage from '@/types/ApiMesseages/RawCodeToRunMessage'
import StompApiConnection from './StompApiConnection'
import CodeRunnerRequestMessage from '@/types/CodeRunnerRequestMessage'
import AuthenticationResponse from '@/types/ApiMesseages/Authentication/AuthenticationResponse'
import ExerciseIdToRunMessage from '@/types/ApiMesseages/ExerciseIdToRunMessage'
import ExerciseTestToRunMesseage from '@/types/ApiMesseages/ExerciseTestToRunMesseage'
import ExercsieCreatorValidationMesage from '@/types/ApiMesseages/ExercsieCreatorValidationMesage'

class StompApiSender {
  private _stompApiConnection: StompApiConnection

  public constructor(stompApiConnection: StompApiConnection) {
    this._stompApiConnection = stompApiConnection
  }

  //#region ApiAuthentication

  public readonly authenticationStomp = (mesage: AuthenticationResponse) => {
    this._stompApiConnection.sendMessage('/public/app/tokenAuth', mesage)
  }

  //#endregion

  //#region  CodeRunHandler

  public readonly runExerciseIdCode = (mesage: ExerciseIdToRunMessage) => {
    this._stompApiConnection.sendMessage(
      '/public/app/CodeRun/ExerciseIdRun',
      mesage
    )
  }

  public readonly runRawCode = (mesage: RawCodeToRunMessage) => {
    this._stompApiConnection.sendMessage(
      '/public/app/CodeRun/RawCodeRun',
      mesage
    )
  }

  public readonly runExercsieIdValidationCode = (
    mesage: ExerciseIdToRunMessage
  ) => {
    this._stompApiConnection.sendMessage(
      '/public/app/CodeRun/ExerciseIdValidation',
      mesage
    )
  }

  public readonly runExerciseTestsCode = (
    mesage: ExerciseTestToRunMesseage
  ) => {
    this._stompApiConnection.sendMessage(
      '/public/app/CodeRun/ExerciseCodeTests',
      mesage
    )
  }

  public readonly runExerciseCreatorValidationCode = (
    mesage: ExercsieCreatorValidationMesage
  ) => {
    this._stompApiConnection.sendMessage(
      '/public/app/CodeRun/ExerciseCreationValidation',
      mesage
    )
  }

  //#endregion

  //#region CodeRunner Connection controller

  public readonly codeRunnerRequest = (mesage: CodeRunnerRequestMessage) => {
    console.log('codeRunnerRequest: ' + JSON.stringify(mesage))
    this._stompApiConnection.sendMessage(
      '/public/app/codeRunnerRequest',
      mesage
    )
  }




  //#endregion

  //#region
  public readonly healthCheck = (mesage: string) => {
    this._stompApiConnection.sendMessage('/public/app/public/Health', mesage)
  }

  //#endregion
}

export default StompApiSender
