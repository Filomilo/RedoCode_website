export default interface ExerciseTest {
  input: string | string[] | number | number[]
  output: string | string[] | number | number[]
  errorOutput: string
  consoleOutput: string
  isSolved: boolean | null
}
