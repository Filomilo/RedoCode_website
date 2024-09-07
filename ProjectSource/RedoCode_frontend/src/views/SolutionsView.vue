<template>
  <div>
    <main class="main" v-if="refSolutionData !== undefined">
      <ExerciseInfoTopPanel
        :title="refSolutionData.title"
        :description="refSolutionData.desc"
      />
      <SolutionsPanel
        class="SolutionsPanel"
        :solutionList="refSolutionData.SolutionsList"
        :maxExectuionTime="refSolutionData.maxExecutionTimeMs"
      />
      <CommentSection
        class="commentPanel"
        :comments="refSolutionData.comments"
        :id="exercsieID"
      />
    </main>

    <NoDataFoundPanel v-else />
  </div>
</template>

<script setup lang="ts">
  import SolutionsPanel from '@/components/SolutionsPanel.vue'
  import SolutionsData from '@/types/ApiMesseages/SolutionsData'
  import CodeRunnerType from '@/types/CodeRunnerTypes'
  import { useGlobalStateStore } from '@/stores/GlobalStateStore'
  import LoadingIndicator from '@/components/LoadingIndicator.vue'
  import { ComputedRef, Ref, ref } from 'vue'
  import EndpointAcces from '@/controllers/EndpointsAcces'
  import { useRoute } from 'vue-router'
  import { onBeforeMount, onMounted } from 'vue'
  import CommentSection from '@/components/CommentSection.vue'
  import ExerciseInfoTopPanel from '@/components/ExerciseInfoTopPanel.vue'
  import { useActiveUserStore } from '@/stores/ActiveUserStore'
  import NoDataFoundPanel from '@/components/NoDataFoundPanel.vue'
  console.log('Test')
  const refSolutionData: Ref<SolutionsData | undefined> = ref()
  const ActiveUserStore = useActiveUserStore()
  const globalStateStore = useGlobalStateStore()
  const route = useRoute()
  console.log('route.params: ' + JSON.stringify(route.params))
  const exercsieID: number = Number(route.params.id)
  console.log('exercsieID: ' + exercsieID)

  const loadData = async () => {
    console.log('Loading solutions')
    globalStateStore.showLoadingScreen('Loading solutions')
    EndpointAcces.authorized
      .getSolutionsData(exercsieID, ActiveUserStore.getToken())
      .then((data: SolutionsData) => {
        refSolutionData.value = data
        globalStateStore.hideLoadingScreen()
      })
      .finally(() => {
        globalStateStore.hideLoadingScreen()
      })
  }
  onMounted(() => {
    if (exercsieID !== undefined && exercsieID > 0) loadData()
  })
</script>

<style lang="css">
  .main {
    min-height: 100%;
    max-width: 100%;
    overflow-y: scroll;
    display: flex;
    align-items: center;
    justify-items: center;
    justify-content: center;
    flex-direction: column;
  }
  .SolutionsPanel {
    margin-top: 5rem;
    width: 90vw;
    height: 80vh;
  }

  .commentPanel {
    width: 100%;
  }
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
    justify-content: center;
    display: flex;
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
    justify-self: center;
    align-self: center;
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
    height: fit-content;
    margin: 2rem;
    padding-bottom: 2rem;
    color: white;
  }
</style>
