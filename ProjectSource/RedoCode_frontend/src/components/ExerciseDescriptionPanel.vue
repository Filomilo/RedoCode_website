<template>
  <div class="DesciptionPanelContainer">
    <div class="DesciptionTitleContainer">
      <div class="DesciptionTitle">{{ codeRunnerStore.exerciseData.title }} </div>
    </div>
    <div class="DescriptioNContainer" v-html="desc">

    </div>
  </div>
</template>

<script setup lang="ts">
import MarkdownIt from 'markdown-it'
import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
import { computed } from 'vue';
const codeRunnerStore = useCodeRunnerStore()
const markdown = new MarkdownIt()

const desc=computed(()=> codeRunnerStore.exerciseData.desc.replace('\n',"<br>"))

const mdSrc = `# tile
        descitpiron of taks, create fibonachi sewaunce`
</script>
