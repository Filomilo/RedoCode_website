<template>
    <div >
      <div class="change-password-input">
      previous password
      <Password
      v-model="v$.password.$model"

        class="TextInputConainer AuthPanelElement passwordCont"
        placeholder="previous password"
        :feedback="false"
        id="previous-password"
        style="margin-bottom: 2rem;"
      />
      <div v-if="v$.password.$error" class="errorText">
        {{ v$.password.$silentErrors[0].$message }}
      </div>
      new password
      <Password
      v-model="v$.newPassword.$model"
        class="TextInputConainer AuthPanelElement passwordCont"
        placeholder="new password"
        :feedback="false"
        id="previous-password"
      />
      <div v-if="v$.newPassword.$error" class="errorText">
        {{ v$.newPassword.$silentErrors[0].$message }}
      </div>
      repeat new password
      <Password
       v-model="v$.repeatNewPassword.$model"
        class="TextInputConainer AuthPanelElement passwordCont"
        placeholder="repeat new password"
        :feedback="false"
        id="previous-password"
      />

      <div v-if="v$.repeatNewPassword.$error" class="errorText">
          {{ v$.repeatNewPassword.$silentErrors[0].$message }}
        </div>
        <Button
        class="BasicButton"
        label="Change password"
        @click="changePassword"
        id="change-password-button"
      />
      </div>

        </div>
       
 
    </template>
    
    
    
    <script lang="ts" setup>
    import EndpointAccess from '@/controllers/EndpointsAccess';
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
    const emit = defineEmits(['ChangedPassword'])

    



    const dataState = reactive({
      password: '',
      newPassword: '',
    repeatNewPassword: '',
  })

  const rules = () => {
    return {
      password: { required, minLength: minLength(12) },
      newPassword: { required, minLength: minLength(12),
        diffAsPassword: helpers.withMessage(
          'Password cannot be the same as previous',
          (value)=>{return dataState.password!==value}
        ),

       },
      repeatNewPassword: {
        required,
        sameAsPassword: helpers.withMessage(
          'Password do not match',
          sameAs(dataState.newPassword)
        ),
        $autoDirty: true,
      },
    }
  }
  const v$ = useVuelidate(rules, dataState)



    const changePassword=()=>{
      v$.value
      .$validate()
      .then((state: Boolean) => {
        if (state)
       {
        EndpointAccess.authorized.postChangePassword(dataState.password,dataState.newPassword)
        .then(()=>{
          toastStore.showSuccessMessage("Successfully changed password")
          emit('ChangedPassword')
        })
        .catch(ex=>{
          toastStore.showErrorMessage("Failed to change password: "+ ex);
        })
       }
        else toastStore.showErrorMessage('Incorrect data provided')
      })
      .catch(() => {
        toastStore.showErrorMessage('Incorrect data')
      })
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
.errorText{
  color: red;
}

    
    </style>