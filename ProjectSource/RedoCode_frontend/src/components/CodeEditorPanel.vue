<template>
    
    <div class="CodeEditorPanelSetting">
      <Dropdown  v-model="chosenLangague" :options="langaugesOptions" placeholder="Select programming langauge" class="dropDown"/>
   <div class="CodeEditorDropDownContainer">
    
   </div>
      <div class="CodeEditorPlayButton" >
        <Button >
          <IconPlay style="z-index: 9;"/>
        </Button>
       
      </div>
    </div>
<div class="CodeEditorContainer">
    <vue-monaco-editor v-model:value="model" theme="vs-dark" :options="MONACO_EDITOR_OPTIONS" @mount="handleMount"
      :language="chosenLangague" />
    </div>

</template>
  
<script lang="ts" setup>
import { ref, shallowRef } from 'vue'
import IconPlay from '@/assets/icons/IconPlay.vue';
const model = defineModel()
defineProps({
  code: Object as () => string,
})
const codeRef = ref('ss')

const MONACO_EDITOR_OPTIONS = {
  automaticLayout: true,
  formatOnType: true,
  formatOnPaste: true,
}
const chosenLangague=ref("Cpp");
const langaugesOptions=["cpp","Js"]

const editorRef = shallowRef()
const handleMount = (editor: any) => (editorRef.value = editor)

// your action
function formatCode() {
  editorRef.value?.getAction('editor.action.formatDocument').run()
}
</script>