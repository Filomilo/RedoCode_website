import type ProgramResults from './ProgramResults'

export default interface CodeRunnerResultsCallBack {
  (results: ProgramResults[]): void
}
