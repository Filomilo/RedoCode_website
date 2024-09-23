import ExerciseTest from './ExerciseTest'
import RangeType from './RangeType'
import VarType from './VarType'

export default interface ITestParameters {
  manualTests: ExerciseTest[]
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
  executionTime: number
}
