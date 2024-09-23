import CommentType from './CommentType'
import SolutionItemList from './SolutionItemList'

export default interface SolutionsData {
  title: string
  desc: string
  maxExecutionTimeMs: number
  solutionList: SolutionItemList[]
  comments: CommentType[]
}
