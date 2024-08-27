<script setup lang="ts">
  import { RouterLink, RouterView } from 'vue-router'
  import TopBar from '@/components/TopBar.vue'
  import ChainOfReposibiltyWindow from '@/components/ChainOfReposibiltyWindow.vue'
  import { useExecutionChainStore } from '@/stores/ExecutionChainStore'
  const executionChainStore = useExecutionChainStore()
</script>

<template>
  <html lang="pl" data-bs-theme="dark">
    <ChainOfReposibiltyWindow />
    <div
      id="MainPageContainer"
      :class="executionChainStore.lock ? 'lock' : ''"
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
