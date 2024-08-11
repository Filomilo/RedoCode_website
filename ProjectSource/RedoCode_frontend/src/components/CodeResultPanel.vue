<template>
  <!-- type: -->
  {{ JSON.stringify(props.ManualTests) }}
  <div class="CodeResultContainer">
    <div class="EngineStatusContianer">
      <div class="EngineStatusPanel">
        <div class="EngineStatusTitle">Machine:</div>
        <div class="EngineStatusStatus" id="coderunner-type">
          {{
            codeRunnerStore.codeRunnerConnection.codeRunnerState
              .codeRunnerType
          }}
        </div>
      </div>

      <div class="EngineStatusPanel">
        <div class="EngineStatusTitle" id="coderunner-status">Status:</div>
        <div class="EngineStatusStatus">
          {{ codeRunnerStore.codeRunnerConnection.codeRunnerState.state }}
        </div>
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
    
    <DataResultPanel
    v-if="manualTestArray"
    :ManualTests="manualTestArray"
    :AutoTests="props.AutoTests"
  />

    <div
      class="ExerciseControlPanle"
      v-if="props.onSubmit!==undefined"
      :style="true ? '' : 'pointer-events: none'"
    >
      <Button
        class="submitButton"
        :disabled="!SubmitAccess"
        @click="props.onSubmit"
        id="coderunner-submit-button"
      >
        Submit
      </Button>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { computed, ComputedRef } from 'vue'
  import DataResultPanel from './DataResultPanel.vue'
  import type CodeResultType from '@/types/CodeResultsType'
  import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
  import { useApiConnectionStore } from '@/stores/ApiConnectionStore'
  import ExerciseTest from '@/types/ExcericseTest'
  import ProgramResult, { ConsoleOutput } from "@/types/ProgramResults"
  import StringParser from '@/tools/StringParser'
  const codeRunnerStore = useCodeRunnerStore()
  const ApiConnectionStore = useApiConnectionStore()
  const props = defineProps({
    isDataResult: { type: Boolean, required: false },
    onSubmit: { type: Function, required: false },
    ManualTests: { type: Array as () => ExerciseTest[] |ConsoleOutput , required: true },
    AutoTests: { type: Array as () => ExerciseTest[], required: false },
    SubmitAccess: { type: Boolean, required: true },
  })


  const manualTestArray: ComputedRef<ExerciseTest[]| undefined>=computed(()=>{
    return Array.isArray(props.ManualTests)?props.ManualTests as ExerciseTest[]:undefined;
  })

  const formattedConsole= computed<string>(() =>
    Array.isArray(props.ManualTests)?'':StringParser.parseStringToHtml(
      (props.ManualTests as ConsoleOutput).output
    ));
  const formattedEror = computed<string>(() =>
    Array.isArray(props.ManualTests)?'':StringParser.parseStringToHtml(
      (props.ManualTests as ConsoleOutput).errorOutput
    ));

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
