import ExerciseTest from "../ExcericseTest";
import RangeType from "../RangeType";
import VarSize from "../VarSize";
import VarType from "../VarType";
import ITestParameters from '@/types/ITestParameters';

interface StringIndexed {
    [index: string]: string;
}
interface testsIndexed {
    [index: string]: ExerciseTest[];
}

export default interface ExercsieCreatorValidationMesage extends ITestParameters {
    title: string
    desc: string
    languages: string[] | {label: string, value: string}[]
    ram: number;
    executionTimeMs: number;
    inputType: VarType;
    inputSize: VarSize;
    outputType: VarType;
    outputSize: VarSize;
    amountOfAutoTests: number;
    autoTestminValue: number;
    autoTestMaxValue: number;
    lengthRange: RangeType;
    xArrayRange: RangeType;
    yArrayRange: RangeType;
    upperCaseInput: boolean;
    lowerCaseInput: boolean;
    numberInput: boolean;
    specialCharacterInput: boolean;
    breakCharacterInupt: boolean;
    spaceInupt: boolean;
    solutions:StringIndexed;
    timeForTaskM: number;
    timeForTaskH: number;
}







