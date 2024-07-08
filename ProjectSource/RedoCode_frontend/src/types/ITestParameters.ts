import ExcericseTest from './ExcericseTest'
import RangeType from './RangeType'
import VarType from './VarType'

export default interface ITestParameters {
  manualTests: ExcericseTest[]
  inputType: VarType
  outputType: VarType
  amountOfAutoTests: number
  autoTestminValue: number
  autoTestMaxValue: number
  lengthRange: RangeType
  xArrayRange: RangeType | null
  yArrayRange: RangeType | null
  upperCaseInput: boolean | null
  lowerCaseInput: boolean | null
  numberInput: boolean | null
  specialCharacterInput: boolean | null
  breakCharacterInupt: boolean | null
  spaceInupt: boolean | null
}
