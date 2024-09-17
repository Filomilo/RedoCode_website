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
          placeholder="e-mail"
          id="register-email"
        />
        <div v-if="v$.email.$error" class="errorText">
          {{ v$.email.$errors[0].$message }}
        </div>
        <InputText
          type="text"
          v-model="dataState.nickname"
          class="TextInputConainer AuthPanelElement"
          ca
          placeholder="nick"
          id="register-nick"
        />

        <div v-if="v$.nickname.$error" class="errorText">
          {{ v$.nickname.$errors[0].$message }}
        </div>
        <Password
          v-model="dataState.password"
          class="TextInputConainer AuthPanelElement passwordCont"
          placeholder="password"
          toggleMask
          :feedback="false"
          id="register-password"
        />
        <div v-if="v$.password.$error" class="errorText">
          {{ v$.password.$errors[0].$message }}
        </div>
        <Password
          v-model="dataState.repeatPassword"
          class="TextInputConainer AuthPanelElement passwordCont"
          placeholder="repeat password"
          :feedback="false"
          id="register-repeatpassword"
        />
        <div v-if="v$.repeatPassword.$error" class="errorText">
          {{ v$.repeatPassword.$errors[0].$message }}
        </div>
        <div class="AuthPanelElement boldText">
          Already have have an account?
        </div>
        <router-link to="/" class="AuthPanelElement linkText">
          sign in
        </router-link>
        <Button
          class="BasicButton"
          label="Sign up"
          @click="regster"
          id="register-button"
        />
      </div>
    </div>
    <div class="CommunicatsListContainer"></div>
  </main>
</template>

<script setup lang="ts">
  import InputText from 'primevue/inputtext'
  import { ref, computed, reactive, watch } from 'vue'
  import BasicButton from '@/components/BasicButton.vue'
  import ExcerciseListPromotion from '../components/ExcerciseListPromotion.vue'
  import { useVuelidate } from '@vuelidate/core'
  import {
    required,
    email,
    minLength,
    sameAs,
    helpers,
  } from '@vuelidate/validators'
  import { useActiveUserStore } from '@/stores/ActiveUserStore'
  import { useToastStore } from '@/stores/ToastStore'

  const activeUserStore = useActiveUserStore()
  const toastStore = useToastStore()
  const dataState = reactive({
    email: 'test@test.test',
    nickname: 'testNick',
    password: 'TestPassword+123',
    repeatPassword: 'TestPassword+123',
  })

  const rules = () => {
    return {
      email: { required, email },
      nickname: { required, minLength: minLength(5) },
      password: { required, minLength: minLength(12) },
      repeatPassword: {
        required,
        sameAsPassword: helpers.withMessage(
          'Password do not match',
          sameAs(dataState.password)
        ),
        $autoDirty: true,
      },
    }
  }
  const v$ = useVuelidate(rules, dataState)

  const regster = () => {
    v$.value
      .$validate()
      .then((state: Boolean) => {
        if (state)
          activeUserStore.authController.register(
            dataState.email,
            dataState.nickname,
            dataState.password
          )
        else toastStore.showErrorMessage('Incorrect registration data provided')
      })
      .catch(() => {
        toastStore.showErrorMessage('Incorrect registration data provided')
      })
  }

  // watch(dataState,()=>{
  //   console.log("chnage")
  //   v$.value.$validate;
  // });
</script>

<style lang="scss">
  .errorText {
    color: red;
  }
</style>
