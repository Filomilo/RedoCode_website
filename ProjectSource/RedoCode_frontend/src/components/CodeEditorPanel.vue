<!-- eslint-disable vue/no-mutating-props -->
<template>
  test:
  {{ props.starting }}
  <ConfirmDialog></ConfirmDialog>
  <div class="CodeEditorPanelSetting">
    <Dropdown
      :modelValue="lnagaugeDropdownVaule"
      :options="langaugesOptions"
      placeholder="Select programming langauge"
      class="dropDown"
      @change="onChangeLnageugeDropDown"
      
    />
    <div class="CodeEditorDropDownContainer"></div>
    <div class="CodeEditorPlayButton">
      <Button @click="props.onRunCode" v-if="!codeRunnerStore.isAwaitingCompilation">
        <IconPlay style="z-index: 9" />
      </Button>
      <div v-else>
        <LoadingIndicator style="max-height: 2rem; max-width: 2rem" />
      </div>
    </div>
  </div>
  <div class="CodeEditorContainer">
    <vue-monaco-editor
      style="width: 100%; height: 100%"
      v-model:value="codeRef"
      
      theme="vs-dark"
      :options="MONACO_EDITOR_OPTIONS"
      @mount="handleMount"
      :language="editrLangesMap[ApiConnectionStore.codeRunnerConnectionControler.codeRunnerActive.codeRunnerType]"
      @keyup.ctrl.enter.prevent="onShortCutRun"
      :onChange="onCodeChnaage"
    />
  </div>
</template>

<script lang="ts" setup>
import { ref, shallowRef, computed, watch, onMounted, Ref } from 'vue'
import IconPlay from '@/assets/icons/IconPlay.vue'
import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
import { useConfirm } from 'primevue/useconfirm'
import LoadingIndicator from './LoadingIndicator.vue'
import { onBeforeRouteLeave } from 'vue-router'
import { languageChoices } from '@/config/Data'
import { useApiConnectionStore } from '@/stores/ApiConnectionStore'
const props = defineProps({
  starting: { type: String, required: true }, 
  codeUpdateMethod: {type: Function, required: true},
  onRunCode: {type: Function, required: true}
})

const codeRunnerStore = useCodeRunnerStore();
const ApiConnectionStore= useApiConnectionStore();

const dropDownLangaugeMap: EditorLanguagesMap = {
  CPP_RUNNER: 'cpp',
  JS_RUNNER: 'js',
  UNIDENTIFIED: ''
}
const codeRef = ref(props.starting)

const onCodeChnaage=(text:string)=>{
  props.codeUpdateMethod(text);
}
const confirm = useConfirm()
const MONACO_EDITOR_OPTIONS = {
  automaticLayout: true
}
const chosenLangague = ref('Cpp')
// const langaugesOptions = ['cpp', 'Js']
const langaugesOptions = computed(() =>
  codeRunnerStore.exerciseData.title === ''
    ? languageChoices
    : codeRunnerStore.exerciseData.availbleCodeRunners
)
interface EditorLanguagesMap {
  [key: string]: string
}
const editrLangesMap: EditorLanguagesMap = {
  CPP_RUNNER: 'cpp',
  JS_RUNNER: 'javascript',
  UNIDENTIFIED: ''
}
watch(
  () => props.starting,
  (first,second) => {
    console.log("props chahned-----------------------: "+ second+" -> "+first )
    codeRef.value = first
  }
)

const lnagaugeDropdownVaule = computed(
  () => dropDownLangaugeMap[ApiConnectionStore.codeRunnerConnectionControler.codeRunnerActive.codeRunnerType]
)

const editorLang = computed(() => {
  return editrLangesMap[ApiConnectionStore.codeRunnerConnectionControler.codeRunnerActive.codeRunnerType]
})

const editorRef = shallowRef()
const handleMount = (editor: any) => (editorRef.value = editor)

const onChangeLnageugeDropDown = (lang: any) => {
  if (lang.value! !== ApiConnectionStore.codeRunnerConnectionControler.codeRunnerActive.codeRunnerType) {
    console.log('test change: ' + lang.value)
    confirmChangeOFCodeRuner(lang.value)
  }
}
const confirmChangeOFCodeRuner = (type: string) => {
  confirm.require({
    message: 'Changing code runner may take a while',
    header: 'Are you sure?',
    rejectClass: 'CancelButton',
    acceptClass: 'AcceptButton',
    rejectLabel: 'Cancel',
    acceptLabel: 'Change',
    accept: () => {
      console.log(' confirm change: ' + type)
      ApiConnectionStore.codeRunnerConnectionControler.requestCodeRunner(type)
    },
    reject: () => {
      console.log(' reject change: ' + type)
    }
  })
}

function formatCode() {
  editorRef.value?.getAction('editor.action.formatDocument').run()
}

watch(
  () => codeRunnerStore.exerciseLoading,
  () => {
    // console.log('updatedexercise data########################')
    // if (!codeRunnerStore.exerciseLoading)
    //   codeRef.value = codeRunnerStore.exerciseData.startingFunction
  }
)

// const onRunCode = () => {
//   console.log('running code: \n' + JSON.stringify(codeRef.value))
//   codeRunnerStore.runCode(codeRef.value)
// }

onBeforeRouteLeave((to, from) => {
  codeRef.value = ''
})

const onShortCutRun = () => {
  console.log('onShortCutRun')
  props.onRunCode()
}
</script>
