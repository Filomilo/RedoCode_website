import { defineStore } from 'pinia'
import { ref, computed, type Ref, inject } from 'vue'
import { useToastStore } from './ToastStore'
import axios from 'axios'
import { VueCookies } from 'vue-cookies'
import { useApiConnectionStore } from './ApiConnectionStore'
import { useCodeRunnerStore } from './CodeRunnerStore'
import EndpointAccess from '@/controllers/EndpointsAccess'
import USER_TYPE from '@/types/ApiMessages/UserType'
import { stringify } from 'flatted'

export const useActiveUserStore = defineStore('activeUserStore', () => {
  const toastStore = useToastStore()
  const unAuthUser = {
    nickname: '',
    mail: '',
    profilePicture: '',
    type: USER_TYPE.UNAUTHENTICATED,
  }

  //#region Cookies

  const $cookies: VueCookies | undefined = inject('$cookies')

  function deleteCookie() {
    if ($cookies?.isKey('token')) {
      console.log('Temoving token')
      $cookies?.remove('token')
      if ($cookies.isKey('token')) throw 'FAILED to REMOVE cookie'
    }
  }

  function getCookie() {
    return $cookies?.get('token')
  }
  function saveCookieToken() {
    console.log('Save cookie')
    $cookies?.set('token', getToken())
  }

  function doesCookieExist(): boolean {
    if ($cookies == undefined) return false
    return $cookies.isKey('token')
  }

  //#endregion

  //#region account Data managing
  const accountInfo = ref(unAuthUser)
  const _token: Ref<string | null> = ref(null)
  const isLogged = computed(() => {
    if (import.meta.env.MODE === 'development') {
      return true
    }
    console.log()
    return (
      accountInfo.value != undefined &&
      accountInfo.value.type !== USER_TYPE.UNAUTHENTICATED
    )
  })

  async function login(
    email: string,
    pass: string,
    stayLoggedIn: boolean
  ): Promise<boolean> {
    try {
      console.log('email: ' + email)

      const token: string = await EndpointAccess.unauthorized.login(email, pass)
      setToken(token)

      if (stayLoggedIn) [saveCookieToken()]
      toastStore.showSuccessMessage('Successfully logged in')

      return true
    } catch (error) {
      console.error(error)
      toastStore.showErrorMessage("Couldn't Login, " + error)

      return false
    }
  }

  async function validateAuthentication(): Promise<boolean> {
    await updateAccountData()
    console.log('USERS VALIDATED: ' + isLogged.value)
    return isLogged.value
  }

  async function updateAccountData() {
    try {
      const token: string | null = getToken()
      console.log('AuthController loading data for token: ' + token)
      if (token !== null && token !== '') {
        console.log('token loading for: ' + token)
        const response = await EndpointAccess.authorized.getUserInfo()
        console.log('Account info: ' + stringify(response))

        accountInfo.value = response
        // Object.assign(accountInfo, response);
      } else {
        accountInfo.value = unAuthUser
      }
    } catch (ex) {
      console.warn('couldnt get user data ' + ex)
      accountInfo.value = unAuthUser
    }
  }

  //#endregion

  //#region LocalStorage
  function getToken(): string | null {
    return localStorage.getItem('jwtToken')
  }

  function setToken(token: string) {
    console.log('AuthController setToken: ' + token)
    localStorage.setItem('jwtToken', token)
    console.log('session token: ' + localStorage.getItem('jwtToken'))
    _token.value = token
    setupAxios()
    updateAccountData()
  }

  function setupAxios() {
    console.log('AuthController setupAxios')
    const token: string | null = getToken()
    console.log('AuthController setupAxios token: ' + token)
    if (token != null && token.length > 0) {
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    }
  }

  //#endregion

  //#region Login process

  const IsToken = computed(() => {
    return (
      _token.value !== null && _token.value !== undefined && _token.value !== ''
    )
  })

  async function register(email: string, nickname: string, pass: string) {
    const toastStore = useToastStore()

    try {
      console.log('AuthController register: ' + email)
      const token = await EndpointAccess.unauthorized.register(
        email,
        nickname,
        pass
      )
      console.log('AuthController token: ' + token)
      if (token === '') {
        toastStore.showErrorMessage("Couldn't register")
      } else {
        toastStore.showSuccessMessage('successfully registered')
        setToken(token)
      }
    } catch (ex: any) {
      toastStore.showErrorMessage(ex)
      toastStore.showErrorMessage("Couldn't register")
    }
  }

  async function logout() {
    console.log('Logout: ')
    setToken('')
    await localStorage.clear()
    console.log('token: ' + getToken())
    await deleteCookie()
    // updateAccountData()
    //     this._token.value = ''
    //     this.deleteCookie()
    //   }

    //   public login(): string|null{
  }

  function loadFromSession() {
    console.log('attempt to load from Session')
    const token = getToken()
    if (token !== null) {
      setToken(token)
    }
  }

  function loadFromCookies() {
    console.log('attempt to load from Cookies')
    const token = getCookie()
    if (token !== null) {
      setToken(token)
    }
  }

  //#endregion

  //#region init
  if (doesCookieExist()) {
    loadFromCookies()
  } else {
    loadFromSession()
  }

  const apiConnectionStore = useApiConnectionStore()
  const codeRunnerStore = useCodeRunnerStore()
  apiConnectionStore.stompApiConnection.addOnConnectEvent(async () => {
    const token = getToken()
    if (token != null) {
      const strToken: string = token as string
      await console.log(
        'on connected userAuthentication: ' + JSON.stringify(getToken())
      )
      if (strToken.length > 0 && apiConnectionStore.isConnected) {
        try{
        await apiConnectionStore.stompApiSender.authenticationStomp({
          token: strToken,
        })
      }
      catch(ex)
      {
        console.warn("couldnt authrnticated stomp: "+ ex)
      }
      }
      await codeRunnerStore.codeRunnerConnection.updateCodeRunner()
    }
  })

  //#endregion

  return {
    // authController
    isLogged,
    login,
    logout,
    accountInfo,
    register,
    validateAuthentication,
    IsToken,
    updateAccountData,
    // getToken,
  }
})
