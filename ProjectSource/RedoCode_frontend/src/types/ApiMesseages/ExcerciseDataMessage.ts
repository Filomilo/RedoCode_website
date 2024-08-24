import CodeRunnerType from '../CodeRunnerTypes'
import type ExerciseTest from '../ExcericseTest'
import IExerciseDescriptionI from '../IExerciseDescriptionI'
import VarType from '../VarType'
export default interface ExcerciseDataMessage extends IExerciseDescriptionI {
  availbleCodeRunners: CodeRunnerType[]
  id: number | null
  desc: string
  outputType: VarType
  inputType: VarType
  tests: ExerciseTest[]
  automaticTests: ExerciseTest[]
  startingFunction: string
}
