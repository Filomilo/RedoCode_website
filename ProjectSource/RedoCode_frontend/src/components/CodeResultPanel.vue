<template>
  <div class="CodeResultContainer">
    <div class="EngineStatusContianer">
      <div class="EngineStatusPanel">
        <div class="EngineStatusTitle">Machine:</div>
        <div class="EngineStatusStatus">{{ codeRunnerStore.codeRunnerActive.codeRunnerType }}</div>
      </div>

      <div class="EngineStatusPanel">
        <div class="EngineStatusTitle">Status:</div>
        <div class="EngineStatusStatus">{{ codeRunnerStore.codeRunnerActive.state }}</div>
      </div>
    </div>
    <div class="ConsoleResultConsoleCOntainer"
     v-if="!isDataResult"
     style="display: flex;
     overflow: scroll
     "
     >
      <div
        class="ConsoleResultConsoleCOntainerText"
        style="color: red; height: fit-content; max-height: fit-content"
        v-html="formattedEror"
        >
      </div>
      <div
      class="ConsoleResultConsoleCOntainerText" 
      style=" height: fit-content; max-height: fit-content"
      v-html="formattedConsole"
      >
      </div>
    </div>

    <DataResultPanel v-if="isDataResult" :results="results" />

    <div class="ExerciseControlPanle" v-if="isDataResult">
      <router-link to="/Results" class="TopBarItemContainer" id="Result_burron">
        <Button class="submitButton"> Submit </Button>
      </router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import DataResultPanel from './DataResultPanel.vue'
import type CodeResultType from '@/types/CodeResultsType'
import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
const codeRunnerStore = useCodeRunnerStore()
const props = defineProps({
  isDataResult: { type: Boolean, required: false }
})


const formattedConsole = computed<string>(() =>
  ">> " + codeRunnerStore.exerciseData.tests[0].consoleOutput.replace(/\n/g, '<br> >> ')
)
const formattedEror = computed<string>(() =>
  codeRunnerStore.exerciseData.tests[0].errorOutput.replace(/\n/g, '<br>')
)

const results: CodeResultType[] = [
  {
    Console_output: formattedConsole.value,
    Error_output: 'Couldnt find x',
    Solution_type: 'Array',
    correct_solution: 1,
    achived_solution: 2,
    error: 'invalid output'
  },
  {
    Console_output: '>> hello world',
    Error_output: 'Couldnt find x',
    Solution_type: 'Array',
    correct_solution: 1,
    achived_solution: 2,
    error: 'invalid output'
  },
  {
    Console_output: '>> hello world',
    Error_output: 'Couldnt find x',
    Solution_type: 'Array',
    correct_solution: 1,
    achived_solution: 2,
    error: 'invalid output'
  },
  {
    Console_output: '>> hello world',
    Error_output: 'Couldnt find x',
    Solution_type: 'Array',
    correct_solution: 1,
    achived_solution: 2,
    error: 'invalid output'
  },
  {
    Console_output: '>> hello world',
    Error_output: 'Couldnt find x',
    Solution_type: 'Array',
    correct_solution: 1,
    achived_solution: 2,
    error: 'invalid output'
  },
  {
    Console_output: '>> hello world',
    Error_output: 'Couldnt find x',
    Solution_type: 'Array',
    correct_solution: 1,
    achived_solution: 2,
    error: 'invalid output'
  },
  {
    Console_output: '>> hello world',
    Error_output: 'Couldnt find x',
    Solution_type: 'Array',
    correct_solution: 1,
    achived_solution: 2,
    error: 'invalid output'
  },
  {
    Console_output: '>> hello world',
    Error_output: 'Couldnt find x',
    Solution_type: 'Array',
    correct_solution: 1,
    achived_solution: 2,
    error: 'invalid output'
  }
]
</script>
