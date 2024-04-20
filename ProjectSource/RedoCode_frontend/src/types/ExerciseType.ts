export default interface ExerciseType {
  id: number
  name: string
  languages: { name: string }[]
  difficulty: string
  popularity: number
  description: string
}
