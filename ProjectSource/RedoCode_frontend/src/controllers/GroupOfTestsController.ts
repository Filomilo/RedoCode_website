//#region imports
import ExerciseTest from "@/types/ExcericseTest";
import VarType, { isTypeArray, isTypeDoubleArray, isTypeSingle,isTypeString } from "@/types/VarType";
//#endregion

export default class GroupOfTestsController{
    tests: ExerciseTest[] = [];
    autoTests: ExerciseTest[] = [];
    

    private getVarAcording(type: VarType):any{
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

    public addblankTest(inputType: VarType, outputype: VarType):void {
        const input = this.getVarAcording(inputType)
        const output = this.getVarAcording(outputype)
        console.log('ading ' + inputType + ' _ ' + ' :: ' + JSON.stringify(input))
        this.tests.push({
          input: input,
          expectedOutput: output,
          output: null,
          errorOutput: '',
          consoleOutput: '',
          isSolved: null,
        })
        console.log('added: ' + JSON.stringify(this.tests))
      }

}