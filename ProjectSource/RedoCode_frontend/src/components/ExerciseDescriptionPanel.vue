<template>
  <div class="DesciptionPanelContainer">
    <div class="DesciptionTitleContainer">
      <div class="DesciptionTitle">{{ exerciseInfo.title }}</div>
    </div>
    <div class="DescriptioNContainer" v-html="desc"></div>
  </div>
</template>

<script setup lang="ts">
import MarkdownIt from 'markdown-it'
import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
import { computed } from 'vue'
import ExerciseData from '@/types/ExerciseData'
import IExerciseDescriptionI from '@/types/IExerciseDescriptionI'

const props = defineProps({
  exerciseInfo: { type: Object as () => IExerciseDescriptionI, required: true }
})

const codeRunnerStore = useCodeRunnerStore()
const markdown = new MarkdownIt()

const desc = computed(() => props.exerciseInfo.desc.replace('\n', '<br>'))

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
