import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { useToastStore } from './ToastStore'
export const useActiveUserStore = defineStore('activeUserStore', () => {
  const toastStore = useToastStore()
  const isLogged = ref(false)
  const nick = ref('')

  const acoountInfo = computed(() => {
    return { nick: nick }
  })

  const setIsLogged = (state: boolean) => {
    isLogged.value = state
  }

  const login = (nickIn: string, pass: string) => {
    if (nickIn === pass && nickIn.length > 0) {
      isLogged.value = true
      nick.value = nickIn
      toastStore.shotCorrectLogin()
    } else {
      toastStore.shotWrongLogin()
    }
  }

  const logout = () => {
    isLogged.value = false
    nick.value = ''
  }

  return { isLogged, login, logout, acoountInfo }
})
