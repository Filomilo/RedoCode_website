<template>
  <div class="SolutionsContainer">
    <div class="LeftSideBar-solutions">
      <SolutionList
        v-model="selectedSolutionId"
        :solutionList="props.solutionList"
        :maxExecutionTime="props.maxExecutionTime"
      />
    </div>
    <div class="codeContainer">
      <vue-monaco-editor
        style="width: 100%; height: 100%; max-width: 100%"
        :value="code"
        theme="vs-dark"
        :options="MONACO_EDITOR_OPTIONS"
        :language="lang"
        id="coderunner-editor-panel"
        @mount="handleMount"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
  import SolutionItemList from '@/types/ApiMessages/SolutionItemList'
  import SolutionList from '@/components/SolutionList.vue'
  import { computed, ref, shallowRef, watch } from 'vue'
  import { EditorLanguagesMap } from '@/config/Data'
  import CodeRunnerType from '@/types/CodeRunnerTypes'
  import EndpointsAccess from '@/controllers/EndpointsAccess'

  const selectedSolutionId = ref(-1)
  const code = ref('')
  const loadSolutionCode = async (solutionId: number) => {
    code.value = ''
    try {
      const codeIn =
        await EndpointsAccess.authorized.getSolutionsCodesData(solutionId)
      console.log('results: ' + JSON.stringify(codeIn))
      code.value = codeIn
    } catch (error) {
      console.error('Error loading solution code:', error)
    }
  }

  watch(selectedSolutionId, newValue => {
    loadSolutionCode(newValue)
  })

  const props = defineProps<{
    solutionList: SolutionItemList[]
    maxExecutionTime: number
  }>()

  const lang = computed(() => {
    if (props.solutionList === undefined) return undefined
    const type: CodeRunnerType | undefined = props.solutionList.find(
      x => x.solutionId === selectedSolutionId.value
    )?.codeRunner
    if (type === undefined) return ''
    return EditorLanguagesMap[type]
  })

  console.log(JSON.stringify(props))

  // const code=ref("TEST TEST")
  const MONACO_EDITOR_OPTIONS = {
    automaticLayout: true,
    autoClosingBrackets: false,

    readonly: true,
  }
  const editorRef = shallowRef()
  const handleMount = (editor: any) => {
    editorRef.value = editor
    editorRef.value.updateOptions({ readOnly: true })
  }
</script>

<style lang="css">
  .SolutionsContainer {
    border-radius: 1.5rem;
    border: 0.2rem solid var(--primary-color);
    overflow: hidden;
    display: flex;
  }

  .LeftSideBar-solutions {
    width: 20%;
    height: 100%;

    overflow-y: scroll;
    min-width: 15rem;
    max-width: 15rem;
    border-right: 0.1rem solid rgb(112, 112, 112);
  }

  .codeContainer {
    width: 100%;
    height: 100%;
  }
</style>
