import ExcerciseDataMessage from '@/types/ApiMesseages/ExcerciseDataMessage'
import CodeRunnerControllerBase from './CodeRunnerControllerBase'
import ExerciseTest from '@/types/ExcericseTest'
import VarType from '@/types/VarType'
import generateStartingFunction from '@/tools/StartingFunctionGenerator'
import CodeRunnerType from '@/types/CodeRunnerTypes'
import ProgramResult from '@/types/ProgramResults'
import IExerciseDescriptionI from '@/types/IExerciseDescriptionI'

export default class ExerciseSolverController
  extends CodeRunnerControllerBase
  implements IExerciseDescriptionI
{
  public id: number = 0
  public title!: string
  public desc!: string
  public solution!: string
  public manualTests!: ExerciseTest[]
  public autoTests!: ExerciseTest[]

  public inputType!: VarType
  public outputType!: VarType
  public isSolved: boolean = false
  constructor() {
    super()
  }

  public loadInitialData(id: number, data: ExcerciseDataMessage) {
    this.reset()
    console.log(
      'Loadingi inital ExerciseSolverController ' + JSON.stringify(data)
    )
    this.title = data.title
    this.desc = data.desc
    this.manualTests = data.tests
    this._languages = data.availbleCodeRunners
    this.inputType = data.inputType as VarType
    this.outputType = data.outputType as VarType
    this.id = id
    2
  }

  public startFunction(type: CodeRunnerType): string {
    if (this.solution == '')
      this.solution = generateStartingFunction(
        type,
        this.inputType,
        this.outputType
      )
    return generateStartingFunction(type, this.inputType, this.outputType)
  }

  reset(): void {
    ;(this.title = ''),
      (this.desc = ''),
      (this.solution = ''),
      (this.manualTests = []),
      (this.autoTests = []),
      (this.isSolved = false)
  }

  updateTests(results: ProgramResult[]) {
    console.log(
      'ExerciseSolverController update ts: ' + JSON.stringify(results)
    )
    const procesedResults = this.processCodeResultLoad(results, {
      tests: this.manualTests,
      autoTests: this.autoTests,
    })
    this.manualTests = procesedResults.tests
    this.autoTests = procesedResults.autoTests
    this.updateSubmitAcces()
  }
  public updateSubmitAcces() {
    this.isSolved = this.validateAllTests()
  }

  private validateAllTests(): boolean {
    if (Object.values(this.manualTests).length == 0) return false
    console.log('---isSolved values: ' + JSON.stringify(this.manualTests))

    for (const test of this.manualTests) {
      console.log('test: ' + JSON.stringify(test))
      if (test.isSolved !== true) {
        console.log('false')
        return false
      }
    }
    for (const test of this.autoTests) {
      console.log('test: ' + JSON.stringify(test))
      if (test.isSolved !== true) {
        console.log('false')
        return false
      }
    }

    console.log('TRUe')
    return true
  }
}
