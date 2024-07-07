import ChainNodeStatus from './ChainNodeStatus'
import ExecutionResponseBase from './ExecutionResponseBase'

export default interface ExecutionChainScheme extends ExecutionResponseBase {
  levels: ChainNodeStatus[]
}
