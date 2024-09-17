<template>
  <main style="">
    <div class="MainContainer" v-if="refResultData !== undefined">
      <CodeRatingPanel
        class="CodeRatingPanel"
        :ExecutionTime="refResultData.executionTimeMs"
        :MaxExecutionTime="refResultData.maxExecutionTimeMs"
        :BetterThanProcent="refResultData.betterThanProcent"
        :RankingPlacement="refResultData.SolutionRanking"
      />
      <div class="" style="margin-top: 5rem">
        <h2>Rate difficulty to see other solutions</h2>
      </div>
      <RateSelector
        class="RateSelectorContainer"
        :rateOptions="rateOptions"
        id="Result-rate"
        v-model="selectedRating"
      />
      <div class="" style="margin-top: 2rem">
        <Button class="saveButton" @click="onSaveRate" id="save-rate-click">
          save Rate
        </Button>
      </div>
    </div>
    <NoDataFoundPanel v-else />
  </main>

  <!-- <Image :src=data.profilePic class="profilePic" /> -->
</template>

<script setup lang="ts">
  import { useActiveUserStore } from '@/stores/ActiveUserStore'
  import RateSelector, { RateOption } from '@/components/RateSelector.vue'
  import CodeRatingPanel from '@/components/CodeRatingPanel.vue'
  import SolutionsPanel from '@/components/SolutionsPanel.vue'
  import SolutionsData from '@/types/ApiMesseages/SolutionsData'
  import CodeRunnerType from '@/types/CodeRunnerTypes'
  import { useGlobalStateStore } from '@/stores/GlobalStateStore'
  import LoadingIndicator from '@/components/LoadingIndicator.vue'
  import { ComputedRef, Ref, ref } from 'vue'
  import EndpointAcces from '@/controllers/EndpointsAcces'
  import { useRoute, useRouter } from 'vue-router'
  import { onBeforeMount, onMounted } from 'vue'
  import CommentSection from '@/components/CommentSection.vue'
  import ExerciseInfoTopPanel from '@/components/ExerciseInfoTopPanel.vue'
  import ResultData from '@/types/ApiMesseages/ResultData'
  import NoDataFoundPanel from '@/components/NoDataFoundPanel.vue'
  import { useToastStore } from '@/stores/ToastStore'

  const ActiveUserStore = useActiveUserStore()
  const globalStateStore = useGlobalStateStore()
  const route = useRoute()
  const router = useRouter()
  const toastStore = useToastStore()
  const refResultData: Ref<ResultData | undefined> = ref()

  console.log('route.params: ' + JSON.stringify(route.params))
  const exercsieID: number = Number(route.params.id)
  console.log('exercsieID: ' + exercsieID)

  const loadData = async () => {
    console.log('Loading solutions')
    globalStateStore.showLoadingScreen('Loading solutions')
    EndpointAcces.authorized
      .getResultData(exercsieID)
      .then((data: ResultData) => {
        refResultData.value = data
        globalStateStore.hideLoadingScreen()
      })
      .catch(x => {
        toastStore.showErrorMessage(x)
      })
      .finally(() => {
        globalStateStore.hideLoadingScreen()
      })
  }

  onMounted(() => {
    if (exercsieID !== undefined && exercsieID > 0) loadData()
  })

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

  const selectedRating: Ref<number> = ref(-1)

  const onSaveRate = () => {
    EndpointAcces.authorized
      .postRate(selectedRating.value, exercsieID)
      .then(() => {
        toastStore.showSuccessMessage('saved rating')
        router.push({ name: 'Solutions', params: { id: exercsieID } })
      })
      .catch(ex => {
        toastStore.showErrorMessage("Couldn't save rating: \n " + ex)
      })
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
