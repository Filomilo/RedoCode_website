import ExerciseDataMessage from '@/types/ApiMessages/ExerciseDataMessage'
import CodeRunnerControllerBase from './CodeRunnerControllerBase'
import ExerciseTest from '@/types/ExerciseTest'
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

  public loadInitialData(id: number, data: ExerciseDataMessage) {
    this.reset()
    console.log(
      'Loading initial ExerciseSolverController ' + JSON.stringify(data)
    )
    this.title = data.title
    this.desc = data.desc
    this.manualTests = data.tests
    this._languages = data.availableCodeRunners
    this.inputType = data.inputType as VarType
    this.outputType = data.outputType as VarType
    this.id = id
    console.log(
      'this._languages ' + JSON.stringify(data.availbleCodeRunners)
    )
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
    const processedResults = this.processCodeResultLoad(results, {
      tests: this.manualTests,
      autoTests: this.autoTests,
    })
    this.manualTests = processedResults.tests
    this.autoTests = processedResults.autoTests
    this.updateSubmitAccess()
  }
  public updateSubmitAccess() {
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
