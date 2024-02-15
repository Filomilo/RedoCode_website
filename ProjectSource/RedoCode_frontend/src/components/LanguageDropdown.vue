<template>
    <div>
    <dropdown-menu>
      <template #trigger>
        <button>Open Dropdown</button>
      </template>
      
      <!-- <template #header> Dropdown Header </template>
       -->
      <template #body>
        
          <div v-for="i in languageChoices" :key="i" class="excercise-table-row">
            {{i}}
          </div>
       
      </template>
      
      <!-- <template #footer> Dropdown Footer </template> -->
    </dropdown-menu>
</div>
  </template>
  
  <script lang="ts" setup>

  import DropdownMenu from 'v-dropdown-menu'
import 'v-dropdown-menu/css'
 import {languageChoices} from '../config/Data'

  </script>