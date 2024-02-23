<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import TopBar from '@/components/TopBar.vue'
</script>

<template>
  <html lang="pl" data-bs-theme="dark">

  <div id="MainPageContainer">
    <TopBar id="NavigationConatiner"/>
     
  <RouterView id="ContentConatiner"/>
  <!-- <div "  />
  <div id="ContentConatiner"  /> -->
    <!-- <div style="height: 100vh; width: 100vw; background-color: rgb(30, 114, 89)" /> -->
  </div>
  </html>
</template>


<style lang="scss">



</style>