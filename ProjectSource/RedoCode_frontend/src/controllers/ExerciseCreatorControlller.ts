import ExercsieCreatorValidationMesage from '@/types/ApiMesseages/ExercsieCreatorValidationMesage'
import ExerciseTest from '@/types/ExcericseTest'
import ExerciseData from '@/types/ExerciseData'
import ExerciseParametersType from '@/types/ExerciseParametersType'
import IExerciseDescriptionI from '@/types/IExerciseDescriptionI'
import ITestParameters from '@/types/ITestParameters'
import RangeType from '@/types/RangeType'
import VarSize from '@/types/VarSize'
import VarType from '@/types/VarType'
import { reactive } from 'vue'
interface StringIndexed {
  [index: string]: string
}
interface TestsIndexed {
  [index: string]: ExerciseTest[]
}
export default class ExerciseCreatorController
  implements IExerciseDescriptionI, ExercsieCreatorValidationMesage
{
  title!: string
  desc!: string
  languages!: string[] | { label: string; value: string }[]
  ram!: number
  timeForTaskM!: number
  timeForTaskH!: number
  executionTimeMs!: number
  inputType!: VarType
  inputSize!: VarSize
  outputType!: VarType
  outputSize!: VarSize
  amountOfAutoTests!: number
  autoTestminValue!: number
  autoTestMaxValue!: number
  lengthRange!: RangeType
  xArrayRange!: RangeType
  yArrayRange!: RangeType
  upperCaseInput!: boolean
  lowerCaseInput!: boolean
  numberInput!: boolean
  specialCharacterInput!: boolean
  breakCharacterInupt!: boolean
  spaceInupt!: boolean
  solutions!: StringIndexed
  manualTestsSolutions!: TestsIndexed
  autoTests!: TestsIndexed

  resetParams(this: any): void {
    ;(this.ram = 128),
      (this.timeForTaskM = 10),
      (this.timeForTaskH = 0),
      (this.executionTimeMs = 100),
      (this.inputType = 'int' as VarType),
      (this.inputSize = 'SINGLE_VALUE' as VarSize),
      (this.outputType = 'int' as VarType),
      (this.outputSize = 'SINGLE_VALUE' as VarSize),
      (this.amountOfAutoTests = 1),
      (this.autoTestminValue = -1),
      (this.autoTestMaxValue = 1),
      (this.upperCaseInput = true),
      (this.lowerCaseInput = true),
      (this.numberInput = true),
      (this.specialCharacterInput = true),
      (this.breakCharacterInupt = true),
      (this.languages = []),
      (this.xArrayRange = { min: 1, max: 10 }),
      (this.yArrayRange = { min: 1, max: 10 }),
      (this.title = ''),
      (this.desc = ''),
      (this.lengthRange = { min: 1, max: 10 }),
      (this.spaceInupt = false)
    this.solutions = {}
    this.manualTestsSolutions = {}
  }

  constructor() {
    this.resetParams()
  }
  manualTests!: ExerciseTest[] //compatiblity reasons do not use
}
