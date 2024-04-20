<template>
  <div class="resultPanelContainer">
    <div class="testInputContiner">
      <div style="margin: 0.4rem">input: {{ JSON.stringify(data.input) }}</div>
    </div>
    <div class="testOuputSection">
      <TabView>
        <TabPanel header="Console">
          <div style="overflow: scroll">
            <div
              class="ConsoleResultConsoleCOntainerText"
              style="height: fit-content; max-height: fit-content; color: red"
              v-html="formattedEror"
            ></div>
            <div
              class="ConsoleResultConsoleCOntainerText"
              style="height: fit-content; max-height: fit-content"
              v-html="formattedConsole"
            ></div>
          </div>
        </TabPanel>
        <TabPanel header="Result">
          <div class="TypeResultContainer" st>
            <div class="TypeResultContainerPanel" style="text-wrap: wrap">
              expeteced: {{ JSON.stringify(data.expectedOutput) }}
            </div>
            <div class="TypeResultContainerPanel">achived: {{ JSON.stringify(data.output) }}</div>
          </div>
        </TabPanel>
      </TabView>
    </div>
    <div class="testValidationSection">
      {{
        data.isSolved !== null
          ? data.expectedOutput == data.output
            ? 'Correct'
            : 'Failed'
          : ''
      }}
    </div>
  </div>
</template>

<script setup lang="ts">
import type ExerciseTest from '@/types/ExcericseTest'
import { formatToHtml } from '@/config/Tools'
import { computed } from 'vue'
const props = defineProps<{
  data: ExerciseTest
}>()

const formattedConsole = computed<string>(() =>
  // "bbbbbbbbbbbbbbbbbbbbb"
  formatToHtml(props.data.consoleOutput)
)
const formattedEror = computed<string>(() =>
  // "aaaaaaaaaaaaaaaaaaaaa"
  formatToHtml(props.data.errorOutput)
)
</script>
