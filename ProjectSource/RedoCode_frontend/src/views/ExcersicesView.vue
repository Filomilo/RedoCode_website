<template>
  <main class="PlayGroundBase locked">
    <div class="datatable-container">
      <EasyDataTable
        :headers="fields"
        :items="exerciseData"
        alternating
        buttons-pagination
        class="dataTableStyle"
        :server-items-length="1"
        theme-color="var(--primary)"
        v-model:server-options="serverOptions"
      >
        <template #expand="item">
          {{ item.description }}
        </template>

        <template #loading>
          <LoadingIndicator />
        </template>
        <template #item-lang="item">
          {{ item.languages.map((elemnt: any) => elemnt.name) }}
        </template>
        <template #item-actions="item">
          <Button
            v-on:click="onExcersiceButton(item.id)"
            style="background-color: transparent; border-color: transparent; fill: white"
          >
            <IconPlay height="1.3rem" />
          </Button>
        </template>

        <template #pagination="{ prevPage, nextPage, isFirstPage, isLastPage }">
          <div class="paginatorButtons">
            <Button :disabled="isFirstPage" @click="prevPage">
              <IconNextLeft />
            </Button>
            <Button :disabled="isLastPage" @click="nextPage">
              <IconNextRight />
            </Button>
          </div>

          <router-link
            to="/Create"
            class="createButton"
            id="Home_Button"
            :class="ActiveUserStore.isLogged ? '' : 'diabled'"
          >
            <Button :disabled="!ActiveUserStore.isLogged"> Create </Button>
          </router-link>
        </template>
      </EasyDataTable>
    </div>
  </main>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { onMounted, ref, watch } from 'vue'
import axios from 'axios'
import type ExerciseType from '@/types/ExerciseType'
import type OnTableClickType from '@/types/OnTableClickType'
import LoadingIndicator from '@/components/LoadingIndicator.vue'
import type { Header, Item, ServerOptions } from 'vue3-easy-data-table'
import Button from 'primevue/button'
import IconNextRight from '../assets/icons/IconNextRight.vue'
import IconNextLeft from '../assets/icons/IconNextLeft.vue'
import IconPlay from '@/assets/icons/IconPlay.vue'
import ExerciseListRequestMessage from '@/types/ExerciseListRequestMessage'
import { isArray } from 'chart.js/helpers'
import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'
import { useActiveUserStore } from '@/stores/ActiveUserStore'

const ActiveUserStore = useActiveUserStore()
const CodeRunnerStore = useCodeRunnerStore()
const router = useRouter()

const fields: any[] = [
  { text: 'Name', value: 'name' },
  { text: 'language', value: 'lang' },
  { text: 'difficulty', value: 'difficulty' },
  { text: '', value: 'actions', width: 30 }
]

const onExcersiceButton = (id: number) => {
  router.push({ name: 'Exercise', params: { id: id } })
}
const exerciseData = ref<ExerciseType[]>([])

const serverOptions = ref<ServerOptions>({
  page: 1,
  rowsPerPage: 25,
  sortBy: 'name',
  sortType: 'desc'
})

const loadFromServer = () => {
  const sortby: string =
    serverOptions.value.sortBy === undefined
      ? 'name'
      : isArray(serverOptions.value.sortBy)
        ? serverOptions.value.sortBy[0]
        : serverOptions.value.sortBy

  const request: ExerciseListRequestMessage = {
    sortBy: sortby,
    rowsPerPage: serverOptions.value.rowsPerPage,
    page: serverOptions.value.page,
    sortDirection: serverOptions.value.sortType === 'desc'
  }
  console.log('Getting exercises')
  axios.get('/public/exercises/list', { params: request }).then((response) => {
    if (response === undefined) {
      console.error("couldn't retrieve excercise list from server")
      throw "couldn't retrieve excercise list from server"
    }
    console.log('Exercises respones: ' + JSON.stringify(response))
    exerciseData.value = response.data
    console.log('exerciseData.value: ' + JSON.stringify(exerciseData.value))
  })
}

watch(
  serverOptions,
  (newValue, oldValue) => {
    console.log(
      `load : count changed from ${JSON.stringify(oldValue)} to ${JSON.stringify(newValue)}`
    )
    loadFromServer()
  },
  { immediate: true, deep: true }
)

/*
onBeforeRouteUpdate(()=>{
  console.log("On mounted")
});
*/
const HeadType: string = 'Dark'
</script>
<style>
.dataTableStyle {
  height: 5rem;
  overflow: hidden;
}

.vue3-easy-data-table {
  height: 4rem;
  max-height: 100%;
}
.vue3-easy-data-table__main {
  height: calc(100% - 2.1rem);
}

.diabled {
  pointer-events: none;
}
</style>
