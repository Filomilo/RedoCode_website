<template>
  <main>
    <div class="AuthLoginScreenConatiner" v-if="!activeUserStore.isLogged">
      <div class="CommunicatPanel">
        <div class="CommunicatContainer">
          <label class="CommunicatTitle">Join us</label>
          <label class="CommunicatDesc">
            create acount to have better priority in container queue and to create your own
            excerscies
          </label>
        </div>
      </div>
      <div class="LoginPanelConatiner">
        <InputText
          type="text"
          v-model="inputLogin"
          class="TextInputConainer AuthPanelElement"
          ca
          placeholder="nick/email"
        />
        <Password
          v-model="inputPass"
          class="TextInputConainer AuthPanelElement passwordCont"
          placeholder="password"
          :feedback="false"
        />
        <div class="AuthPanelElement boldText">Don't have an account?</div>
        <router-link to="/Register" class="AuthPanelElement linkText"> sign up </router-link>
        <Button class="BasicButton" label="Sign in" @click="onSginInButton" />
      </div>
    </div>
    <div class="CommunicatsListContainer">
      <div class="CommunicatInfoContainer">
        <div class="CommunicatImage">
          <Image :src="FrontImage1" alt="info iamge 1" :max-width="'100%'" :max-height="'100%'" />
        </div>

        <div class="CommunicatTextContainer">
          <div class="CommunicatTextTitle">Practice</div>
          <div class="CommunicatText">
            Practice your programming skills anywhere using our container runner system
          </div>
        </div>
      </div>

      <div class="CommunicatInfoContainer">
        <div class="CommunicatTextContainer">
          <div class="CommunicatTextTitle">Excercises</div>
          <div class="CommunicatText">Get acces to alrge databse of programinng excersices</div>
        </div>
        <div class="CommunicatImage">
          <Image :src="FrontImage2" alt="info iamge 1" :max-width="'100%'" :max-height="'100%'" />
        </div>
      </div>

      <div class="CommunicatInfoContainer">
        <div class="CommunicatImage">
          <Image :src="FrontImage3" alt="info iamge 1" :max-width="'100%'" :max-height="'100%'" />
        </div>
        <div class="CommunicatTextContainer">
          <div class="CommunicatTextTitle">Statistics</div>
          <div class="CommunicatText">Get acces to your statistic and monitor your progress</div>
        </div>
      </div>
    </div>
    <ExcerciseListPromotion></ExcerciseListPromotion>
  </main>
</template>

<script setup lang="ts">
import InputText from 'primevue/inputtext'
import { ref } from 'vue'
import BasicButton from '@/components/BasicButton.vue'
import ExcerciseListPromotion from '../components/ExcerciseListPromotion.vue'
import FrontImage1 from '@/assets/Images/fronImage1.png'
import FrontImage2 from '@/assets/Images/fronImage2.png'
import FrontImage3 from '@/assets/Images/fronImage3.png'
import { useActiveUserStore } from '../stores/ActiveUserStore'
import { useToast } from "primevue/usetoast";
const toast = useToast();
const activeUserStore = useActiveUserStore()
const inputLogin = ref('')
const inputPass = ref('')

const onSginInButton = () => {
  activeUserStore.login(inputLogin.value,inputPass.value)
}
</script>

<style>

.passwordCont{
  background-color: red;
}
.passwordCont .p-inputtext{
  background-color: red;
}

</style>