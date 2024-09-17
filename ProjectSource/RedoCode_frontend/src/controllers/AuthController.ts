import { ref, computed, type Ref, inject, watch } from 'vue'
import EndpointAcces from './EndpointsAcces';
import AccountInfo from '@/types/ApiMesseages/AccountInfo';
import USER_TYPE from '@/types/ApiMesseages/UserType';
import { useApiConnectionStore } from '@/stores/ApiConnectionStore';
import { useCodeRunnerStore } from '@/stores/CodeRunnerStore';
import { VueCookies } from 'vue-cookies';
import { useToastStore } from '@/stores/ToastStore';
import axios from 'axios';
export default  class AuthController{





constructor()
{

    // this.$cookies = inject<VueCookies>('$cookies')
    //   let apiConnectionStore = useApiConnectionStore()
    //   let codeRunnerStore = useCodeRunnerStore()
    //   apiConnectionStore.stompApiConnection.addOnConnectEvent(() => {
    //     console.log('on connected userAuhtenticaton: ' + JSON.stringify(this.getToken()))
    //     if (this.getToken()!=null && (this.getToken() as string).length > 0) {
    //       apiConnectionStore.stompApiSender.authenticationStomp({
    //         token: this.getToken() as string,
    //       })
    //     }
    //     codeRunnerStore.codeRunnerConnection.updateCodeRunner()
    //   })
}


//#region Cookies

private $cookies: VueCookies|undefined;

private deleteCookie  () {
    if (this.$cookies?.isKey('token')) {
      this.$cookies?.remove('token')
    }
  }
  private saveCookieToken () {
    console.log('Save cookie')
    this.$cookies?.set('token', this.getToken())
  }

  private doesCookieExist(): boolean
  {
    if(this.$cookies==undefined)
        return false;
    return this.$cookies.isKey('token');
  }

//#endregion

//#region account Data managing
public accountInfo: Ref<AccountInfo> = ref({
    nickname: "",
    mail: "",
    profilePicture: "",
    type: USER_TYPE.UNAUTHENTICATED
  });

  public isLogged = computed(()=>{
    if (import.meta.env.MODE === 'development') {
        return true;
      }
    
    return this.accountInfo!=undefined
    && this.accountInfo.value!=undefined
    && this.accountInfo.value.type!=USER_TYPE.UNAUTHENTICATED
})

  public async login(email: string, pass: string, stayLoggedIn: boolean):Promise<boolean> {
return false;
    try{
        console.log("email: "+ email)

    //   const token:string= await EndpointAcces.unauthorized.login(email,pass);
    //   _token.value = token
    //   isLogged.value = true
    //   if (stayLoggedIn) {
    //     saveCookie()
    //   }
    //   router.push({ path: '/Home', replace: true })
    //   toastStore.showSuccessMessage("Succesfully logged in");
    //   }
    //   catch(error){
    //     console.error(error)
    //         toastStore.showErrorMessage(
    //           "Couldn't Login, "+error)
    //         }
          }
          catch(ex){
            console.error(ex)
            return false
          }
        }


public validateAuthentication(): boolean
{
return false;
}

private updateAccountData()
{
  
    const token:string|null=this.getToken();
    console.log("AuthControlelr loading data for token: "+token)
    if(token!==null)
    {
        EndpointAcces.authorized.getUserInfo(token).then((response: AccountInfo)=>{
            console.log("Account info: "+ JSON.stringify(response))
            this.accountInfo.value=response;
    });
}
else{
        this.accountInfo.value={
            nickname: "",
            mail: "",
            profilePicture: "",
            type: USER_TYPE.UNAUTHENTICATED
          }
    }

}


//#endregion




//#region WebSession
private getToken():string|null {
    return "";
    // return sessionStorage.getItem('jwtToken');
  }

  private setToken(token: string){
    console.log("AuthControlelr setToken: "+token)
    sessionStorage.setItem('jwtToken', token);
    console.log("sesssino token: "+  sessionStorage.getItem('jwtToken') )
  
    this.setupAxios();
    this.updateAccountData();

  }
  
private setupAxios()
{
  console.log("AuthController setupAxios")
  const token: string| null=this.getToken();
  console.log("AuthController setupAxios token: "+ token)
if(token!=null && token.length>0)
{

  axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
}
}

//#endregion


//#region Login process

public async register(email: string, nickname: string, pass: string){
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
      this.setToken(token)
    }
  }
catch(ex:any){
  toastStore.showErrorMessage(ex)
}
}

public logout() {
  console.log("Logout: ")
  
//     this._token.value = ''
//     this.deleteCookie()
//   }

//   public login(): string|null{

}


private validateToken = (): boolean => {
    return false;
//     if (import.meta.env.MODE === 'development') {
//       return true
//     }
//     if (this._token.value === '') return false
//     return true
//   }

// private initLogin()
// {
//     if(this.doesCookieExist())
//     {

//     }
//     else if()
}


//#endregion



    

}