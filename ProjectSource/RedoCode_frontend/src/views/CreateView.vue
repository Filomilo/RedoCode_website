<template>

    <main class="PlayGroundBase">
    <CodeRunnerPanel :showLeftPanel="true" :show-creator-panel="true" :connectAtStart="false" />
    </main>
    </template>
    
    
    <script setup lang="ts">
    import { ref, onMounted } from 'vue';
    import axios from 'axios';
    import CodeRunnerPanel from '@/components/CodeRunnerPanel.vue';
    const text=ref("")
    
    </script>