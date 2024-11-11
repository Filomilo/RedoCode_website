import CodeRunnerType from "./CodeRunnerTypes"

export default interface CodeExerciseTab {
  title: string
  description: string
  id: number
  languages: CodeRunnerType[]
}
