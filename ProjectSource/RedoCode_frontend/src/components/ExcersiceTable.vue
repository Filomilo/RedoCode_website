<template>
    <div class="excercise-table-container">
    <b-table 
    striped hover 
    :items="data"
    :fields="fields"
    class="excercise-table-wrapper"
    >


    {{data}}
  


<table class="table">
    <thead>
        <tr>
        <th v-for="(item,index) in  Object.keys(data[0])" scope="col" :key="index" >
        {{ item }}
        </th>
    </tr>
    </thead>
    <tbody>
        
        <tr  v-for="item in  data"  :key="item.id">
        <th v-for="(elemnt,index) in  item" scope="col" :key="index">
            {{ elemnt }}
            </th>
        </tr>
    </tbody>
    </table>


      <hr>
    <table class="table">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">First</th>
            <th scope="col">Last</th>
            <th scope="col">Handle</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <th scope="row">1</th>
            <td>Mark</td>
            <td>Otto</td>
            <td>@mdo</td>
          </tr>
          <tr>
            <th scope="row">2</th>
            <td>Jacob</td>
            <td>Thornton</td>
            <td>@fat</td>
          </tr>
          <tr>
            <th scope="row">3</th>
            <td colspan="2">Larry the Bird</td>
            <td>@twitter</td>
          </tr>
        </tbody>
      </table>

  
<template #cell(actions)="{item}">
    <b-button 
    pill
    @click="onRowClick(item.id)"
    variant="primary"
    >
        try
    </b-button>
</template>
</b-table>
</div>
</template>



<script setup lang="ts">
import type ExerciseType from "@/types/ExerciseType";
import type OnTableClickType from "@/types/OnTableClickType"

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
  data: ExerciseType[],
  onRowClick: OnTableClickType
}>()



</script>