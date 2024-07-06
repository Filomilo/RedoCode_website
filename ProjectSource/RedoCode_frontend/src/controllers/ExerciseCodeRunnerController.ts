import {
  requstDefaultVmMachine,
  subcribeToVmStatus,
  subscribeToCodeResults
} from '@/config/CodeRunnerConnection'
import CoderunnerState from '@/types/CodeRunnerState'
import CodeRunnerState from '@/types/CodeRunnerState'
import ExerciseParametersType from '@/types/ExerciseParametersType'
import RangeType from '@/types/RangeType'
import VarSize from '@/types/VarSize'
import VarType from '@/types/VarType'
import { computed, ref, Ref } from 'vue'
import type ProgramResult from '@/types/ProgramResults'
import { useActiveUserStore } from '@/stores/ActiveUserStore'
import { useApiConnectionStore } from '@/stores/ApiConnectionStore'
import CodeRunnerController from './CodeRunnerController'

export default class ExerciseCodeRunnerController extends CodeRunnerController {}
