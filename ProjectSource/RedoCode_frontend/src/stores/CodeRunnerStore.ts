import { defineStore } from 'pinia'
import { ref, computed, type Ref } from 'vue'
import type CodeRunnerState from '../types/CodeRunnerState'
import ExerciseData from '@/types/ExerciseData'
import axios from 'axios'
import '../interceptors/axios'
import { languageChoices } from '../config/Data'
import ExerciseTest from '@/types/ExcericseTest'
import {connectStomp,onConnectStomp} from '@/config/StompApiConnection'
import type ProgramResult from '@/types/ProgramResults'
import type CodeToRunMessage from '@/types/CodeToRunMessage'
import {
  requstDefaultVmMachine,
  subcribeToVmStatus,
  sendToCompile,
  subscribeToCodeResults,

} from '../config/CodeRunnerConnection'
import CoderunnerState from '../types/CodeRunnerState'
import { IFrame } from '@stomp/stompjs'
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
  const setExerciseData=(exerciseDataRecived: ExerciseData)=>{
    exerciseData.value=exerciseDataRecived
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



  const VmMachineStatusCallBack=(state: CoderunnerState)=>{
    console.log("new vm machine status: "+JSON.stringify(state))
    codeRunnerActive.value=state;
  }
  const CodeRunnerResultsCallBack=(res: ProgramResult[])=>{
    console.log("new code runner resutls: "+JSON.stringify(res))
    exerciseData.value.tests.forEach((test: ExerciseTest,index: number)=>{
      test.consoleOutput=  res[index].consoleOutput.output===null?"": res[index].consoleOutput.output;
      test.errorOutput=  res[index].consoleOutput.errorOutput===null?"": res[index].consoleOutput.errorOutput;
      test.output=res[index].variables;
    test.isSolved=res[index].variables===test.expectedOutput
    })
    isAwaitingCompilation.value=false;
  }

  

  const requestCodeRunner = (codeRunnerName: string) => {

    codeRunnerActive.value.state="AWAITING"
    subcribeToVmStatus(VmMachineStatusCallBack);
    subscribeToCodeResults(CodeRunnerResultsCallBack)
    requstDefaultVmMachine(codeRunnerName);
    
    // console.log("connecting to vm mahicne state callback");
  }
  const runCode = async (code: string) => {
    console.log("sending code to run: "+code)
    const message: CodeToRunMessage={
      code: code,
      exercise_id: null
    }
sendToCompile(message);
isAwaitingCompilation.value = true
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
    
  }
})
