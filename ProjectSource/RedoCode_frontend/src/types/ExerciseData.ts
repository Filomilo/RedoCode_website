import type ExerciseTest from './ExcericseTest'
export default interface ExerciseData {
  availbleCodeRunners: string[]
  title: string
  desc: string
  outputType: string
  inputType: string
  tests: ExerciseTest[]
  automaticTests: ExerciseTest[]
  startingFunction: string
}
