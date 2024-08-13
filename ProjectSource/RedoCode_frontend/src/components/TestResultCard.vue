<template>
  <div class="resultPanelContainer">
    <div class="testInputContiner">
      <div style="margin: 0.4rem" :id="'tab-result-input-' + props.index">
        input: {{ JSON.stringify(data.input) }}
      </div>
    </div>
    <div class="testOuputSection">
      <TabView>
        <TabPanel header="Console" :id="'tab-result-console-' + props.index">
          <div style="overflow: scroll">
            <div
              class="ConsoleResultConsoleCOntainerText"
              style="height: fit-content; max-height: fit-content; color: red"
              v-html="formattedEror"
              :id="'tab-result-console-error-container-' + props.index"
            ></div>
            <div
              class="ConsoleResultConsoleCOntainerText"
              style="height: fit-content; max-height: fit-content"
              v-html="formattedConsole"
              :id="'tab-result-console-console-container-' + props.index"
            ></div>
          </div>
        </TabPanel>
        <TabPanel header="Result">
          <div class="TypeResultContainer" st>
            <div
              class="TypeResultContainerPanel"
              style="text-wrap: wrap"
              :id="'tab-result-expected-container-' + props.index"
            >
              expeteced: {{ JSON.stringify(data.expectedOutput) }}
            </div>
            <div
              class="TypeResultContainerPanel"
              :id="'tab-result-achived-container-' + props.index"
            >
              achived: {{ JSON.stringify(data.output) }}
            </div>
          </div>
        </TabPanel>
      </TabView>
    </div>
    <div
      class="testValidationSection"
      :class="
        data.isSolved !== null
          ? data.isSolved
            ? 'correct'
            : 'wrong'
          : 'unknown'
      "
    >
      {{ data.isSolved !== null ? (data.isSolved ? 'Correct' : 'Failed') : '' }}
    </div>
  </div>
</template>

<script setup lang="ts">
  import type ExerciseTest from '@/types/ExcericseTest'
  import { formatToHtml } from '@/config/Tools'
  import { computed } from 'vue'
  const props = defineProps<{
    data: ExerciseTest
    index: number
  }>()

  const formattedConsole = computed<string>(() =>
    formatToHtml(props.data.consoleOutput)
  )
  const formattedEror = computed<string>(() =>
    formatToHtml(props.data.errorOutput)
  )
</script>

<style>
  .correct {
    border-color: lime;
    color: lime;
  }

  .wrong {
    border-color: red;
    color: red;
  }

  .unknown {
    border-color: transparent;
    height: 10rem;
  }
</style>
