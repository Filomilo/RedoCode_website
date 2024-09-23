<template>
  <div class="DataResultContainer">
    {{ JSON.stringify(props.AutoTests) }}
    <TestResultCard
      v-for="(item, index) in tests"
      :key="index"
      :data="item"
      :index="index"
      :id="'TestResultCard' + index"
    />
  </div>
</template>

<script setup lang="ts">
    import TestResultCard from './TestResultCard.vue'
  import { computed } from 'vue'
  import ExerciseTest from '@/types/ExerciseTest'
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
    if (props.ManualTests !== undefined) {
      if (props.AutoTests !== undefined)
        return props.ManualTests.concat(props.AutoTests)
      return props.ManualTests
    }
    return []
  })
</script>
