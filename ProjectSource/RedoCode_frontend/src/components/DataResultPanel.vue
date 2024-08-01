<template>
  <div class="DataResultContainer">
    <TestResultCard
      v-for="(item, index) in tests"
      :key="index"
      :data="item"
      :index="index"
      :id="'TestResultCard'+index"
    />
  </div>
</template>

<script setup lang="ts">
  import type CodeResultsType from '@/types/CodeResultsType'
  import TestResultCard from './TestResultCard.vue'
  import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'
  import { computed } from 'vue'
  import ExerciseTest from '@/types/ExcericseTest'
  const props = defineProps({
    ManualTests: {
      type: Array as () => ExerciseTest[] | undefined,
      required: true,
    },
    AutoTests: {
      type: Array as () => ExerciseTest[] | undefined,
      required: true,
    },
  })
  const tests = computed(() => {
    if (props.ManualTests !== undefined && props.AutoTests !== undefined) {
      return props.ManualTests.concat(props.AutoTests)
    }
    return []
  })
</script>
