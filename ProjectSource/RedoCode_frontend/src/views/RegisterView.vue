<template>
  <main>
    <div
      class="AuthLoginScreenConatiner"
      style="align-items: center; display: flex; justify-content: center"
    >
      <div class="LoginPanelConatiner">
        <InputText
          type="text"

          v-model="v$.email.$model"
          class="TextInputConainer AuthPanelElement"
          ca
          placeholder="e-mail"
        />
        <div v-if="v$.email.$error">
  {{ v$.email.$errors[0].$message }}
</div>
        <!-- <div :v-if="v$.email.$silentErrors.length>0"> -->
      
            <!-- {{ validation.email.$silentErrors[0].$message }} -->
        <!-- </div> -->
        <InputText
          type="text"
          v-model="dataState.nickname"
          class="TextInputConainer AuthPanelElement"
          ca
          placeholder="nick"
        />
        <Password
          v-model="dataState.password"
          class="TextInputConainer AuthPanelElement passwordCont"
          placeholder="password"
          toggleMask 
        />
        <Password
          v-model="dataState.repeatPassword"
          class="TextInputConainer AuthPanelElement passwordCont"
          placeholder="repeat password"
        />
    
        <div class="AuthPanelElement boldText">Already have have an account?</div>
        <router-link to="/" class="AuthPanelElement linkText"> sign in </router-link>
        <Button class="BasicButton" label="Sign up" />
      </div>
    </div>
    <div class="CommunicatsListContainer"></div>
  </main>
</template>

<script setup lang="ts">
import InputText from 'primevue/inputtext'
import { ref,computed,reactive,watch } from 'vue'
import BasicButton from '@/components/BasicButton.vue'
import ExcerciseListPromotion from '../components/ExcerciseListPromotion.vue'
import { useVuelidate } from '@vuelidate/core'
import { required, email } from '@vuelidate/validators'

const dataState=reactive({
  email: "",
  nickname: "",
  password: "",
  repeatPassword: ""
});

const rules=()=>{return{
  email:{ required,email },
  nickname: "",
  password: "",
  repeatPassword: ""
}};
const v$ = useVuelidate(rules, dataState)

// watch(dataState,()=>{
//   console.log("chnage")
//   v$.value.$validate;
// });

</script>
