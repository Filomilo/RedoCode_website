//#region imports
import ExerciseTest from '@/types/ExcericseTest'
import VarType, {
  isTypeArray,
  isTypeDoubleArray,
  isTypeSingle,
  isTypeString,
} from '@/types/VarType'
import { randomUUID } from 'crypto'
//#endregion

export default class GroupOfTestsController {
  tests: ExerciseTest[] = []
  autoTests: ExerciseTest[] = []

  private getVarAcording(type: VarType): any {
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

  public addblankTest(inputType: VarType, outputype: VarType): void {
    console.log(
      'outpout type ' + outputype + ' _ ' + ' :: ' + JSON.stringify(outputype)
    )

    const input = this.getVarAcording(inputType)
    const output = this.getVarAcording(outputype)
    console.log('ading ' + outputype + ' _ ' + ' :: ' + JSON.stringify(output))
    this.tests.push({
      input: input,
      expectedOutput: output,
      output: null,
      errorOutput: '',
      consoleOutput: '',
      isSolved: null,
      uuid: crypto.randomUUID(),
    })
    console.log('added: ' + JSON.stringify(this.tests))
  }
}
