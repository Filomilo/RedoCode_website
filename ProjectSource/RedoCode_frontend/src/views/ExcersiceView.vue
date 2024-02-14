<template>
   <main>
      {{ JSON.stringify(text) }}
        </main>
</template>


<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';

const text=ref("")


onMounted(()=>{
   console.log("axios")
    axios
      .get('http://localhost:8080/exercises')
      .then(response => (text.value = response.data));
})


</script>