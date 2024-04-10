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
        :options="langaugesOptions"
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
import { languageChoices } from '../config/Data'
import { computed, ref } from 'vue'
import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
const chosenLangague = ref('')
const codeRunnerStore = useCodeRunnerStore()
const langaugesOptions=computed (()=> codeRunnerStore.exerciseData.title===''?languageChoices:codeRunnerStore.exerciseData.availbleCodeRunners)
const connectToCodeRunner = async (codeRunner: string) => {
  codeRunnerStore.requestCodeRunner(chosenLangague.value)
  // codeRunnerStore.increment()
  console.log('Json log: ' + JSON.stringify(codeRunnerStore))
}
const onConnectButton = () => {
  console.log('connect ' + JSON.stringify(chosenLangague.value.name))
  connectToCodeRunner(chosenLangague.value.name)
}
</script>

<style>
.centered-text {
  text-align: center;
}
</style>
