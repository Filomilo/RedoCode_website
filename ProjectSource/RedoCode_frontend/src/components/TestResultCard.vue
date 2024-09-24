<template>
  <div class="resultPanelContainer">
    <div class="testInputContainer">
      <div style="margin: 0.4rem" :id="'tab-result-input-' + props.index">
        input: {{ JSON.stringify(data.input) }}
      </div>
    </div>
    <div class="testOutputSection">
      <TabView>
        <TabPanel
          header="Console"
          :pt="{ id: 'tab-result-console-' + props.index }"
        >
          <div style="overflow: scroll">
            <div
              class="ConsoleResultConsoleCOntainerText"
              style="height: fit-content; max-height: fit-content; color: red"
              v-html="formattedError"
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
              expected: {{ JSON.stringify(data.expectedOutput) }}
            </div>
            <div
              class="TypeResultContainerPanel"
              :id="'tab-result-achieved-container-' + props.index"
            >
              achieved: {{ JSON.stringify(data.output) }}
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
      <div class="statusSector" :id="'testResultStatus_' + props.index">
        {{
          data.isSolved !== null ? (data.isSolved ? 'Correct' : 'Failed') : ''
        }}
      </div>
      <div class="executionTimeSector">
        {{ executionTimeLabel }}
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import type ExerciseTest from '@/types/ExerciseTest'
  import { formatToHtml } from '@/config/Tools'
  import { computed } from 'vue'
  const props = defineProps<{
    data: ExerciseTest
    index: number
  }>()

  const executionTimeLabel = computed<string>(() => {
    if (
      props.data.executionTime === undefined ||
      props.data.executionTime === null ||
      props.data.executionTime < 0
    )
      return ''
    return props.data.executionTime + ' ms'
  })

  const formattedConsole = computed<string>(() =>
    formatToHtml(props.data.consoleOutput)
  )
  const formattedError = computed<string>(() =>
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
  }

  .statusSector {
    text-align: left;
  }
  .executionTimeSector {
    text-align: right;
  }

  .testValidationSection {
    width: 100%;
    background-color: rgb(99, 99, 99);
    height: 3rem;
    border-top: solid;
    display: flex;
    justify-content: center;
  }
  .testValidationSection * {
    width: 100%;
    margin: 0.5rem;
  }
</style>
