<template>
  <main style="">
    <div class="MainContainer">
      <CodeRatingPanel
        class="CodeRatingPanel"
        :ExecutionTime="454"
        :MaxExecutionTime="1250"
        :BetterThanProcent="47"
        :RankingPlacement="3"
      />
      <div class="" style="margin-top: 5rem">
        <h2>Rate difficulty to see other solutions</h2>
      </div>
      <RateSelector
        class="RateSelectorContainer"
        :rateOptions="rateOptions"
        v-model="selectedRating"
      />
      <div class="" style="margin-top: 2rem" v-if="!alreadyRated">
        <Button class="saveButton" @click="onSaveRate"> save Rate </Button>
      </div>
    </div>

  
  </main>

  <!-- <Image :src=data.profilePic class="profilePic" /> -->
</template>

<script setup lang="ts">
  import { computed, Ref, ref } from 'vue'
  import { useActiveUserStore } from '@/stores/ActiveUserStore'
  import RateSelector, { RateOption } from '@/components/RateSelector.vue'
  import CodeRatingPanel from '@/components/CodeRatingPanel.vue'
 const ActiveUserStore = useActiveUserStore()
  const rateOptions: RateOption[] = [
    {
      value: 1,
      label: 'Very easy',
    },
    {
      value: 2,
      label: 'Easy',
    },
    {
      value: 3,
      label: 'Moderate',
    },
    {
      value: 4,
      label: 'Hard',
    },
    {
      value: 5,
      label: 'Very hard',
    },
  ]

  const ratingLevelShow: Ref<number> = ref(0)

const selectedRating: Ref<number> = ref(-1)
const alreadyRated: Ref<boolean> = ref(!ActiveUserStore.isLogged)

  const onSaveRate = () => {
    alreadyRated.value = true
  }
</script>

<style>
  .MainContainer {
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    align-content: center;
    justify-content: center;
  }

  .CodeRatingPanel {
    margin-top: 6rem;
    width: 80%;
    height: 30%;
    min-height: 15rem;
  }

  .RateSelectorContainer {
    width: 20rem;
    height: 10rem;
  }
  .VerticalLine {
    width: 100%;
  }

  .rateButton {
    border-color: transparent;
    margin: 0.1rem;
    border-radius: 0.2rem;
    align-self: flex-end;
  }

  .algainBottom {
    display: flex;
    flex-direction: row;
  }
  .saveButton {
    border-radius: 0.2rem;
    border-color: transparent;
  }
  .commentButton {
    border-radius: 0.2rem;
    border-color: transparent;
    align-self: flex-end;
  }

  .CommentArea {
    width: 95%;
    background-color: rgb(49, 49, 49);
    height: 10rem;
    width: 95%;
    color: white;
  }

  .ProfilePicContainer {
    width: 5rem;
    max-width: 5rem;
    margin-left: 2rem;
  }
  .profilePic {
    width: 100%;
    border-radius: 50rem;
    max-width: 100%;
    height: 5rem;
  }

  .CommentContainer {
    background-color: rgb(22, 22, 22);
    border-radius: 2rem;
    width: 95%;
    height: 20rem;
    margin: 2rem;
    color: white;
  }
</style>
