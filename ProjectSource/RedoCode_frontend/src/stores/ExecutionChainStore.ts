import { defineStore } from 'pinia'
import { ref, computed, Ref, reactive } from 'vue'
import ChainNodeStatus from '@/types/ApiMesseages/ExecutionResponses/ChainNodeStatus'
import { useApiConnectionStore } from './ApiConnectionStore'
import ExecutionResponseBase from '@/types/ApiMesseages/ExecutionResponses/ExecutionResponseBase'
import ExecutionChainScheme from '@/types/ApiMesseages/ExecutionResponses/ExecutionChainScheme'
import ExecutionResponseStatusUpdate from '@/types/ApiMesseages/ExecutionResponses/ExecutionResponseStatusUpdate'
import { rejects } from 'assert'
import ExecutionChainController from '@/controllers/ExecutionChainController'
import StompApiSubsciptionContorller from '@/controllers/Stomp/StompApiSubsriptionsController'

export const useExecutionChainStore = defineStore('executionChainStore', () => {
  const apiConnectionStore = useApiConnectionStore()

  const executionChainController =reactive(new ExecutionChainController(apiConnectionStore.stompApiSubsciptionContorller as StompApiSubsciptionContorller) )


  const lock=ref(false)

  return {
    executionChainController,
    lock
  }
})
