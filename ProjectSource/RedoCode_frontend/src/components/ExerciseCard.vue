<template>
  <div class="ExerciseCard">
    <div class="ExerciseCardTopBar">
      <div class="topBarInfoSection">
        <div class="ExerciseCardTile">
          {{ excerciseData.title }}
        </div>
        <div class="ExerciseCardLangaugesConatinenr">
          <label v-for="lang in excerciseData.languages"> {{ lang }}, </label>
        </div>
      </div>
      <div class="topBarButonSection">
        <div class="button">
          <RouterLink :to="`/Exercises/${excerciseData.id}`">
            <IconPlay width="6rem" class="svg-icon" style="fill: white" />
          </RouterLink>
        </div>
      </div>
    </div>
    <div class="DescritpionCardSection">
      {{ excerciseData.description }}
    </div>
  </div>
  <!-- <div class="ExerciseCardTopBar">
    <div class="titleContainer">
      <div class="title">
        {{ excerciseData.title }}
      </div>
    
  </div>

  </div>
  <div class="content">
    
  </div> -->
</template>

<script setup lang="ts">
  import IconPlay from '@/assets/icons/IconPlay.vue'
  import type CodeExerciseTab from '../types/CodeExerciseTab'
  defineProps<{
    excerciseData: CodeExerciseTab
  }>()
</script>

<style lang="css">
  .ExerciseCard {
    height: 100%;
    display: flex;
    flex-direction: column;
  }
  .ExerciseCardTopBar {
    border-bottom: 0.3rem solid var(--primary);
    display: flex;
    margin: 1rem;
  }
  .DescritpionCardSection {
    height: 100%;
    overflow-y: scroll;
    margin: 0.6rem;
  }

  .topBarInfoSection {
    flex: 1;
  }

  .topBarButonSection {
  }

  .ExerciseCardTile {
    font-size: 2rem;
    color: var(--primary);
  }
  .ExerciseCardLangaugesConatinenr {
  }
</style>
