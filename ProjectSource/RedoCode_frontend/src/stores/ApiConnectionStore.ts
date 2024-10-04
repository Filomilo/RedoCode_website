import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import { useToastStore } from './ToastStore'
import StompApiConnection from '@/controllers/Stomp/StompApiConnection'
import StompApiSender from '@/controllers/Stomp/StompApiSender'
import StompApiSubscriptionsController from '@/controllers/Stomp/StompApiSubscriptionController'
import StompApiSubscriptionController from '@/controllers/Stomp/StompApiSubscriptionController'
import MessageNotification, {
  messageType,
} from '@/types/ApiMessages/MessageNotification'

export const useApiConnectionStore = defineStore('apiConnectionStore', () => {
  const _isConnected = ref(false)
  const isConnected = computed(() => {
    if (import.meta.env.MODE === 'development') {
      return true
    }
    return _isConnected.value
  })

  if (import.meta.env.MODE === 'development') {
    _isConnected.value = true
  }
  const toastStore = useToastStore()

  const stompApiConnection: StompApiConnection = new StompApiConnection(
    'ws://localhost:8080/public/web-socket',
    () => {
      toastStore.showProcessingMessage('attempting connecting to server')
    },
    () => {
      toastStore.showSuccessMessage('successfully connected')

      _isConnected.value = true
      console.log('on connected settings value: ' + isConnected.value)
    },
    (message: string) => {
      toastStore.showErrorMessage(message)
      _isConnected.value = false
    },
    () => {
      _isConnected.value = false
      console.log('on discontented settings value: ' + isConnected.value)
    }
  )

  const stompApiSender: StompApiSender = new StompApiSender(stompApiConnection)

  const stompApiSubscriptionController: StompApiSubscriptionsController =
    new StompApiSubscriptionController(stompApiConnection)

  stompApiSubscriptionController.addServerNotificationsSubscription(
    (notif: MessageNotification) => {
      console.log(
        'stompApiSubscriptionController toast: ' + JSON.stringify(notif)
      )
      switch (notif.type) {
        case messageType.ERROR:
          toastStore.showServerError(notif.message)
          break
        case messageType.INFO:
          toastStore.showServerInfo(notif.message)
          break
        case messageType.WARNING:
          toastStore.showServerWarn(notif.message)
          break
      }
    }
  )

  return {
    stompApiSender,
    stompApiSubscriptionController,
    stompApiConnection,
    isConnected,
  }
})
