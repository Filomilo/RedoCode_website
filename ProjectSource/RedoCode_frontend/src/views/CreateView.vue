<template>
  <!-- data solutions:{{ JSON.stringify(codeRunnerStore.exerciseCreatorController) }} <br />
  <br /> -->

  <main class="PlayGroundBase">
    <TabView @tab-click="ontablClik">
      <div class="childHeight">
        <TabPanel header="Information" class="childHeight">
          <ExerciseInfoSetup class="childHeight" />
        </TabPanel>
      </div>
      <TabPanel header="Setup">
        <ExerciseSetupPanel />
      </TabPanel>

      <TabPanel header="Solution">
        <!-- <CodeRunnerPanel /> -->
        <CodeRunnerPanel
          :languageChoices="exerciseLnageus"
          :exerciseInfo="codeRunnerStore.exerciseCreatorController"
          :codeContianer="codeRunnerStore.exerciseCreatorController.solutionCodes"
          :starting="
            codeRunnerStore.exerciseCreatorController.solutionCodes[
              ApiConnectionStore.codeRunnerConnection.codeRunnerState.codeRunnerType
            ]
          "
          :codeContainerUpdate="codeUpdate"
          :onRunCode="onRunCode"
          :onSubmit="onSubmit"
          :ManualTests="
            codeRunnerStore.exerciseCreatorController.manualTestsSolutions[
              EditorLanguagesMap[
                ApiConnectionStore.codeRunnerConnection.codeRunnerState.codeRunnerType
              ]
            ]
          "
          :AutoTests="[]"
        />
      </TabPanel>
    </TabView>
  </main>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'
import CodeRunnerPanel from '@/components/CodeRunnerPanel.vue'
import { useToastStore } from '@/stores/ToastStore'
import { onBeforeRouteUpdate } from 'vue-router'
import ExerciseSetupPanel from '@/components/ExerciseSetupPanel.vue'
import ExerciseDescriptionPanel from '@/components/ExerciseDescriptionPanel.vue'
import ExerciseInfoSetup from '@/components/ExerciseInfoSetup.vue'
import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'
import { EditorLanguagesMap, languageChoices } from '@/config/Data'
import { useApiConnectionStore } from '@/stores/ApiConnectionStore'
import { text } from 'stream/consumers'
import { TabViewClickEvent } from 'primevue/tabview'
const ToastStore = useToastStore()
const codeRunnerStore = useCodeRunnerStore()
const ApiConnectionStore = useApiConnectionStore()

const codeUpdate = (code: string) => {
  console.log('codee update: ' + code)
  // codeRunnerStore.exerciseCreatorController.solutions
  codeRunnerStore.exerciseCreatorController.solutionCodes[
    ApiConnectionStore.codeRunnerConnection.codeRunnerState.codeRunnerType
  ] = code
  console.log('update: ' + JSON.stringify(codeRunnerStore.exerciseCreatorController.solutionCodes))
}

const onRunCode = () => {
  console.log('On run code')
  ApiConnectionStore.codeRunnerConnection.runExercsieTestsCode(
    codeRunnerStore.exerciseCreatorController
  )
}

const onSubmit = () => {
  console.log('On sumbit')

  ApiConnectionStore.codeRunnerConnection.submitExerciseCreationRequest({
    title: '',
    description: '',
    ram: 0,
    timeForExecutionMs: 0,
    inputType: 'SINGLE_INTEGER',
    outputType: 'SINGLE_INTEGER',
    amountOfAutoTests: 0,
    autoTestminValue: 0,
    autoTestMaxValue: 0,
    lengthRange: {
      min: 0,
      max: 0
    },
    xArrayRange: null,
    yArrayRange: null,
    upperCaseInput: false,
    lowerCaseInput: false,
    numberInput: false,
    specialCharacterInput: false,
    breakCharacterInupt: false,
    spaceInupt: false,
    solutionCodes: {
      CPP_RUNNER: 'example_solution'
    },
    timeForTaskMin: 0,
    manualTests: []
  })
}

const exerciseLnageus = computed(() => {
  return (codeRunnerStore.exerciseCreatorController.languages as any).map((x: any) => x.value)
})

onMounted(() => {
  ToastStore.featureNotImplemented()
})

const ontablClik = (event: TabViewClickEvent) => {
  console.log('event: ' + JSON.stringify(event))
  if (event.index === 2) onOpenCodeRunner()
}

const onOpenCodeRunner = () => {
  console.log('coderunner opened')
  codeRunnerStore.transferTestFromBufferTpCreator()
}
</script>

<style>
.p-tabview-nav-container * {
  background-color: black;
}
.p-tabview-nav {
  width: 100%;
  justify-content: center;
  background-color: black;
}
.p-tabview-nav * {
  width: 100%;
  background-color: rgb(31, 31, 31);
}
.p-tabview-nav {
  background-color: red;
  padding-left: 0;
}
.p-tabview-header {
  text-align: center;
}
.p-tabview-panels {
  width: 100%;
  height: 100%;
  padding: 0;
  min-width: 100%;
  flex-grow: 100%;
}
.p-tabview * {
}

.p-tabview-panels * {
}

.p-tabview-nav * {
}

.p-tabview-panels {
  background-color: rgb(31, 31, 31);
  min-height: 100%;
  overflow: hidden;
  max-height: 100%;
}
.p-tabview .p-component {
}
.p-tabview {
  background-color: cadetblue;
  height: 93%;
}
.info {
}
.childHeight * {
  height: 100%;
}
.p-tabview-nav-container {
  height: 4rem;
}
.p-tabview-panel {
  height: 100%;
}
</style>
