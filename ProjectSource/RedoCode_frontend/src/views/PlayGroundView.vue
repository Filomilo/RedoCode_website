<template>
  Playgournd
  <main class="PlayGroundBase">
    <CodeRunnerPanel
      :exerciseInfo="{ title: 'no impelnted', description: 'no implented' }"
      :languageChoices="languageChoices.map(element => element.value)"
      :codeContainerUpdate="codeConatienrUpdate"
      starting=""
      :onRunCode="onRunCode"
      :onSubmit="onSubmit"
      :onResults="onCodeResult"
      :SubmitAccess="true"
    />
  </main>
</template>

<script lang="ts" setup>
  import CodeRunnerPanel from '@/components/CodeRunnerPanel.vue'
  import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'
  import { onMounted, Ref, ref } from 'vue'
  import LoadingIndicator from '@/components/LoadingIndicator.vue'
  import { onBeforeRouteLeave, onBeforeRouteUpdate } from 'vue-router'
  import { setTimeout } from 'timers'
  import { languageChoices } from '@/config/Data'
  import { useToastStore } from '@/stores/ToastStore'
  import { useApiConnectionStore } from '@/stores/ApiConnectionStore'
  import ProgramResultsMessage from '@/types/ApiMesseages/ProgramResultsMessage'
  const ApiConnectionStore = useApiConnectionStore()
  const setupPlayground = () => {
    // console.log("-----------------")
    codeRunnerStore.setExerciseLoading(true)
    codeRunnerStore.setExceriseDataToPlayground()
    codeRunnerStore.setExerciseLoading(false)
  }
  const codeRunnerStore = useCodeRunnerStore()
  const toastStore = useToastStore()
  onMounted(() => {
    setupPlayground()
  })

  const activeCode: Ref<string> = ref('')

  const codeConatienrUpdate = (code: string) => {
    // toastStore.featureNotImplemented(code)
    activeCode.value = code
  }

  const onRunCode = () => {
    // toastStore.featureNotImplemented('onRunCode')
    ApiConnectionStore.codeRunnerConnection.runRawCode(activeCode.value)
  }

  const onSubmit = () => {
    toastStore.featureNotImplemented('feature not related')
  }
  const onCodeResult = (results: ProgramResultsMessage) => {
    console.log('playgronud view results: ' + JSON.stringify(results))
    codeRunnerStore.updateTestData(results.results)
  }
</script>

<style></style>
