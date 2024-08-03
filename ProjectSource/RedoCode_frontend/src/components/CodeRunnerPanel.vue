<!-- eslint-disable vue/no-mutating-props -->

<template>
  <!-- CoderunnerState:
  {{ ApiConnectionStore.codeRunnerConnection.codeRunnerState }}
<div style="color: white" >
  {{ JSON.stringify(props) }}
</div>
  {{ JSON.stringify(props.AutoTests) }} -->
  <Dialog
    :visible="codeRunnerStore.exerciseLoading"
    modal
    header="Edit Profile"
    :style="{ width: '25rem' }"
  >
    <template #container>
      <div class="CodeRunnerLoadingPanel">
        <LoadingIndicator />
        <div>Loading data</div>
      </div>
    </template>
  </Dialog>

  <Dialog
    :visible="ApiConnectionStore.codeRunnerConnection.isAwaitngCodeRunner"
    modal
    header="Edit Profile"
    :style="{ width: '25rem' }"
  >
    <template #container>
      <div class="CodeRunnerLoadingPanel">
        <LoadingIndicator />
        <div>
          Awiating acces to code runner, plase be patient. Consider Creating and
          account to have priority in queue
        </div>
      </div>
    </template>
  </Dialog>

  <div
    v-if="
      ApiConnectionStore.codeRunnerConnection.doesHaveACtiveToCodeRunner ||
      ApiConnectionStore.codeRunnerConnection.isAwaitngCodeRunner
    "
    class="heightLimit"
  >
    <Splitter style="height: 100%; width: 100%">
      <SplitterPanel
        v-if="props.ManualTests !== undefined"
        style="max-width: 100%; width: 100%"
        :size="25"
      >
        <Splitter layout="vertical" style="width: 100%">
          <SplitterPanel style="width: 100%; max-width: 100%; width: 100%">
            <ExerciseDescriptionPanel :exerciseInfo="props.exerciseInfo" />
          </SplitterPanel>
        </Splitter>
      </SplitterPanel>

      <SplitterPanel :size="50">
        <CodeEditor
          class="CodeEditorContainer"
          :starting="props.starting"
          :codeUpdateMethod="props.codeContainerUpdate"
          :onRunCode="props.onRunCode"
        />
      </SplitterPanel>
      <SplitterPanel :size="25">
        <CodeResultPanel
          :onSubmit="props.onSubmit"
          :ManualTests="props.ManualTests"
          :AutoTests="props.AutoTests"
          :SubmitAccess="props.SubmitAccess"
        />
      </SplitterPanel>
    </Splitter>
  </div>
  <div v-else style="height: 100%">
    {{ JSON.stringify(props.languageChoices) }}
    <ConnectToCodeRunnerPanel :languageChoices="props.languageChoices" />
  </div>
</template>

<script lang="ts" setup>
  import CodeEditor from '@/components/CodeEditorPanel.vue'
  import BasicButton from '@/components/BasicButton.vue'
  import type { Button } from 'bootstrap'
  import { ref, onMounted, type Ref, PropType } from 'vue'
  import { onBeforeRouteLeave, onBeforeRouteUpdate } from 'vue-router'
  import axios from 'axios'
  import ConnectToCodeRunnerPanel from './ConnectToCodeRunnerPanel.vue'

  import type { IFrame } from '@stomp/stompjs'
  import LanguageDropdown from './LanguageDropdown.vue'
  // import {
  //   requstDefaultVmMachine,
  //   subcribeToVmStatus,
  //   subscribeToCodeResults
  // } from '../config/CodeRunnerConnection'
  import type CodeRunnerState from '@/types/CodeRunnerState'
  import type CodeToRunMessage from '@/types/CodeToRunMessage'
  import ResultsPanel from './ResultsPanel.vue'
  import { basicResultTemplate } from '../config/Data'
  import type CodeResultsType from '@/types/CodeResultsType'
  import CodeResultPanel from './CodeResultPanel.vue'
  import ExerciseDescriptionPanel from './ExerciseDescriptionPanel.vue'
  import ExerciseSetupPanel from './ExerciseSetupPanel.vue'
  import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
  import LoadingIndicator from './LoadingIndicator.vue'
  import { useApiConnectionStore } from '@/stores/ApiConnectionStore'
  import IExerciseDescriptionI from '@/types/IExerciseDescriptionI'
  import ExerciseTest from '@/types/ExcericseTest'
  import codeRunnerType from '@/types/CodeRunnerTypes'
  import CodeRunnerStatus from '@/types/CodeRunnerStatus'
  import { ComputedRef } from 'vue'
  import ProgramResultsMessage from '@/types/ApiMesseages/ProgramResultsMessage'
  const props = defineProps({
    exerciseInfo: {
      type: Object as () => IExerciseDescriptionI,
      required: true,
    },
    languageChoices: { type: Array as () => codeRunnerType[], required: true },
    codeContainerUpdate: { type: Function, required: true },
    starting: { type: String, required: true },
    onRunCode: { type: Function, required: true },
    onSubmit: { type: Function, required: true },
    onResults: {
      type: Function as PropType<(result: ProgramResultsMessage) => void>,
      required: true,
    },
    ManualTests: { type: Array as () => ExerciseTest[], required: false },
    AutoTests: { type: Array as () => ExerciseTest[], required: false },
    SubmitAccess: {type: Boolean, required: true}
  })

  const codeRunnerStore = useCodeRunnerStore()
  const ApiConnectionStore = useApiConnectionStore()
  const subscribeStatus = ref(false)
  const meaages = ref('')
  const tryingToEstablishConnection: Ref<boolean> = ref(false)
  const establishedConnection: Ref<boolean> = ref(false)
  const VmAcces: Ref<boolean> = ref(false)
  const chosenLangague: Ref<codeRunnerType> = ref(props.languageChoices[0])
  const code: Ref<string> = ref('Write Code')
  const resultData = ref(basicResultTemplate)

  const connectStomp = () => {
    ApiConnectionStore.stompApiConnection.activate()
  }
  const disconnectStomp = () => {
    ApiConnectionStore.stompApiConnection.deactivate()
  }

  const updateVmStatus = (state: CodeRunnerState) => {
    console.log('status: ' + state)
    if (
      state.state == CodeRunnerStatus.STOPPED ||
      state.state == CodeRunnerStatus.RUNNING_MACHINE
    ) {
      console.log('vmacces')
      VmAcces.value = true
    } else VmAcces.value = false
  }

  const updateResults = (results: CodeResultsType[]) => {
    console.log('results recived: ' + JSON.stringify(results))
    resultData.value = results
  }

  onMounted(() => {
    console.log('props: ' + JSON.stringify(props))
    codeRunnerStore.updateCodeRunner()
    // if (props.connectAtStart) {
    connectStomp()
    ApiConnectionStore.setOnCodeResult(props.onResults)
    //connectToCodeRunner()
    // }
  })

  onBeforeRouteLeave(async (to, from, next) => {
    ApiConnectionStore.clearOnCodeResult()
    disconnectStomp()
    next()
  })

  const onSelectLanguage = (lang: codeRunnerType) => {
    console.log('info selcted:' + lang)
    chosenLangague.value = lang
    // if (establishedConnection.value) requstDefaultVmMachine(lang)
  }

  // const onRunCode = () => {
  //   console.log('on run code: ' + code.value)
  //   const toCompielMes: CodeToRunMessage = {
  //     code: code.value,
  //     exercise_id: null
  //   }
  //   sendToCompile(toCompielMes)
  // }

  onBeforeRouteLeave(async (to, from) => {
    // console.log("leave************************************************")
    // codeRunnerStore.disconnetWithCodeRunner();
    disconnectStomp()
  })
</script>

<style>
  .heightLimit {
    max-height: 100%;
    height: 100%;
  }
</style>
../controllers/StompApiConnection
