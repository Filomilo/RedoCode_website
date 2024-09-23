import { defineStore } from 'pinia'
import { reactive } from 'vue'
import { useApiConnectionStore } from './ApiConnectionStore'
import ExecutionChainController from '@/controllers/ExecutionChainController'
import StompApiSubscriptionController from '@/controllers/Stomp/StompApiSubscriptionController'

export const useExecutionChainStore = defineStore('executionChainStore', () => {
  const apiConnectionStore = useApiConnectionStore()

  const executionChainController = reactive(
    new ExecutionChainController(
      apiConnectionStore.stompApiSubscriptionController as StompApiSubscriptionController
    )
  )

  return {
    executionChainController,
  }
})
