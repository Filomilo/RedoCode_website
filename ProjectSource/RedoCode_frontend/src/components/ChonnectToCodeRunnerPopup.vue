<template>
<Dialog v-model:visible="props.show" modal header="Edit Profile" :style="{ width: '25rem' }">
test
</Dialog> 
</template>

<script setup lang="ts">
const props = defineProps({
    show: { type: Boolean, required: false },
})


    


</script>