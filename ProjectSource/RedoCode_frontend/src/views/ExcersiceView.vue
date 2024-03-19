<template>

<main class="PlayGroundBase">
<CodeRunnerPanel :showLeftPanel="true" :connectAtStart="false" />
</main>
</template>


<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import CodeRunnerPanel from '@/components/CodeRunnerPanel.vue';
const text=ref("")


onMounted(()=>{
   console.log("axios")
    axios
      .get('http://localhost:8080/exercises')
      .then(response => (text.value = response.data));
})


</script>