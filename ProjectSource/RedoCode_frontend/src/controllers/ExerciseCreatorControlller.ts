import ExercsieCreatorValidationMesage from '@/types/ApiMesseages/ExercsieCreatorValidationMesage'
import ExerciseTest from '@/types/ExcericseTest'
import ExerciseData from '@/types/ExerciseData'
import ExerciseParametersType from '@/types/ExerciseParametersType'
import IExerciseDescriptionI from '@/types/IExerciseDescriptionI'
import ITestParameters from '@/types/ITestParameters'
import RangeType from '@/types/RangeType'
import VarType from '@/types/VarType'
import type CodeRunnerType from '@/types/CodeRunnerTypes'
import { computed, ComputedRef, reactive } from 'vue'

type StringIndexed = {
  [key in CodeRunnerType]?: string
}
type TestsIndexed = { [key in CodeRunnerType]?: ExerciseTest[] }

export default class ExerciseCreatorController
  implements IExerciseDescriptionI, ExercsieCreatorValidationMesage
{
  title!: string
  description!: string
  languages!: CodeRunnerType[]
  ram!: number
  timeForTaskMin!: number
  timeForExecutionMs!: number
  inputType!: VarType
  outputType!: VarType
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
  solutionCodes!: StringIndexed
  manualTestsSolutions!: TestsIndexed
  autoTests!: TestsIndexed
  executionTime!: number
  isSolved!: boolean

  resetParams(this: any): void {
    ;(this.ram = 128),
      (this.timeForTaskMin = 15),
      (this.timeForExecutionMs = 100),
      (this.inputType = 'SINGLE_INTEGER' as VarType),
      (this.outputType = 'SINGLE_INTEGER' as VarType),
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
      (this.title = 'TESTESTTEST'),
      (this.description = 'DEscritptionDescription'),
      (this.lengthRange = { min: 1, max: 10 }),
      (this.spaceInupt = false)
    ;(this.solutionCodes = {}),
      (this.manualTestsSolutions = {} as TestsIndexed),
      (this.executionTime = 100)
  }

  constructor() {
    this.resetParams()
  }

  public updateSubmitAcces() {
    this.isSolved = this.calculalteSubmitAcces()
  }

  public calculalteSubmitAcces() {
    if (Object.values(this.manualTestsSolutions).length == 0) return false
    console.log(
      '---isSolved values: ' + JSON.stringify(this.manualTestsSolutions)
    )
    console.log(
      '---isSolved values amont: : ' +
        Object.values(this.manualTestsSolutions).length +
        ' : ' +
        JSON.stringify(Object.values(this.manualTestsSolutions))
    )

    for (const tests of Object.values(this.manualTestsSolutions)) {
      for (const test of tests) {
        console.log('test: ' + JSON.stringify(test))
        if (test.isSolved !== true) {
          console.log('false')
          return false // Exit the outer function early
        }
      }
    }

    console.log('TRUe')
    return true
  }

  manualTests!: ExerciseTest[] //compatiblity reasons do not use
}
