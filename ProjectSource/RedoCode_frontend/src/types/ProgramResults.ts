interface ConsoleOutput {
  exitCode: number
  output: string
  errorOutput: string
}
export default interface ExerciseTest {
  consoleOutput: ConsoleOutput
  variables: number[] | number | string | string[]
}
