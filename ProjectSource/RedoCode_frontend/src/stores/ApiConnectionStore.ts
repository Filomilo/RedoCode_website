import { defineStore } from 'pinia'
import { ref, computed, Ref } from 'vue'
import { useToastStore } from './ToastStore'
import CodeRunnerConnectionControler from '@/controllers/CodeRunnerConnectionControler'
import StompApiConnection from '@/controllers/StompApiConnection'
import StompApiSubscription from '@/controllers/StompApiSubscription'
import CodeRunnerConnection from '@/controllers/CodeRunnerConnection'

export const useApiConnectionStore = defineStore('apiConnectionStore', () => {
  const toastStore= useToastStore();

    const stompApiConnection: StompApiConnection= 
    new StompApiConnection(
      'ws://localhost:8080/web-socket',
      (message: string)=>{
        toastStore.showProccessingMessage(message);
      },
      (message: string)=>{
        toastStore.showSuccessMessage(message);
      },
      (message: string)=>{
        toastStore.showErrorMessage(message);
      }

    )

    const  codeRunnerConnection :CodeRunnerConnection=  new CodeRunnerConnection(
      stompApiConnection
    );

    


    const helloWorldSubscription: StompApiSubscription=
    stompApiConnection.subscribe("/user/topic/health",(response: Object)=>{
      toastStore.showProccessingMessage("Hello world reposnes: "+JSON.stringify(response))
    })


// connectionDestinnation

const sendHealthCheck=()=>
  {
      stompApiConnection.sendMessage("/app/Health","messaaage")
  }

    // const codeRunnerConnectionControler= ref(new CodeRunnerConnectionControler());
    // const doesHaveACtiveToCodeRunner = computed(() => {
    //     // return true;
    //     return codeRunnerConnectionControler.value.codeRunnerActive.state === 'ACTIVE'
    //   })
    //   const isAwaitngCodeRunner = computed(() => codeRunnerConnectionControler.value.codeRunnerActive.state == 'AWAITING')
      


    
  return { 
    stompApiConnection,
    sendHealthCheck
    // codeRunnerConnectionControler,

    // doesHaveACtiveToCodeRunner,
    // isAwaitngCodeRunner
  }
})
