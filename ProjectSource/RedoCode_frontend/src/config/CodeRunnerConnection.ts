import type { IMessage, IPublishParams, messageCallbackType } from '@stomp/stompjs'
import { stompClient } from './StompApiConnection'
import type CodeRunnerRequestMessage from '@/types/CodeRunnerRequestMessage'
import type CodeRunnerStateCallBack from '@/types/CodeRunnerStateCallBack'
import type CoderunnerState from '@/types/CodeRunnerState'
import type CodeToRunMessage from '@/types/CodeToRunMessage'
import type CodeRunnerResultsCallBack from '@/types/CodeRunnerResultsCallBack'
import type ProgramResult from '@/types/ProgramResults'
import ExerciseIdToRunMessage from '@/types/ApiMesseages/ExerciseIdToRunMessage'
import RawCodeToRunMessage from '@/types/ApiMesseages/RawCodeToRunMessage'
import ExerciseTestToRunMesseage from '@/types/ApiMesseages/ExerciseTestToRunMesseage'
import ExercsieCreatorValidationMesage from '@/types/ApiMesseages/ExercsieCreatorValidationMesage'

export const requstDefaultVmMachine = (type: string) => {
  const request: CodeRunnerRequestMessage = {
    CodeRunnerType: type
  }

  const message: IPublishParams = {
    destination: '/app/codeRunnerRequest',
    body: JSON.stringify(request)
  }
  console.log('requewsting vm: ' + JSON.stringify(message))
  stompClient.publish(message)
}

export const subcribeToVmStatus = (func: CodeRunnerStateCallBack) => {
  console.log('subscribing')
  stompClient.subscribe('/user/topic/codeRunnerState', (mesage: IMessage) => {
    console.log('subcribeToVmStatus activated---')
    console.log('staee: ' + JSON.stringify(mesage.body))
    console.log('parsed beofre pasrisng')
    const state: CoderunnerState = JSON.parse(mesage.body)
    console.log('parsed: ' + JSON.stringify(state))
    func(state)
  })
}

export const sendToExerciseIdRun = (content: ExerciseIdToRunMessage) => {
  console.log('sendToExerciseIdRun: ' + content)
  const message: IPublishParams = {
    destination: '/app//CodeRun/ExerciseIdRun',
    body: JSON.stringify(content)
  }
  stompClient.publish(message)
}

export const sendToRawCodeRun = (content: RawCodeToRunMessage) => {
  console.log('sendToRawCodeRun: ' + content)
  const message: IPublishParams = {
    destination: '/app//CodeRun/RawCodeRun',
    body: JSON.stringify(content)
  }
  stompClient.publish(message)
}

export const sendToExerciseIdValidation = (content: ExerciseIdToRunMessage) => {
  console.log('sendToExerciseIdRun: ' + content)
  const message: IPublishParams = {
    destination: '/app//CodeRun/ExerciseIdValidation',
    body: JSON.stringify(content)
  }
  stompClient.publish(message)
}

export const sendToExerciseCreationValidation = (content: ExercsieCreatorValidationMesage) => {
  console.log('sendToExerciseCodeTests: ' + content)
  const message: IPublishParams = {
    destination: '/app//CodeRun/ExerciseCreationValidation',
    body: JSON.stringify(content)
  }
  stompClient.publish(message)
}
export const sendToExerciseCodeTests = (content: ExerciseTestToRunMesseage) => {
  console.log('sendToExerciseExerciseCodeTests: ' + JSON.stringify( content))
  const message: IPublishParams = {
    destination: '/app//CodeRun/ExerciseCodeTests',
    body: JSON.stringify(content)
  }
  stompClient.publish(message)
}




export const subscribeToCodeResults = (func: CodeRunnerResultsCallBack) => {
  console.log('subscribing tor results')
  stompClient.subscribe('/user/topic/codeRunnerResults', (mesage: IMessage) => {
    console.log('staee code resulr: ' + JSON.stringify(mesage.body))
    const results: ProgramResult[] = JSON.parse(mesage.body).map((elem: any) => {
      if (elem.variables === null) return elem
      return {
        ...elem,
        variables: elem.variables.value
      }
    })
    func(results)
  })
}
