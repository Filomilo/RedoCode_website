<template>
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
    :visible="codeRunnerStore.isAwaitngCodeRunner"
    modal
    header="Edit Profile"
    :style="{ width: '25rem' }"
  >
    <template #container>
      <div class="CodeRunnerLoadingPanel">
        <LoadingIndicator />
        <div>
          Awiating acces to code runner, plase be patient. Consider Creating and account to have
          priority in queue
        </div>
      </div>
    </template>
  </Dialog>

  <div
    v-if="codeRunnerStore.doesHaveACtiveToCodeRunner || codeRunnerStore.isAwaitngCodeRunner"
    class="heightLimit"
  >
    <Splitter style="height: 100%">
      <SplitterPanel v-if="showLeftPanel">
        <Splitter layout="vertical">
          <SplitterPanel>
            <ExerciseDescriptionPanel />
          </SplitterPanel>
          <SplitterPanel v-if="showCreatorPanel">
            <ExerciseSetupPanel />
          </SplitterPanel>
        </Splitter>
      </SplitterPanel>

      <SplitterPanel>
        <CodeEditor class="CodeEditorContainer" />
      </SplitterPanel>
      <SplitterPanel>
        <CodeResultPanel :isDataResult="showLeftPanel" />
      </SplitterPanel>
    </Splitter>
  </div>
  <div v-else style="height: 100%">
    <ConnectToCodeRunnerPanel />
  </div>
</template>

<script lang="ts" setup>
import CodeEditor from '@/components/CodeEditorPanel.vue'
import BasicButton from '@/components/BasicButton.vue'
import type { Button } from 'bootstrap'
import { ref, onMounted, type Ref } from 'vue'
import { onBeforeRouteLeave, onBeforeRouteUpdate } from 'vue-router'
import axios from 'axios'
import ConnectToCodeRunnerPanel from './ConnectToCodeRunnerPanel.vue'
import {
  connectStomp,
  disconnectStomp,
  onConnectStomp,
  getConnetedUserName
} from '../config/StompApiConnection'
import type { IFrame } from '@stomp/stompjs'
import LanguageDropdown from './LanguageDropdown.vue'
import {
  requstDefaultVmMachine,
  subcribeToVmStatus,
  sendToCompile,
  subscribeToCodeResults
} from '../config/CodeRunnerConnection'
import type CodeRunnerState from '@/types/CodeRunnerState'
import type CodeToRunMessage from '@/types/CodeToRunMessage'
import ResultsPanel from './ResultsPanel.vue'
import { basicResultTemplate, languageChoices } from '../config/Data'
import type CodeResultsType from '@/types/CodeResultsType'
import CodeResultPanel from './CodeResultPanel.vue'
import ExerciseDescriptionPanel from './ExerciseDescriptionPanel.vue'
import ExerciseSetupPanel from './ExerciseSetupPanel.vue'
import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
import LoadingIndicator from './LoadingIndicator.vue'
const props = defineProps({
  connectAtStart: { type: Boolean, required: false },
  showLeftPanel: { type: Boolean, required: false },
  showCreatorPanel: { type: Boolean, required: false }
})

const codeRunnerStore = useCodeRunnerStore()

const subscribeStatus = ref(false)
const meaages = ref('')
const tryingToEstablishConnection: Ref<boolean> = ref(false)
const establishedConnection: Ref<boolean> = ref(false)
const VmAcces: Ref<boolean> = ref(false)
const chosenLangague: Ref<String> = ref(languageChoices[0])
const code: Ref<string> = ref('Write Code')
const resultData = ref(basicResultTemplate)

const updateVmStatus = (state: CodeRunnerState) => {
  console.log('status: ' + state)
  if (state.state == 'STOPPED' || state.state == 'RUNNING_MACHINE') {
    console.log('vmacces')
    VmAcces.value = true
  } else VmAcces.value = false
}

const updateResults = (results: CodeResultsType[]) => {
  console.log('results recived: ' + JSON.stringify(results))
  resultData.value = results
}

const diconnectFromCodeRunners = () => {
  // console.log("diconnect from code runners")
  //TODO: dicconrt requstDefaultVmMachine
  disconnectStomp()
  codeRunnerStore.disconnetWithCodeRunner();
}

onMounted(() => {
  console.log('props: ' + JSON.stringify(props))
  // if (props.connectAtStart) {
  connectStomp()
  //connectToCodeRunner()
  // }
})

onBeforeRouteLeave(async (to, from, next) => {
  diconnectFromCodeRunners()

  next()
})

const onSelectLanguage = (lang: string) => {
  console.log('info selcted:' + lang)
  chosenLangague.value = lang
  if (establishedConnection.value) requstDefaultVmMachine(lang)
}

const onRunCode = () => {
  console.log('on run code: ' + code.value)
  const toCompielMes: CodeToRunMessage = {
    code: code.value,
    exercise_id: null
  }
  sendToCompile(toCompielMes)
}

onBeforeRouteLeave(async (to, from) => {
  // console.log("leave************************************************")
  // codeRunnerStore.disconnetWithCodeRunner();
  diconnectFromCodeRunners()
})
</script>

<style>
.heightLimit * {
  max-height: 100vh;
}
</style>
