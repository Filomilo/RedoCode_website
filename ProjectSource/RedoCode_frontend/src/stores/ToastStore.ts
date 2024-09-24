import { defineStore } from 'pinia'
import { useToast } from 'primevue/usetoast'

export const useToastStore = defineStore('toastStore', () => {
  const toast = useToast()

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
      life: 2000,
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

  return {
    shotWrongLogin,
    shotCorrectLogin,
    featureNotImplemented,
    showSuccessMessage,
    showErrorMessage,
    showProcessingMessage: showProcessingMessage,
  }
})
