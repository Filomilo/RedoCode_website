<template>
    <div style="
    height: 10rem;
    width: 10rem;
    min-width: 50rem;
    min-height: 5rem;
    <!-- display: flex; -->
    ">
    <vue-monaco-editor
      v-model:value="code"
      theme="vs-dark"
      :options="MONACO_EDITOR_OPTIONS"
      @mount="handleMount"
      language="sql"
    />

</div>
  </template>
  
  <script lang="ts" setup>
  import { ref, shallowRef } from 'vue'
  
  const MONACO_EDITOR_OPTIONS = {
    automaticLayout: true,
    formatOnType: true,
    formatOnPaste: true,
  }
  
  const code = ref('// some code...')
  const editorRef = shallowRef()
  const handleMount = (editor: any) => (editorRef.value = editor)
  
  // your action
  function formatCode() {
    editorRef.value?.getAction('editor.action.formatDocument').run()
  }
  </script>