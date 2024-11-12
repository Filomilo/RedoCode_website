<template>
  <main>
    <CodeEditor
      class="CodeEditorContainer"
      :starting="''"
      :codeUpdateMethod="() => {}"
      :onRunCode="() => {}"
      :languageChoices="[]"
    />
  </main>
</template>

<script setup lang="ts">
  import CodeEditorPanel from '@/components/CodeEditorPanel.vue'
  import { useRouter, useRoute } from 'vue-router'
  import CodeEditor from '@/components/CodeEditorPanel.vue'
  import { onMounted, PropType, computed } from 'vue'
  import { onBeforeRouteLeave } from 'vue-router'
  import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
  import { useApiConnectionStore } from '@/stores/ApiConnectionStore'
  import IExerciseDescriptionI from '@/types/IExerciseDescriptionI'
  import ExerciseTest from '@/types/ExerciseTest'
  import codeRunnerType from '@/types/CodeRunnerTypes'
  import { ComputedRef } from 'vue'
  import TestsResultsMessage from '@/types/ApiMessages/ProgramResultsMessage'
  import { ConsoleOutput } from '@/types/ProgramResults'
  import CodeRunnerStatus from '@/types/CodeRunnerStatus'
  const router = useRouter()
  const route = useRoute()
  const onGoToLogin = () => {
    router.replace('Home')
  }
</script>

<style>
  .MainContainer {
    width: 100%;
    display: flex;
    align-items: center;
    align-content: center;
    justify-content: center;
    height: 100%;
  }

  .MessageContainer {
    width: 70%;
    background-color: rgb(20, 20, 20);
    border-radius: 3rem;
    border: 0.11rem solid var(--primary);
    display: flex;
    align-items: center;
    flex-direction: column;
    font-size: large;
    height: fit-content;
    padding: 3rem;
  }

  #GotToLogin {
    background-color: var(--primary);
    border: 0;
    color: white;
    border-radius: 0.5rem;
    widows: 10%;
    margin: 3rem;
  }
</style>
