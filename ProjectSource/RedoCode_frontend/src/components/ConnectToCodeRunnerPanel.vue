<template>
  <div
  :key="refreshKey"
    class="AuthLoginScreenConatiner"
    style="align-items: center; display: flex; justify-content: center"
  >
  {{  stringify(ApiConnectionStore.stompApiConnection.isActive) }}

    <div
      class="LoginPanelConatiner"
      v-if="ApiConnectionStore.stompApiConnection.isActive"
    >
      <div class="AuthPanelElement boldText centered-text">
        your start coding you need to connect to a code runner, this can be
        changed latert
      </div>
      test
      {{  stringify(ApiConnectionStore.stompApiConnection.isActive) }}

      <Dropdown
        v-model="chosenLangague"
        :options="laguageDropDown"
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
        :disabled="!allowConnection"
      />
    </div>
    <div v-else>trying to establish connection to api</div>
  </div>
</template>

<script setup lang="ts">
  import LanguageDropdown from './LanguageDropdown.vue'
  import { computed, Ref, ref, ComputedRef,watch } from 'vue'
  import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
  import { useApiConnectionStore } from '@/stores/ApiConnectionStore'
  import { languageDropDownType } from '@/types/CodeRunnerTypes'
  import codeRunnerType from '@/types/CodeRunnerTypes'
  import { languageChoices } from '@/config/Data'
  import LangaugeSelection from '@/tools/LangaugeSelection'
import { stringify } from 'flatted'


  const ApiConnectionStore = useApiConnectionStore()
  const isNotCOnnectedToApi = computed(() => !ApiConnectionStore.stompApiConnection.isActive)
  const refreshKey=computed(()=>{
    return ApiConnectionStore.stompApiConnection.isActive?"Activated coonn":"not activated";
  })

  const props = defineProps({
    languageChoicesSelection: {
      type: Array as () => codeRunnerType[],
      required: true,
    },
  })

  const chosenLangague: Ref<codeRunnerType> = ref(codeRunnerType.UNIDENTIFIED)

  const allowConnection = computed(() => {
    return chosenLangague.value != codeRunnerType.UNIDENTIFIED
  })

  const codeRunnerStore = useCodeRunnerStore()
  // const ApiConnectionStore = useApiConnectionStore()
  const laguageDropDown: ComputedRef<languageDropDownType[]> = computed(() => {
    return LangaugeSelection.getDropDownFromLanguages(
      props.languageChoicesSelection
    )
  })

  const connectToCodeRunner = async (codeRunner: codeRunnerType) => {
    codeRunnerStore.codeRunnerSender.requestCodeRunner(chosenLangague.value)
    // codeRunnerStore.increment()
    // console.log('Json log: ' + JSON.stringify(codeRunnerStore))
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
