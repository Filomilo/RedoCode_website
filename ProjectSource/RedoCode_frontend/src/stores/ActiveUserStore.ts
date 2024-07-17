import { defineStore } from 'pinia'
import { ref, computed, type Ref } from 'vue'
import { useToastStore } from './ToastStore'
import axios from 'axios'
import RegisterRequest from '@/types/ApiMesseages/Authentication/RegisterRequest'
import router from '@/router'
import AuthenticationRequest from '@/types/ApiMesseages/Authentication/AuthenticationRequest'
export const useActiveUserStore = defineStore('activeUserStore', () => {
  const toastStore = useToastStore()
  const isLogged = ref(false)
  const nick = ref('')
  const token = ref('')
  const isAwaitingAuthentication: Ref<boolean> = ref(false)

  const acoountInfo = computed(() => {
    return { nick: nick }
  })

  const setIsLogged = (state: boolean) => {
    isLogged.value = state
  }

  const login = async (email: string, pass: string) => {
    const request: AuthenticationRequest = {
      password: pass,
      email: email
    }
    axios
      .post('/public/auth/login', request)
      .then((response) => {
        console.log('Response: ' + JSON.stringify(response))
        if (response.status == 200) {
          toastStore.showSuccessMessage('Succesfully logged in')
          token.value = response.data.token
          isLogged.value = true
          router.push({ path: '/Home', replace: true })
        } else {
          console.log('test')
          toastStore.showErrorMessage("Couldn't Login, please check email nad password")
        }
      })
      .catch((error) => {
        console.error(error)
        if (error.response) {
          console.error('Error response:', error.response)
          console.error('Status:', error.response.status)
          console.error('Data:', error.response.data)
          console.error('Headers:', error.response.headers)

          if (error.response.status != 200) {
            toastStore.showErrorMessage("Couldn't Login, please check email nad password")
          }
        }
      })
      .finally(() => {
        //  console.log("axios finshed")
      })
  }

  const logout = () => {
    isLogged.value = false
    nick.value = ''
    token.value = ''
  }

  const register = async (email: string, nickname: string, pass: string) => {
    const request: RegisterRequest = {
      nickname: nickname,
      password: pass,
      email: email
    }
    axios
      .post('/public/auth/register', request)
      .then((response) => {
        console.log('Response: ' + JSON.stringify(response))
        if (response.status == 200) {
          toastStore.showSuccessMessage('Succesfully registered user')
          token.value = response.data.token
          isLogged.value = true
          router.push({ path: '/Home', replace: true })
        } else {
          toastStore.showErrorMessage("Couldn't register user")
        }
      })
      .catch((error) => {
        if (error.response) {
          if (error.response.status != 200) {
            toastStore.showErrorMessage("Couldn't register user")
          }
        }
      })
      .finally(() => {
        //  console.log("axios finshed")
      })
  }

  return { isLogged, login, logout, acoountInfo, register }
})
