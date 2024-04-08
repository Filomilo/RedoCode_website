<template>

  <ConfirmDialog></ConfirmDialog>
  <div class="CodeEditorPanelSetting">
    <Dropdown
      v-model="chosenLangague"
      :options="langaugesOptions"
      placeholder="Select programming langauge"
      class="dropDown"
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
      :language="chosenLangague"
    />
  </div>
</template>

<script lang="ts" setup>
import { ref, shallowRef } from 'vue'
import IconPlay from '@/assets/icons/IconPlay.vue'
import {useCodeRunnerStore} from '../stores/CodeRunnerStore'
import { useConfirm } from "primevue/useconfirm";
const codeRunnerStore=useCodeRunnerStore();
const model = defineModel()
defineProps({
  code: Object as () => string
})
const codeRef = ref('ss')
const confirm = useConfirm();
const MONACO_EDITOR_OPTIONS = {
  automaticLayout: true,
  formatOnType: true,
  formatOnPaste: true
}
const chosenLangague = ref('Cpp')
const langaugesOptions = ['cpp', 'js']
interface EditorLanguagesMap {
  [key: string]: string;
}
const editrLangesMap: EditorLanguagesMap={
  cpp: "cpp",
  js: "javascript"
}
const editorLang=computed(()=>{


  return editrLangesMap[codeRunnerStore.codeRunnerActive.codeRunnerType]
})

const editorRef = shallowRef()
const handleMount = (editor: any) => (editorRef.value = editor)

const onChangeLnageugeDropDown=(lang: any)=>{
  if(lang.value!!== codeRunnerStore.codeRunnerActive.codeRunnerType)
{ 
  console.log("test change: "+ lang.value)
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
            console.log(" confirm change: "+type)
            codeRunnerStore.requestCodeRunner(type)
        },
        reject: () => {
          console.log(" reject change: "+type)

        }
    });



  // console.log("lang: "+ type)
  // codeRunnerStore.requestCodeRunner(type)
}

// your action
function formatCode() {
  editorRef.value?.getAction('editor.action.formatDocument').run()
}
</script>
