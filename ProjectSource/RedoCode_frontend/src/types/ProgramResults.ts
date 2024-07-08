interface ConsoleOutput {
  exitCode: number
  output: string
  errorOutput: string
}
export default interface ProgramResult {
  consoleOutput: ConsoleOutput
  variables: number[] | number | string | string[] | null
}
