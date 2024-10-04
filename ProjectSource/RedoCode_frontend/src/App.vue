<script setup lang="ts">
  import { RouterLink, RouterView } from 'vue-router'
  import TopBar from '@/components/TopBar.vue'
  import ChainOfReposibiltyWindow from '@/components/ChainOfReposibiltyWindow.vue'
  import { useGlobalStateStore } from '@/stores/GlobalStateStore'
  import LoadingPanelVue from '@/components/LoadingPanel.vue'
import { useApiConnectionStore } from './stores/ApiConnectionStore'
  const globalStateStore = useGlobalStateStore()
  const ApiConnectionStore = useApiConnectionStore();
</script>

<template>
  <html lang="pl" data-bs-theme="dark">
    <LoadingPanelVue v-if="globalStateStore.loadingMessage !== ''" />
    <ChainOfReposibiltyWindow />
    <div
      id="MainPageContainer"
      :class="globalStateStore.isLocked ? 'lock' : ''"
    >
      <TopBar id="TopBarContainer" />
      <div class="BackGroundContainer">
        <Toast style="margin-top: 4rem" />
        <!-- <div  id="ContentConatiner" style="background-color: red;"></div> -->
        <RouterView id="ContentConatiner" />
      </div>
    </div>
  </html>
</template>

<style lang="scss"></style>
