import CodeRunnerType from './CodeRunnerTypes'
import type ExerciseTest from './ExcericseTest'
import IExerciseDescriptionI from './IExerciseDescriptionI'
export default interface ExerciseData extends IExerciseDescriptionI {
  availbleCodeRunners: CodeRunnerType[]
  id: number | null
  description: string
  outputType: string
  inputType: string
  tests: ExerciseTest[]
  automaticTests: ExerciseTest[]
  startingFunction: string
}
