<template>
    <div >
    
        <div class="CodeRatingContainer">
                <div class="RatingPanelContaier">
                    <div class="RatingPanel">
                        <Doughnut
                        :data="dataTmp"
                        :options="PieChartOptions" 
                        />
                    </div>
        
                </div>
 
                <div class="RatingPanelContaier">
                    <div class="RatingPanel">

                        <Doughnut
                        :data="dataTmp"
                        :options="PieChartOptions" 
                        />

                        
                    </div>
        
                </div>
 
            
                <div class="RatingPanelContaier">
                    <div class="RatingPanel">

                        Your solution ranking number

                         # {{props.RankingPlacement}}

                    </div>
        
                </div>
 
        </div>
    </div>
</template>


<script setup lang="ts">
import { Doughnut } from 'vue-chartjs'
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js'
const props =  defineProps<{
  ExecutionTime: number
  MaxExecutionTime: number
  BetterThanProcent: number
  RankingPlacement: number
}>()




ChartJS.register(ArcElement)

const dataTmp = {
  labels: ['VueJs', 'EmberJs', 'ReactJs', 'AngularJs'],
  datasets: [
    {
      backgroundColor: ['#41B883', '#E46651', '#00D8FF', '#DD1B16'],
      data: [40, 20, 80, 10]
    }
  ]
}

const PieChartOptions = {
  responsive: true,
  maintainAspectRatio: false,
  legend: {
    display: false
  },
  cutout: '90%',
}




</script>


<style lang="css">

.CodeRatingContainer{
    background-color: aqua;
    width: 100%;
    height: 100%;
    display: flex;
}

.RatingPanelContaier{
    background-color: brown;
    flex: 1;
    margin: 1%;
    align-content: center;
    justify-content: center
    
}
.RatingPanel{
    background-color: rgb(42, 91, 165);
    width: 100%;
    max-height: 100%;
    
    aspect-ratio: 1 / 1;
}


</style>