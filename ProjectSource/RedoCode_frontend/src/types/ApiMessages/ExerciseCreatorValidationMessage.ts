import CodeRunnerType from '../CodeRunnerTypes'
import ExerciseTest from '../ExerciseTest'
import RangeType from '../RangeType'
import VarType from '../VarType'
import ITestParameters from '@/types/ITestParameters'

type StringIndexed = {
  [key in CodeRunnerType]?: string
}
export interface TestsIndexed extends Record<CodeRunnerType, ExerciseTest[]> {}

export default interface ExerciseCreatorValidationMessage
  extends ITestParameters {
  title: string
  description: string
  ram: number
  inputType: VarType
  outputType: VarType
  amountOfAutoTests: number
  autoTestMinValue: number
  autoTestMaxValue: number
  lengthRange: RangeType
  xArrayRange: RangeType | null
  yArrayRange: RangeType | null
  upperCaseInput: boolean | null
  lowerCaseInput: boolean | null
  numberInput: boolean | null
  specialCharacterInput: boolean | null
  breakCharacterInput: boolean | null
  spaceInput: boolean | null
  solutionCodes: StringIndexed
  timeForExecutionMs: number
}
