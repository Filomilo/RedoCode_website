<script setup lang='ts'>
import {ref} from 'vue'
console.log("test home");

const name=ref('test')

</script>


<template>
test

<p> text is: {{ name }}</p>
<input v-model="name" />
</template>