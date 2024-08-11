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
import ExerciseCreatorController from '@/controllers/CodeRunner/ExerciseCreatorControlller'
import CoderunnerState from '../types/CodeRunnerState'
import { IFrame } from '@stomp/stompjs'
import VarType, {
  isTypeArray,
  isTypeDoubleArray,
  isTypeSingle,
  isTypeString,
} from '@/types/VarType'
import ExerciseParametersType from '@/types/ExerciseParametersType'
import { useApiConnectionStore } from './ApiConnectionStore'
import { isNullOrUndef } from 'chart.js/helpers'
import CodeRunnerType from '@/types/CodeRunnerTypes'
import CodeRunnerStatus from '@/types/CodeRunnerStatus'
import { useActiveUserStore } from './ActiveUserStore'
import CodeRunnerConnection from '@/controllers/CodeRunner/CodeRunnerConnection'
import StompApiSender from '@/controllers/Stomp/StompApiSender'
import StompApiSubsciptionContorller from '@/controllers/Stomp/StompApiSubsriptionsController'
import codeRunnerSender from '@/controllers/CodeRunner/CodeRunnerSender'
import CodeRunnerSender from '@/controllers/CodeRunner/CodeRunnerSender'
import PlayGroundRunnerCotroller from '@/controllers/CodeRunner/PlayGroundRunnerCotroller'
export const useCodeRunnerStore = defineStore('codeRunnerStore', () => {
  const apiConnectionStore = useApiConnectionStore()
  const activeUserStore = useActiveUserStore()

  console.log("apiConnectionStore: "+JSON.stringify(apiConnectionStore))

  const codeRunnerConnection: CodeRunnerConnection = new CodeRunnerConnection(
    apiConnectionStore.stompApiSender as StompApiSender,
    apiConnectionStore.stompApiSubsciptionContorller as StompApiSubsciptionContorller
  )
  const codeRunnerSender: CodeRunnerSender=new CodeRunnerSender(apiConnectionStore.stompApiSender as StompApiSender,codeRunnerConnection );

const playGroundRunnerCotroller: PlayGroundRunnerCotroller= new PlayGroundRunnerCotroller();

  



  const playGroundBase: ExerciseData = {
    inputType: '',
    title: '',
    description: '',
    id: null,
    outputType: '',
    availbleCodeRunners: languageChoices.map(element => element.value),
    tests: [
      {
        input: '',
        output: '',
        errorOutput: '',
        consoleOutput: '',
        expectedOutput: '',
        isSolved: null,
        uuid: ''
      },
    ],
    automaticTests: [],
    startingFunction: '',
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



  const exerciseCreatorController = reactive(new ExerciseCreatorController())


  const manualTestBuffer: Ref<ExerciseTest[]> = ref([])


  const updateTestData = (reuslts: ProgramResult[]) => {
    console.log('----updateTestData')
    exerciseData.value.tests.forEach((val: ExerciseTest, index: number) => {
      val.consoleOutput = isNullOrUndef(reuslts[index].consoleOutput.output)
        ? ''
        : reuslts[index].consoleOutput.output
      val.errorOutput = isNullOrUndef(reuslts[index].consoleOutput.errorOutput)
        ? ''
        : reuslts[index].consoleOutput.errorOutput
      val.output = isNullOrUndef(reuslts[index].variables)
        ? null
        : reuslts[index].variables
      val.isSolved = val.expectedOutput === reuslts[index].variables
    })
  }


  //   const test: ExerciseTest[] =
  //     exerciseCreatorController.manualTestsSolutions[
  //       apiConnectionStore.codeRunnerConnection.codeRunnerState.codeRunnerType
  //     ] ?? []
  //   console.log('tests: ' + test.length + ' : ' + JSON.stringify(test))
  //   test.forEach((val: ExerciseTest, index: number) => {
  //     console.log(
  //       '--Test: val' + JSON.stringify(val) + ' index: ' + JSON.stringify(index)
  //     )

  //     val.consoleOutput = isNullOrUndef(reuslts[index].consoleOutput.output)
  //       ? ''
  //       : reuslts[index].consoleOutput.output
  //     val.errorOutput = isNullOrUndef(reuslts[index].consoleOutput.errorOutput)
  //       ? ''
  //       : reuslts[index].consoleOutput.errorOutput
  //     val.output = isNullOrUndef(reuslts[index].variables)
  //       ? null
  //       : reuslts[index].variables
  //     val.isSolved = val.expectedOutput === reuslts[index].variables
  //   })

  //   exerciseCreatorController.manualTestsSolutions[
  //     apiConnectionStore.codeRunnerConnection.codeRunnerState.codeRunnerType
  //   ] = test
  // }




  return {
    codeRunnerConnection,
    exerciseCreatorController,
    codeRunnerSender,
    playGroundRunnerCotroller
    // codeRunnerActive,
    // doesHaveACtiveToCodeRunner,
    // requestCodeRunner,
    // isAwaitngCodeRunner,
    // exerciseData,
    // // isAwaitingCompilation,
    // setExceriseDataToPlayground,
    // setExerciseData,
    // exerciseLoading,
    // setExerciseLoading,
    // // disconnetWithCodeRunner,
    // // startingMethod,
    // // areResultCorsrect,
    // // removeTest,
    // exerciseCreatorController,
    // // manualTestBuffer,
    // // clearTestsFromBuffer,
    // // addblankTestToBuffer,
    // // removeTestFromBuffer,
    // // transferTestFromBufferTpCreator,
    // // updateTestData,
    // getExerciseSetupError,
    // // updateCreationTestData,
    // updateCodeRunner,
  }
})
