import { ref, computed, type Ref, inject, watch } from 'vue'
import EndpointAcces from './EndpointsAcces';
import AccountInfo from '@/types/ApiMesseages/AccountInfo';
import USER_TYPE from '@/types/ApiMesseages/UserType';
import { useApiConnectionStore } from '@/stores/ApiConnectionStore';
import { useCodeRunnerStore } from '@/stores/CodeRunnerStore';
import { VueCookies } from 'vue-cookies';

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


private updateAccountData()
{
//     const token:string|null=this.getToken();

//     if(token!==null)
//     {
//         EndpointAcces.authorized.getUserInfo(token).then((response: AccountInfo)=>{
//             console.log("Account info: "+ JSON.stringify(response))
//             this.accountInfo.value=response;
//     });
// }
// else{
//         this.accountInfo.value={
//             nickname: "",
//             mail: "",
//             profilePicture: "",
//             type: USER_TYPE.UNAUTHENTICATED
//           }
    // }

}


//#endregion




//#region WebSession
private getToken():string|null {
    return "";
    // return sessionStorage.getItem('jwtToken');
  }

  private setToken(token: string){
    // sessionStorage.setItem('jwtToken', token);

}
  


//#endregion


//#region Login process

public register(email: string, nickname: string, pass: string){
    // const token=EndpointAcces.unauthorized.register(email, nickname, pass)
}

public logout() {
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