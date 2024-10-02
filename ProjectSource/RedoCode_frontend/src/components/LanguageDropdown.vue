<template>
  <div>

    <Dropdown
      :v-model="chosenValue"
      :options="languageChoices"
      optionLabel="name"
      optionValue="name"
      class="w-full md:w-14rem"
      :placeholder="chosenValue"
      v-on:update:modelValue="onValueClick"
    />
  </div>
</template>

<script lang="ts" setup>
  import Dropdown from 'primevue/dropdown'
  import { languageChoices } from '../config/Data'
  import { ref, onMounted, type Ref } from 'vue'

  defineProps({
    chosenValue: Object as () => string,
  })
  const emit = defineEmits(['select'])

  const onValueClick = (val: String) => {
    console.log('clicked: ' + val)
    emit('select', val)
  }
</script>
