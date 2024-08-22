<template>
  <main class="PlayGroundBase">

    <CodeRunnerPanel
    v-if="codeRunnerStore.exerciseSolverController.manualTests!==undefined"
      :exerciseInfo="codeRunnerStore.exerciseSolverController"
      :languageChoices="codeRunnerStore.exerciseSolverController.languages"
      :starting="codeRunnerStore.exerciseSolverController.startFunction(
        codeRunnerStore.codeRunnerConnection.codeRunnerState.codeRunnerType
      )"
      :onRunCode="onRunCode"
      :onSubmit="onSubmit"
      :ManualTests="codeRunnerStore.exerciseSolverController.manualTests"
      :AutoTests="codeRunnerStore.exerciseSolverController.autoTests"
      :codeContainerUpdate="codeConatienrUpdate"
      :onResults="onCodeResult"
      :SubmitAccess="codeRunnerStore.exerciseSolverController.isSolved"
    />
    {{ JSON.stringify(codeRunnerStore.exerciseSolverController) }}

  </main>
</template>

<script setup lang="ts">
  // #region imports
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  import { useRoute } from 'vue-router'
  import CodeRunnerPanel from '@/components/CodeRunnerPanel.vue'
  import LoadingIndicator from '@/components/LoadingIndicator.vue'
  import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'
  import { useToastStore } from '@/stores/ToastStore'
  import { languageChoices } from '@/config/Data'
  import ProgramResultsMessage from '@/types/ApiMesseages/ProgramResultsMessage'
  import EndpointAcces from '@/controllers/EndpointsAcces'
  import ExerciseSolverController from '@/controllers/CodeRunner/ExerciseSolverController'
  //#endregion
  const codeRunnerStore = useCodeRunnerStore()
  const toastStore = useToastStore()

  const text = ref('')
  const route = useRoute()

  const fetchExerciseData = (id: number) => {

    EndpointAcces.getExerciseData(id).then(x=>{
      
    codeRunnerStore.exerciseSolverController.loadInitialData(id,x)

    });

    // codeRunnerStore.exerciseLoading = true

    // const params = {
    //   id: route.params.id,
    // }
  
  }

  onMounted(() => {
    let exerciseId: number = parseInt(
      typeof route.params.id === 'string' ? route.params.id : route.params.id[0]
    )
    fetchExerciseData(exerciseId)
  })

  const codeConatienrUpdate = (code: string) => {
    codeRunnerStore.exerciseSolverController.solution=code;
  }

  const onRunCode = () => {
    codeRunnerStore.codeRunnerSender.runExerciseIdCode(
      codeRunnerStore.exerciseSolverController as ExerciseSolverController,
    )

  }

  const onSubmit = () => {

    codeRunnerStore.codeRunnerSender.runExercsieIdValidationCode(
      codeRunnerStore.exerciseSolverController as ExerciseSolverController
    )
  }

  const onCodeResult = (results: ProgramResultsMessage) => {
    console.log('Exercise view results: ' + JSON.stringify(results))
    codeRunnerStore.exerciseSolverController.updateTests(
      results.results
    )
  }
</script>
