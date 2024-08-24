import ProgramResultsMessage from '@/types/ApiMesseages/ProgramResultsMessage'
import ExerciseTest from '@/types/ExcericseTest'
import ProgramResult, { ConsoleOutput } from '@/types/ProgramResults'
import { computed, ComputedRef, ref, Ref } from 'vue'

class PlayGroundRunnerCotroller {

  


  constructor() {}
  public consoleOutput: ConsoleOutput = {
    exitCode: 0,
    output: '',
    errorOutput: '',
  }

  public executionTime: number=-1;

  public updateResult(results: ProgramResult) {
    console.log('playground results update: ' + JSON.stringify(results))

    this.consoleOutput = results.consoleOutput;
    this.executionTime=results.executionTime;
    // console.log('  this._consoleOutput.value:  ' + JSON.stringify(  this.consoleOutput.value))

    // this.updateCreationTestData(results.results)
    // this.updateSubmitAcces()
  }
}

export default PlayGroundRunnerCotroller
