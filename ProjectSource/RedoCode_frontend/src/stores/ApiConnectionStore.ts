import { defineStore } from 'pinia'
import { ref, computed, Ref } from 'vue'
import { useToastStore } from './ToastStore'
import StompApiConnection from '@/controllers/StompApiConnection'
import StompApiSubscription from '@/controllers/StompApiSubscription'
import CodeRunnerConnection from '@/controllers/CodeRunnerConnection'
import ProgramResultsMessage from '@/types/ApiMesseages/ProgramResultsMessage'
import { useCodeRunnerStore } from './CodeRunnerStore'
import ProgramResult from '@/types/ProgramResults'

export const useApiConnectionStore = defineStore('apiConnectionStore', () => {
  const toastStore = useToastStore()
  const codeRunnerStore = useCodeRunnerStore()
  const stompApiConnection: StompApiConnection = new StompApiConnection(
    'ws://localhost:8080/public/web-socket',
    (message: string) => {
      toastStore.showProccessingMessage(message)
    },
    (message: string) => {
      toastStore.showSuccessMessage(message)
    },
    (message: string) => {
      toastStore.showErrorMessage(message)
    }
  )

  const codeRunnerConnection: CodeRunnerConnection = new CodeRunnerConnection(
    stompApiConnection
  )

  // const _CodeRunnerResultsSubscriptions: StompApiConnection =
  stompApiConnection.subscribe(
    '/user/public/topic/codeRunnerResults',
    (response: Object) => {
      const results: ProgramResultsMessage = response as ProgramResultsMessage
      console.log('_CodeRunnerResultsSubscriptions: ' + JSON.stringify(results))

      codeRunnerStore.updateTestData(results.results)
    }
  )

  const helloWorldSubscription: StompApiSubscription =
    stompApiConnection.subscribe(
      '/user/public/topic/health',
      (response: Object) => {
        toastStore.showProccessingMessage(
          'Hello world reposnes: ' + JSON.stringify(response)
        )
      }
    )

  // connectionDestinnation

  const sendHealthCheck = () => {
    stompApiConnection.sendMessage('/public/app/Health', 'messaaage')
  }

  // const codeRunnerConnectionControler= ref(new CodeRunnerConnectionControler());
  // const doesHaveACtiveToCodeRunner = computed(() => {
  //     // return true;
  //     return codeRunnerConnectionControler.value.codeRunnerActive.state === 'ACTIVE'
  //   })
  //   const isAwaitngCodeRunner = computed(() => codeRunnerConnectionControler.value.codeRunnerActive.state == 'AWAITING')

  return {
    stompApiConnection,
    codeRunnerConnection,
    // codeRunnerConnectionControler,

    // doesHaveACtiveToCodeRunner,
    // isAwaitngCodeRunner
  }
})
