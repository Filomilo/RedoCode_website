<template>
  <div class="DesciptionPanelContainer">
    <div class="DesciptionTitleContainer">
      <div class="DesciptionTitle" id="exercise-title">
        {{ exerciseInfo.title }}
      </div>
    </div>
    <div
      class="DescriptioNContainer"
      v-html="desc"
      id="exercise-description"
    ></div>
  </div>
</template>

<script setup lang="ts">
  import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
  import { computed } from 'vue'
  import IExerciseDescriptionI from '@/types/IExerciseDescriptionI'

  const props = defineProps({
    exerciseInfo: {
      type: Object as () => IExerciseDescriptionI,
      required: true,
    },
  })

  const codeRunnerStore = useCodeRunnerStore()

  const desc = computed(() =>{
    console.log("IExerciseDescriptionI: "+ JSON.stringify(props.exerciseInfo))
    if(props.exerciseInfo.description==undefined)
    return "";
    return props.exerciseInfo.description.replace('\n', '<br>')}
  )

  const mdSrc = `# tile
        descitpiron of taks, create fibonachi sewaunce`
</script>

<style>
  .titleEdit {
    height: 100%;
    width: 100%;
    background-color: transparent;
  }
  .descriptorEdit {
    height: 100%;
    width: 100%;
    background-color: transparent;
  }
</style>
