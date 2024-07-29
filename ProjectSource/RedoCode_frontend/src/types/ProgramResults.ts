interface ConsoleOutput {
  exitCode: number
  output: string
  errorOutput: string
}
interface Vars {
  value: number[] | number | string | string[] | null
}

export default interface ProgramResult {
  consoleOutput: ConsoleOutput
  variables: Vars
}
