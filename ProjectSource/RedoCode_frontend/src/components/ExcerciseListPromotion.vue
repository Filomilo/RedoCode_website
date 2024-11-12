<template>
  <ScrollPanel
    style="width: 100%; height: fit-content; margin-top: 6rem"
    orientation="horizontal"
  >
    <div class="promotionScrollPanel">
      <div
        class="promotionExcerciseCard"
        v-for="(item, index) in promtionData"
        v-bind:key="index"
      >
        <ExerciseCard :excerciseData="item"></ExerciseCard>
      </div>
    </div>
  </ScrollPanel>
</template>

<script setup lang="ts">
  import ExerciseCard from './ExerciseCard.vue'
  import type CodeExerciseTab from '../types/CodeExerciseTab'
  import { computed, Ref, ref, ComputedRef, watch, onMounted } from 'vue'
  import EndpointAccess from '@/controllers/EndpointsAccess'
  import { useToastStore } from '@/stores/ToastStore'
  import { PromotionDataMessage } from '@/types/ApiMessages/PromotionDataMessage'

  const ToastStore = useToastStore()

  const promtionData: Ref<CodeExerciseTab[]> = ref([])

  onMounted(() => {
    fetchPromotionData()
  })
  const fetchPromotionData = () => {
    EndpointAccess.unauthorized
      .getPromotionExercises()
      .then((x: PromotionDataMessage) => {
        promtionData.value = x.promotions
      })
      .catch(ex => {
        ToastStore.showErrorMessage("Couldn't fetch promotion data ")
      })
  }
</script>
