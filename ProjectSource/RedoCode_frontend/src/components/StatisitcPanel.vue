<template>

<main>
{{ piehartData }}
<div class="titleContainer">
  <label class="title">
    Amount of language used
  </label>

</div>
  <div class="statContainer" v-if="piehartData!==undefined">
          <Doughnut :data="piehartData" :options="pieCharOption" />
  </div>
  <div class="titleContainer">
    <label class="title">
      Lately done exercises
    </label>
  
  </div>
  <div class="statContainer barContainer" v-if="latelyDoneData!==undefined">
    <Bar  :data="latelyDoneData" :options="barOptions" />
</div>
     
</main>
    
</template>


<script lang="ts" setup>
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js'
import { Doughnut, Bar } from 'vue-chartjs'
import * as chartConfig from 'chart.js'
import StatisticMessage from '@/types/ApiMesseages/StatisticMessage'
import { computed, ref, Ref, onMounted } from 'vue';
import EndpointAcces from '@/controllers/EndpointsAcces';
import {
  Title,
  BarElement,
  CategoryScale,
  LinearScale,
  
} from 'chart.js'
import { color } from 'chart.js/helpers';
import { scale } from 'chroma-js';
const StatData: Ref<StatisticMessage| undefined>=ref();


const piehartData = computed(() => {

  if(StatData.value===undefined || StatData.value.languageUse.length===0 )
  return undefined;

  return {
    labels: StatData.value?.languageUse.map(x=> x.name),
    datasets:[
      {
        label: 'Amount of exercises done',

        backgroundColor: ['#41B883', '#E46651', '#00D8FF', '#DD1B16'],
        data: StatData.value?.languageUse.map(x=> x.amount)
      }
    ]
  }
});

const latelyDoneData = computed(() => {
  if(StatData.value===undefined)
  return undefined;

  return {
    labels: StatData.value.amountOfLatelyDone.map(x=> new Date(x.date).toDateString()),
    datasets:[{
      label: 'Amount of exercises done',

      backgroundColor: '#a550b3',
      data: StatData.value.amountOfLatelyDone.map(x=>x.amount)
    }]
  }

}
);








ChartJS.register(ArcElement, Tooltip, Legend,BarElement,CategoryScale,LinearScale  )
// ChartJS.register( BarElement, Title, Tooltip, Legend)


const barOptions = {
  responsive: true,
  maintainAspectRatio: false,
  scales: {
     x: {
      ticks: {
        color: "white"
      }
    },
    y: {
      ticks: {
        color: "white"
      }
    }
  },
  plugins: {
            legend: {
                labels: {
                    color: "white",
                    font: {
                        size: 14
                    }
                }
            }
        }
}

const pieCharOption={
  ...barOptions,
  scales: {
    x: {
      display: false // Hides the X axis
    },
    y: {
      display: false // Hides the Y axis
    }
  }
}

onMounted(() => {
  EndpointAcces.authorized.getUserStatisticData().then(result=>{
    StatData.value=result
  })
})

</script>


<style lang="css">
.statContainer{
  width: 100%;
  height: fit-content;
  color: white;
  margin-bottom: 5rem;
  height: 20rem;

}
.statContainer *{

  color: white;
}




.titleContainer{
width: 100%;
align-items: center;
justify-content: center;
align-content: center;
justify-items: center;
margin: 1rem;
height: fit-content;
display: flex;
}

.title{
font-size: 2rem;
text-align: center;
align-self: center;
}

</style>