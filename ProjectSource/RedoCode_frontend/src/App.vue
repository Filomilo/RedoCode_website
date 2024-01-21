<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import TopBar from '@/components/TopBar.vue'
</script>

<template>
  <html lang="pl" data-bs-theme="dark">
  <TopBar />
  <RouterView />
  </html>
</template>


<style lang="scss">



</style>