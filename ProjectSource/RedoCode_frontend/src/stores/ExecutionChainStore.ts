import { defineStore } from 'pinia'
import { ref, computed, Ref } from 'vue'
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

  const executionChainController: Ref<ExecutionChainController> =ref(new ExecutionChainController(apiConnectionStore.stompApiSubsciptionContorller as StompApiSubsciptionContorller) )as Ref<ExecutionChainController>




  return {
    executionChainController,
  }
})
