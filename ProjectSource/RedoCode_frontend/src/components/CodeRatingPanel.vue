<template>
  <div id="code_rating_panel">
    {{ stringify(props) }}
    <div class="CodeRatingContainer">
      <div class="RatingPanelContaier">
        <div class="RatingPanelHeader">
          Your execution time compared to maximum
        </div>
        <div class="RatingPanel">
          <div class="DugoutContainer">
            <ProcentDonout
              :value="props.ExecutionTime"
              :maxValue="props.MaxExecutionTime"
              :label="props.ExecutionTime + ' ms'"
              fillColor="#a463ac"
              basicColor="#ffffff"
              class="MsText"
              :radius="65"
              id="execution-time-donout"
            />
          </div>
        </div>
      </div>

      <div class="RatingPanelContaier">
        <div class="RatingPanelHeader">Your solution is better than</div>
        <div class="RatingPanel">
          <!-- <div class="DougnoutInsidesContainer procentText">
            {{ props.BetterThanProcent }}%
          </div> -->
          <div class="DugoutContainer">
            <ProcentDonout
              :value="props.BetterThanProcent"
              :maxValue="100"
              :label="Math.round(100 * props.BetterThanProcent) / 100 + '%'"
              fillColor="#a463ac"
              basicColor="#ffffff"
              class="procentText"
              :radius="65"
              id="Better-than-donout"
            />
          </div>
        </div>
      </div>

      <div class="RatingPanelContaier">
        <div class="RatingPanelHeader">Your solution ranking number</div>
        <div class="RatingPanel">
          <div class="RankingNumberContainer">
            #
            <h1 id="result-code-rate">
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
  import ProcentDonout from '@/components/ProcentDonout.vue'
  import { stringify } from 'flatted'
  const props = defineProps<{
    ExecutionTime: number
    MaxExecutionTime: number
    BetterThanProcent: number
    RankingPlacement?: number
  }>()
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
    display: flex;
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
    z-index: 0;
    margin: 0rem;
    height: 12rem;

    align-self: center;
    justify-self: center;
    overflow: hidden;
    aspect-ratio: 1/1;
    padding: 1rem;
    padding-top: -5rem;
    overflow: hidden;
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
    font-size: 2rem;
  }
  .MsText {
    font-size: 1.8rem;
  }
  .RankingNumberContainer h1 {
    font-size: 9rem;
  }
</style>
