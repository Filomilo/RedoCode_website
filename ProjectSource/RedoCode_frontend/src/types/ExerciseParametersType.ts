import IExerciseDescriptionI from './IExerciseDescriptionI'
import RangeType from './RangeType'
import VarType from './VarType'

export default interface ExerciseParametersType extends IExerciseDescriptionI {
  name: string
  description: string
  languages: string[]
  ram: number
  timeForTaskM: number
  timeForTaskH: number
  executionTimeMs: number
  inputType: VarType
  outputType: VarType
  amountOfAutoTests: number
  autoTestminValue: number
  autoTestMaxValue: number
  lengthRange: RangeType
  xArrayRange: RangeType
  yArrayRange: RangeType
  upperCaseInput: boolean
  lowerCaseInput: boolean
  numberInput: boolean
  specialCharacterInput: boolean
  breakCharacterInupt: boolean
  spaceInupt: boolean
}
