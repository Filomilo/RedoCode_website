import { defineStore } from 'pinia'
import { ref, computed, type Ref, reactive } from 'vue'
import type CodeRunnerState from '../types/CodeRunnerState'
import ExerciseData from '@/types/ExerciseData'
import axios from 'axios'
import '../interceptors/axios'
import { languageChoices } from '../config/Data'
import ExerciseTest from '@/types/ExcericseTest'
import { connectStomp, onConnectStomp } from '@/config/StompApiConnection'
import type ProgramResult from '@/types/ProgramResults'
import type CodeToRunMessage from '@/types/CodeToRunMessage'
import ExerciseCreatorController from '@/controllers/ExerciseCreatorControlller'
import {
  requstDefaultVmMachine,
  subcribeToVmStatus,
  subscribeToCodeResults
} from '../config/CodeRunnerConnection'
import CoderunnerState from '../types/CodeRunnerState'
import { IFrame } from '@stomp/stompjs'
import VarType from '@/types/VarType'
import VarSize from '@/types/VarSize'
import ExerciseParametersType from '@/types/ExerciseParametersType'
import { useApiConnectionStore } from './ApiConnectionStore'
export const useCodeRunnerStore = defineStore('codeRunnerStore', () => {
const apiConnectionStore= useApiConnectionStore();

  const playGroundBase: ExerciseData = {
    inputType: '',
    title: '',
    desc: '',
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
      if (apiConnectionStore.codeRunnerConnectionControler.codeRunnerActive.state === 'ACTIVE') {
        console.log(
          '--------------------------codeRunnerType is not UNIDENTIFIED: ' +
            JSON.stringify(apiConnectionStore.codeRunnerConnectionControler.codeRunnerActive)
        )
        return exerciseData.value.startingFunction[
          dropDownLangaugeMap[apiConnectionStore.codeRunnerConnectionControler.codeRunnerActive.codeRunnerType]
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








  const exerciseCreatorController= reactive(new ExerciseCreatorController());
  const removeTestFromBuffer = (index: number) => {
    console.log('remove: test: ' + index)
    console.log('tests before: ' + JSON.stringify(exerciseData.value.tests))
    manualTestBuffer.splice(index, 1)
    console.log('tests after: ' + JSON.stringify(exerciseData.value.tests))
  }



  const getVarAcording: any = (type: VarType, size: VarSize) => {
    if (type === 'string') {
      switch (size) {
        case 'single_value':
          return ''
        case 'array':
          return ['']
        case '2d_array':
          return [['']]
      }
    } else {
      switch (size) {
        case 'single_value':
          return 0
        case 'array':
          return [0]
        case '2d_array':
          return [[0]]
      }
    }
    return 0
  }



 const manualTestBuffer:any= reactive([]);

 const addblankTestToBuffer = (
    inputType: VarType,
    outputype: VarType,
    inputSize: VarSize,
    outputSize: VarSize
  ) => {
    const input = getVarAcording(inputType, inputSize)
    const output = getVarAcording(outputype, outputSize)
    console.log('ading ' + inputType + ' _ ' + inputSize + ' :: ' + JSON.stringify(input))
    manualTestBuffer.push({
      input: input,
      expectedOutput: output,
      output: null,
      errorOutput: '',
      consoleOutput: '',
      isSolved: null
    }
   )
   console.log("added: "+manualTestBuffer )

  }
  const clearTestsFromBuffer = () => {
    manualTestBuffer.value = []
  }

  const transferTestFromBufferTpCreator=()=>{
    console.log("test transfer2: "+JSON.stringify(manualTestBuffer))
    exerciseCreatorController.languages.forEach((element) => {
      exerciseCreatorController.manualTestsSolutions[element.value]=manualTestBuffer;
    });
    console.log("tests after: "+ JSON.stringify(exerciseCreatorController.manualTestsSolutions) )
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
    transferTestFromBufferTpCreator
  }
})
