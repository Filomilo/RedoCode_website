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
  const isConnected = ref(false)
  if (import.meta.env.MODE === 'development') {
    isConnected.value = true
  }
  const toastStore = useToastStore()
  const stompApiConnection: StompApiConnection = new StompApiConnection(
    'ws://localhost:8080/public/web-socket',
    () => {
      toastStore.showProccessingMessage('attempting connecting to server')
    },
    () => {
      toastStore.showSuccessMessage('successfully connected')

      isConnected.value = true
      console.log('on connected settign value: ' + isConnected.value)
    },
    (message: string) => {
      toastStore.showErrorMessage(message)
    },
    () => {
      isConnected.value = false
      console.log('on disconcneted settign value: ' + isConnected.value)
    }
  )

  const stompApiSender: StompApiSender = new StompApiSender(stompApiConnection)

  const stompApiSubsciptionContorller: StompApiSubsriptionsController =
    new StompApiSubsriptionsController(stompApiConnection)

  return {
    stompApiSender,
    stompApiSubsciptionContorller,
    stompApiConnection,
    isConnected,
    // codeRunnerConnection,
    // setOnCodeResult,
    // clearOnCodeResult,
    // codeRunnerConnectionControler,

    // doesHaveACtiveToCodeRunner,
    // isAwaitngCodeRunner
  }
})
