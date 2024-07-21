import { defineStore } from 'pinia'
import { ref, computed, type Ref, reactive } from 'vue'
import type CodeRunnerState from '../types/CodeRunnerState'
import ExerciseData from '@/types/ExerciseData'
import axios from 'axios'
import '../interceptors/axios'
import { languageChoices } from '../config/Data'
import ExerciseTest from '@/types/ExcericseTest'
import type ProgramResult from '@/types/ProgramResults'
import type CodeToRunMessage from '@/types/CodeToRunMessage'
import ExerciseCreatorController from '@/controllers/ExerciseCreatorControlller'
import CoderunnerState from '../types/CodeRunnerState'
import { IFrame } from '@stomp/stompjs'
import VarType, {
  isTypeArray,
  isTypeDoubleArray,
  isTypeSingle,
  isTypeString
} from '@/types/VarType'
import ExerciseParametersType from '@/types/ExerciseParametersType'
import { useApiConnectionStore } from './ApiConnectionStore'
import { isNullOrUndef } from 'chart.js/helpers'

export const useCodeRunnerStore = defineStore('codeRunnerStore', () => {
  const apiConnectionStore = useApiConnectionStore()

  const playGroundBase: ExerciseData = {
    inputType: '',
    title: '',
    description: '',
    id: null,
    outputType: '',
    availbleCodeRunners: languageChoices.map((element) => element),
    tests: [
      {
        input: '',
        output: '',
        errorOutput: '',
        consoleOutput: '',
        expectedOutput: '',
        isSolved: null
      }
    ],
    automaticTests: [],
    startingFunction: ''
  }

  const exerciseData: Ref<ExerciseData> = ref(playGroundBase)

  const setExerciseData = (exerciseDataRecived: ExerciseData) => {
    exerciseData.value = exerciseDataRecived
  }

  const setExceriseDataToPlayground = () => {
    exerciseData.value = playGroundBase
  }

  const exerciseLoading: Ref<boolean> = ref(false)
  const setExerciseLoading = (state: boolean) => {
    exerciseLoading.value = state
  }

  const dropDownLangaugeMap: any = {
    CPP_RUNNER: 'cpp',
    JS_RUNNER: 'js',
    UNIDENTIFIED: ''
  }
  const startingMethod = computed(() => {
    if (exerciseData.value.id != null) {
      console.log('--------------------------id is not null')
      if (apiConnectionStore.codeRunnerConnection.codeRunnerState.state === 'ACTIVE') {
        console.log(
          '--------------------------codeRunnerType is not UNIDENTIFIED: ' +
            JSON.stringify(apiConnectionStore.codeRunnerConnection.codeRunnerState)
        )
        return exerciseData.value.startingFunction[
          dropDownLangaugeMap[
            apiConnectionStore.codeRunnerConnection.codeRunnerState.codeRunnerType
          ]
        ]
      }
    }
    console.log('retune non')
    return ''
  })

  const areResultCorrect = computed(() => {
    return exerciseData.value.tests.every((x) => x.expectedOutput === x.output)
  })
  const isAwaitingCompilation: Ref<boolean> = ref(false)

  const exerciseCreatorController = reactive(new ExerciseCreatorController())
  const removeTestFromBuffer = (index: number) => {
    console.log('remove: test: ' + index)
    console.log('tests before: ' + JSON.stringify(exerciseData.value.tests))
    manualTestBuffer.splice(index, 1)
    console.log('tests after: ' + JSON.stringify(exerciseData.value.tests))
  }

  const getVarAcording: any = (type: VarType) => {
    if (isTypeString(type)) {
      if (isTypeSingle(type)) return ''
      if (isTypeArray(type)) return ['']
      if (isTypeDoubleArray(type)) return [['']]
    } else {
      if (isTypeSingle(type)) return 0
      if (isTypeArray(type)) return [0]

      if (isTypeDoubleArray(type)) return [[0]]
    }
  }

  const manualTestBuffer: any = reactive([])

  const addblankTestToBuffer = (inputType: VarType, outputype: VarType) => {
    const input = getVarAcording(inputType)
    const output = getVarAcording(outputype)
    console.log('ading ' + inputType + ' _ ' + ' :: ' + JSON.stringify(input))
    manualTestBuffer.push({
      input: input,
      expectedOutput: output,
      output: null,
      errorOutput: '',
      consoleOutput: '',
      isSolved: null
    })
    console.log('added: ' + manualTestBuffer)
  }
  const clearTestsFromBuffer = () => {
    manualTestBuffer.value = []
  }

  const transferTestFromBufferTpCreator = () => {
    console.log('test transfer2: ' + JSON.stringify(manualTestBuffer))

    exerciseCreatorController.languages.forEach(
      (element: { label: string; value: string } | string) => {
        const labelVal: { label: string; value: string } = element as unknown as {
          label: string
          value: string
        }
        exerciseCreatorController.manualTestsSolutions[labelVal.value] = manualTestBuffer
      }
    )
    console.log('tests after: ' + JSON.stringify(exerciseCreatorController.manualTestsSolutions))
  }

  const updateTestData = (reuslts: ProgramResult[]) => {
    console.log('----updateTestData')
    exerciseData.value.tests.forEach((val: ExerciseTest, index: number) => {
      val.consoleOutput = isNullOrUndef(reuslts[index].consoleOutput.output)
        ? ''
        : reuslts[index].consoleOutput.output
      val.errorOutput = isNullOrUndef(reuslts[index].consoleOutput.errorOutput)
        ? ''
        : reuslts[index].consoleOutput.errorOutput
      val.output = isNullOrUndef(reuslts[index].variables) ? null : reuslts[index].variables
      val.isSolved = val.expectedOutput === reuslts[index].variables
      isAwaitingCompilation.value=false
    })
  }

  return {
    // codeRunnerActive,
    // doesHaveACtiveToCodeRunner,
    // requestCodeRunner,
    // isAwaitngCodeRunner,
    exerciseData,
    isAwaitingCompilation,
    setExceriseDataToPlayground,
    setExerciseData,
    exerciseLoading,
    setExerciseLoading,
    // disconnetWithCodeRunner,
    startingMethod,
    // areResultCorsrect,
    // removeTest,
    exerciseCreatorController,
    manualTestBuffer,
    clearTestsFromBuffer,
    addblankTestToBuffer,
    removeTestFromBuffer,
    transferTestFromBufferTpCreator,
    updateTestData
  }
})
