<template>
  <div
    class="AuthLoginScreenConatiner"
    style="align-items: center; display: flex; justify-content: center"
  >
    <div class="LoginPanelConatiner">
      <div class="AuthPanelElement boldText centered-text">
        your start coding you need to connect to a code runner, this can be
        changed latert
      </div>
      <Dropdown
        v-model="chosenLangague"
        :options="
          languageChoices.filter(x => props.languageChoices.includes(x))
        "
        placeholder="Select programming langauge"
        class="dropDown"
        style="height: 3rem"
        id="coderunner-dropdown"
        optionLabel="label"
        optionValue="value"
      />
      <Button
        class="BasicButton"
        label="Connect"
        @click="onConnectButton"
        id="connect-button"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
  import LanguageDropdown from './LanguageDropdown.vue'
  import { computed, Ref, ref, ComputedRef } from 'vue'
  import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
  import { useApiConnectionStore } from '@/stores/ApiConnectionStore'
  import { languageDropDownType } from '@/types/CodeRunnerTypes'
  import codeRunnerType from '@/types/CodeRunnerTypes'
  // import { languageChoices } from '@/config/Data'
  const props = defineProps({
    languageChoices: { type: Array as () => codeRunnerType[], required: true },
  })

  const chosenLangague: Ref<codeRunnerType> = ref(codeRunnerType.UNIDENTIFIED)
  const codeRunnerStore = useCodeRunnerStore()
  // const ApiConnectionStore = useApiConnectionStore()
  // const laguageDropDown: ComputedRef<languageDropDownType[]> = computed(() => {
  //   return languageChoices.filter(element =>
  //     languageChoices.some(choice => choice.value === element.value)
  //   )
  // })

  const connectToCodeRunner = async (codeRunner: codeRunnerType) => {
    codeRunnerStore.codeRunnerConnection.requestCodeRunner(
      chosenLangague.value
    )
    // codeRunnerStore.increment()
    console.log('Json log: ' + JSON.stringify(codeRunnerStore))
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
