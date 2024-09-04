import { defineStore } from 'pinia'
import { ref, computed, type Ref, inject } from 'vue'
import { useToastStore } from './ToastStore'
import axios from 'axios'
import RegisterRequest from '@/types/ApiMesseages/Authentication/RegisterRequest'
import router from '@/router'
import AuthenticationRequest from '@/types/ApiMesseages/Authentication/AuthenticationRequest'
import { VueCookies } from 'vue-cookies'
import { useApiConnectionStore } from './ApiConnectionStore'
import { useCodeRunnerStore } from './CodeRunnerStore'

export const useActiveUserStore = defineStore('activeUserStore', () => {
  const toastStore = useToastStore()
  const isLogged = ref(false)
  const nick = ref('')
  const _token: Ref<string> = ref('')
  const isAwaitingAuthentication: Ref<boolean> = ref(false)
  const $cookies = inject<VueCookies>('$cookies')
  const acoountInfo = computed(() => {
    return { nick: nick }
  })

  const validateToken = (): boolean => {
    if (import.meta.env.MODE === 'development') {
      return true
    }
    if (_token.value === '') return false
    return true
  }
  const setIsLogged = (state: boolean) => {
    isLogged.value = state
  }

  const saveCookie = () => {
    console.log('Save cookie')
    $cookies?.set('token', _token.value)
  }
  const deleteCookie = () => {
    if ($cookies?.isKey('token')) {
      $cookies?.remove('token')
    }
  }

  const attemptToLoginThroughCookie = () => {
    console.log(JSON.stringify($cookies))
    if ($cookies?.isKey('token')) {
      const token = $cookies.get('token')
      console.log('set token: ' + JSON.stringify(token))
      _token.value = token
      if (validateToken()) {
        setIsLogged(true)
        const apiConnectionStore = useApiConnectionStore()
      } else {
        _token.value = ''
      }
    }
  }
  attemptToLoginThroughCookie()

  const login = async (email: string, pass: string, stayLoggedIn: boolean) => {
    const request: AuthenticationRequest = {
      password: pass,
      email: email,
    }
    axios
      .post('/public/auth/login', request)
      .then(response => {
        console.log('Response: ' + JSON.stringify(response))
        if (response.status == 200) {
          toastStore.showSuccessMessage('Succesfully logged in')
          _token.value = response.data.token
          const apiConnectionStore = useApiConnectionStore()
          isLogged.value = true
          router.push({ path: '/Home', replace: true })
          if (stayLoggedIn) {
            saveCookie()
          }
        } else {
          console.log('test')
          toastStore.showErrorMessage(
            "Couldn't Login, please check email nad password"
          )
        }
      })
      .catch(error => {
        console.error(error)
        if (error.response) {
          console.error('Error response:', error.response)
          console.error('Status:', error.response.status)
          console.error('Data:', error.response.data)
          console.error('Headers:', error.response.headers)

          if (error.response.status != 200) {
            toastStore.showErrorMessage(
              "Couldn't Login, please check email nad password"
            )
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
    _token.value = ''
    deleteCookie()
  }

  const register = async (email: string, nickname: string, pass: string) => {
    const request: RegisterRequest = {
      nickname: nickname,
      password: pass,
      email: email,
    }
    axios
      .post('/public/auth/register', request)
      .then(response => {
        console.log('Response: ' + JSON.stringify(response))
        if (response.status == 200) {
          toastStore.showSuccessMessage('Succesfully registered user')
          _token.value = response.data.token
          isLogged.value = true
          router.push({ path: '/Home', replace: true })
        } else {
          toastStore.showErrorMessage("Couldn't register user")
        }
      })
      .catch(error => {
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

  const getToken = (): string => {
    return _token.value
  }

  if (import.meta.env.MODE === 'development') {
    isLogged.value = true
  }

  const apiConnectionStore = useApiConnectionStore()
  const codeRunnerStore = useCodeRunnerStore()
  apiConnectionStore.stompApiConnection.addOnConnectEvent(() => {
    console.log('on connected userAuhtenticaton: ' + JSON.stringify(getToken()))
    if (getToken().length > 0) {
      apiConnectionStore.stompApiSender.authenticationStomp({
        token: getToken() as string,
      })
    }
    codeRunnerStore.codeRunnerConnection.updateCodeRunner()
  })

  return {
    isLogged,
    login,
    logout,
    acoountInfo,
    register,
    validateToken,
    getToken,
  }
})
