<template>
  <main>
    <div
      class="AuthLoginScreenConatiner"
      style="align-items: center; display: flex; justify-content: center"
    >
      <div class="LoginPanelConatiner">
        <InputText
          type="text"
          v-model="inputMail"
          class="TextInputConainer AuthPanelElement"
          ca
          placeholder="e-mail"
        />
        <InputText
          type="text"
          v-model="inputLogin"
          class="TextInputConainer AuthPanelElement"
          ca
          placeholder="nick"
        />
        <Password
          v-model="inputPass"
          class="TextInputConainer AuthPanelElement"
          placeholder="password"
        />
        <Password
          v-model="inputPassRepeat"
          class="TextInputConainer AuthPanelElement"
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
import { ref } from 'vue'
import BasicButton from '@/components/BasicButton.vue'
import ExcerciseListPromotion from '../components/ExcerciseListPromotion.vue'
const inputMail = ref('')
const inputLogin = ref('')
const inputPass = ref('')
const inputPassRepeat = ref('')
</script>
