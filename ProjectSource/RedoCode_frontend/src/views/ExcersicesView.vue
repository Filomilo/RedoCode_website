<template>
    <button 
  class="btn btn-primary" 
  data-bs-target="#collapseTarget" 
  data-bs-toggle="collapse">
  Bootstrap collapse
</button>
<ExcersiceTable :data="exerciseData" :onRowClick="onExcersiceButton"></ExcersiceTable>
    

</template>


<script setup lang="ts">
import type ExerciseType from "@/types/ExerciseType";
import ExcersiceTable from "@/components/ExcersiceTable.vue"
import { useRouter } from "vue-router";

const router = useRouter();

const onExcersiceButton=(id: number)=>{
    router.push({ name: "Exercise", params:{id: id} });
}



const exerciseData:ExerciseType[] =[
    {
        name: "task1",
        language: "c++",
        difficulty: "hard",
        popularity: 222,
        id: 1,
    },
    {
        name: "task2",
        language: "any",
        difficulty: "hard",
        popularity: 222122,
        id: 2
    },
    {
        name: "task3",
        language: "c++",
        difficulty: "easy",
        popularity: 23,
        id: 3
    }
]


const HeadType: string="Dark"

</script>@/types/ExerciseType