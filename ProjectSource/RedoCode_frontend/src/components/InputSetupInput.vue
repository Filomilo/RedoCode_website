<template>
  <InputNumber v-model="val" inputId="integeronly" class="InputSizeContainer" />
</template>

<script lang="ts" setup>
import { ref } from 'vue'
const val = ref(0)
</script>

<style>
.dimensionsPanel {
  display: flex;
}

.rowContainer {
  margin: 0.5rem;
  display: flex;
  flex-direction: row;
}
.signleValContainer {
  background-color: rgb(43, 29, 13);
  margin: 0.5rem;
}

.InputSizeContainer {
  margin-right: -5rem;
}

.InputSizeContainer * {
  width: 2rem;
  margin-right: -1rem;
}

.inputCotainer {
  overflow: scroll;
}
</style>
