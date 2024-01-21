<template>
   <div>
    {{ text}}
   </div>
</template>


<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
const text=ref('');




onMounted(()=>{
    axios
      .get('https://api.coindesk.com/v1/bpi/currentprice.json')
      .then(response => (text.value = response.data));
})


</script>