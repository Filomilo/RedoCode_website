<template>
  <div :class="classStyle">
    <img :src="imgURL" class="circleContainer" />
    <div class="UserNameContainer">
      {{ solutionItem.username }}
    </div>
    <ProcentDonout
      :value="props.solutionItem.executionTimeMs"
      :maxValue="props.maxExecutionTime"
      :label="props.solutionItem.executionTimeMs + ' ms'"
      :fillColor="
        getColorSelectionFromProcent(
          props.solutionItem.executionTimeMs / props.maxExecutionTime
        )
      "
      basicColor="#DDDDDD"
      :radius="55"
      class="circleContainer cirlceText"
    />
  </div>
</template>

<script setup lang="ts">
  import SolutionItemList from '@/types/ApiMesseages/SolutionItemList'
  import { computed, ref, type Ref } from 'vue'
  import ProcentDonout from '@/components/ProcentDonout.vue'
  import chroma from 'chroma-js'
  import profilePicImageResolve from '@/tools/ImageResolve'

  const props = defineProps<{
    solutionItem: SolutionItemList
    maxExecutionTime: number
    selected: boolean
  }>()
  console.log(JSON.stringify(props.selected))
  const classStyle = computed(() => {
    return 'SolutionContainer ' + (props.selected ? ' Selected' : '')
  })

  const imgURL = computed(() => {
    return profilePicImageResolve(props.solutionItem.profilePic)
  })

  const gradient = chroma.scale(['#00ff00', '#ff0000']).mode('lab').colors(10)
  console.log(`geadint: ${JSON.stringify(gradient)}`)
  const getColorSelectionFromProcent = (val: number): string => {
    const index: number = Math.floor(val * 10)
    return gradient[index]
  }
</script>

<style lang="css">
  .circleContainer {
    border-radius: 50%;
    height: 100%;
    justify-self: center;
    align-self: center;
    aspect-ratio: 1/1;
    align-self: flex-end;
    overflow: hidden;
  }

  .SolutionContainer {
    height: 3rem;
    display: flex;
    flex-direction: row;
    overflow-x: hidden;
    background-color: rgb(31, 31, 31);
    margin-top: 0.04rem;
    border-radius: 0.7rem;
    margin-bottom: 0.5rem;
    transition: background-color 0.1s ease;
  }
  .SolutionContainer:hover {
    cursor: pointer;
    background-color: rgb(39, 39, 39);
  }

  .UserNameContainer {
    width: fit-content;
    display: flex;
    justify-content: center;
    align-items: center;
    justify-self: flex-end;
    flex: 1;
    border-radius: 0.6rem;
  }

  .Selected {
    background-color: #6b4870;
    cursor: auto;
  }
  .Selected:hover {
    background-color: #6b4870;
    cursor: auto;
  }
  .cirlceText {
    font-size: 0.7rem;
  }
</style>
