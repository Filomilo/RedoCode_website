<template>
  <div>
    <!-- <dropdown-menu 
    :withDropdownCloser="true"
    >
      <template #trigger>
        <button>{{chosenValue}}</button>
      </template>

      <template #body>
        
          <div v-for="i in languageChoices" :key="i" >
            <div class="excercise-table-row" @click="onValueClick(i)"   dropdown-closer  >
            {{i}}
        </div>
          </div>
       
      </template>
      
    </dropdown-menu> -->

    <Dropdown :v-model="chosenValue" :options="languageChoices" optionLabel="name" optionValue="name"
      class="w-full md:w-14rem" :placeholder="chosenValue" v-on:update:modelValue="onValueClick" />
  </div>
</template>
  
<script lang="ts" setup>
import Dropdown from 'primevue/dropdown';
import { languageChoices } from '../config/Data'
import { ref, onMounted, type Ref } from 'vue';

const selectedCity: Ref<string> = ref("")

defineProps({
  chosenValue: Object as () => string

})
const emit = defineEmits(['select'])

const onValueClick = (val: String) => {
  console.log("clicked: " + val);
  emit('select', val)
}


</script>