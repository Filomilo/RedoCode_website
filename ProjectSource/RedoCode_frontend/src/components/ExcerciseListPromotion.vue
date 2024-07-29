<template>
  <!-- <div class="promotionScrollPanel"> -->
  <ScrollPanel
    style="width: 100%; height: fit-content"
    orientation="horizontal"
  >
    <div class="promotionScrollPanel">
      <div
        class="promotionExcerciseCard"
        v-for="(item, index) in data"
        v-bind:key="index"
      >
        <ExerciseCard :excerciseData="item"></ExerciseCard>
      </div>
    </div>
  </ScrollPanel>
  <!-- </div> -->
</template>

<script setup lang="ts">
  import ExerciseCard from './ExerciseCard.vue'
  import type CodeExerciseTab from '../types/CodeExerciseTab'

  const data: CodeExerciseTab[] = [
    {
      title: 'example task',
      description: 'example task, panel functionality not yet implemented',
      id: 2,
    },
    {
      title: 'example task',
      description: 'example task, panel functionality not yet implemented',
      id: 2,
    },
    {
      title: 'example task',
      description: 'example task, panel functionality not yet implemented',
      id: 2,
    },
    {
      title: 'example task',
      description: 'example task, panel functionality not yet implemented',
      id: 2,
    },
    {
      title: 'example task',
      description: 'example task, panel functionality not yet implemented',
      id: 2,
    },
  ]
</script>
