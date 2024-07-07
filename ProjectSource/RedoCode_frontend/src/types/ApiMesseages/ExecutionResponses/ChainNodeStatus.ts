export default interface ChainNodeStatus {
  nodeName: String
  processingMessage: String
  status: 'PENDING' | 'RUNNING' | 'SUCCESS' | 'FAILED'
}
