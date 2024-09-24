import { defineStore } from 'pinia'
import { ref } from 'vue'
import { useToastStore } from './ToastStore'
import StompApiConnection from '@/controllers/Stomp/StompApiConnection'
import StompApiSender from '@/controllers/Stomp/StompApiSender'
import StompApiSubscriptionsController from '@/controllers/Stomp/StompApiSubscriptionController'
import StompApiSubscriptionController from '@/controllers/Stomp/StompApiSubscriptionController'

export const useApiConnectionStore = defineStore('apiConnectionStore', () => {
  const isConnected = ref(false)
  if (import.meta.env.MODE === 'development') {
    isConnected.value = true
  }
  const toastStore = useToastStore()
  const stompApiConnection: StompApiConnection = new StompApiConnection(
    'ws://localhost:8080/public/web-socket',
    () => {
      toastStore.showProcessingMessage('attempting connecting to server')
    },
    () => {
      toastStore.showSuccessMessage('successfully connected')

      isConnected.value = true
      console.log('on connected settings value: ' + isConnected.value)
    },
    (message: string) => {
      toastStore.showErrorMessage(message)
    },
    () => {
      isConnected.value = false
      console.log('on discontented settings value: ' + isConnected.value)
    }
  )

  const stompApiSender: StompApiSender = new StompApiSender(stompApiConnection)

  const stompApiSubscriptionController: StompApiSubscriptionsController =
    new StompApiSubscriptionController(stompApiConnection)

  return {
    stompApiSender,
    stompApiSubscriptionController,
    stompApiConnection,
    isConnected,
  }
})
