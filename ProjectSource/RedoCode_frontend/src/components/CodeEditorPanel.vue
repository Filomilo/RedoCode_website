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
      id="coderunner-langage-dropdown"
      optionLabel="label"
      optionValue="value"
    />
    <div class="CodeEditorDropDownContainer"></div>
    <div class="CodeEditorPlayButton">
      <Button
        @click="codeRunButton"
        v-if="!codeRunnerStore.isAwaitingCompilation"
        id="coderunner-run-button"
      >
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
  <div class="CodeEditorContainer">
    <vue-monaco-editor
      style="width: 100%; height: 100%"
      v-model:value="codeRef"
      theme="vs-dark"
      :options="MONACO_EDITOR_OPTIONS"
      @mount="handleMount"
      :language="
        EditorLanguagesMap[
          ApiConnectionStore.codeRunnerConnection.codeRunnerState.codeRunnerType
        ]
      "
      @keyup.ctrl.enter.prevent="onShortCutRun"
      :onChange="onCodeChnaage"
      id="coderunner-editor-panel"
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
  import { EditorLanguagesMap, languageChoices } from '@/config/Data'
  import { useApiConnectionStore } from '@/stores/ApiConnectionStore'
  import codeRunnerType from '@/types/CodeRunnerTypes'
  const props = defineProps({
    starting: { type: String, required: true },
    codeUpdateMethod: { type: Function, required: true },
    onRunCode: { type: Function, required: true },
  })

  const codeRunnerStore = useCodeRunnerStore()
  const ApiConnectionStore = useApiConnectionStore()

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
  }
  const chosenLangague = ref('Cpp')
  // const langaugesOptions = ['cpp', 'Js']
  const langaugesOptions = computed(() =>
    codeRunnerStore.exerciseData.title === ''
      ? languageChoices
      : codeRunnerStore.exerciseData.availbleCodeRunners
  )

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
        ApiConnectionStore.codeRunnerConnection.codeRunnerState.codeRunnerType
      ]
  )

  const editorLang = computed(() => {
    return EditorLanguagesMap[
      ApiConnectionStore.codeRunnerConnection.codeRunnerState.codeRunnerType
    ]
  })

  const editorRef = shallowRef()
  const handleMount = (editor: any) => (editorRef.value = editor)

  const onChangeLnageugeDropDown = (lang: any) => {
    if (
      lang.value! !==
      ApiConnectionStore.codeRunnerConnection.codeRunnerState.codeRunnerType
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
        ApiConnectionStore.codeRunnerConnection.requestCodeRunner(type)
      },
      reject: () => {
        console.log(' reject change: ' + type)
      },
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
