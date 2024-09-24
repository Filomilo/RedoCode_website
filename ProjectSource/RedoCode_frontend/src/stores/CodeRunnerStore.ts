import { defineStore } from 'pinia'
import { ref, type Ref, reactive } from 'vue'
import '../interceptors/axios'
import ExerciseCreatorController from '@/controllers/CodeRunner/ExerciseCreatorController'
import { useApiConnectionStore } from './ApiConnectionStore'
import CodeRunnerConnection from '@/controllers/CodeRunner/CodeRunnerConnection'
import StompApiSender from '@/controllers/Stomp/StompApiSender'
import CodeRunnerSender from '@/controllers/CodeRunner/CodeRunnerSender'
import PlayGroundRunnerController from '@/controllers/CodeRunner/PlayGroundRunnerController'
import ExerciseSolverController from '@/controllers/CodeRunner/ExerciseSolverController'
import StompApiSubscriptionController from '@/controllers/Stomp/StompApiSubscriptionController'
export const useCodeRunnerStore = defineStore('codeRunnerStore', () => {
  const apiConnectionStore = useApiConnectionStore()
  const codeRunnerConnection: CodeRunnerConnection = new CodeRunnerConnection(
    apiConnectionStore.stompApiSender as StompApiSender,
    apiConnectionStore.stompApiSubscriptionController as StompApiSubscriptionController
  )
  const codeRunnerSender: CodeRunnerSender = new CodeRunnerSender(
    apiConnectionStore.stompApiSender as StompApiSender,
    codeRunnerConnection
  )

  const playGroundRunnerController: PlayGroundRunnerController =
    new PlayGroundRunnerController()
  const exerciseCreatorController = reactive(new ExerciseCreatorController())
  const exerciseSolverController: Ref<ExerciseSolverController> = ref(
    new ExerciseSolverController()
  ) as Ref<ExerciseSolverController>






  const isProcessing: Ref<boolean> = ref(false)

  return {
    codeRunnerConnection,
    exerciseCreatorController,
    codeRunnerSender,
    playGroundRunnerController: playGroundRunnerController,
    exerciseSolverController,
    isProcessing,
 
  }
})
