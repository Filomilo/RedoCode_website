<template>
<div style="
background-color: pink
">
    result Panel

    <div 
    v-for="(result,index) in resultData"
    :key="index"
    style="
    background-color: green;
    margin: 1rem;
    
    "
    >
        {{ JSON.stringify(result) }}
    </div>
</div>
</template>

<script setup lang="ts">

import type CodeResult from '@/types/CodeResultsType';

defineProps({
    resultData: Object as ()=> CodeResult[]
})


</script>