<template>
  <main class="PlayGroundBase locked">
    <div class="dataTable-container">
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
          {{ item.languages.map((element: any) => element.name) }}
        </template>
        <template #item-actions="item">
          <Button
            :id="'try_exercise-button-' + item.id"
            v-on:click="onExerciseButton(item.id)"
            style="
              background-color: transparent;
              border-color: transparent;
              fill: white;
            "
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
            :class="ActiveUserStore.isLogged ? '' : 'disabled'"
          >
            <Button :disabled="!ActiveUserStore.isLogged" id="Create-button">
              Create
            </Button>
          </router-link>
        </template>
      </EasyDataTable>
    </div>
  </main>
</template>

<script setup lang="ts">
  import { useRouter } from 'vue-router'
  import { ref, watch } from 'vue'
  import type ExerciseType from '@/types/ExerciseType'
  import LoadingIndicator from '@/components/LoadingIndicator.vue'
  import type { ServerOptions } from 'vue3-easy-data-table'
  import Button from 'primevue/button'
  import IconNextRight from '../assets/icons/IconNextRight.vue'
  import IconNextLeft from '../assets/icons/IconNextLeft.vue'
  import IconPlay from '@/assets/icons/IconPlay.vue'
  import { isArray } from 'chart.js/helpers'
  import { useActiveUserStore } from '@/stores/ActiveUserStore'
  import EndpointAccess from '@/controllers/EndpointsAccess'

  const ActiveUserStore = useActiveUserStore()
  const router = useRouter()

  const fields: any[] = [
    { text: 'Name', value: 'name' },
    { text: 'language', value: 'lang' },
    { text: 'difficulty', value: 'difficulty' },
    { text: '', value: 'actions', width: 30 },
  ]

  const onExerciseButton = (id: number) => {
    router.push({ name: 'Exercise', params: { id: id } })
  }
  const exerciseData = ref<ExerciseType[]>([])

  const serverOptions = ref<ServerOptions>({
    page: 1,
    rowsPerPage: 25,
    sortBy: 'name',
    sortType: 'desc',
  })

  const loadFromServer = () => {
    const sortby: string =
      serverOptions.value.sortBy === undefined
        ? 'name'
        : isArray(serverOptions.value.sortBy)
          ? serverOptions.value.sortBy[0]
          : serverOptions.value.sortBy

    EndpointAccess.unauthorized
      .getListOfExercises(
        sortby,
        sortby,
        serverOptions.value.rowsPerPage,
        serverOptions.value.page
      )
      .then((data: ExerciseType[]) => {
        exerciseData.value = data
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
</script>
<style>
  .dataTable-container {
    height: 100%;
  }
  .dataTableStyle {
    height: 100%;
    overflow: hidden;
  }

  .vue3-easy-data-table {
    min-height: 4rem;
    max-height: 100%;
  }
  .vue3-easy-data-table__main {
    height: calc(100% - 2.1rem);
  }

  .disabled {
    pointer-events: none;
  }
</style>
