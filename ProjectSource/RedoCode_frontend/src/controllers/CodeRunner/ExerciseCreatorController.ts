import ExerciseTest from '@/types/ExerciseTest'
import IExerciseDescriptionI from '@/types/IExerciseDescriptionI'
import RangeType from '@/types/RangeType'
import CodeRunnerType from '@/types/CodeRunnerTypes'
import TestsController from '@/controllers/CodeRunner/GroupOfTestsController'
import VarType, {
  setTypeToArray,
  setTypeToDoubleArray,
  setTypeToSingle,
  setTypeToString,
  setTypeToInt,
  setTypeToFloat,
} from '@/types/VarType'
import ProgramResult from '@/types/ProgramResults'
import CodeRunnerControllerBase from './CodeRunnerControllerBase'

export type StringIndexed = {
  [key in CodeRunnerType]?: string
}
export type TestsIndexed = { [key in CodeRunnerType]?: TestsController }

export default class ExerciseCreatorController
  extends CodeRunnerControllerBase
  implements IExerciseDescriptionI
{
  //#region data
  get languages() {
    return this._languages
  }
  set languages(newLanguages: CodeRunnerType[]) {
    this._languages = newLanguages
    this.updateTestsFields()
  }

  title!: string
  desc!: string

  ram!: number
  timeForExecutionMs!: number
  inputType!: VarType
  outputType!: VarType
  amountOfAutoTests!: number
  autoTestMinValue!: number
  autoTestMaxValue!: number
  lengthRange!: RangeType
  xArrayRange!: RangeType
  yArrayRange!: RangeType
  upperCaseInput!: boolean
  lowerCaseInput!: boolean
  numberInput!: boolean
  specialCharacterInput!: boolean
  breakCharacterInput!: boolean
  spaceInput!: boolean
  solutionCodes!: StringIndexed
  manualTestsSolutions!: TestsIndexed
  isSolved!: boolean

  //#endregion

  //#region

  reset(this: any): void {
    console.log('creator reset')
    ;(this.ram = 128),
      (this.timeForTaskMin = 15),
      (this.timeForExecutionMs = 100),
      (this.inputType = 'SINGLE_INTEGER' as VarType),
      (this.outputType = 'SINGLE_INTEGER' as VarType),
      (this.amountOfAutoTests = 1),
      (this.autoTestMinValue = -1),
      (this.autoTestMaxValue = 1),
      (this.upperCaseInput = true),
      (this.lowerCaseInput = true),
      (this.numberInput = true),
      (this.specialCharacterInput = true),
      (this.breakCharacterInput = true),
      (this._languages = []),
      (this.xArrayRange = { min: 1, max: 10 }),
      (this.yArrayRange = { min: 1, max: 10 }),
      (this.title = ''),
      (this.desc = ''),
      (this.lengthRange = { min: 1, max: 10 }),
      (this.spaceInput = false)
    ;(this.solutionCodes = {}),
      (this.manualTestsSolutions = {} as TestsIndexed),
      (this.executionTime = 100)
    console.log('this.languages: ' + JSON.stringify(this.languages))
  }

  constructor() {
    super()
    this.reset()
  }

  public updateSubmitAccess() {
    this.isSolved = this.validateAllTests()
  }

  private validateAllTests(): boolean {
    if (Object.values(this.manualTestsSolutions).length == 0) return false
    console.log(
      '---isSolved values: ' + JSON.stringify(this.manualTestsSolutions)
    )
    console.log(
      '---isSolved values amount: : ' +
        Object.values(this.manualTestsSolutions).length +
        ' : ' +
        JSON.stringify(Object.values(this.manualTestsSolutions))
    )

    for (const tests of Object.values(this.manualTestsSolutions)) {
      for (const test of tests.tests) {
        console.log('test: ' + JSON.stringify(test))
        if (test.isSolved !== true) {
          console.log('false')
          return false
        }
      }
      for (const test of tests.autoTests) {
        console.log('test: ' + JSON.stringify(test))
        if (test.isSolved !== true) {
          console.log('false')
          return false
        }
      }
    }

    console.log('TRUe')
    return true
  }

  //#region

  private getAmountOfLanguages(): number {
    return this._languages.length
  }

  private getSingleCodeRunnerKey(): CodeRunnerType {
    return this._languages[0]
  }

  //#endregion

  //#region code management

  public updateSolutionCode(code: string, type: CodeRunnerType) {
    this.solutionCodes[type] = code
  }

  //#endregion

  //#region Test management

  private updateTestsFields() {
    this.manualTestsSolutions = {}
    this._languages.forEach((x: CodeRunnerType) => {
      this.manualTestsSolutions[x] = new TestsController()
    })
    console.log(
      'Update test fields: ' + JSON.stringify(this.manualTestsSolutions)
    )
  }

  public clearTests(): void {}

  public addEmptyTest() {
    if (this.getAmountOfLanguages() == 0)
      throw 'First you need select languages'

    if (
      this.manualTestsSolutions[this.getSingleCodeRunnerKey()]!.tests.length >=
      10
    ) {
      throw 'only 10 manual test are allowed'
    }
    console.log('output type: ' + this.outputType)
    Object.keys(this.manualTestsSolutions).forEach((x: string) => {
      const type: CodeRunnerType = x as CodeRunnerType
      this.manualTestsSolutions[type]?.addBlankTest(
        this.inputType,
        this.outputType
      )
    })
  }

  get getSingleRowOfManualTests(): ExerciseTest[] {
    if (this.getAmountOfLanguages() == 0) return []

    return this.manualTestsSolutions[this.getSingleCodeRunnerKey()]!.tests
  }

  set getSingleRowOfManualTests(tests: ExerciseTest[]) {
    if (
      this.getAmountOfLanguages() === 0 ||
      !this.manualTestsSolutions[this.getSingleCodeRunnerKey()]
    ) {
      throw 'Cannot set variables input'
    }

    this._languages.forEach((x: CodeRunnerType) => {
      this.manualTestsSolutions[x]!.tests = tests
    })
  }

  public setTestValue(isInput: boolean, index: number, value: any) {
    this.languages.forEach((x: CodeRunnerType) => {
      if (isInput) {
        this.manualTestsSolutions[x]!.tests[index].input = value
      } else {
        console.log('set output: ' + value)
        this.manualTestsSolutions[x]!.tests[index].expectedOutput = value
      }
    })
  }

  public updateTests(results: ProgramResult[], language: CodeRunnerType) {
    console.log('---------------------------------------------------\n')
    console.log('create test language update: ' + JSON.stringify(language))
    console.log('create test result update: ' + JSON.stringify(results))
    console.log('---------------------------------------------------\n')
    const processedResults = this.processCodeResultLoad(results, {
      tests: this.manualTestsSolutions[language]!.tests,
      autoTests: this.manualTestsSolutions[language]!.autoTests,
    })

    this.manualTestsSolutions[language]!.tests = processedResults.tests
    this.manualTestsSolutions[language]!.autoTests = processedResults.autoTests

    // this.updateCreationTestData(results.results)
    this.updateSubmitAccess()
  }

  get ExerciseSetupError() {
    if (this.languages !== undefined && this.languages.length == 0) {
      return 'at least one programming language should be available'
    }

    if (this.getSingleRowOfManualTests.length < 3) {
      return 'there should be at least 3 manual tests'
    }
    if (this.getSingleRowOfManualTests.length > 10) {
      return 'amount of manual test cannot exceed 10'
    }

    return ''
  }

  public removeTest(index: number) {
    this._languages.forEach((type: CodeRunnerType) => {
      this.manualTestsSolutions[type]!.tests.splice(index, 1)
    })
  }

  public getTestString(isInput: boolean, index: number): string {
    return isInput
      ? JSON.stringify(this.getSingleRowOfManualTests[index].input)
      : JSON.stringify(this.getSingleRowOfManualTests[index].expectedOutput)
  }

  //#endregion

  //#region input output type management

  //#region input

  public setInputTypeInt(): void {
    this.clearTests()
    this.inputType = setTypeToInt(this.inputType)
  }

  public setInputTypeFloat(): void {
    this.clearTests()
    this.inputType = setTypeToFloat(this.inputType)
  }
  public setInputTypeString(): void {
    this.clearTests()
    this.inputType = setTypeToString(this.inputType)
  }
  public setInputTypeSingle(): void {
    this.clearTests()
    this.inputType = setTypeToSingle(this.inputType)
  }

  public setInputTypeArray(): void {
    this.clearTests()
    this.inputType = setTypeToArray(this.inputType)
  }

  public setInputTypeDoubleArray(): void {
    this.clearTests()
    this.inputType = setTypeToDoubleArray(this.inputType)
  }

  //#endregion

  //#region output

  public setOutputTypeInt(): void {
    this.clearTests()
    this.outputType = setTypeToInt(this.outputType)
  }

  public setOutputTypeFloat(): void {
    this.clearTests()
    this.outputType = setTypeToFloat(this.outputType)
  }
  public setOutputTypeString(): void {
    this.clearTests()
    this.outputType = setTypeToString(this.outputType)
  }
  public setOutputTypeSingle(): void {
    this.clearTests()
    this.outputType = setTypeToSingle(this.outputType)
  }

  public setOutputTypeArray(): void {
    this.clearTests()
    this.outputType = setTypeToArray(this.outputType)
  }

  public setOutputTypeDoubleArray(): void {
    this.clearTests()
    this.outputType = setTypeToDoubleArray(this.outputType)
  }

  //#endregion

  //#endregion
}
