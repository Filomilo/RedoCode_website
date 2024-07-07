import { defineStore } from 'pinia'
import { ref, computed, Ref } from 'vue'
import ChainNodeStatus from '@/types/ApiMesseages/ExecutionResponses/ChainNodeStatus'
import { useApiConnectionStore } from './ApiConnectionStore'
import ExecutionResponseBase from '@/types/ApiMesseages/ExecutionResponses/ExecutionResponseBase'
import ExecutionChainScheme from '@/types/ApiMesseages/ExecutionResponses/ExecutionChainScheme'
import ExecutionResponseStatusUpdate from '@/types/ApiMesseages/ExecutionResponses/ExecutionResponseStatusUpdate'
import { rejects } from 'assert'

export const useExecutionChainStore = defineStore('executionChainStore', () => {
  const apiConnectionStore = useApiConnectionStore()

  const executionChain: Ref<ChainNodeStatus[]> = ref([])

  const loadChainScheme = (scheme: ExecutionChainScheme) => {
    console.log('ExecutionResponses  loadChainScheme:' + JSON.stringify(scheme))
    executionChain.value = scheme.levels
    showExecutionChain.value = true
  }

  const waitForScheme = (expectedLvl: number, timeout: number) => {
    return new Promise<void>((resolve, reject) => {
      const interval = 50
      const checkArraySize = () => {
        if (executionChain.value.length > expectedLvl) {
          resolve()
        } else if (timeout <= 0) {
          reject(new Error('Error receiving messages'))
        } else {
          timeout -= interval
          setTimeout(checkArraySize, interval)
        }
      }
      checkArraySize()
    })
  }

  const updateStatus = async (update: ExecutionResponseStatusUpdate) => {
    console.log('ExecutionResponses  udpate:' + JSON.stringify(update))
    await waitForScheme(update.stepUpdate, 100)
      .then(() => {
        executionChain.value[update.stepUpdate].processingMessage = update.message
        executionChain.value[update.stepUpdate].status = update.lvlStatus
        if (
          update.lvlStatus === 'FAILED' ||
          (update.stepUpdate === executionChain.value.length - 1 && update.lvlStatus === 'SUCCESS')
        ) {
          showCloseButton.value = true
        }
      })
      .catch(() => {
        showCloseButton.value = true
      })
  }
  apiConnectionStore.stompApiConnection.subscribe(
    '/user/topic/ExecutionResponses',
    (message: Object) => {
      const responseBase: ExecutionResponseBase = message as ExecutionResponseBase

      console.log('ExecutionResponses revcived: ' + JSON.stringify(responseBase))
      if (responseBase.messageType === 'CHAIN_SCHEME')
        loadChainScheme(responseBase as ExecutionChainScheme)
      else updateStatus(responseBase as ExecutionResponseStatusUpdate)
    }
  )

  const showExecutionChain: Ref<Boolean> = ref(false)
  const showCloseButton: Ref<Boolean> = ref(false)

  const close = () => {
    showExecutionChain.value = false
    showCloseButton.value = false
    executionChain.value = []
  }

  return {
    executionChain,
    showExecutionChain,
    showCloseButton,
    close
  }
})
