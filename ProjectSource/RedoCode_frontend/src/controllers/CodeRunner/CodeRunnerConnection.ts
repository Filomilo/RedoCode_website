import type CodeRunnerRequestMessage from '@/types/CodeRunnerRequestMessage'
import type CoderunnerState from '@/types/CodeRunnerState'
import CodeRunnerType from '@/types/CodeRunnerTypes'

import { computed, ComputedRef, Ref, ref } from 'vue'

import CodeRunnerStatus from '@/types/CodeRunnerStatus'
import StompApiSender from '../Stomp/StompApiSender'
import StompApiSubsciptionContorller from '../Stomp/StompApiSubsriptionsController'
import EnpointAcces from '../EndpointsAcces'
import { useActiveUserStore } from '@/stores/ActiveUserStore'
export default class CodeRunnerConnection {
  private _stompApiSender: StompApiSender
  private activeUserStore = useActiveUserStore()

  public codeRunnerState: Ref<CoderunnerState> = ref({
    codeRunnerType: CodeRunnerType.UNIDENTIFIED,
    state: CodeRunnerStatus.NONE,
  })

  public readonly doesHaveACtiveToCodeRunner: ComputedRef<Boolean> = computed(
    () => {
      return this.codeRunnerState.value.state === 'ACTIVE'
    }
  )
  public readonly isAwaitngCodeRunner = computed(
    () => this.codeRunnerState.value.state == 'AWAITING'
  )

  public onCodeRunnerStateChanged(codeRunnerState: CoderunnerState) {
    console.log(JSON.stringify(codeRunnerState))
    this.codeRunnerState.value = codeRunnerState
  }

  constructor(
    stompApiSender: StompApiSender,
    stompApiSubscriptions: StompApiSubsciptionContorller
  ) {
    this._stompApiSender = stompApiSender
    stompApiSubscriptions.addVmStatusSubscription(
      this.onCodeRunnerStateChanged.bind(this)
    )
    console.log(JSON.stringify(this.codeRunnerState.value))
  }

  public updateCodeRunner = () => {
    EnpointAcces.getCodeRunnerState(
      this.activeUserStore.getToken() as string
    ).then((data: CoderunnerState) => {
      console.log('update: ' + data)
      this.codeRunnerState.value = data
    })
  }

  public setAwaiting() {
    console.log('set awaitng')
    console.log(JSON.stringify(this.codeRunnerState))
    this.codeRunnerState.value.state = CodeRunnerStatus.AWAITING
    console.log(JSON.stringify(this.codeRunnerState.value))
  }
  public setNoneStatus() {
    this.codeRunnerState.value.state = CodeRunnerStatus.NONE
  }
}

// console.log('updateCodeRunner: ' + activeUserStore.getToken())

// public readonly sendToExerciseIdRun = (content: ExerciseIdToRunMessage) => {
//   //   console.log('sendToExerciseIdRun: ' + content)
//   //   const message: IPublishParams = {
//   //     destination: '/app//CodeRun/ExerciseIdRun',
//   //     body: JSON.stringify(content)
//   //   }
//   //   stompClient.publish(message)
// }

// public readonly sendToRawCodeRun = (content: RawCodeToRunMessage) => {
//   //   console.log('sendToRawCodeRun: ' + content)
//   //   const message: IPublishParams = {
//   //     destination: '/app//CodeRun/RawCodeRun',
//   //     body: JSON.stringify(content)
//   //   }
//   //   stompClient.publish(message)
// }

// public readonly sendToExerciseIdValidation = (
//   content: ExerciseIdToRunMessage
// ) => {
//   //   console.log('sendToExerciseIdRun: ' + content)
//   //   const message: IPublishParams = {
//   //     destination: '/app//CodeRun/ExerciseIdValidation',
//   //     body: JSON.stringify(content)
//   //   }
//   //   stompClient.publish(message)
// }

// public readonly sendToExerciseCreationValidation = (
//   content: ExercsieCreatorValidationMesage
// ) => {
//   //   console.log('sendToExerciseCodeTests: ' + content)
//   //   const message: IPublishParams = {
//   //     destination: '/app//CodeRun/ExerciseCreationValidation',
//   //     body: JSON.stringify(content)
//   //   }
//   //   stompClient.publish(message)
// }
// public readonly sendToExerciseCodeTests = (
//   content: ExerciseTestToRunMesseage
// ) => {
//   //   console.log('sendToExerciseExerciseCodeTests: ' + JSON.stringify( content))
//   //   const message: IPublishParams = {
//   //     destination: '/app//CodeRun/ExerciseCodeTests',
//   //     body: JSON.stringify(content)
//   //   }
//   //   stompClient.publish(message)
// }

// public readonly subscribeToCodeResults = (
//   func: CodeRunnerResultsCallBack
// ) => {
//   //   console.log('subscribing tor results')
//   //   stompClient.subscribe('/user/topic/codeRunnerResults', (mesage: IMessage) => {
//   //     console.log('staee code resulr: ' + JSON.stringify(mesage.body))
//   //     const results: ProgramResult[] = JSON.parse(mesage.body).map((elem: any) => {
//   //       if (elem.variables === null) return elem
//   //       return {
//   //         ...elem,
//   //         variables: elem.variables.value
//   //       }
//   //     })
//   //     func(results)
//   //   })
// }

// // public readonly runExercsieTestsCode = (
// //   exerciseCreatorController: ExerciseCreatorController
// // ) => {
// //   if (this.codeRunnerState.value !== null) {
// //     const codeType: CodeRunnerType = this.codeRunnerState.value.codeRunnerType
// //     console.log('codeType' + codeType)
// //     console.log(
// //       'exerciseCreatorController: ' +
// //         JSON.stringify(exerciseCreatorController)
// //     )

// //     const tests: ExerciseTest[] =
// //       exerciseCreatorController.manualTestsSolutions[codeType] ?? []

// //     const formattedTests: ExerciseTest[] = tests.map(x => {
// //       const test: ExerciseTest = {
// //         input: JSON.stringify(x.input),
// //         output: JSON.stringify(x.output),
// //         expectedOutput: JSON.stringify(x.expectedOutput),
// //         errorOutput: x.errorOutput,
// //         consoleOutput: x.consoleOutput,
// //         isSolved: null,
// //       }
// //       return test
// //     })
// //     const message: ExerciseTestToRunMesseage = {
// //       code: exerciseCreatorController.solutionCodes[codeType] ?? '',
// //       manualTests: formattedTests,
// //       inputType: exerciseCreatorController.inputType,
// //       outputType: exerciseCreatorController.outputType,
// //       amountOfAutoTests: exerciseCreatorController.amountOfAutoTests,
// //       autoTestminValue: exerciseCreatorController.autoTestminValue,
// //       autoTestMaxValue: exerciseCreatorController.autoTestMaxValue,
// //       lengthRange: exerciseCreatorController.lengthRange,
// //       xArrayRange: exerciseCreatorController.xArrayRange,
// //       yArrayRange: exerciseCreatorController.yArrayRange,
// //       upperCaseInput: exerciseCreatorController.upperCaseInput,
// //       lowerCaseInput: exerciseCreatorController.lowerCaseInput,
// //       numberInput: exerciseCreatorController.numberInput,
// //       specialCharacterInput: exerciseCreatorController.specialCharacterInput,
// //       breakCharacterInupt: exerciseCreatorController.breakCharacterInupt,
// //       spaceInupt: exerciseCreatorController.spaceInupt,
// //       executionTime: exerciseCreatorController.timeForExecutionMs,
// //     }
// //     console.log(
// //       'exerciseCreatorController.manualTestsSolutions: ' +
// //         JSON.stringify(exerciseCreatorController.manualTestsSolutions)
// //     )
// //     console.log('runExercsieTestsCode: ' + JSON.stringify(message))

// //     this._stompApiConnection.sendMessage(
// //       '/public/app/CodeRun/ExerciseCodeTests',
// //       message
// //     )
// //   }
// // }

// public readonly submitExerciseCreationRequest = (
//   request: ExercsieCreatorValidationMesage
// ) => {
//   console.log('submitExerciseCreationRequest: ' + JSON.stringify(request))
//   this._stompApiConnection.sendMessage(
//     '/public/app/CodeRun/ExerciseCreationValidation',
//     request
//   )
// }

// // private readonly _vmStatusSubscription: StompApiSubscription
// }

// }
