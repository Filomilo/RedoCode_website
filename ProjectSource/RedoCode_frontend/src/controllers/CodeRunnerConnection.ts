import type { IMessage, IPublishParams, messageCallbackType } from '@stomp/stompjs'
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

import StompApiConnection from './StompApiConnection'
import StompApiSubscription from './StompApiSubscription'
import { computed, ComputedRef, Ref, ref } from 'vue'
import { CodeRunnerMap } from '@/config/Data'
import ExerciseCreatorController from './ExerciseCreatorControlller'

export default class CodeRunnerConnection {
  private _stompApiConnection: StompApiConnection

  public readonly codeRunnerState: Ref<CoderunnerState> = ref({
    codeRunnerType: '',
    state: ''
  })
  

  public readonly doesHaveACtiveToCodeRunner: ComputedRef<Boolean> = computed(() => {
    return this.codeRunnerState.value.state === 'ACTIVE'
  })
  public readonly isAwaitngCodeRunner = computed(
    () => this.codeRunnerState.value.state == 'AWAITING'
  )

  constructor(stompApiConnection: StompApiConnection) {
    this._stompApiConnection = stompApiConnection

    this._vmStatusSubscription = this._stompApiConnection.subscribe(
      '/user/public/topic/codeRunnerState',
      (message: Object) => {
        console.log('codeRunnerState recived  ' + JSON.stringify(message))
        const state: CoderunnerState = message as CoderunnerState
        this.codeRunnerState.value = state
      }
    )
  }

  public readonly runRawCode = (code: string) => {
    const message: RawCodeToRunMessage = {
      code: code
    }
    this._stompApiConnection.sendMessage('/public/app/CodeRun/RawCodeRun', message)
    console.log('runRawCode: ' + JSON.stringify(message))
  }

  public readonly requestCodeRunner = (codeRunnerName: string) => {
    this.codeRunnerState.value.state = 'AWAITING'
    const request: CodeRunnerRequestMessage = {
      CodeRunnerType: CodeRunnerMap[codeRunnerName]
    }
    console.log('requestCodeRunner: ' + JSON.stringify(request))
    this._stompApiConnection.sendMessage('/public/app/codeRunnerRequest', request)
  }

  public readonly sendToExerciseIdRun = (content: ExerciseIdToRunMessage) => {
    //   console.log('sendToExerciseIdRun: ' + content)
    //   const message: IPublishParams = {
    //     destination: '/app//CodeRun/ExerciseIdRun',
    //     body: JSON.stringify(content)
    //   }
    //   stompClient.publish(message)
  }

  public readonly sendToRawCodeRun = (content: RawCodeToRunMessage) => {
    //   console.log('sendToRawCodeRun: ' + content)
    //   const message: IPublishParams = {
    //     destination: '/app//CodeRun/RawCodeRun',
    //     body: JSON.stringify(content)
    //   }
    //   stompClient.publish(message)
  }

  public readonly sendToExerciseIdValidation = (content: ExerciseIdToRunMessage) => {
    //   console.log('sendToExerciseIdRun: ' + content)
    //   const message: IPublishParams = {
    //     destination: '/app//CodeRun/ExerciseIdValidation',
    //     body: JSON.stringify(content)
    //   }
    //   stompClient.publish(message)
  }

  public readonly sendToExerciseCreationValidation = (content: ExercsieCreatorValidationMesage) => {
    //   console.log('sendToExerciseCodeTests: ' + content)
    //   const message: IPublishParams = {
    //     destination: '/app//CodeRun/ExerciseCreationValidation',
    //     body: JSON.stringify(content)
    //   }
    //   stompClient.publish(message)
  }
  public readonly sendToExerciseCodeTests = (content: ExerciseTestToRunMesseage) => {
    //   console.log('sendToExerciseExerciseCodeTests: ' + JSON.stringify( content))
    //   const message: IPublishParams = {
    //     destination: '/app//CodeRun/ExerciseCodeTests',
    //     body: JSON.stringify(content)
    //   }
    //   stompClient.publish(message)
  }

  public readonly subscribeToCodeResults = (func: CodeRunnerResultsCallBack) => {
    //   console.log('subscribing tor results')
    //   stompClient.subscribe('/user/topic/codeRunnerResults', (mesage: IMessage) => {
    //     console.log('staee code resulr: ' + JSON.stringify(mesage.body))
    //     const results: ProgramResult[] = JSON.parse(mesage.body).map((elem: any) => {
    //       if (elem.variables === null) return elem
    //       return {
    //         ...elem,
    //         variables: elem.variables.value
    //       }
    //     })
    //     func(results)
    //   })
  }

  public readonly runExercsieTestsCode = (exerciseCreatorController: ExerciseCreatorController) => {
    // console.log('runExercsieTestsCode: ' + JSON.stringify(exerciseCreatorController))
    // const message: ExerciseTestToRunMesseage = {
    //   code: exerciseCreatorController.solutions[this.codeRunnerActive.codeRunnerType],
    //   manualTests:
    //     exerciseCreatorController.manualTestsSolutions[this.codeRunnerActive.codeRunnerType],
    //   inputType: exerciseCreatorController.inputType,
    //   inputSize: exerciseCreatorController.inputSize,
    //   outputType: exerciseCreatorController.outputType,
    //   outputSize: exerciseCreatorController.outputSize,
    //   amountOfAutoTests: exerciseCreatorController.amountOfAutoTests,
    //   autoTestminValue: exerciseCreatorController.autoTestminValue,
    //   autoTestMaxValue: exerciseCreatorController.autoTestMaxValue,
    //   lengthRange: exerciseCreatorController.lengthRange,
    //   xArrayRange: exerciseCreatorController.xArrayRange,
    //   yArrayRange: exerciseCreatorController.yArrayRange,
    //   upperCaseInput: exerciseCreatorController.upperCaseInput,
    //   lowerCaseInput: exerciseCreatorController.lowerCaseInput,
    //   numberInput: exerciseCreatorController.numberInput,
    //   specialCharacterInput: exerciseCreatorController.specialCharacterInput,
    //   breakCharacterInupt: exerciseCreatorController.breakCharacterInupt,
    //   spaceInupt: exerciseCreatorController.spaceInupt
  }

  public readonly submitExerciseCreationRequest = (request: ExercsieCreatorValidationMesage) => {
    console.log('submitExerciseCreationRequest: ' + JSON.stringify(request))
    this._stompApiConnection.sendMessage('/public/app/CodeRun/ExerciseCreationValidation', request)
  }

  private readonly _vmStatusSubscription: StompApiSubscription
}

// }
