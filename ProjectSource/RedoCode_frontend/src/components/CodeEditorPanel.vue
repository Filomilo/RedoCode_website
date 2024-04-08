<template>
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
      <Button>
        <IconPlay style="z-index: 9" />
      </Button>
    </div>
  </div>
  <div class="CodeEditorContainer">
    <vue-monaco-editor
      v-model:value="model"
      theme="vs-dark"
      :options="MONACO_EDITOR_OPTIONS"
      @mount="handleMount"
      :change="chosenLangague"
    :language="editrLangesMap[codeRunnerStore.codeRunnerActive.codeRunnerType]"
    />

  </div>
</template>

<script lang="ts" setup>
import { ref, shallowRef,computed } from 'vue'
import IconPlay from '@/assets/icons/IconPlay.vue'
import {useCodeRunnerStore} from '../stores/CodeRunnerStore'
const codeRunnerStore=useCodeRunnerStore();
const model = defineModel()
defineProps({
  code: Object as () => string
})
const codeRef = ref('ss')

const MONACO_EDITOR_OPTIONS = {
  automaticLayout: true,
  formatOnType: true,
  formatOnPaste: true
}
const chosenLangague = ref('Cpp')
const langaugesOptions = ['cpp', 'Js']
interface EditorLanguagesMap {
  [key: string]: string;
}
const editrLangesMap: EditorLanguagesMap={
  cpp: "cpp",
  Js: "javascript"
}
const editorLang=computed(()=>{


  return editrLangesMap[codeRunnerStore.codeRunnerActive.codeRunnerType]
})

const editorRef = shallowRef()
const handleMount = (editor: any) => (editorRef.value = editor)

const onChangeLnageugeDropDown=(lang: any)=>{
  if(lang.value!!== codeRunnerStore.codeRunnerActive.codeRunnerType)
  confirmChangeOFCodeRuner(lang.value)
}

const confirmChangeOFCodeRuner=(type: string)=>{
  console.log("lang: "+ type)
  codeRunnerStore.requestCodeRunner(type)
}

// your action
function formatCode() {
  editorRef.value?.getAction('editor.action.formatDocument').run()
}
</script>
