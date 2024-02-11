<template>
    button
<div :style="buttonStyle" >
{{ height }}
</div>
</template>


<script setup lang="ts">
import { computed } from 'vue';


defineProps<{
  label?: string|null,
  width: string,
  height: string
}>()


const buttonStyle=computed(()=>{
    return "background-color: "+"red"+";"+
    "height: "+"4rem" +";"+
    "width: "+"1rem" +";";
    // return 'background-color: '+'red'+';'+
    // 'height: '+this.height+';'+
    // 'width: '+this.width+';'
})

</script>