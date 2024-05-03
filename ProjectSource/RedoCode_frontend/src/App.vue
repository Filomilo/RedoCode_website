<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import TopBar from '@/components/TopBar.vue'
</script>

<template>
  <html lang="pl" data-bs-theme="dark">
    <div id="MainPageContainer">
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
