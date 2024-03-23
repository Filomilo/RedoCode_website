<template>
  <div class="CodeResultContainer" >
    <div class="EngineStatusContianer">
      <div class="EngineStatusPanel">
        <div class="EngineStatusTitle">
            Machine:
        </div>
        <div class="EngineStatusStatus">
          Cpp
      </div>
      </div>



      <div class="EngineStatusPanel">
        <div class="EngineStatusTitle">
            Status:
        </div>
        <div class="EngineStatusStatus">
          Ruuning
      </div>
      </div>
      
    </div>
    <div class="ConsoleResultConsoleCOntainer">
      <div class="ConsoleResultConsoleCOntainerText" v-html="formatted">

      </div>
    </div>
    
  </div>
  
  
  </template>
  
  
  <script setup lang="ts">
import { computed } from 'vue';
const console = `
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        >> hello world
        `;

   
      const formatted=computed<string>(()=> console.replace(/\n/g, '<br>'))

  </script>