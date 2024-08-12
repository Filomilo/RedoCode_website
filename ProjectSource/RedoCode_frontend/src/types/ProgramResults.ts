export interface ConsoleOutput {
  exitCode: number
  output: string
  errorOutput: string
}
type Vars = number[] | number | string | string[] | null

export default interface ProgramResult {
  consoleOutput: ConsoleOutput
  variables: Vars
  variablesInput: Vars|null ;
}
