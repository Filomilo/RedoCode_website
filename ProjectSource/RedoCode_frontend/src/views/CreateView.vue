<template>
  <!-- data solutions:{{ JSON.stringify(codeRunnerStore.exerciseCreatorController) }} <br />
  <br /> -->

  <main class="PlayGroundBase">
    <TabView @tab-click="onTabClick">
      <div class="childHeight" id="information-switch">
        <TabPanel header="Information" class="childHeight">
          <ExerciseInfoSetup class="childHeight" />
        </TabPanel>
      </div>
      <TabPanel header="Setup" :disabled="!infoValidation">
        <ExerciseSetupPanel />
      </TabPanel>

      <TabPanel header="Solution" :disabled="!testValidation">
        <!-- <CodeRunnerPanel /> -->
        <CodeRunnerPanel
          :v-if="testValidation"
          key="CreateCodeRunner"
          :languageChoices="codeRunnerStore.exerciseCreatorController.languages"
          :exerciseInfo="codeRunnerStore.exerciseCreatorController"
          :codeContainer="
            codeRunnerStore.exerciseCreatorController.solutionCodes
          "
          :starting="
            codeRunnerStore.exerciseCreatorController.solutionCodes[
              codeRunnerStore.codeRunnerConnection.codeRunnerState
                .codeRunnerType
            ] ?? ''
          "
          :codeContainerUpdate="codeUpdate"
          :onRunCode="onRunCode"
          :onSubmit="onSubmit"
          :ManualTests="
            codeRunnerStore.exerciseCreatorController.manualTestsSolutions[
              codeRunnerStore.codeRunnerConnection.codeRunnerState
                .codeRunnerType
            ]?.tests as ExerciseTest[]
          "
          :AutoTests="
            codeRunnerStore.exerciseCreatorController.manualTestsSolutions[
              codeRunnerStore.codeRunnerConnection.codeRunnerState
                .codeRunnerType
            ]?.autoTests
          "
          :onResults="
            (results: ProgramResultsMessage) => {
              codeRunnerStore.exerciseCreatorController.updateTests(
                results.results,
                codeRunnerStore.codeRunnerConnection.codeRunnerState
                  .codeRunnerType
              )
            }
          "
          :SubmitAccess="submitValidation"
        />
      </TabPanel>
    </TabView>
  </main>
</template>

<script setup lang="ts">
  import { onMounted, computed } from 'vue'
  import CodeRunnerPanel from '@/components/CodeRunnerPanel.vue'
  import ExerciseSetupPanel from '@/components/ExerciseSetupPanel.vue'
  import ExerciseInfoSetup from '@/components/ExerciseInfoSetup.vue'
  import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'
  import { TabViewClickEvent } from 'primevue/tabview'
  import ExerciseTest from '@/types/ExerciseTest'
  import ProgramResultsMessage from '@/types/ApiMessages/ProgramResultsMessage'
  import ExerciseCreatorController from '@/controllers/CodeRunner/ExerciseCreatorController'
  import { useRouter } from 'vue-router'
  import { useExecutionChainStore } from '@/stores/ExecutionChainStore'
  const codeRunnerStore = useCodeRunnerStore()
  const executionChainStore = useExecutionChainStore()
  const router = useRouter()

  onMounted(() => {
    executionChainStore.executionChainController.onCloseSuccess =
      onSuccessCrated
  })
  const onSuccessCrated = () => {
    router.replace({ name: 'Exercises' })
  }
  const codeUpdate = (code: string) => {
    console.log('code update: ' + code)
    codeRunnerStore.exerciseCreatorController.updateSolutionCode(
      code,
      codeRunnerStore.codeRunnerConnection.codeRunnerState.codeRunnerType
    )
    console.log(
      'update: ' +
        JSON.stringify(codeRunnerStore.exerciseCreatorController.solutionCodes)
    )
  }

  const onRunCode = () => {
    console.log('On run code')
    codeRunnerStore.codeRunnerSender.runSingleExerciseCreationTest(
      codeRunnerStore.exerciseCreatorController as ExerciseCreatorController,
      codeRunnerStore.codeRunnerConnection.codeRunnerState.codeRunnerType
    )
  }

  const onSubmit = () => {
    console.log('On submit')
    codeRunnerStore.codeRunnerSender.runExerciseCreationValidation(
      codeRunnerStore.exerciseCreatorController as ExerciseCreatorController
    )
  }

  const onTabClick = (event: TabViewClickEvent) => {
    console.log('event: ' + JSON.stringify(event))
    if (event.index === 2) onOpenCodeRunner()
  }

  const onOpenCodeRunner = () => {
    console.log('coderunner opened')
  }

  const infoValidation = computed(() => {
    if (import.meta.env.MODE === 'development') {
      return true
    }
    return (
      codeRunnerStore.exerciseCreatorController.title.length > 5 &&
      codeRunnerStore.exerciseCreatorController.title.length < 100 &&
      codeRunnerStore.exerciseCreatorController.desc.length > 20 &&
      codeRunnerStore.exerciseCreatorController.desc.length < 5000
    )
  })
  const testValidation = computed(() => {
    if (import.meta.env.MODE === 'development') {
      return true
    }
    return codeRunnerStore.exerciseCreatorController.ExerciseSetupError === ''
  })
  const submitValidation = computed(() => {
    if (import.meta.env.MODE === 'development') {
      return true
    }
    return codeRunnerStore.exerciseCreatorController.isSolved
  })
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

  .p-tabview-panels {
    background-color: rgb(31, 31, 31);
    min-height: 100%;
    overflow: hidden;
    max-height: 100%;
  }

  .p-tabview {
    background-color: cadetblue;
    height: 93%;
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
