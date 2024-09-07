<template>
  <div class="LoadingPanelContainer">
    <div class="panelContainer" id="loading-panel">
      <div class="LoadingIndicator">
        <LoadingIndicator />
      </div>
      <div class="textConatiner">
        {{ loadingMessage }}
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import LoadingIndicator from '@/components/LoadingIndicator.vue'
  import { useGlobalStateStore } from '@/stores/GlobalStateStore'
  import { computed } from 'vue'
  const globalStateStore = useGlobalStateStore()

  const loadingMessage = computed(() => {
    return globalStateStore.loadingMessage
  })
</script>

<style lang="css">
  .LoadingPanelContainer {
    width: 100vw;
    height: 100vh;
    position: absolute;
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 5;
  }

  .LoadingIndicator {
    width: 10rem;
    height: 10rem;
    margin: 3rem;
  }

  .panelContainer {
    background-color: rgb(33, 33, 33);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border-radius: 0.8rem;
  }
  .textConatiner {
    color: white;
    margin: 3rem;
    color: var(--primary-color);
  }
</style>
