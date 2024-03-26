<template>
    <div class="datatable-container">
                <EasyDataTable
              :headers="fields"
              :items="data"
              alternating
              buttons-pagination
              
              class="dataTableStyle"
            >
        
            <template #expand="item">
              {{ item.description }}
              </template>

              <template #loading>
                <LoadingIndicator/>
            </template>
            <template #item-actions="item">
                <Button  v-on:click="onRowClick(item.id)" style="background-color: transparent;  border-color: transparent; fill: white;" >
                  <IconPlay height="1.3rem" />
                </Button>
            </template>

            
            <template #pagination="{ prevPage, nextPage, isFirstPage, isLastPage }">
                <div class="paginatorButtons">
                <Button :disabled="isFirstPage" @click="prevPage">
                  <IconNextLeft />
                </Button>
                <Button :disabled="isLastPage" @click="nextPage">
                    <IconNextRight />
                </Button>
            </div>



            <router-link to="/Create" class="createButton" id="Home_Button">
                <Button >
                    Create
                </Button>
            </router-link>
    
          
              </template>
            
        </EasyDataTable>
    </div>
          



</template>



<script setup lang="ts">
import type ExerciseType from "@/types/ExerciseType";
import type OnTableClickType from "@/types/OnTableClickType"
import LoadingIndicator from "./LoadingIndicator.vue";
import type { ref } from "vue";
import type { Header, Item } from "vue3-easy-data-table";
import Button from 'primevue/button';
import IconNextRight from  '../assets/icons/IconNextRight.vue'
import IconNextLeft from  '../assets/icons/IconNextLeft.vue'
import IconPlay from '@/assets/icons/IconPlay.vue';

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

<style>


.dataTableStyle{
    height: 5rem;
    overflow: hidden;
}

.vue3-easy-data-table{
    background-color:  blue;
    height: 4rem;
    max-height: 100%;
}
.vue3-easy-data-table__main{
  height: calc(100% - 2.1rem)
}

</style>