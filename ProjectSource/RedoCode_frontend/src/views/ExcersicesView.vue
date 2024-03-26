<template>
  <main class="PlayGroundBase">
<ExcersiceTable :data="exerciseData" :onRowClick="onExcersiceButton"></ExcersiceTable>
</main>

</template>


<script setup lang="ts">
import type ExerciseType from "@/types/ExerciseType";
import ExcersiceTable from "@/components/ExcersiceTable.vue"
import { onBeforeRouteUpdate, useRouter } from "vue-router";
import { onMounted, ref } from "vue";
import axios from "axios";

const router = useRouter();

const onExcersiceButton=(id: number)=>{
    router.push({ name: "Exercise", params:{id: id} });
}
const exerciseData =ref<ExerciseType[]>([]);



onMounted(()=>{
  console.log("On mounted")
    axios
      .get('http://localhost:8080/exercises')
      .then(response => {
    
        if(response===undefined){
            console.error("couldn't retrieve excercise list from server")
        throw "couldn't retrieve excercise list from server"
      }
        exerciseData.value = response.data
    });
})



/*
onBeforeRouteUpdate(()=>{
  console.log("On mounted")
});
*/
const HeadType: string="Dark"

</script>@/types/ExerciseType