<template>
  <ConfirmDialog></ConfirmDialog>
  <div class="CodeEditorPanelSetting">
    <Dropdown
      :modelValue="codeRunnerStore.codeRunnerActive.codeRunnerType"
      :options="langaugesOptions"
      placeholder="Select programming langauge"
      class="dropDown"
      @change="onChangeLnageugeDropDown"
    />
    <div class="CodeEditorDropDownContainer"></div>
    <div class="CodeEditorPlayButton">
      <Button @click="onRunCode" v-if="!codeRunnerStore.isAwaitingCompilation">
        <IconPlay style="z-index: 9" />
      </Button>
      <div v-else>
        <LoadingIndicator style="max-height: 2rem; max-width: 2rem" />
      </div>
    </div>
  </div>
  <div class="CodeEditorContainer">
    <vue-monaco-editor
      v-model:value="codeRef"
      theme="vs-dark"
      :options="MONACO_EDITOR_OPTIONS"
      @mount="handleMount"
      :change="chosenLangague"
      :language="editrLangesMap[codeRunnerStore.codeRunnerActive.codeRunnerType]"
    />
  </div>
</template>

<script lang="ts" setup>
import { ref, shallowRef, computed, watch } from 'vue'
import IconPlay from '@/assets/icons/IconPlay.vue'
import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
import { useConfirm } from 'primevue/useconfirm'
import LoadingIndicator from './LoadingIndicator.vue'
import { onBeforeRouteLeave } from 'vue-router'
import { languageChoices } from '@/config/Data'
const codeRunnerStore = useCodeRunnerStore()
defineProps({
  code: Object as () => string
})
const codeRef = ref(codeRunnerStore.exerciseData.startingFunction)
const confirm = useConfirm()
const MONACO_EDITOR_OPTIONS = {
  automaticLayout: true,
  formatOnType: true,
  formatOnPaste: true
}
const chosenLangague = ref('Cpp')
// const langaugesOptions = ['cpp', 'Js']
const langaugesOptions=computed (()=> codeRunnerStore.exerciseData.title===''?languageChoices:codeRunnerStore.exerciseData.availbleCodeRunners)
interface EditorLanguagesMap {
  [key: string]: string
}
const editrLangesMap: EditorLanguagesMap = {
  cpp: 'cpp',
  js: 'javascript'
}
const editorLang = computed(() => {
  return editrLangesMap[codeRunnerStore.codeRunnerActive.codeRunnerType]
})

const editorRef = shallowRef()
const handleMount = (editor: any) => (editorRef.value = editor)

const onChangeLnageugeDropDown = (lang: any) => {
  if (lang.value! !== codeRunnerStore.codeRunnerActive.codeRunnerType) {
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
      codeRunnerStore.requestCodeRunner(type)
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
      console.log("updatedexercise data########################")
      if(!codeRunnerStore.exerciseLoading)
      codeRef.value=codeRunnerStore.exerciseData.startingFunction
    },
  )

const onRunCode = () => {
  console.log('running code: \n' + JSON.stringify(codeRef.value))
  codeRunnerStore.runCode(codeRef.value)
}

onBeforeRouteLeave((to, from) => {
  codeRef.value=""
})
</script>
