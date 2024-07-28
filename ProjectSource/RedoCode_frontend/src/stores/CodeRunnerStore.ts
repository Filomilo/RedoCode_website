import { defineStore } from 'pinia'
import { ref, computed, type Ref, reactive, ComputedRef } from 'vue'
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
import CodeRunnerType from '@/types/CodeRunnerTypes'
import CodeRunnerStatus from '@/types/CodeRunnerStatus'

export const useCodeRunnerStore = defineStore('codeRunnerStore', () => {
  const apiConnectionStore = useApiConnectionStore()

  const playGroundBase: ExerciseData = {
    inputType: '',
    title: '',
    description: '',
    id: null,
    outputType: '',
    availbleCodeRunners: languageChoices.map((element) => element.value),
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


  const startingMethod = computed(() => {
    // if (exerciseData.value.id != null) {
    //   console.log('--------------------------id is not null')
    //   if (apiConnectionStore.codeRunnerConnection.codeRunnerState.state === 'ACTIVE') {
    //     console.log(
    //       '--------------------------codeRunnerType is not UNIDENTIFIED: ' +
    //         JSON.stringify(apiConnectionStore.codeRunnerConnection.codeRunnerState)
    //     )
    //     return exerciseData.value.startingFunction[
    //       dropDownLangaugeMap[
    //         apiConnectionStore.codeRunnerConnection.codeRunnerState.codeRunnerType
    //       ]
    //     ]
    //   }
    // }
    // console.log('retune non')
    return ''
  })

  const areResultCorrect = computed(() => {
    return exerciseData.value.tests.every((x) => x.expectedOutput === x.output)
  })
  const isAwaitingCompilation: ComputedRef<boolean> = computed(()=>{
    return apiConnectionStore.codeRunnerConnection.codeRunnerState.state===CodeRunnerStatus.RUNNING
  })

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

  const manualTestBuffer: any = ref([])

  const addblankTestToBuffer = (inputType: VarType, outputype: VarType) => {
    const input = getVarAcording(inputType)
    const output = getVarAcording(outputype)
    console.log('ading ' + inputType + ' _ ' + ' :: ' + JSON.stringify(input))
    manualTestBuffer.value.push({
      input: input,
      expectedOutput: output,
      output: null,
      errorOutput: '',
      consoleOutput: '',
      isSolved: null
    })
    console.log('added: ' + JSON.stringify(manualTestBuffer))
  }
  const clearTestsFromBuffer = () => {
    manualTestBuffer.value = []
  }

  const transferTestFromBufferTpCreator = () => {
    console.log('test transfer2: ' + JSON.stringify(manualTestBuffer))

    exerciseCreatorController.languages.forEach(
      (element: CodeRunnerType) => {
        exerciseCreatorController.manualTestsSolutions[element] = manualTestBuffer
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
    })
  }

  const getExerciseSetupError = (): String => {
    if (exerciseCreatorController.languages.length == 0) {
      return 'at least one programing lnaguage should be available'
    }
    if (exerciseCreatorController.timeForTaskMin < 15) {
      return 'at least 15 minute should be for task'
    }
    if (manualTestBuffer.value.length < 3) {
      return 'tthere should be at least 3 manuall tests'
    }
    if (manualTestBuffer.value.length > 10) {
      return 'amount of manula test cannot exceed 10'
    }

    return ''
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
    updateTestData,
    getExerciseSetupError
  }
})
