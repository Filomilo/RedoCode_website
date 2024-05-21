import { defineStore } from 'pinia'
import { ref, computed, Ref } from 'vue'
import { useToastStore } from './ToastStore'
import CodeRunnerConnectionControler from '@/controllers/CodeRunnerConnectionControler'
export const useApiConnectionStore = defineStore('apiConnectionStore', () => {
    const codeRunnerConnectionControler= ref(new CodeRunnerConnectionControler());
    const doesHaveACtiveToCodeRunner = computed(() => {
        // return true;
        return codeRunnerConnectionControler.value.codeRunnerActive.state === 'ACTIVE'
      })
      const isAwaitngCodeRunner = computed(() => codeRunnerConnectionControler.value.codeRunnerActive.state == 'AWAITING')
      


    
  return { 
    codeRunnerConnectionControler,

    doesHaveACtiveToCodeRunner,
    isAwaitngCodeRunner
   }
})
