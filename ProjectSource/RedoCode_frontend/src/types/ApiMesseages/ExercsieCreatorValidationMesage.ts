import CodeRunnerType from '../CodeRunnerTypes'
import ExerciseTest from '../ExcericseTest'
import RangeType from '../RangeType'
import VarType from '../VarType'
import ITestParameters from '@/types/ITestParameters'

type StringIndexed = {
  [key in CodeRunnerType]?: string;
};interface TestsIndexed extends Record<CodeRunnerType, ExerciseTest[]> {}

export default interface ExercsieCreatorValidationMesage
  extends ITestParameters {
  title: string
  description: string
  ram: number
  inputType: VarType
  outputType: VarType
  amountOfAutoTests: number
  autoTestminValue: number
  autoTestMaxValue: number
  lengthRange: RangeType
  xArrayRange: RangeType | null
  yArrayRange: RangeType | null
  upperCaseInput: boolean | null
  lowerCaseInput: boolean | null
  numberInput: boolean | null
  specialCharacterInput: boolean | null
  breakCharacterInupt: boolean | null
  spaceInupt: boolean | null
  solutionCodes: StringIndexed
  timeForTaskMin: number
  timeForExecutionMs: number
}
