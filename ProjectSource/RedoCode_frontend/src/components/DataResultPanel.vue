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
import ExerciseTest from '@/types/ExcericseTest';
const props = defineProps({
  ManualTests: {type: Array as () => ExerciseTest[], required: true},
  AutoTests: {type: Array as () => ExerciseTest[], required: true},
})
const tests = computed(() =>
props.ManualTests.concat(props.AutoTests)

)
</script>
