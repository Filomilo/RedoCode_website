<template>
  <main>
    test
    <RadioButton :model-value="true" :value="true" />
  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'
const value = ref(true)
</script>

<style></style>
