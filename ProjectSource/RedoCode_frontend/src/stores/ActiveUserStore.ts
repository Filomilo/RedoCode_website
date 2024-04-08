import { defineStore } from 'pinia'
import { ref } from 'vue'
export const useActiveUserStore = defineStore('activeUserStore', () => {
  const isLogged = ref(false)

  const setIsLogged = (state: boolean) => {
    isLogged.value = state
  }

  return { isLogged, setIsLogged }
})
