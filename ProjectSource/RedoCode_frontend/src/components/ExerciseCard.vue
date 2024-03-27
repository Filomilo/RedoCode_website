<template>
  <div class="titleContainer">
    <div class="title">
      {{ excerciseData.title }}
    </div>
    <div class="button">
      <IconPlay width="6rem" class="svg-icon" style="fill: white" />
    </div>
  </div>
  <div class="content">
    {{ excerciseData.description }}
  </div>
</template>

<script setup lang="ts">
import IconPlay from '@/assets/icons/IconPlay.vue'
import type CodeExerciseTab from '../types/CodeExerciseTab'
defineProps<{
  excerciseData: CodeExerciseTab
}>()
</script>
