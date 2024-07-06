import type { IMessage, IPublishParams, messageCallbackType } from '@stomp/stompjs'
import { stompClient } from '../controllers/StompApiConnection'
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
import { Ref, ref } from 'vue'

export default class CodeRunnerConnection {
  private _stompApiConnection: StompApiConnection
  

  public readonly vmState:Ref<CoderunnerState>= ref({
    codeRunnerType: "",
    state:""
  });

  constructor(stompApiConnection: StompApiConnection) {
    this._stompApiConnection = stompApiConnection

    this._vmStatusSubscription=
  this._stompApiConnection.subscribe(
    "/topic/codeRunnerState",
    (message: Object)=>{
        const state: CoderunnerState = JSON.parse(mesage.body);
        this.vmState.value=state;
    }
  );
  }

  public readonly requestDefaultVmMachine = (type: string) => {
    const request: CodeRunnerRequestMessage = {
      CodeRunnerType: type
    }
    this._stompApiConnection.sendMessage('/app/codeRunnerRequest', request);
  }


  private readonly _vmStatusSubscription:StompApiSubscription;



}



// export const sendToExerciseIdRun = (content: ExerciseIdToRunMessage) => {
//   console.log('sendToExerciseIdRun: ' + content)
//   const message: IPublishParams = {
//     destination: '/app//CodeRun/ExerciseIdRun',
//     body: JSON.stringify(content)
//   }
//   stompClient.publish(message)
// }

// export const sendToRawCodeRun = (content: RawCodeToRunMessage) => {
//   console.log('sendToRawCodeRun: ' + content)
//   const message: IPublishParams = {
//     destination: '/app//CodeRun/RawCodeRun',
//     body: JSON.stringify(content)
//   }
//   stompClient.publish(message)
// }

// export const sendToExerciseIdValidation = (content: ExerciseIdToRunMessage) => {
//   console.log('sendToExerciseIdRun: ' + content)
//   const message: IPublishParams = {
//     destination: '/app//CodeRun/ExerciseIdValidation',
//     body: JSON.stringify(content)
//   }
//   stompClient.publish(message)
// }

// export const sendToExerciseCreationValidation = (content: ExercsieCreatorValidationMesage) => {
//   console.log('sendToExerciseCodeTests: ' + content)
//   const message: IPublishParams = {
//     destination: '/app//CodeRun/ExerciseCreationValidation',
//     body: JSON.stringify(content)
//   }
//   stompClient.publish(message)
// }
// export const sendToExerciseCodeTests = (content: ExerciseTestToRunMesseage) => {
//   console.log('sendToExerciseExerciseCodeTests: ' + JSON.stringify( content))
//   const message: IPublishParams = {
//     destination: '/app//CodeRun/ExerciseCodeTests',
//     body: JSON.stringify(content)
//   }
//   stompClient.publish(message)
// }

// export const subscribeToCodeResults = (func: CodeRunnerResultsCallBack) => {
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
// }
