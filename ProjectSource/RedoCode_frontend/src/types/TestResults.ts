import ProgramResult, { Vars } from './ProgramResults'
import VarType from './VarType'

export default interface TestResults extends ProgramResult {
  expectedRes: Vars
}
