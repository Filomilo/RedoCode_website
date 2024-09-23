import ExerciseTest from '@/types/ExerciseTest'

export default class SingleTestController implements ExerciseTest {
  input: string | number | string[] | number[] = []
  output: string | number | string[] | number[] | null = []
  expectedOutput: string | number | string[] | number[] = []
  errorOutput: string = ''
  consoleOutput: string = ''
  isSolved: boolean | null = false
  uuid: string = '' //compatibility with gui
  executionTime: number = -1
}
