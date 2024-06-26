import { defineStore } from 'pinia'
import { ref, computed, type Ref } from 'vue'
import type CodeRunnerState from '../types/CodeRunnerState'
import ExerciseData from '@/types/ExerciseData'
import axios from 'axios'
import '../interceptors/axios'
import { languageChoices } from '../config/Data'
import ExerciseTest from '@/types/ExcericseTest'
import { connectStomp, onConnectStomp } from '@/config/StompApiConnection'
import type ProgramResult from '@/types/ProgramResults'
import type CodeToRunMessage from '@/types/CodeToRunMessage'
import {
  requstDefaultVmMachine,
  subcribeToVmStatus,
  sendToCompile,
  subscribeToCodeResults
} from '../config/CodeRunnerConnection'
import CoderunnerState from '../types/CodeRunnerState'
import { IFrame } from '@stomp/stompjs'
import VarType from '@/types/VarType'
import VarSize from '@/types/VarSize'
export const useCodeRunnerStore = defineStore('codeRunnerStore', () => {
  const codeRunnerActive: Ref<CodeRunnerState> = ref({
    codeRunnerType: '',
    state: ''
  })
  const doesHaveACtiveToCodeRunner = computed(() => {
    // return true;
    return codeRunnerActive.value.state === 'ACTIVE'
  })
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

  const isAwaitngCodeRunner = computed(() => codeRunnerActive.value.state == 'AWAITING')
  const setExerciseData = (exerciseDataRecived: ExerciseData) => {
    exerciseData.value = exerciseDataRecived
  }

  const setExceriseDataToPlayground = () => {
    exerciseData.value = playGroundBase
  }

  const isAwaitingCompilation: Ref<boolean> = ref(false)
  const exerciseLoading: Ref<boolean> = ref(false)
  const setExerciseLoading = (state: boolean) => {
    exerciseLoading.value = state
  }

  const disconnetWithCodeRunner = () => {
    console.log('setting code unner to none')
    codeRunnerActive.value = {
      state: '',
      codeRunnerType: ''
    }
  }

  const VmMachineStatusCallBack = (state: CoderunnerState) => {
    console.log('new vm machine status: ' + JSON.stringify(state.codeRunnerType))
    codeRunnerActive.value = state
    // codeRunnerActive.value.codeRunnekkrType = 'CPP_RUNNER'
    // codeRunnerActive.value.codeRunnerType=state.codeRunnerType==="UUIANDTIFIED"?"":state.codeRunnerType
    console.log('codeRunnerActive: ' + JSON.stringify(codeRunnerActive))
  }
  const CodeRunnerResultsCallBack = (res: ProgramResult[]) => {
    isAwaitingCompilation.value = false

    console.log('new code runner resutls: ' + JSON.stringify(res))
    exerciseData.value.tests.forEach((test: ExerciseTest, index: number) => {
      test.consoleOutput =
        res[index].consoleOutput.output === null ? '' : res[index].consoleOutput.output
      test.errorOutput =
        res[index].consoleOutput.errorOutput === null ? '' : res[index].consoleOutput.errorOutput
      test.output = res[index].variables
      test.isSolved = res[index].variables === test.expectedOutput
    })
  }

  const requestCodeRunner = (codeRunnerName: string) => {
    codeRunnerActive.value.state = 'AWAITING'
    subcribeToVmStatus(VmMachineStatusCallBack)
    subscribeToCodeResults(CodeRunnerResultsCallBack)
    requstDefaultVmMachine(codeRunnerName)

    // console.log("connecting to vm mahicne state callback");
  }
  const runCode = async (code: string) => {
    console.log('sending code to run: ' + code)
    const message: CodeToRunMessage = {
      code: code,
      exercise_id: exerciseData.value.id
    }
    sendToCompile(message)
    isAwaitingCompilation.value = true
  }

  const dropDownLangaugeMap: any = {
    CPP_RUNNER: 'cpp',
    JS_RUNNER: 'js',
    UNIDENTIFIED: ''
  }
  const startingMethod = computed(() => {
    if (exerciseData.value.id != null) {
      console.log('--------------------------id is not null')
      if (codeRunnerActive.value.state === 'ACTIVE') {
        console.log(
          '--------------------------codeRunnerType is not UNIDENTIFIED: ' +
            JSON.stringify(codeRunnerActive.value)
        )
        return exerciseData.value.startingFunction[
          dropDownLangaugeMap[codeRunnerActive.value.codeRunnerType]
        ]
      }
    }
    console.log('retune non')
    return ''
  })

  const areResultCorrect = computed(() => {
    return exerciseData.value.tests.every((x) => x.expectedOutput === x.output)
  })

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

  const addblankTest = (
    inputType: VarType,
    outputype: VarType,
    inputSize: VarSize,
    outputSize: VarSize
  ) => {
    const input = getVarAcording(inputType, inputSize)
    const output = getVarAcording(outputype, outputSize)
    console.log('ading ' + inputType + ' _ ' + inputSize + ' :: ' + JSON.stringify(input))
    exerciseData.value.tests.push({
      input: input,
      expectedOutput: output,
      output: null,
      errorOutput: '',
      consoleOutput: '',
      isSolved: null
    })

    console.log('excerise tests: ' + JSON.stringify(exerciseData.value.tests))
  }

  const removeTest = (index: number) => {
    console.log('remove: test: ' + index)
    console.log('tests before: ' + JSON.stringify(exerciseData.value.tests))
    exerciseData.value.tests.splice(index, 1)
    console.log('tests after: ' + JSON.stringify(exerciseData.value.tests))
  }

  const setupCreatingExercise = () => {
    console.log('setigin creating test')

    exerciseData.value = {
      availbleCodeRunners: [],
      title: '',
      id: -1,
      desc: '',
      outputType: '',
      inputType: '',
      tests: [],
      automaticTests: [],
      startingFunction: ''
    }
  }

  const clearTests = () => {
    exerciseData.value.tests = []
  }

  return {
    codeRunnerActive,
    doesHaveACtiveToCodeRunner,
    requestCodeRunner,
    isAwaitngCodeRunner,
    exerciseData,
    isAwaitingCompilation,
    setExceriseDataToPlayground,
    runCode,
    setExerciseData,
    exerciseLoading,
    setExerciseLoading,
    disconnetWithCodeRunner,
    startingMethod,
    areResultCorrect,
    addblankTest,
    removeTest,
    setupCreatingExercise,
    clearTests
  }
})
