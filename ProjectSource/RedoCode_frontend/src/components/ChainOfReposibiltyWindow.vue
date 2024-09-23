<template>
  <div class="floatWindowContainer" v-if="shouldBevisable">
    <div class="floatWindow">
      <Timeline :value="chain" class="chainContainer" align="left">
        <template #marker="slotProps" class="iconContainer">
          <div v-if="slotProps.item.status === 'PENDING'" class="iconContainer">
            <IconStatusPending
              style="fill: white; margin-top: -0.3rem; width: 100%"
            />
          </div>
          <div v-if="slotProps.item.status === 'RUNNING'" class="iconContainer">
            <LoadingIndicator />
          </div>
          <div v-if="slotProps.item.status === 'SUCCESS'" class="iconContainer">
            <IconStatusSuccess
              style="fill: green; margin-top: -0.3rem; width: 100%"
            />
          </div>
          <div v-if="slotProps.item.status === 'FAILED'" class="iconContainer">
            <IconStatusFail
              style="fill: red; margin-top: -0.3rem; width: 100%"
            />
          </div>
        </template>

        <template #content="slotProps">
          <h1 class="title">
            {{ slotProps.item.nodeName }}
          </h1>

          <h2 class="description">
            {{ slotProps.item.processingMessage }}
          </h2>
        </template>
      </Timeline>
      <Button
        class="BasicButton"
        style="font-size: 1rem; padding: 0.8rem"
        @click="onCloseButton"
        v-if="canBeclosed"
      >
        Close
      </Button>
    </div>
  </div>
</template>
<script setup lang="ts">
  import { computed, Ref, ref } from 'vue'
  import { useExecutionChainStore } from '@/stores/ExecutionChainStore'
  import LoadingIndicator from '@/components/LoadingIndicator.vue'
  import IconStatusSuccess from '@/assets/icons/IconStatusSuccess.vue'
  import IconStatusFail from '@/assets/icons/IconStatusFail.vue'
  import IconStatusPending from '@/assets/icons/IconStatusPending.vue'
  import ExecutionChainController from '@/controllers/ExecutionChainController'
  import ChainNodeStatus from '@/types/ApiMessages/ExecutionResponses/ChainNodeStatus'
  import { useGlobalStateStore } from '@/stores/GlobalStateStore'
  const executionChainStore = useExecutionChainStore()
  const GlobalStateStore = useGlobalStateStore()

  const onVisibiltyUpdate = (state: ExecutionChainController) => {
    console.log('updare: ' + JSON.stringify(state._executionChain))
    chainData.value = [...state.executionChain]
    shouldBevisable.value = state.shouldBeVisible
    canBeclosed.value = state.closeReady
    GlobalStateStore.isLocked = state.shouldBeVisible
  }
  executionChainStore.executionChainController.onVisibilityUpdate =
    onVisibiltyUpdate
  console.log('seted')

  const shouldBevisable = ref(false)
  const canBeclosed = ref(false)
  const chainData: Ref<ChainNodeStatus[]> = ref([])

  const chain = computed(() => chainData.value)

  const onCloseButton = () => {
    console.log('ExecutionResponses close')
    executionChainStore.executionChainController.close()
  }
</script>

<style lang="scss">
  .floatWindowContainer {
    position: absolute;
    background-color: transparent;
    z-index: 5;
    width: 100vw;
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .floatWindow {
    background-color: rgb(39, 39, 39);
    z-index: 5;
    width: 100vw;
    height: 100vh;
    width: 50%;
    height: fit-content;
    align-items: center;
    justify-content: center;
    justify-items: center;
    text-align: center;
    padding: 1.5rem;
    border-radius: 0.21rem;
  }

  .chainContainer {
    justify-items: left;
    justify-content: left;
  }

  .p-timeline-event {
    justify-content: left;
  }

  .p-timeline-event-opposite {
    width: 0rem;
    flex: 0;
  }

  .title {
    font-size: 1.5rem;
    color: white;
  }
  .description {
    margin-bottom: 3rem;
    font-size: 1rem;
  }

  .iconContainer {
    width: 1.5rem;
    aspect-ratio: 1 / 1;
    overflow: hidden;
    justify-content: center;
    align-items: center;
    padding: auto;
  }
  .iconContainer * {
    width: 100%;
    height: 100%;
  }
</style>
