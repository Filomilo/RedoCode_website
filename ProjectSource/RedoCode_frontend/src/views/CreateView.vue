<template>
  <main class="PlayGroundBase">
    <CodeRunnerPanel :showLeftPanel="true" :show-creator-panel="true" :connectAtStart="false" />
  </main>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'
import CodeRunnerPanel from '@/components/CodeRunnerPanel.vue'
import { useToastStore } from '@/stores/ToastStore';
import { onBeforeRouteUpdate } from 'vue-router';
const text = ref('')
const ToastStore = useToastStore();

onMounted(()=>{
  ToastStore.featureNotImplemented();
});
</script>
