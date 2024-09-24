export default interface ExerciseTest {
  input: string | string[] | number | number[]
  output: string | string[] | number | number[] | null
  expectedOutput: string | string[] | number | number[] | null
  errorOutput: string
  consoleOutput: string
  isSolved: boolean | null
  executionTime: number
  uuid: string // Required for correct list showcasing in vue, it doesn't take part in any other process and should not be used
}
