<template>
  <main>
    <div class="SolutionListContainer">
      <div
        v-for="(element, index) in solutionList"
        v-bind:key="element.solutionId"
      >
        <SolutionItemList
          :solutionItem="element"
          :maxExecutionTime="props.maxExecutionTime"
          :selected="model === element.solutionId"
          @click="model = element.solutionId"
          :id="'solution-list-item-' + index"
        />
      </div>
    </div>
  </main>
</template>

<script setup lang="ts">
  import SolutionItemList from '@/components/SolutionItemList.vue'
  import type SolutionItemListType from '@/types/ApiMessages/SolutionItemList'
  const props = defineProps<{
    solutionList: SolutionItemListType[]
    maxExecutionTime: number
  }>()
  const model = defineModel()
</script>

<style lang="css">
  .SolutionListContainer {
    overflow: scroll;
  }
</style>
