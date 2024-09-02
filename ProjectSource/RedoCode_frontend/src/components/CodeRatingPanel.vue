<template>
  <div>
    <div class="CodeRatingContainer">
      <div class="RatingPanelContaier">
        <div class="RatingPanelHeader">
          Your execution time compared to maximum
        </div>
        <div class="RatingPanel">
          <div class="DougnoutInsidesContainer MsText">
            {{ props.ExecutionTime }} ms
          </div>
          <Doughnut
            :data="ExeceutionData"
            :options="PieChartOptions"
            class="DugoutContainer"
          />
        </div>
      </div>

      <div class="RatingPanelContaier">
        <div class="RatingPanelHeader">Your solution is better than</div>
        <div class="RatingPanel">
          <div class="DougnoutInsidesContainer procentText">
            {{ props.BetterThanProcent }}%
          </div>
          <Doughnut
            :data="ProcentData"
            :options="PieChartOptions"
            class="DugoutContainer"
          />
        </div>
      </div>

      <div class="RatingPanelContaier">
        <div class="RatingPanelHeader">Your solution ranking number</div>
        <div class="RatingPanel">
          <div class="RankingNumberContainer">
            #
            <h1>
              {{ props.RankingPlacement }}
            </h1>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { Doughnut } from 'vue-chartjs'
  import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js'
  const props = defineProps<{
    ExecutionTime: number
    MaxExecutionTime: number
    BetterThanProcent: number
    RankingPlacement?: number
  }>()

  ChartJS.register(ArcElement, Tooltip)

  const ExeceutionData = {
    labels: ['You execution time', 'Maximum execution time'],
    datasets: [
      {
        backgroundColor: ['#a463ac', '#ffffff'],
        data: [
          props.ExecutionTime,
          props.MaxExecutionTime - props.ExecutionTime,
        ],
      },
    ],
  }
  const ProcentData = {
    labels: [
      '% of solutions worse than yours',
      '% of solutions better than yours',
    ],
    datasets: [
      {
        backgroundColor: ['#a463ac', '#ffffff'],
        data: [props.BetterThanProcent, 100 - props.BetterThanProcent],
      },
    ],
  }
  const PieChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    legend: {
      display: false,
    },
    cutout: '90%',
  }
</script>

<style lang="css">
  .CodeRatingContainer {
    width: 100%;
    max-width: 100%;
    height: 100%;
    display: flex;
    border-radius: 1rem;
    box-shadow: 0 0 0 0.08rem var(--primary-color);
    overflow: hidden;
  }

  .RatingPanelContaier {
    max-width: 34%;
    flex: 1;
    max-height: 100%;
    align-content: center;
    justify-content: center;
    display: flex;

    border-left: 0.02rem solid var(--primary-color);
    flex-direction: column;
  }

  .RatingPanelHeader {
    width: 100%;
    min-height: 30%;
    max-height: 30%;
    background-color: rgb(46, 46, 46);
    text-align: center;
    color: var(--primary-color);
    justify-content: center;
    align-items: center;
    font-weight: bold;
    display: flex;
    flex: 1;
  }
  .RatingPanel {
    justify-items: center;
    justify-content: center;
    align-items: center;
    max-height: calc(100% - 30%);
    align-content: center;
    flex: 1;
    width: 100%;
    flex-grow: 1;
    overflow: hidden;
  }

  .RankingNumberContainer {
    font-size: 3rem;
    word-break: keep-all;
    word-wrap: unset;
    line-break: normal;
    display: flex;
    color: white;
    justify-items: center;
    justify-content: center;
  }
  .DugoutContainer {
    position: relative;
    top: -70%;
    z-index: 0;
    margin: 1rem;
  }
  .DougnoutInsidesContainer {
    position: relative;
    top: 15%;
    right: -25%;
    height: 70%;
    width: 50%;
    z-index: 1;
    color: white;
    justify-items: center;
    justify-content: center;
    display: flex;
    align-items: center;
  }

  .procentText {
    font-size: 3rem;
  }
  .MsText {
    font-size: 1.8rem;
  }
  .RankingNumberContainer h1 {
    font-size: 9rem;
  }
</style>
