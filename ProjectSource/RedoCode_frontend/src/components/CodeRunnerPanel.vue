<!-- eslint-disable vue/no-mutating-props -->

<template>
  <div class="PlayGroundBase">
    <Dialog
      :visible="false"
      modal
      header="Edit Profile"
      :style="{ width: '25rem' }"
    >
      <template #container>
        <div class="CodeRunnerLoadingPanel" id="data-loading-dialog">
          <LoadingIndicator />
          <div>Loading data</div>
        </div>
      </template>
    </Dialog>

    <Dialog
      :visible="awaiting"
      modal
      header="Edit Profile"
      :style="{ width: '25rem' }"
    >
      <template #container>
        <div class="CodeRunnerLoadingPanel" id="coderunner-loading-dialog">
          <LoadingIndicator class="loadingIndicator" />
          <div>
            Awaiting access to code runner, please be patient. Consider Creating
            and account to have priority in queue
          </div>
        </div>
      </template>
    </Dialog>

    <div
      v-if="
        codeRunnerStore.codeRunnerConnection.doesHaveACtiveToCodeRunner ||
        codeRunnerStore.codeRunnerConnection.isAwaitingCodeRunner
      "
      class="heightLimit widthLimit"
    >
      <Splitter style="max-height: 100%">
        <SplitterPanel
          v-if="props.exerciseInfo !== undefined"
          style="width: 5rem"
          :size="15"
        >
          <Splitter layout="vertical" style="">
            <SplitterPanel style="">
              <ExerciseDescriptionPanel :exerciseInfo="props.exerciseInfo" />
            </SplitterPanel>
          </Splitter>
        </SplitterPanel>

        <SplitterPanel :size="70" :min-size="40" style="max-width: 100%">
          <CodeEditor
            class="CodeEditorContainer"
            :starting="props.starting"
            :codeUpdateMethod="props.codeContainerUpdate"
            :onRunCode="onRunCode"
            :languageChoices="props.languageChoices"
          />
        </SplitterPanel>
        <SplitterPanel :size="15" style="max-width: 100%; width: 5rem">
          <CodeResultPanel
            :onSubmit="props.onSubmit"
            :ManualTests="props.ManualTests"
            :AutoTests="props.AutoTests"
            :SubmitAccess="props.SubmitAccess"
            :ExecutionTime="props.ExecutionTime"
          />
        </SplitterPanel>
      </Splitter>
    </div>
    <div v-else style="height: 100%">
      {{ JSON.stringify(props.languageChoices) }}
      <ConnectToCodeRunnerPanel
        :languageChoicesSelection="props.languageChoices"
      />
    </div>
  </div>
</template>

<script lang="ts" setup>
  //#region imports
  import CodeEditor from '@/components/CodeEditorPanel.vue'
  import { onMounted, PropType, computed } from 'vue'
  import { onBeforeRouteLeave } from 'vue-router'
  import ConnectToCodeRunnerPanel from './ConnectToCodeRunnerPanel.vue'

  import CodeResultPanel from './CodeResultPanel.vue'
  import ExerciseDescriptionPanel from './ExerciseDescriptionPanel.vue'
  import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
  import LoadingIndicator from './LoadingIndicator.vue'
  import { useApiConnectionStore } from '@/stores/ApiConnectionStore'
  import IExerciseDescriptionI from '@/types/IExerciseDescriptionI'
  import ExerciseTest from '@/types/ExerciseTest'
  import codeRunnerType from '@/types/CodeRunnerTypes'
  import { ComputedRef } from 'vue'
  import ProgramResultsMessage from '@/types/ApiMessages/ProgramResultsMessage'
  import { ConsoleOutput } from '@/types/ProgramResults'
  //#endregion
  //#region props
  const props = defineProps<{
    exerciseInfo?: IExerciseDescriptionI
    languageChoices: codeRunnerType[]
    codeContainerUpdate: (cose: string) => void
    starting: string
    onRunCode: () => void
    onSubmit?: () => void
    onResults: (result: ProgramResultsMessage) => void
    ManualTests: ExerciseTest[] | ConsoleOutput
    AutoTests?: ExerciseTest[]
    SubmitAccess?: boolean
    ExecutionTime?: number
  }>()
  //#endregion

  const codeRunnerStore = useCodeRunnerStore()
  const ApiConnectionStore = useApiConnectionStore()

  const connectStomp = async () => {
    ApiConnectionStore.stompApiConnection.activate()
  }
  const disconnectStomp = () => {
    ApiConnectionStore.stompApiSubscriptionController.removeCodeResultsSubscription(
      onResult
    )
    ApiConnectionStore.stompApiConnection.deactivate()
  }

  const onResult = (mes: ProgramResultsMessage) => {
    console.log('onResult: ' + JSON.stringify(mes))
    codeRunnerStore.isProcessing = false
    props.onResults(mes)
  }

  onMounted(() => {
    console.log('props: ' + JSON.stringify(props))
    console.log('Code runner init')
    connectStomp()
    codeRunnerStore.codeRunnerConnection.updateCodeRunner()
    // if (props.connectAtStart) {

    ApiConnectionStore.stompApiSubscriptionController.addCodeResultsSubscription(
      onResult
    )
    //connectToCodeRunner()
    // }
  })

  onBeforeRouteLeave(async (to, from, next) => {
    disconnectStomp()
    next()
  })

  const onRunCode = () => {
    codeRunnerStore.isProcessing = true
    props.onRunCode()
  }

  onBeforeRouteLeave(async () => {
    disconnectStomp()
  })

  const awaiting: ComputedRef<boolean> = computed(() => {
    if (import.meta.env.MODE === 'development') return false
    return codeRunnerStore.codeRunnerConnection.isAwaitingCodeRunner
  })
</script>

<style>
  .heightLimit {
    max-height: 100%;
    height: 100%;
  }
  .widthLimit {
    max-width: 100%;
    width: 100%;
  }

  .loadingIndicator {
    aspect-ratio: 1/1;
    width: 5rem;
    max-width: 5rem;
    max-height: 5rem;
  }
</style>
