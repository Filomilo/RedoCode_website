<template>
  <div class="CodeResultContainer">
    <div class="EngineStatusContianer">
      <div class="EngineStatusPanel">
        <div class="EngineStatusTitle">Machine:</div>
        <div class="EngineStatusStatus">{{ApiConnectionStore.codeRunnerConnectionControler.codeRunnerActive.codeRunnerType }}</div>
      </div>

      <div class="EngineStatusPanel">
        <div class="EngineStatusTitle">Status:</div>
        <div class="EngineStatusStatus">{{ ApiConnectionStore.codeRunnerConnectionControler.codeRunnerActive.state }}</div>
      </div>
    </div>
    <div
      class="ConsoleResultConsoleCOntainer"
      v-if="!isDataResult"
      style="display: flex; overflow: hidden"
    >
      <div
        class="ConsoleResultConsoleCOntainerText"
        style="color: red; height: fit-content; max-height: fit-content"
        v-html="formattedEror"
      ></div>
      <div
        class="ConsoleResultConsoleCOntainerText"
        style="height: fit-content; max-height: fit-content"
        v-html="formattedConsole"
      ></div>
    </div>

    <DataResultPanel v-if="isDataResult" />

    <div
      class="ExerciseControlPanle"
      v-if="isDataResult"
      :style="codeRunnerStore.areResultCorrect ? '' : 'pointer-events: none'"
    >
      <router-link to="/Results" class="TopBarItemContainer" id="Result_burron">
        <Button class="submitButton" :disabled="!codeRunnerStore.areResultCorrect"> Submit </Button>
      </router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import DataResultPanel from './DataResultPanel.vue'
import type CodeResultType from '@/types/CodeResultsType'
import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
import { useApiConnectionStore } from '@/stores/ApiConnectionStore';
const codeRunnerStore = useCodeRunnerStore()
const ApiConnectionStore= useApiConnectionStore();
const props = defineProps({
  isDataResult: { type: Boolean, required: false }
})

const formattedConsole = computed<string>(
  () => '>> ' + codeRunnerStore.exerciseData.tests[0].consoleOutput.replace(/\n/g, '<br> >> ')
)
const formattedEror = computed<string>(() =>
  codeRunnerStore.exerciseData.tests[0].errorOutput.replace(/\n/g, '<br>')
)
const isCorrect = computed<boolean>(() => {
  return false
})
</script>

<style>
.disabled {
  opacity: 0.5;
  pointer-events: none;
}
</style>
