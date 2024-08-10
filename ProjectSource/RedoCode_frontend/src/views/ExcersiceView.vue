<template>
  <main class="PlayGroundBase">
    <CodeRunnerPanel
      :exerciseInfo="{ title: 'no impelnted', description: 'no implented' }"
      :languageChoices="languageChoices.map(element => element.value)"
      starting="not impented"
      :onRunCode="onRunCode"
      :onSubmit="onSubmit"
      :ManualTests="[]"
      :AutoTests="[]"
      :codeContainerUpdate="codeConatienrUpdate"
      :onResults="onCodeResult"
      :SubmitAccess="true"
    />
  </main>
</template>

<script setup lang="ts">
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  import { useRoute } from 'vue-router'
  import CodeRunnerPanel from '@/components/CodeRunnerPanel.vue'
  import LoadingIndicator from '@/components/LoadingIndicator.vue'
  import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'
  import ExerciseData from '@/types/ExerciseData'
  import { useToastStore } from '@/stores/ToastStore'
  import { languageChoices } from '@/config/Data'
  import ProgramResultsMessage from '@/types/ApiMesseages/ProgramResultsMessage'
  const codeRunnerStore = useCodeRunnerStore()
  const toastStore = useToastStore()

  const text = ref('')
  const route = useRoute()

  const fetchExerciseData = (id: number) => {
    toastStore.showErrorMessage("not impelented fetchExerciseData")
    // codeRunnerStore.exerciseLoading = true

    // const params = {
    //   id: route.params.id,
    // }
    // axios
    //   .get('http://localhost:8080/public/exercises/data', { params: params })
    //   .then(response => {
    //     const data: ExerciseData = response.data
    //     data.tests = data.tests.map((elem: any) => {
    //       return {
    //         ...elem,
    //         input: elem.input.value,
    //         expectedOutput: elem.expectedOutput.value,
    //       }
    //     })
    //     codeRunnerStore.setExerciseData(data)
    //     console.log('data: ' + JSON.stringify(data))
    //     codeRunnerStore.exerciseLoading = false
    //   })
  }

  onMounted(() => {
    console.log('axios')
    console.log(route.params.id)
    let exerciseId: number = parseInt(
      typeof route.params.id === 'string' ? route.params.id : route.params.id[0]
    )
    fetchExerciseData(exerciseId)
  })

  const codeConatienrUpdate = (code: string) => {
    toastStore.featureNotImplemented(code)
  }

  const onRunCode = () => {
    toastStore.featureNotImplemented('onRunCode')
  }

  const onSubmit = () => {
    toastStore.featureNotImplemented('onSubmit')
  }

  const onCodeResult = (results: ProgramResultsMessage) => {
    toastStore.featureNotImplemented('onCodeResult')
    console.log('Exercise view results: ' + JSON.stringify(results))
    // codeRunnerStore.updateTestData(results.results)
  }
</script>
