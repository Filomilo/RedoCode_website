<template>
  <main>
    <div class="topPanel">
      <div class="titleContainer" id="solutions-exercise-title">
        {{ props.title }}
      </div>
      <div class="descriptionContainer" id="solutions-exercise-desc">
        {{ props.description }}
      </div>
    </div>
  </main>
</template>

<script setup lang="ts">
  const props = defineProps<{
    title: string
    description: string
  }>()
</script>

<style lang="css">
  .topPanel {
    width: 100%;
    min-width: 100%;
    display: flex;
    flex-direction: column;
    width: 90vw;
    border-end-end-radius: 3rem;
    border-bottom-left-radius: 3rem;
    background-color: rgb(40, 40, 40);
    box-shadow: 0px 0px 10px rgb(14, 14, 14);
  }

  .titleContainer {
    font-size: 2rem;
    display: flex;
    justify-content: center;
  }

  .descriptionContainer {
    font-size: 1rem;
    display: flex;
    justify-content: center;
  }
</style>
