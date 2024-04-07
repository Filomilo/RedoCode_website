<template>
  <div class="CodeResultContainer">
    <div class="EngineStatusContianer">
      <div class="EngineStatusPanel">
        <div class="EngineStatusTitle">Machine:</div>
        <div class="EngineStatusStatus">Cpp</div>
      </div>

      <div class="EngineStatusPanel">
        <div class="EngineStatusTitle">Status:</div>
        <div class="EngineStatusStatus">Ruuning</div>
      </div>
    </div>
    <div class="ConsoleResultConsoleCOntainer" v-if="!isDataResult">
      <div class="ConsoleResultConsoleCOntainerText" v-html="formatted"></div>
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
const props = defineProps({
  isDataResult: { type: Boolean, required: false }
})

const console = `
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        `
const formatted = computed<string>(() => console.replace(/\n/g, '<br>'))

const results: CodeResultType[] = [
  {
    Console_output: formatted.value,
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
