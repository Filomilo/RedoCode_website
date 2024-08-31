<template>

<div v-if="props.rateOptions!==undefined && props.rateOptions.length>0">
<div class="ColumnsContainer">
    <div v-for="(item,index) in props.rateOptions" v-bind:key="item.value" class="ColumnContainer"
    :style="'height: '+(100/props.rateOptions.length*(index+1))+'%'"
    >
    <div class="Column"
    @click="selectColumn(item.value)"
    :style="'background-color: '+color+' ;'"
    @mouseover="columnEnter(item.value)"
    @mouseleave="columnleave"

    />


    </div>
</div>
    RateSelectorContainer

</div>

</template>


<script setup lang="ts">
import { required } from '@vuelidate/validators';
import { Ref, ref } from 'vue';
// import Gradient from 'gradient'

const props =  defineProps<{
  rateOptions: RateOption[]
  heightChange?: number
}>()

const selectedLabel= ref("");
const selectedValue:Ref<number|string>=ref(-1)
const color=ref("grey")
const selectColumn=(value: number|string)=>
{
    selectedValue.value=value;
    const selLabel=props.rateOptions.find(x=>x.value==x.value)?.label;
    selectedLabel.value=selLabel===undefined?"":selLabel;
}

// const grad = Gradient('#00ff00','#ff0000', props.rateOptions.length);
// console.log(`geadint: ${JSON.stringify(grad)}`)
const getDefaultColor=(val:number): string=>{
// return grad[val];
}


const columnEnter=(value: number|string)=>{
    console.log(`column enter ${value}`)
}
const columnleave=()=>{
    console.log(`column leave`)
}

console.log(JSON.stringify(props))

const heightChangeColumn=props.heightChange===undefined?10:props.heightChange===undefined;
document.documentElement.style.setProperty('--heightChangeColumn', `${heightChangeColumn}px`);</script>
<script lang="ts">
export interface RateOption{
    value: any
    label?: string|undefined
    color?: string |undefined
}


</script>

<style>
.RateSelector{
    background-color: red
}
.ColumnsContainer{
    display: flex;
    flex-direction: row;
    flex: 1;
    background-color: green;
    width: 100%;
    height: 100%;
    padding: 2%;
    align-items: flex-end;
    align-content: flex-start;
}
.ColumnContainer{
    flex-direction: column;
    flex: 1;
    background-color: red;
    flex-direction: row;
    align-items: flex-start;
    align-content: flex-start;
    margin: 1%;

    display: flex;
    flex-direction: row;
    flex: 1;
    background-color: red;
    width: 100%;
    height: 100%;
    padding: 2%;
    align-items: flex-end;
    align-content: flex-start;
}
.Column{
    background-color: yellow;
    flex: auto;
    margin: 1%;
    width: 100%;
    height: calc(100% - var(--heightChangeColumn));
        border-radius: 1.5vh;
        transition: height 0.1s ease;
        cursor: pointer;
}
.Column:hover{
    height: calc(100%);
}
</style>