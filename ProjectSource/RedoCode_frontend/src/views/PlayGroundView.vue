<template>
  <main class="PlayGroundBase">
    <CodeRunnerPanel 
    :showLeftPanel="false" 
    :connectAtStart="false" 
    :functionAtStart="false"
    :languageChoices="languageChoices"
    />
  </main>
</template>

<script lang="ts" setup>
import CodeRunnerPanel from '@/components/CodeRunnerPanel.vue'
import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'
import { onMounted, ref } from 'vue'
import LoadingIndicator from '@/components/LoadingIndicator.vue'
import { onBeforeRouteLeave, onBeforeRouteUpdate } from 'vue-router'
import { setTimeout } from 'timers'
import { languageChoices } from '@/config/Data'
const setupPlayground = () => {
  // console.log("-----------------")
  codeRunnerStore.setExerciseLoading(true)
  codeRunnerStore.setExceriseDataToPlayground()
  codeRunnerStore.setExerciseLoading(false)
}
const codeRunnerStore = useCodeRunnerStore()
onMounted(() => {
  setupPlayground()
})
</script>

<style></style>
