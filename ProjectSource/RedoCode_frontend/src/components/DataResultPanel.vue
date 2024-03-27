<template>
    <div class="DataResultContainer" >
  <TestResultCard v-for="(item, index) in results" :key="index" :data="item" />
</div>
</template>

<script setup lang="ts">
import type CodeResultsType from "@/types/CodeResultsType";
import TestResultCard from "./TestResultCard.vue";
const props = defineProps<{
    results: CodeResultsType[];
}>();


</script>