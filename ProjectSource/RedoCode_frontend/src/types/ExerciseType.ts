export default interface ExerciseType {
  id: number
  name: string
  languages: { name: string }[]
  difficulty: number
  popularity: number
  description: string
  alreadyDone: boolean
}
