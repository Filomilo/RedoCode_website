<template>
  <main class="PlayGroundBase">
    {{ JSON.stringify(codeRunnerStore.exerciseSolverController.languages) }}
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
      :codeContainerUpdate="codeContainerUpdate"
      :onResults="onCodeResult"
      :SubmitAccess="SubmitAccess"
    />
  </main>
</template>

<script setup lang="ts">
  // #region imports
  import { onMounted, computed } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import CodeRunnerPanel from '@/components/CodeRunnerPanel.vue'
  import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'
  import ProgramResultsMessage from '@/types/ApiMessages/ProgramResultsMessage'
  import EndpointAccess from '@/controllers/EndpointsAccess'
  import ExerciseSolverController from '@/controllers/CodeRunner/ExerciseSolverController'
  import { useExecutionChainStore } from '@/stores/ExecutionChainStore'
  //#endregion
  const codeRunnerStore = useCodeRunnerStore()

  const route = useRoute()
  const router = useRouter()
  const executionsChainStore = useExecutionChainStore()
  const fetchExerciseData = (id: number) => {
    EndpointAccess.unauthorized
      .getExerciseData(id)
      .then((x: any) => {
        console.log('fetched exercise data: ' + JSON.stringify(x))
        codeRunnerStore.exerciseSolverController.loadInitialData(id, x)
      })
      .catch(() => {
        router.replace({ path: '/NotFound' })
      })
  }

  onMounted(() => {
    let exerciseId: number = parseInt(
      typeof route.params.id === 'string' ? route.params.id : route.params.id[0]
    )
    fetchExerciseData(exerciseId)
    executionsChainStore.executionChainController.onCloseSuccess =
      onSuccessCrated
  })
  onMounted(() => {})
  const onSuccessCrated = () => {
    console.log('Exercise VIew onSuccessCrated')
    router.replace({ name: 'Results', params: route.params })
  }

  const codeContainerUpdate = (code: string) => {
    codeRunnerStore.exerciseSolverController.solution = code
  }

  const onRunCode = () => {
    codeRunnerStore.codeRunnerSender.runExerciseIdCode(
      codeRunnerStore.exerciseSolverController as ExerciseSolverController
    )
  }

  const onSubmit = () => {
    codeRunnerStore.codeRunnerSender.runExorciseIdValidationCode(
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
