<template>
  <div class="panelsContainer">
    <InputText
      v-model="codeRunnerStore.exerciseCreatorController.title"
      placeholder="Exercise name"
      class="titleContainer"
      style="height: 3rem"
      maxlength="40"
      minlength="2"
      id="Exercise-title-input"
    />

    <Textarea
      v-model="codeRunnerStore.exerciseCreatorController.description"
      rows="5"
      cols="30"
      placeholder="Exercise dexcription"
      maxlength="2500"
      class="descritptionConatiner"
      style="height: 100%"
      id="Exercise-description-input"
    />
  </div>
</template>

<script setup lang="ts">
  import { ref } from 'vue'
  import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'

  const codeRunnerStore = useCodeRunnerStore()

  const value = ref(null)
</script>

<style>
  .p-inputtext {
    height: 3rem;
    font-size: 1.5rem;
  }

  .panelsContainer {
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;
    min-width: 100%;
    background-color: rgb(81, 133, 161);
  }
  .titleContainer * {
    height: 1rem;
    max-height: 1rem;
    background-color: red;
  }
  .descritptionConatiner {
    height: 100rem;
    font-size: 1.2rem;
  }
</style>
