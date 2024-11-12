import ProgramResult, { ConsoleOutput } from '@/types/ProgramResults'
import { Ref, ref } from 'vue'

class PlayGroundRunnerController {
  constructor() {}
  public consoleOutput: Ref<ConsoleOutput> = ref({
    exitCode: 0,
    output: '',
    errorOutput: '',
  })

  public executionTime: number = -1

  public updateResult(results: ProgramResult) {
    console.log('playground results update: ' + JSON.stringify(results))

    this.consoleOutput.value = results.consoleOutput
    this.executionTime = results.executionTime
  }
  public clear() {
    this.executionTime = 0
    this.consoleOutput.value = {
      exitCode: 0,
      output: '',
      errorOutput: '',
    }
    // console.log('Playground CLEAR: ' + JSON.stringify(this.consoleOutput))
  }
}

export default PlayGroundRunnerController
