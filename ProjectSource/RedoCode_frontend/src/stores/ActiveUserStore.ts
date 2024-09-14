import { defineStore } from 'pinia'
import { ref, computed, type Ref, inject, watch } from 'vue'
import { useToastStore } from './ToastStore'
import axios from 'axios'
import RegisterRequest from '@/types/ApiMesseages/Authentication/RegisterRequest'
import router from '@/router'
import AuthenticationRequest from '@/types/ApiMesseages/Authentication/AuthenticationRequest'
import { VueCookies } from 'vue-cookies'
import { useApiConnectionStore } from './ApiConnectionStore'
import { useCodeRunnerStore } from './CodeRunnerStore'
import AccountInfo from '@/types/ApiMesseages/AccountInfo'
import EndpointAcces from '@/controllers/EndpointsAcces'
import USER_TYPE from '@/types/ApiMesseages/UserType'

export const useActiveUserStore = defineStore('activeUserStore', () => {
  const toastStore = useToastStore()
  const isLogged = ref(false)
  const _token: Ref<string> = ref('')
  const isAwaitingAuthentication: Ref<boolean> = ref(false)
  const $cookies = inject<VueCookies>('$cookies')
  const accountInfo: Ref<AccountInfo> = ref({
    nickname: "",
    mail: "",
    profilePicture: "",
    type: USER_TYPE.UNAUTHENTICATED
  });


  watch(_token,()=>{
    console.log("token change: "+ JSON.stringify( _token.value))
    if(_token.value!=="")
    {

      EndpointAcces.authorized.getUserInfo(_token.value).then((response: AccountInfo)=>{
        console.log("Account info: "+ JSON.stringify(response))
        accountInfo.value=response;
      })
    }
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

    try{


      const token:string= await EndpointAcces.unauthorized.login(email,pass);
      _token.value = token
      isLogged.value = true
      if (stayLoggedIn) {
        saveCookie()
      }
      router.push({ path: '/Home', replace: true })
      toastStore.showSuccessMessage("Succesfully logged in");
      }
      catch(error){
        console.error(error)
            toastStore.showErrorMessage(
              "Couldn't Login, "+error)
            }
          }

  const logout = () => {
    isLogged.value = false
    accountInfo.value = {
        nickname: "",
        mail: "",
        profilePicture: "",
        type: USER_TYPE.UNAUTHENTICATED
    }
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
    accountInfo,
    register,
    validateToken,
    getToken,
  }
})
