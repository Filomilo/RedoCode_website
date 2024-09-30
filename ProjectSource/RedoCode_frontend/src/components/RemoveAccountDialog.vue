<template>
    <div >
      <div class="change-password-input">
      Confirm password
      <Password
      v-model="password_input"

        class="TextInputConainer AuthPanelElement passwordCont"
        placeholder="password"
        :feedback="false"
        id="previous-password"
        style="margin-bottom: 2rem;"
      />

        <Button
        class="BasicButton"
        label="Remove account"
        @click="removeAccount"
        id="change-password-button"
      />
      </div>





      <Dialog 
      id="confirm_removal_dialog"
      v-model:visible="showConfirm" header="Confirm" position="bottomleft" :modal="true" :draggable="false">
 <div class="confirmButtonContainer"  >
            <Button id="not_remove_account_button"   class="buttonBase" label="NOT remove account" raised  rounded    @click="declineRemoveAccount"/>
        <Button id="do_remove_account_button"   class="buttonBase dangerButton" label="Remove account" severity="danger" raised  rounded @click="confirmRemoveAccount"/>

 </div>


            </Dialog>



        </div>
       
 
    </template>
    
    
    
    <script lang="ts" setup>
    import EndpointAccess from '@/controllers/EndpointsAccess';
import { useActiveUserStore } from '@/stores/ActiveUserStore';
    import { useToastStore } from '@/stores/ToastStore';
import useVuelidate from '@vuelidate/core';
import {
    required,
    email,
    minLength,
    sameAs,
    helpers,
    
  } from '@vuelidate/validators'
    import { json } from 'agent-base';
    import FileUpload from 'primevue/fileupload';
    import type{FileUploadSelectEvent,FileUploadErrorEvent,FileUploadUploadEvent} from 'primevue/fileupload';
    import { Ref, ref,computed,reactive } from 'vue';
    


    const toastStore = useToastStore();
    const activeUserStore=useActiveUserStore();

    const showConfirm=ref(false);
    const password_input=ref("");





    const removeAccount=()=>{
        showConfirm.value=true;
    }

    const confirmRemoveAccount=()=>{
    EndpointAccess.authorized.postRemoveAccount(password_input.value).then(()=>{
        activeUserStore.logout();
        location.reload();
    })
    .catch(ex=>{
        toastStore.showErrorMessage("Error deleting account: "+ ex)
    })
}
const declineRemoveAccount=()=>{
    console.log("declineRemoveAccount")
    showConfirm.value=false;
}

    </script>
    
    
    <style lang="css">
    .change-password-input{
      margin-bottom: 3rem;
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
    }
    .buttonBase{
        width: fit-content;
        border-radius: 1rem;
        background-color: var(--primary);
        border: none;
    }
    
    .dangerButton{
            background-color: red;
    }

    .confirmButtonContainer{
        display: flex;
        width: 40rem;
        justify-content: space-around;
        display: flex;
        }

    </style>