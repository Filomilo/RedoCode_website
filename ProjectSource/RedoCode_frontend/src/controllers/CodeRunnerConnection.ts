import type {
  IMessage,
  IPublishParams,
  messageCallbackType,
} from '@stomp/stompjs'
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
import CodeRunnerType from '@/types/CodeRunnerTypes'
import StompApiConnection from './StompApiConnection'
import StompApiSubscription from './StompApiSubscription'
import { computed, ComputedRef, Ref, ref } from 'vue'
import { CodeRunnerMap } from '@/config/Data'
import ExerciseCreatorController from './ExerciseCreatorControlller'
import CodeRunnerStatus from '@/types/CodeRunnerStatus'
import ExerciseTest from '@/types/ExcericseTest'
export default class CodeRunnerConnection {
  private _stompApiConnection: StompApiConnection

  public readonly codeRunnerState: Ref<CoderunnerState> = ref({
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
      code: code,
    }
    this._stompApiConnection.sendMessage(
      '/public/app/CodeRun/RawCodeRun',
      message
    )
    console.log('runRawCode: ' + JSON.stringify(message))
  }

  public readonly requestCodeRunner = (codeRunnerName: CodeRunnerType) => {
    this.codeRunnerState.value.state = CodeRunnerStatus.AWAITING
    console.log('codeRunnerName: ' + JSON.stringify(codeRunnerName))
    const request: CodeRunnerRequestMessage = {
      CodeRunnerType: codeRunnerName,
    }
    console.log('requestCodeRunner: ' + JSON.stringify(request))
    this._stompApiConnection.sendMessage(
      '/public/app/codeRunnerRequest',
      request
    )
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

  public readonly sendToExerciseIdValidation = (
    content: ExerciseIdToRunMessage
  ) => {
    //   console.log('sendToExerciseIdRun: ' + content)
    //   const message: IPublishParams = {
    //     destination: '/app//CodeRun/ExerciseIdValidation',
    //     body: JSON.stringify(content)
    //   }
    //   stompClient.publish(message)
  }

  public readonly sendToExerciseCreationValidation = (
    content: ExercsieCreatorValidationMesage
  ) => {
    //   console.log('sendToExerciseCodeTests: ' + content)
    //   const message: IPublishParams = {
    //     destination: '/app//CodeRun/ExerciseCreationValidation',
    //     body: JSON.stringify(content)
    //   }
    //   stompClient.publish(message)
  }
  public readonly sendToExerciseCodeTests = (
    content: ExerciseTestToRunMesseage
  ) => {
    //   console.log('sendToExerciseExerciseCodeTests: ' + JSON.stringify( content))
    //   const message: IPublishParams = {
    //     destination: '/app//CodeRun/ExerciseCodeTests',
    //     body: JSON.stringify(content)
    //   }
    //   stompClient.publish(message)
  }

  public readonly subscribeToCodeResults = (
    func: CodeRunnerResultsCallBack
  ) => {
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

  public readonly runExercsieTestsCode = (
    exerciseCreatorController: ExerciseCreatorController
  ) => {
    if (this.codeRunnerState.value !== null) {
      const codeType: CodeRunnerType = this.codeRunnerState.value.codeRunnerType
      console.log('codeType' + codeType)
      console.log(
        'exerciseCreatorController: ' +
          JSON.stringify(exerciseCreatorController)
      )

      const tests: ExerciseTest[] =
        exerciseCreatorController.manualTestsSolutions[codeType] ?? []

      const formattedTests: ExerciseTest[] = tests.map(x => {
        const test: ExerciseTest = {
          input: JSON.stringify(x.input),
          output: JSON.stringify(x.output),
          expectedOutput: JSON.stringify(x.expectedOutput),
          errorOutput: x.errorOutput,
          consoleOutput: x.consoleOutput,
          isSolved: null,
        }
        return test
      })
      const message: ExerciseTestToRunMesseage = {
        code: exerciseCreatorController.solutionCodes[codeType] ?? '',
        manualTests: formattedTests,
        inputType: exerciseCreatorController.inputType,
        outputType: exerciseCreatorController.outputType,
        amountOfAutoTests: exerciseCreatorController.amountOfAutoTests,
        autoTestminValue: exerciseCreatorController.autoTestminValue,
        autoTestMaxValue: exerciseCreatorController.autoTestMaxValue,
        lengthRange: exerciseCreatorController.lengthRange,
        xArrayRange: exerciseCreatorController.xArrayRange,
        yArrayRange: exerciseCreatorController.yArrayRange,
        upperCaseInput: exerciseCreatorController.upperCaseInput,
        lowerCaseInput: exerciseCreatorController.lowerCaseInput,
        numberInput: exerciseCreatorController.numberInput,
        specialCharacterInput: exerciseCreatorController.specialCharacterInput,
        breakCharacterInupt: exerciseCreatorController.breakCharacterInupt,
        spaceInupt: exerciseCreatorController.spaceInupt,
        executionTime: exerciseCreatorController.timeForExecutionMs,
      }
      console.log(
        'exerciseCreatorController.manualTestsSolutions: ' +
          JSON.stringify(exerciseCreatorController.manualTestsSolutions)
      )
      console.log('runExercsieTestsCode: ' + JSON.stringify(message))

      this._stompApiConnection.sendMessage(
        '/public/app/CodeRun/ExerciseCodeTests',
        message
      )
    }
  }

  public readonly submitExerciseCreationRequest = (
    request: ExercsieCreatorValidationMesage
  ) => {
    console.log('submitExerciseCreationRequest: ' + JSON.stringify(request))
    this._stompApiConnection.sendMessage(
      '/public/app/CodeRun/ExerciseCreationValidation',
      request
    )
  }

  private readonly _vmStatusSubscription: StompApiSubscription
}

// }
