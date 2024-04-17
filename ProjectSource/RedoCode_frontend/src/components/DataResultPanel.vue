<template>
  <div class="DataResultContainer">
    <TestResultCard v-for="(item, index) in tests" :key="index" :data="item" />
  </div>
</template>

<script setup lang="ts">
import type CodeResultsType from '@/types/CodeResultsType'
import TestResultCard from './TestResultCard.vue'
import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'
import { computed } from 'vue'
const codeRunnerStore = useCodeRunnerStore()
const tests = computed(() =>
  codeRunnerStore.exerciseData.tests.concat(codeRunnerStore.exerciseData.automaticTests)
)
</script>
