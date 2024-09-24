//#region imports
import ExerciseTest from '@/types/ExerciseTest'
import VarType, {
  isTypeArray,
  isTypeDoubleArray,
  isTypeSingle,
  isTypeString,
} from '@/types/VarType'
//#endregion

export default class GroupOfTestsController {
  tests: ExerciseTest[] = []
  autoTests: ExerciseTest[] = []

  private getVarAccording(type: VarType): any {
    if (isTypeString(type)) {
      if (isTypeSingle(type)) return ''
      if (isTypeArray(type)) return ['']
      if (isTypeDoubleArray(type)) return [['']]
    } else {
      if (isTypeSingle(type)) return 0
      if (isTypeArray(type)) return [0]

      if (isTypeDoubleArray(type)) return [[0]]
    }
  }

  public addBlankTest(inputType: VarType, outputType: VarType): void {
    console.log(
      'output type ' + outputType + ' _ ' + ' :: ' + JSON.stringify(outputType)
    )

    const input = this.getVarAccording(inputType)
    const output = this.getVarAccording(outputType)
    console.log(
      'adding ' + outputType + ' _ ' + ' :: ' + JSON.stringify(output)
    )
    this.tests.push({
      input: input,
      expectedOutput: output,
      output: null,
      errorOutput: '',
      consoleOutput: '',
      isSolved: null,
      uuid: crypto.randomUUID(),
      executionTime: -1,
    })
    console.log('added: ' + JSON.stringify(this.tests))
  }
}
