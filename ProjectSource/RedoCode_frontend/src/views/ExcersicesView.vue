<template>

<ExcersiceTable :data="exerciseData" :onRowClick="onExcersiceButton"></ExcersiceTable>
    

</template>


<script setup lang="ts">
import type ExerciseType from "@/types/ExerciseType";
import ExcersiceTable from "@/components/ExcersiceTable.vue"
import { useRouter } from "vue-router";
import { onMounted, ref } from "vue";
import axios from "axios";

const router = useRouter();

const onExcersiceButton=(id: number)=>{
    router.push({ name: "Exercise", params:{id: id} });
}



const exerciseData =ref<ExerciseType[]>([]);

onMounted(()=>{
    axios
      .get('/RedoCodeBacked/exerciseList')
      .then(response => {
        if(response===undefined){
            console.error("couldn't retrieve excercise list from server")
        throw "couldn't retrieve excercise list from server"
      }
        exerciseData.value = response.data
    });
})



const HeadType: string="Dark"

</script>@/types/ExerciseType