<template>
  <main class="PlayGroundBase">
    <CodeRunnerPanel 
    :exerciseInfo="{title: 'no impelnted', desc: 'no implented'}"
    :languageChoices="['cpp','js']"
    :codeContainerUpdate="codeConatienrUpdate"
    starting="not impented"
    :onRunCode="onRunCode" 
    :onSubmit="onSubmit"
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
import { useToastStore } from '@/stores/ToastStore'
const setupPlayground = () => {
  // console.log("-----------------")
  codeRunnerStore.setExerciseLoading(true)
  codeRunnerStore.setExceriseDataToPlayground()
  codeRunnerStore.setExerciseLoading(false)
}
const codeRunnerStore = useCodeRunnerStore()
const toastStore= useToastStore();
onMounted(() => {
  setupPlayground()
})

const codeConatienrUpdate=(code :string)=>{
  toastStore.featureNotImplemented(code);
}

const onRunCode=()=>{
  toastStore.featureNotImplemented("onRunCode");
}

const onSubmit=()=>{
  toastStore.featureNotImplemented("onSubmit");
}



</script>

<style></style>
