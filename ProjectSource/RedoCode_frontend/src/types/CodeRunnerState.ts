import CodeRunnerType from './CodeRunnerTypes'
import CodeRunnerStatus from './CodeRunnerStatus'
export default interface CoderunnerState {
  codeRunnerType: CodeRunnerType 
  state: CodeRunnerStatus
}

