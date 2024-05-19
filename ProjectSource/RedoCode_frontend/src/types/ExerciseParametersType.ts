import RangeType from "./RangeType"
import VarSize from "./VarSize"
import VarType from "./VarType"

export default interface ExerciseParametersType {
    languages: string[]
    ram: number
    timeForTaskM: number
    timeForTaskH: number
    executionTimeMs: number
    inputType: VarType
    inputSize: VarSize
    outputType: VarType
    outputSize: VarSize
    amountOfAutoTests: number
    autoTestminValue: number
    autoTestMaxValue: number
    xArrayRange: RangeType
    yArrayRange: RangeType
    upperCaseInput: boolean
    lowerCaseInput: boolean 
    numberInput: boolean 
    specialCharacterInput: boolean
    breakCharacterInupt: boolean 
}
  