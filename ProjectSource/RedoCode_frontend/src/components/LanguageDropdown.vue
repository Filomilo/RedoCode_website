<template>
    <div>
    <dropdown-menu 
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
      
    </dropdown-menu>
</div>
  </template>
  
  <script lang="ts" setup>

  import DropdownMenu from 'v-dropdown-menu'
import 'v-dropdown-menu/css'
 import {languageChoices} from '../config/Data'
import { ref,onMounted, type Ref } from 'vue';


  defineProps({
    chosenValue: Object as Ref<String>

  })
const emit=defineEmits(['select'])

  const onValueClick=(val: String)=>{
            console.log("clicked: "+ val);
       emit('select',val)
  }


  </script>