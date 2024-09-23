import { defineStore } from 'pinia'
import { ref, computed, type Ref, inject } from 'vue'
import { useToastStore } from './ToastStore'
import axios from 'axios'
import RegisterRequest from '@/types/ApiMessages/Authentication/RegisterRequest'
import router from '@/router'
import AuthenticationRequest from '@/types/ApiMessages/Authentication/AuthenticationRequest'
import { VueCookies } from 'vue-cookies'
import { useApiConnectionStore } from './ApiConnectionStore'
import { useCodeRunnerStore } from './CodeRunnerStore'

export const useGlobalStateStore = defineStore('GlobalStateStore', () => {
  const isLocked = ref(false)

  const loadingMessage = ref('')

  const showLoadingScreen = (loadingmessage: string) => {
    loadingMessage.value = loadingmessage
    isLocked.value = true
  }

  const hideLoadingScreen = () => {
    isLocked.value = false
    loadingMessage.value = ''
  }

  return {
    isLocked,
    showLoadingScreen,
    hideLoadingScreen,
    loadingMessage,
  }
})
