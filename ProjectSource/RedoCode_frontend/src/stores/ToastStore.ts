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
      life: 2000
    })
  }
  const shotCorrectLogin = () => {
    toast.add({ severity: 'success', summary: 'signed in', detail: 'signed in', life: 2000 })
  }

  const featureNotImplemented = () => {
    toast.add({
      severity: 'warn',
      summary: 'unsuported',
      detail: 'feature not implemented',
      life: 7000
    })
  }

  return {
    shotWrongLogin,
    shotCorrectLogin,
    featureNotImplemented
  }
})
