import CodeRunnerType from '@/types/CodeRunnerTypes'

export default interface SolutionItemList {
  username: string
  date: Date
  executionTimeMs: number
  profilePic: string
  solutionId: number
  codeRunner: CodeRunnerType
}
