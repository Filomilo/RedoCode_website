import { defineStore } from 'pinia'
import { ref, computed, type Ref } from 'vue'
import type CodeRunnerState from '../types/CodeRunnerState'
import ExerciseData from '@/types/ExerciseData'
import axios from 'axios'
import '../interceptors/axios'
import { languageChoices } from '../config/Data'
import ExerciseTest from '@/types/ExcericseTest'
import {connectStomp,onConnectStomp} from '@/config/StompApiConnection'
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

  const fibonachi = (val: number) => {
    const out = []
    if (val > 0) out.push(0)
    if (val > 1) out.push(1)
    for (let i = 2; i < val; i++) {
      out.push(out[i - 1] + out[i - 2])
    }

    return out
  }

  const expectedResult: string = `function result(val) {
      out = []
      if (val > 0)
          out.push(0)
      if (val > 1)
          out.push(1)
      for (i = 2; i < val; i++) {
          out.push(out[i - 1] + out[i - 2])
      }
  
      return out
  }`

  // const runCode = async (code: string) => {
  //   // await axios.post('/runCode',code, {
  //   //     headers: {
  //   //       'Content-Type': 'application/json'
  //   //     }
  //   //   });
  //   isAwaitingCompilation.value = true
  //   console.log("1-------Running code: "+code)
  //   if(exerciseData.value.title===""){
  //   setTimeout(() => {
  //     if (code.includes('print("Hello world")')) {
  //       exerciseData.value.tests[0].consoleOutput = 'hello world'
  //       exerciseData.value.tests[0].errorOutput = ''
  //     } else {
  //       exerciseData.value.tests[0].consoleOutput = ''
  //       exerciseData.value.tests[0].errorOutput =
  //         'this is examoke the code should include phrase ' + 'print("Hello world")'
  //     }
  //     isAwaitingCompilation.value = false
  //   }, 5000)
  // }
  //   else{
  //     setTimeout(() => {
  //       console.log("exercise code runnign")
  //       if (code.includes("out.push(out[i - 1] + out[i - 2])")) {
  //         console.log("correct")

  //         exerciseData.value.tests.forEach((value)=>{
  //         value.errorOutput=""
  //         value.output=fibonachi(typeof value.input==="number"?value.input:0)
  //         value.isSolved=true;
  //        })
  //       } else {
  //         console.log("value: \n\n"+ code+"\n\n is not: \n\n"+ expectedResult)
  //         console.log("value: \n\n"+ code.length+"\n\n is not: \n\n"+ expectedResult.length)
  //         console.log("failed")

  //         exerciseData.value.tests.forEach((value)=>{
  //           value.output=null
  //           value.isSolved=false;

  //           value.errorOutput='this is examoke the code should include phrase\n ' + expectedResult ;
  //          })
  //       }
  //       isAwaitingCompilation.value = false
        
  //     }, 5000)
  //   }
 

  // }

///////////// STOMP CONNECTION
// import { onConnectStomp } from '@/config/StompApiConnection'



  const VmMachineStatusCallBack=(state: CoderunnerState)=>{
    console.log("new vm machine status: "+JSON.stringify(state))
    codeRunnerActive.value=state;
  }

  

  const requestCodeRunner = (codeRunnerName: string) => {
    // connectStomp();
    // onConnectStomp((frame: IFrame)=>{
    //   subcribeToVmStatus(VmMachineStatusCallBack);
    //   requstDefaultVmMachine(codeRunnerName);
    // });
    // console.log("connecting to vm mahicne state callback");
    codeRunnerActive.value.state="AWAITING"
    subcribeToVmStatus(VmMachineStatusCallBack);
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
