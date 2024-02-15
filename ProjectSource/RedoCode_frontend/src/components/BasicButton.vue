<template>
  
<div class="BasicButton"  v-on:click="onClickMethod">
  <div class="buttonContent">
  <slot></slot>
</div>
</div>
</template>


<script setup lang="ts">
import { computed } from 'vue';


const props = defineProps({
  onClick: {type: Function, required: true}
})

const onClickMethod=()=>{
  console.log("click")
  props.onClick();
}




</script>