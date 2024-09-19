<template>
  <main class="PlayGroundBase">
    <CodeRunnerPanel
      v-if="codeRunnerStore.exerciseSolverController.manualTests !== undefined"
      :exerciseInfo="codeRunnerStore.exerciseSolverController"
      :languageChoices="codeRunnerStore.exerciseSolverController.languages"
      :starting="
        codeRunnerStore.exerciseSolverController.startFunction(
          codeRunnerStore.codeRunnerConnection.codeRunnerState.codeRunnerType
        )
      "
      :onRunCode="onRunCode"
      :onSubmit="onSubmit"
      :ManualTests="codeRunnerStore.exerciseSolverController.manualTests"
      :AutoTests="codeRunnerStore.exerciseSolverController.autoTests"
      :codeContainerUpdate="codeConatienrUpdate"
      :onResults="onCodeResult"
      :SubmitAccess="SubmitAccess"
    />
  </main>
</template>

<script setup lang="ts">
  // #region imports
  import { ref, onMounted, computed } from 'vue'
  import axios from 'axios'
  import { useRoute, useRouter } from 'vue-router'
  import CodeRunnerPanel from '@/components/CodeRunnerPanel.vue'
  import LoadingIndicator from '@/components/LoadingIndicator.vue'
  import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'
  import { useToastStore } from '@/stores/ToastStore'
  import { languageChoices } from '@/config/Data'
  import ProgramResultsMessage from '@/types/ApiMesseages/ProgramResultsMessage'
  import EndpointAcces from '@/controllers/EndpointsAcces'
  import ExerciseSolverController from '@/controllers/CodeRunner/ExerciseSolverController'
  import { useExecutionChainStore } from '@/stores/ExecutionChainStore'
  //#endregion
  const codeRunnerStore = useCodeRunnerStore()
  const toastStore = useToastStore()

  const text = ref('')
  const route = useRoute()
  const router = useRouter()
  const exercutionChainStore = useExecutionChainStore()
  const fetchExerciseData = (id: number) => {
    EndpointAcces.unauthorized
      .getExerciseData(id)
      .then((x: any) => {
        console.log('fetched exercise data: ' + JSON.stringify(x))
        codeRunnerStore.exerciseSolverController.loadInitialData(id, x)
      })
      .catch(x => {
        router.replace({ path: '/NotFound' })
      })
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
    exercutionChainStore.executionChainController.onCloseSucces = onSuccesCrated
  })
  onMounted(() => {})
  const onSuccesCrated = () => {
    console.log("Exercise VIew onSuccesCrated")
    router.replace({ name: 'Results', params: route.params })
  }

  const codeConatienrUpdate = (code: string) => {
    codeRunnerStore.exerciseSolverController.solution = code
  }

  const onRunCode = () => {
    codeRunnerStore.codeRunnerSender.runExerciseIdCode(
      codeRunnerStore.exerciseSolverController as ExerciseSolverController
    )
  }

  const onSubmit = () => {
    codeRunnerStore.codeRunnerSender.runExercsieIdValidationCode(
      codeRunnerStore.exerciseSolverController as ExerciseSolverController
    )
  }

  const onCodeResult = (results: ProgramResultsMessage) => {
    console.log('Exercise view results: ' + JSON.stringify(results))
    codeRunnerStore.exerciseSolverController.updateTests(results.results)
  }

  const SubmitAccess = computed(() => {
    if (import.meta.env.MODE === 'development') {
      return true
    }
    return codeRunnerStore.exerciseSolverController.isSolved
  })
</script>
