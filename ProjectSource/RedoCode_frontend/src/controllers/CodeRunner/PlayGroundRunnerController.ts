import ProgramResult, { ConsoleOutput } from '@/types/ProgramResults'

class PlayGroundRunnerController {
  constructor() {}
  public consoleOutput: ConsoleOutput = {
    exitCode: 0,
    output: '',
    errorOutput: '',
  }

  public executionTime: number = -1

  public updateResult(results: ProgramResult) {
    console.log('playground results update: ' + JSON.stringify(results))

    this.consoleOutput = results.consoleOutput
    this.executionTime = results.executionTime
  }
}

export default PlayGroundRunnerController
