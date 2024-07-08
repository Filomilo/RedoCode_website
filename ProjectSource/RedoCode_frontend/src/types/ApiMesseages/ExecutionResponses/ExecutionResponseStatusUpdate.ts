import ExecutionResponseBase from './ExecutionResponseBase'

export default interface ExecutionResponseStatusUpdate extends ExecutionResponseBase {
  message: String
  lvlStatus: 'PENDING' | 'RUNNING' | 'SUCCESS' | 'FAILED'
  stepUpdate: number
}
