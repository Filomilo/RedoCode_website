<template>
  <main>
    <div class="dountContainer">
      <div
        class="dount"
        :style="
          `    background: conic-gradient(
            ` +
          props.fillColor +
          ` 0% ` +
          procent +
          `,
            ` +
          props.basicColor +
          ` 0% 100%
          );
          -webkit-mask-image: radial-gradient(circle, transparent ` +
          props.radius +
          `%, black ` +
          (radius + 1) +
          `%);
          mask-image: radial-gradient(circle, transparent ` +
          props.radius +
          `%, black ` +
          (radius + 1) +
          `%);
          
          `
        "
      ></div>
      <label class="dount-inner">
        {{ label }}
      </label>
    </div>
  </main>
</template>

<script setup lang="ts">
  import { Doughnut } from 'vue-chartjs'
  import { computed, Ref, ref, ComputedRef } from 'vue'

  const props = defineProps<{
    value: number
    maxValue: number
    label: string
    fillColor: string
    basicColor: string
    radius: number
  }>()

  const procent: ComputedRef<string> = computed(() => {
    return (props.value / props.maxValue) * 100 + '%'
  })
</script>

<style lang="css">
  .dountContainer {
    display: flex;
    height: 100%;
    width: 100%;
    min-width: 100%;
    max-width: 100%;
    min-height: 100%;
    max-height: 100%;
    aspect-ratio: 1/1;
    justify-self: center;
    align-self: center;
  }

  .dount {
    flex: 1;
    border-radius: 50%;
    position: relative;

    min-width: 100%;
    max-width: 100%;
    min-height: 100%;
    max-height: 100%;
  }
  .dount-inner {
    border-radius: 50%;
    flex: 1;
    position: relative;
    min-width: 90%;
    max-width: 90%;
    min-height: 90%;
    max-height: 90%;
    aspect-ratio: 1 /1;

    top: 5%;
    right: 95%;
    z-index: 1;
    align-items: center;
    justify-content: center;
    align-content: center;
    display: flex;
  }
</style>
