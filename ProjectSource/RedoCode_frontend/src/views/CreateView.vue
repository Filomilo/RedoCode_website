<template>
  <!-- data solutions:{{ JSON.stringify(codeRunnerStore.exerciseCreatorController) }} <br />
  <br /> -->

  <main class="PlayGroundBase">
    <TabView @tab-click="ontablClik">
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
          key="CreateCodeRunner"
          :languageChoices="codeRunnerStore.exerciseCreatorController.languages"
          :exerciseInfo="codeRunnerStore.exerciseCreatorController"
          :codeContianer="
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
          :onResults="(results: ProgramResultsMessage)=>{codeRunnerStore.exerciseCreatorController.updateTests(results.results,codeRunnerStore.codeRunnerConnection.codeRunnerState.codeRunnerType)}"
          :SubmitAccess="codeRunnerStore.exerciseCreatorController.isSolved"
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
  import ExerciseTest from '@/types/ExcericseTest'
  import ExercsieCreatorValidationMesage from '@/types/ApiMesseages/ExercsieCreatorValidationMesage'
  import ProgramResultsMessage from '@/types/ApiMesseages/ProgramResultsMessage'
  import { useActiveUserStore } from '@/stores/ActiveUserStore'
  import ExerciseCreatorController from '@/controllers/CodeRunner/ExerciseCreatorControlller'

  const ToastStore = useToastStore()
  const codeRunnerStore = useCodeRunnerStore()
  const ApiConnectionStore = useApiConnectionStore()
  const activeUserStore = useActiveUserStore()
  const codeUpdate = (code: string) => {
    console.log('codee update: ' + code)
    codeRunnerStore.exerciseCreatorController.updateSolutionCode(
      code
, codeRunnerStore.codeRunnerConnection.codeRunnerState.codeRunnerType
    );
    console.log(
      'update: ' +
        JSON.stringify(codeRunnerStore.exerciseCreatorController.solutionCodes)
    )
  }

  const onRunCode = () => {
    console.log('On run code')
    // ApiConnectionStore.codeRunnerConnection.runExercsieTestsCode(
    //   codeRunnerStore.exerciseCreatorController
    // )
    console.error("unimplemented");
    codeRunnerStore.codeRunnerSender.runSingleExerciseCreationTest(codeRunnerStore.exerciseCreatorController as ExerciseCreatorController,codeRunnerStore.codeRunnerConnection.codeRunnerState.codeRunnerType)

  }






  const onSubmit = () => {
    console.log('On sumbit')
    console.error("Unimplented")
    codeRunnerStore.codeRunnerSender.runExerciseCreationValistaion(codeRunnerStore.exerciseCreatorController as ExerciseCreatorController)

    // const request: ExercsieCreatorValidationMesage = JSON.parse(
    //   JSON.stringify(codeRunnerStore.exerciseCreatorController)
    // ) as ExercsieCreatorValidationMesage
    // request.manualTests = Object.values(
    //   codeRunnerStore.exerciseCreatorController.manualTestsSolutions
    // )[0] as ExerciseTest[]
    // request.manualTests.forEach((obj: ExerciseTest) => {
    //   obj.expectedOutput = JSON.stringify(obj.expectedOutput.toString())
    //   obj.input = JSON.stringify(obj.input.toString())
    // })
    // const request2: ExercsieCreatorValidationMesage = JSON.parse(
    //   JSON.stringify(request)
    // ) as ExercsieCreatorValidationMesage

    // ApiConnectionStore.codeRunnerConnection.submitExerciseCreationRequest(
    //   request
    // )
  }

  const exerciseLnageus = computed(() => {
    return languageChoices.filter(x =>
      codeRunnerStore.exerciseCreatorController.languages.includes(x.value)
    )
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
  }

  const infoValidation = computed(() => {
    return (
      codeRunnerStore.exerciseCreatorController.title.length > 5 &&
      codeRunnerStore.exerciseCreatorController.title.length < 100 &&
      codeRunnerStore.exerciseCreatorController.description.length > 20 &&
      codeRunnerStore.exerciseCreatorController.description.length < 5000
    )
  })
  const testValidation = computed(() => {
    return codeRunnerStore.exerciseCreatorController.ExerciseSetupError==="";
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
