<template>
    <div class="DiffConatiner">
        <header>
            {{difficultyName}}
        </header>
    <div
    class="DiffucltyBarContainer"
    >
    <!-- Diff:
 {{ props.difficulty }} -->
  <div class="BasicDiffSquare" :style="getStyleOveride(0)"/>
  <div class="BasicDiffSquare" :style="getStyleOveride(1)"/>
  <div class="BasicDiffSquare" :style="getStyleOveride(2)"/>
  <div class="BasicDiffSquare" :style="getStyleOveride(3)"/>
  <div class="BasicDiffSquare" :style="getStyleOveride(4)"/>
    </div>
</div>
  </template>
  
  <script setup lang="ts">
    import LanguageDropdown from './LanguageDropdown.vue'
    import { computed, Ref, ref, ComputedRef, watch } from 'vue'
    import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
    import { useApiConnectionStore } from '@/stores/ApiConnectionStore'
    import { languageDropDownType } from '@/types/CodeRunnerTypes'
    import codeRunnerType from '@/types/CodeRunnerTypes'
    import { languageChoices } from '@/config/Data'
    import LangaugeSelection from '@/tools/LangaugeSelection'
    import { stringify } from 'flatted'
    import { useActiveUserStore } from '@/stores/ActiveUserStore'
    const props = defineProps({
      difficulty: {
        type: Number,
        required: true,
      },
    })

    const getStyleOveride=(index:number):string=>{
        if(Math.floor(props.difficulty)>=index+1)
        return "";
        if(Math.ceil(props.difficulty)<index+1)
        return "background-color: grey;";
        const procent=props.difficulty-Math.floor(props.difficulty);

        return `border-right: calc(0.7rem - ${0.7*procent}rem) solid grey;`;
    }
    const difficultyName=computed(()=>{
        const rounded=Math.round(props.difficulty);
        switch(rounded)
        {
            case 0: return "Not rated";
            case 1: return "Very easy";
            case 2: return "Easy";
            case 3: return "Normal";
            case 4: return "Hard";
            case 5: return "Very hard";
        }
    })
  </script>
  
  <style>
  .DiffConatiner{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    align-content: center;
    width: fit-content;
  }
  .DiffucltyBarContainer{
    height: 100%;
    width: 100%;

    

    display: flex;
    
  }
    .BasicDiffSquare {
     background: red;
     height: 0.7rem;
     margin: 0.1rem;
     aspect-ratio: 1/1;
     border-radius: 0.1rem;
     background-color: var(--primary);

    }
  </style>
  