<template>
    <div class="excercise-table-container">
    <b-table 
    striped hover 
    :items="data"
    :fields="fields"
    class="excercise-table-wrapper"
    >


  
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