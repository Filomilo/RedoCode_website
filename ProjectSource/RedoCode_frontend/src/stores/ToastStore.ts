import { defineStore } from 'pinia'
import { useToast } from 'primevue/usetoast'
import { useApiConnectionStore } from './ApiConnectionStore'
import StompApiSubscriptionsController from '@/controllers/Stomp/StompApiSubscriptionController'

import MessageNotification, { messageType } from '@/types/ApiMessages/MessageNotification'
import StompApiConnection from '@/controllers/Stomp/StompApiConnection'

export const useToastStore = defineStore('toastStore', () => {
  const toast = useToast()
  const apiConnectionStore = useApiConnectionStore();

  const stompApiSubscriptionController: StompApiSubscriptionsController=apiConnectionStore.stompApiSubscriptionController as StompApiSubscriptionsController; 
console.log("apiConnectionStore.isConnected: "+JSON.stringify( apiConnectionStore.isConnected))
  const shotWrongLogin = () => {
    toast.add({
      severity: 'error',
      summary: 'error',
      detail: 'Wrong e-mail or password',
      life: 2000,
    })
  }
  const shotCorrectLogin = () => {
    toast.add({
      severity: 'success',
      summary: 'signed in',
      detail: 'signed in',
      life: 200000,
    })
  }

  const featureNotImplemented = (detail: string = '') => {
    toast.add({
      severity: 'warn',
      summary: 'unsupported',
      detail: 'feature not implemented: ' + detail,
      life: 1000,
    })
  }

  const showSuccessMessage = (message: string) => {
    toast.add({
      severity: 'success',
      summary: 'Success',
      detail: message,
      life: 2000,
    })
  }
  const showErrorMessage = (message: string) => {
    toast.add({
      severity: 'error',
      summary: 'Error',
      detail: message,
      life: 2000,
    })
  }
  const showProcessingMessage = (message: string) => {
    toast.add({
      severity: 'info',
      summary: 'Processing',
      detail: message,
      life: 2000,
    })
  }






  //#region Server notifacations



  const showServerInfo = (message: string) => {
    console.log("showServerInfo: "+JSON.stringify(message))
    toast.add({
      severity: 'info',
      summary: 'Server info',
      detail: message,
      life: 6000,
    })
  }
  const showServerWarn = (message: string) => {
    console.log("showServerWarn: "+JSON.stringify(message))
    toast.add({
      severity: 'warn',
      summary: 'Server warrning',
      detail: message,
      life: 6000,
    })
  }
  const showServerError = (message: string) => {
    console.log("showServerError: "+JSON.stringify(message))
    toast.add({
      severity: 'error',
      summary: 'Server error',
      detail: message,
      life: 6000,
    })
  }

  //#endregion






  return {
    shotWrongLogin,
    shotCorrectLogin,
    featureNotImplemented,
    showSuccessMessage,
    showErrorMessage,
    showProcessingMessage: showProcessingMessage,
    showServerInfo, 
    showServerWarn,
    showServerError
  }
})
