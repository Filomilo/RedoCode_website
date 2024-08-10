import { defineStore } from 'pinia'
import { ref, computed, Ref } from 'vue'
import { useToastStore } from './ToastStore'
import StompApiConnection from '@/controllers/Stomp/StompApiConnection'
import StompApiSubscription from '@/controllers/Stomp/StompApiSubscription'
import CodeRunnerConnection from '@/controllers/CodeRunner/CodeRunnerConnection'
import ProgramResultsMessage from '@/types/ApiMesseages/ProgramResultsMessage'
import StompApiSender from '@/controllers/Stomp/StompApiSender'
import StompApiSubsriptionsController from '@/controllers/Stomp/StompApiSubsriptionsController'

export const useApiConnectionStore = defineStore('apiConnectionStore', () => {
  const toastStore = useToastStore()
  const stompApiConnection: StompApiConnection = new StompApiConnection(
    'ws://localhost:8080/public/web-socket',
    () => {
      toastStore.showProccessingMessage("attepting connecting to server")
    },
    () => {
      toastStore.showSuccessMessage("connected to server")
    },
    (message: string) => {
      toastStore.showErrorMessage(message)
    }
  )


  const stompApiSender: StompApiSender= new StompApiSender(
    stompApiConnection
  );


  const stompApiSubsciptionContorller: StompApiSubsriptionsController= new StompApiSubsriptionsController(
    stompApiConnection
  )


  // let onCodeResult = (result: ProgramResultsMessage): void => {}

  // const setOnCodeResult = (
  //   func: (result: ProgramResultsMessage) => void
  // ): void => {
  //   onCodeResult = func
  // }
  // const clearOnCodeResult = () => {
  //   onCodeResult = (result: ProgramResultsMessage) => {}
  // }




  // connectionDestinnation

  // const sendHealthCheck = () => {
  //   stompApiConnection.sendMessage('/public/app/Health', 'messaaage')
  // }

  // const codeRunnerConnectionControler= ref(new CodeRunnerConnectionControler());
  // const doesHaveACtiveToCodeRunner = computed(() => {
  //     // return true;
  //     return codeRunnerConnectionControler.value.codeRunnerActive.state === 'ACTIVE'
  //   })
  //   const isAwaitngCodeRunner = computed(() => codeRunnerConnectionControler.value.codeRunnerActive.state == 'AWAITING')

  return {
    stompApiSender,
    stompApiSubsciptionContorller,
    stompApiConnection,
    // codeRunnerConnection,
    // setOnCodeResult,
    // clearOnCodeResult,
    // codeRunnerConnectionControler,

    // doesHaveACtiveToCodeRunner,
    // isAwaitngCodeRunner
  }
})
