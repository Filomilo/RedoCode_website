<template>
  <div class="excercise-table-container" v-if="data.length>0">
<table class="table">
    <thead>
        <tr>
        <th v-for="(item,index) in  Object.keys(data[0])" scope="col" :key="index" >
        {{ item }}
        </th>
    </tr>
    </thead>
    <tbody >
        
        <tr  v-for="item in  data"  :key="item.id" v-on:click="onRowClick(item.id)" class="excercise-table-row" >
        <th v-for="(elemnt,index) in  item" scope="col" :key="index">
            {{ elemnt }}
            </th>
        </tr>
    </tbody>
    </table>
</div>
<div class="excercise-table-container" v-else>
    <LoadingIndicator/>
</div>
</template>



<script setup lang="ts">
import type ExerciseType from "@/types/ExerciseType";
import type OnTableClickType from "@/types/OnTableClickType"
import LoadingIndicator from "./LoadingIndicator.vue";
import type { ref } from "vue";

const fields: any[]= [
    {key: 'name'}, 
{
    key: 'language',
    label: 'language'
},
 {key: 'difficulty'},
  {key: 'actions',
    label: ''
}
];
defineProps<{
  data:  ExerciseType[],
  onRowClick: OnTableClickType
}>()



</script>