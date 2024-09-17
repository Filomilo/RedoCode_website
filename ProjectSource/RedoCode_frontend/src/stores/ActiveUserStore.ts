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
import AuthController from '@/controllers/AuthController'

export const useActiveUserStore = defineStore('activeUserStore', () => {
  const toastStore = useToastStore()


  const authController: AuthController= new AuthController(); 



  // const attemptToLoginThroughCookie = () => {
  //   console.log(JSON.stringify($cookies))
  //   if ($cookies?.isKey('token')) {
  //     const token = $cookies.get('token')
  //     console.log('set token: ' + JSON.stringify(token))
  //     _token.value = token
  //     if (validateToken()) {
  //       setIsLogged(true)
  //       const apiConnectionStore = useApiConnectionStore()
  //     } else {
  //       _token.value = ''
  //     }
  //   }
  // }
  // attemptToLoginThroughCookie()






 


  return {
    authController
    // isLogged,
    // login,
    // logout,
    // accountInfo,
    // register,
    // validateToken,
    // getToken,
  }
})
