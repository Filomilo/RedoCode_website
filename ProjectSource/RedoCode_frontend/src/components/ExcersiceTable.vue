<template>
    <b-table 
    striped hover 
    :items="data"
    :fields="fields"
    
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