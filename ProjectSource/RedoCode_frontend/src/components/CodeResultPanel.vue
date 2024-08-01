<template>
  <!-- type: -->
  <!-- {{ JSON.stringify(props) }} -->
  <div class="CodeResultContainer">
    <div class="EngineStatusContianer">
      <div class="EngineStatusPanel">
        <div class="EngineStatusTitle">Machine:</div>
        <div class="EngineStatusStatus" id="coderunner-type">
          {{
            ApiConnectionStore.codeRunnerConnection.codeRunnerState
              .codeRunnerType
          }}
        </div>
      </div>

      <div class="EngineStatusPanel">
        <div class="EngineStatusTitle" id="coderunner-status">Status:</div>
        <div class="EngineStatusStatus">
          {{ ApiConnectionStore.codeRunnerConnection.codeRunnerState.state }}
        </div>
      </div>
    </div>
    tets

    {{ JSON.stringify(codeRunnerStore.exerciseCreatorController.manualTests) }}
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
    x
    <DataResultPanel
      v-if="props.ManualTests !== undefined"
      :ManualTests="props.ManualTests"
      :AutoTests="props.AutoTests"
    />

    <div
      class="ExerciseControlPanle"
      v-if="props.ManualTests !== undefined"
      :style="true ? '' : 'pointer-events: none'"
    >
      <Button
        class="submitButton"
        :disabled="isCorrect"
        @click="props.onSubmit"
        id="coderunner-submit-button"
      >
        Submit
      </Button>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { computed } from 'vue'
  import DataResultPanel from './DataResultPanel.vue'
  import type CodeResultType from '@/types/CodeResultsType'
  import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
  import { useApiConnectionStore } from '@/stores/ApiConnectionStore'
  import ExerciseTest from '@/types/ExcericseTest'
  const codeRunnerStore = useCodeRunnerStore()
  const ApiConnectionStore = useApiConnectionStore()
  const props = defineProps({
    isDataResult: { type: Boolean, required: false },
    onSubmit: { type: Function, required: true },
    ManualTests: { type: Array as () => ExerciseTest[], required: false },
    AutoTests: { type: Array as () => ExerciseTest[], required: false },
  })

  const formattedConsole = computed<string>(() =>
    '>> ' + codeRunnerStore.exerciseData.tests[0].consoleOutput == null
      ? ''
      : codeRunnerStore.exerciseData.tests[0].consoleOutput.replace(
          /\n/g,
          '<br> >> '
        )
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
