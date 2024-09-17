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
  const unAuthUser={
    nickname: "",
    mail: "",
    profilePicture: "",
    type: USER_TYPE.UNAUTHENTICATED
  };
  
  


  //#region Cookies
  
  const $cookies: VueCookies|undefined= inject('$cookies');
  
  function deleteCookie  () {
      if ($cookies?.isKey('token')) {
        $cookies?.remove('token')
      }
    }


    function getCookie  () {
     return  $cookies?.get('token');
    }
    function saveCookieToken () {
      console.log('Save cookie')
      $cookies?.set('token', getToken())
    }
  
    function doesCookieExist(): boolean
    {
      if($cookies==undefined)
          return false;
      return $cookies.isKey('token');
    }
  
  //#endregion
  
  //#region account Data managing
  const accountInfo = ref(unAuthUser);
  
    const isLogged = computed(()=>{
      if (import.meta.env.MODE === 'development') {
          return true;
        }
      console.log()
      return accountInfo.value!=undefined
      && accountInfo.value!=undefined
      && accountInfo.value.type!==USER_TYPE.UNAUTHENTICATED
  })
  
  async function login(email: string, pass: string, stayLoggedIn: boolean): Promise<boolean> {
    try {
      console.log("email: " + email);
      
      const token: string = await EndpointAcces.unauthorized.login(email, pass);
      setToken(token);
      if(stayLoggedIn)
      [
    saveCookieToken()
  ]
      // _token.value = token
      // isLogged.value = true
      // if (stayLoggedIn) {
      //   saveCookie();
      // }
      // router.push({ path: '/Home', replace: true });
      // toastStore.showSuccessMessage("Successfully logged in");
  
      return true;
    } catch (error) {
      console.error(error);
      // toastStore.showErrorMessage("Couldn't Login, " + error);
  
      return false;
    }
  }
  
  
  
  async function  validateAuthentication(): Promise <boolean>
  {
    await updateAccountData();
  return isLogged.value;
  }
  
 async function  updateAccountData()
  {
    
      const token:string|null=getToken();
      console.log("AuthControlelr loading data for token: "+token)
      if(token!==null && token!=='')
      {
        console.log("token loading for: "+ token)
         const response=await EndpointAcces.authorized.getUserInfo();
        console.log("Account info: "+ JSON.stringify(response))
        console.log(JSON.stringify(accountInfo))
         accountInfo.value=response;
        // Object.assign(accountInfo, response);
         console.log(JSON.stringify(accountInfo))
      }
  else{
    console.log(JSON.stringify(accountInfo))
    accountInfo.value=unAuthUser;
          console.log(JSON.stringify(accountInfo))
      }
  
  }
  
  
  //#endregion
  
  
  
  
  //#region LocalStorage
  function getToken():string|null {
      return localStorage.getItem('jwtToken');
    }
  
    function setToken(token: string){
      console.log("AuthControlelr setToken: "+token)
      localStorage.setItem('jwtToken', token);
      console.log("sesssino token: "+  localStorage.getItem('jwtToken') )
    
      setupAxios();
      updateAccountData();
  
    }
    
  function setupAxios()
  {
    console.log("AuthController setupAxios")
    const token: string| null=getToken();
    console.log("AuthController setupAxios token: "+ token)
  if(token!=null && token.length>0)
  {
  
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
  }
  }
  
  //#endregion
  
  
  //#region Login process
  
 async function  register(email: string, nickname: string, pass: string){
    const toastStore=useToastStore();
  
    try{
      console.log("AuthController register: "+email)
      const token=await EndpointAcces.unauthorized.register(email, nickname, pass)
      console.log("AuthController token: "+token)
      if(token===""){
        toastStore.showErrorMessage("Couldn't register")
      }
      else{
        toastStore.showSuccessMessage("succesfully registered")
        setToken(token)
      }
    }
  catch(ex:any){
    toastStore.showErrorMessage(ex)
  }
  }
  
  function logout() {
    console.log("Logout: ")
    setToken("")
    localStorage.clear();
    console.log("token: "+getToken())
    updateAccountData()
    deleteCookie();
  
  //     this._token.value = ''
  //     this.deleteCookie()
  //   }
  
  //   public login(): string|null{
  
  }
  

  
  function loadFromSession(){
    const token= getToken()
    if(token!==null)
    {
      setToken(token)
    }
  }


  function loadFromCookies(){
    const token= getCookie()
    if(token!==null)
    {
      setToken(token)
    }
  }
  
  //#endregion
  
  


//#region init
if(doesCookieExist())
{
  loadFromCookies();
}
else{
  loadFromSession();
}


  

//#endregion

 


  return {
    // authController
    isLogged,
    login,
    logout,
    accountInfo,
    register,
    validateAuthentication,
    // getToken,
  }
})
