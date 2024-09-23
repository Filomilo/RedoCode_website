import CodeRunnerType from '../CodeRunnerTypes'
import type ExerciseTest from '../ExerciseTest'
import IExerciseDescriptionI from '../IExerciseDescriptionI'
import VarType from '../VarType'
export default interface ExerciseDataMessage extends IExerciseDescriptionI {
  availableCodeRunners: CodeRunnerType[]
  id: number | null
  desc: string
  outputType: VarType
  inputType: VarType
  tests: ExerciseTest[]
  automaticTests: ExerciseTest[]
  startingFunction: string
}
