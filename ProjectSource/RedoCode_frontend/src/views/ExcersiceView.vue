<template>
  <main class="PlayGroundBase">
    <CodeRunnerPanel :showLeftPanel="true" :connectAtStart="false" />
  </main>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRoute } from 'vue-router'
import CodeRunnerPanel from '@/components/CodeRunnerPanel.vue'
import LoadingIndicator from '@/components/LoadingIndicator.vue';
import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'
const codeRunnerStore= useCodeRunnerStore()
const text = ref('')
const route = useRoute()


const fetchExerciseData=(id: number)=>{
  codeRunnerStore.exerciseLoading=true;

  const params={
    id: route.params.id
  };
  axios.get('http://localhost:8080/exerciseData/',{params: params})
  .then((response) => 
  {
    codeRunnerStore.setExerciseData(response.data)
    console.log("data: "+JSON.stringify(response))
    codeRunnerStore.exerciseLoading=false;
  })
}


onMounted(() => {
  console.log('axios')
  console.log(route.params.id)
  fetchExerciseData(route.params.id)
  
})
</script>
