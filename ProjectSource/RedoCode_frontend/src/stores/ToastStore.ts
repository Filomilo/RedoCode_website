import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { useToast } from 'primevue/usetoast'

export const useToastStore = defineStore('toastStore', () => {
  const toast = useToast()

  const shotWrongLogin = () => {
    toast.add({
      severity: 'error',
      summary: 'error',
      detail: 'Wrong e-mail or passowrd',
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

  const featureNotImplemented = (deatil: string = '') => {
    toast.add({
      severity: 'warn',
      summary: 'unsuported',
      detail: 'feature not implemented: ' + deatil,
      life: 7000,
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
  const showProccessingMessage = (message: string) => {
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
    showProccessingMessage,
  }
})
