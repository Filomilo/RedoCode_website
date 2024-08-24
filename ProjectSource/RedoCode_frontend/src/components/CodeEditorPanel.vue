<!-- eslint-disable vue/no-mutating-props -->
<template>

<div  class="CodeEditorContainer" style="display:contents;">
  <ConfirmDialog></ConfirmDialog>
  <div class="CodeEditorPanelSetting">
    <Dropdown
      :modelValue="
        codeRunnerStore.codeRunnerConnection.codeRunnerState.codeRunnerType
      "
      :options="laguageDropDown"
      placeholder="Select programming langauge"
      class="dropDown"
      @change="onChangeLnageugeDropDown"
      id="coderunner-langage-dropdown"
      optionLabel="label"
      optionValue="value"
    />
    <div class="CodeEditorDropDownContainer"></div>
    <div class="CodeEditorPlayButton">
      <Button @click="codeRunButton" v-if="true" id="coderunner-run-button">
        <IconPlay style="z-index: 9" />
      </Button>
      <div v-else>
        <LoadingIndicator
          style="height: 2rem; width: 2rem"
          id="coderunner-wait-button"
        />
      </div>
    </div>
  </div>
  <div style="display: none" id="code-preview">
    {{ codeRef }}
  </div>
  <div class="CodeEditorContainer">
    <vue-monaco-editor
      style="width: 100%; height: 100%; max=width:100%"
      v-model:value="codeRef"
      theme="vs-dark"
      :options="MONACO_EDITOR_OPTIONS"
      @mount="handleMount"
      :language="
        EditorLanguagesMap[
          codeRunnerStore.codeRunnerConnection.codeRunnerState.codeRunnerType
        ]
      "
      @keyup.ctrl.enter.prevent="onShortCutRun"
      :onChange="onCodeChnaage"
      id="coderunner-editor-panel"
    />
  </div>
</div>
</template>

<script lang="ts" setup>
  import {
    ref,
    shallowRef,
    computed,
    watch,
    onMounted,
    Ref,
    ComputedRef,
  } from 'vue'
  import IconPlay from '@/assets/icons/IconPlay.vue'
  import { useCodeRunnerStore } from '../stores/CodeRunnerStore'
  import { useConfirm } from 'primevue/useconfirm'
  import LoadingIndicator from './LoadingIndicator.vue'
  import { onBeforeRouteLeave } from 'vue-router'
  import { EditorLanguagesMap } from '@/config/Data'
  import { useApiConnectionStore } from '@/stores/ApiConnectionStore'
  import codeRunnerType, { languageDropDownType } from '@/types/CodeRunnerTypes'
  import LangaugeSelection from '@/tools/LangaugeSelection'

  const props = defineProps({
    starting: { type: String, required: true },
    codeUpdateMethod: { type: Function, required: true },
    onRunCode: { type: Function, required: true },
    languageChoices: { type: Array as () => codeRunnerType[], required: true },
  })

  const codeRunnerStore = useCodeRunnerStore()
  const ApiConnectionStore = useApiConnectionStore()
  // const ApiConnectionStore = useApiConnectionStore()
  const laguageDropDown: ComputedRef<languageDropDownType[]> = computed(() => {
    return LangaugeSelection.getDropDownFromLanguages(props.languageChoices)
  })
  const codeRef = ref(props.starting)

  const codeRunButton = () => {
    props.onRunCode()
  }

  const onCodeChnaage = (text: string) => {
    props.codeUpdateMethod(text)
  }
  const confirm = useConfirm()
  const MONACO_EDITOR_OPTIONS = {
    automaticLayout: true,
    autoClosingBrackets: false,
  }

  watch(
    () => props.starting,
    (first, second) => {
      console.log(
        'props chahned-----------------------: ' + second + ' -> ' + first
      )
      codeRef.value = first
    }
  )

  const lnagaugeDropdownVaule = computed(
    () =>
      EditorLanguagesMap[
        codeRunnerStore.codeRunnerConnection.codeRunnerState.codeRunnerType
      ]
  )

  const editorLang = computed(() => {
    return EditorLanguagesMap[
      codeRunnerStore.codeRunnerConnection.codeRunnerState.codeRunnerType
    ]
  })

  const editorRef = shallowRef()
  const handleMount = (editor: any) => (editorRef.value = editor)

  const onChangeLnageugeDropDown = (lang: any) => {
    if (
      lang.value! !==
      codeRunnerStore.codeRunnerConnection.codeRunnerState.codeRunnerType
    ) {
      console.log('test change: ' + lang.value)
      confirmChangeOFCodeRuner(lang.value)
    }
  }
  const confirmChangeOFCodeRuner = (type: codeRunnerType) => {
    confirm.require({
      message: 'Changing code runner may take a while',
      header: 'Are you sure?',
      rejectClass: 'CancelButton',
      acceptClass: 'AcceptButton',
      rejectLabel: 'Cancel',
      acceptLabel: 'Change',
      accept: () => {
        console.log(' confirm change: ' + type)
        codeRunnerStore.codeRunnerSender.requestCodeRunner(type)
      },
      reject: () => {
        console.log(' reject change: ' + type)
      },
    })
  }

  function formatCode() {
    editorRef.value?.getAction('editor.action.formatDocument').run()
  }

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


<style lang="scss">
.main{
  height: 100%;
  width: 100%;
  display: flex;
  flex: 1;
  flex-direction: row;
}
</style>
