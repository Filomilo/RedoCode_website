<template>
  <div
    class="AuthLoginScreenConatiner"
    style="align-items: center; display: flex; justify-content: center"
  >
    <div class="LoginPanelConatiner">
      <div class="AuthPanelElement boldText centered-text">
        your start coding you need to connect to a code runner, this can be changed latert
      </div>
      <Dropdown
        v-model="chosenLangague"
        :options="languageChoices"
        placeholder="Select programming langauge"
        class="dropDown"
        style="height: 3rem"
      />
      <Button class="BasicButton" label="Connect" @click="onConnectButton" />
    </div>
  </div>
</template>

<script setup lang="ts">
import LanguageDropdown from './LanguageDropdown.vue'
import { computed, ref } from 'vue'
import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
import { useApiConnectionStore } from '@/stores/ApiConnectionStore'

const props = defineProps({
  languageChoices: { type: Array as () => string[], required: true }
})

const chosenLangague = ref('')
const codeRunnerStore = useCodeRunnerStore()
const ApiConnectionStore = useApiConnectionStore()
const connectToCodeRunner = async (codeRunner: string) => {
  // ApiConnectionStore.codeRunnerConnectionControler.requestCodeRunner(chosenLangague.value)
  // codeRunnerStore.increment()
  console.log('Json log: ' + JSON.stringify(codeRunnerStore))
  ApiConnectionStore.sendHealthCheck()
}
const onConnectButton = () => {
  console.log('connect ' + JSON.stringify(chosenLangague.value))
  connectToCodeRunner(chosenLangague.value)
}
</script>

<style>
.centered-text {
  text-align: center;
}
</style>
