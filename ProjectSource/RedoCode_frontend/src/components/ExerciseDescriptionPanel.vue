<template>
  <div class="DesciptionPanelContainer">
    <div class="DesciptionTitleContainer">
      <div class="DesciptionTitle" v-if="!isInEdit">{{ codeRunnerStore.exerciseData.title }}</div>
      <div v-else class="DesciptionTitle titleEdit">
        <InputText class="DesciptionTitle titleEdit" />
      </div>
    </div>
    <div v-if="!isInEdit" class="DescriptioNContainer" v-html="desc"></div>
    <div class="descriptorEdit" v-else>
      <Textarea class="descriptorEdit" />
    </div>
  </div>
</template>

<script setup lang="ts">
import MarkdownIt from 'markdown-it'
import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
import { computed } from 'vue'
const codeRunnerStore = useCodeRunnerStore()
const markdown = new MarkdownIt()
const props = defineProps({
  isInEdit: { type: Boolean, required: false }
})

const desc = computed(() => codeRunnerStore.exerciseData.desc.replace('\n', '<br>'))

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
