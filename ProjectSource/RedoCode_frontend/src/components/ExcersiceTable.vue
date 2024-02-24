<template>
    
                <EasyDataTable
              :headers="fields"
              :items="data"
              alternating
              buttons-pagination
            >
        
            <template #expand="item">
              {{ item.description }}
              </template>

              <template #loading>
                <LoadingIndicator/>
            </template>
            <template #item-actions="item">
                <Button  v-on:click="onRowClick(item.id)" style="background-color: transparent;  border-color: transparent;" >
                  <Play />
                </Button>
            </template>

            
        </EasyDataTable>
       
          


    <!-- <div class="excercise-table-container" v-if="data.length > 0">

        <table class="table">
            <thead>
                <tr>
                    <th v-for="(item, index) in  Object.keys(data[0])" scope="col" :key="index">
                        {{ item }}
                    </th>
                </tr>
            </thead>
            <tbody>

                <tr v-for="item in  data" :key="item.id" v-on:click="onRowClick(item.id)" class="excercise-table-row">
                    <th v-for="(elemnt, index) in  item" scope="col" :key="index">
                        {{ elemnt }}
                    </th>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="excercise-table-container" v-else>
        <LoadingIndicator />
    </div> -->
</template>



<script setup lang="ts">
import type ExerciseType from "@/types/ExerciseType";
import type OnTableClickType from "@/types/OnTableClickType"
import LoadingIndicator from "./LoadingIndicator.vue";
import type { ref } from "vue";
import type { Header, Item } from "vue3-easy-data-table";
import Button from 'primevue/button';
import Play from '../assets/icons/play.vue'
const fields: any[] = [
    { text: "Name", value: "name" },
    { text: "language", value: "language" },
    { text: "difficulty", value: "difficulty" },   
    { text: "", value: "actions", width: 30 },          
// 'name' ,
// 'language',
// 'difficulty' ,
// 'actions',
    
];
const items: Item[] = [
  { player: "Stephen Curry", team: "GSW", number: 30, position: 'G', indicator: {"height": '6-2', "weight": 185}, lastAttended: "Davidson", country: "USA"},
  { player: "Lebron James", team: "LAL", number: 6, position: 'F', indicator: {"height": '6-9', "weight": 250}, lastAttended: "St. Vincent-St. Mary HS (OH)", country: "USA"},
  { player: "Kevin Durant", team: "BKN", number: 7, position: 'F', indicator: {"height": '6-10', "weight": 240}, lastAttended: "Texas-Austin", country: "USA"},
  { player: "Giannis Antetokounmpo", team: "MIL", number: 34, position: 'F', indicator: {"height": '6-11', "weight": 242}, lastAttended: "Filathlitikos", country: "Greece"},
];


const headers: Header[] = [
  { text: "PLAYER", value: "player" },
  { text: "TEAM", value: "team"},
  { text: "NUMBER", value: "number"},
  { text: "POSITION", value: "position"},
  { text: "HEIGHT", value: "indicator.height"},
  { text: "WEIGHT (lbs)", value: "indicator.weight", sortable: true},
  { text: "LAST ATTENDED", value: "lastAttended", width: 200},
  { text: "COUNTRY", value: "country"},
];


defineProps<{
    data: ExerciseType[],
    onRowClick: OnTableClickType
}>()



</script>