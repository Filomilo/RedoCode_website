import ExercsieCreatorValidationMesage from '@/types/ApiMesseages/ExercsieCreatorValidationMesage';
import ExerciseTest from '@/types/ExcericseTest';
import ExerciseData from '@/types/ExerciseData';
import ExerciseParametersType from '@/types/ExerciseParametersType'
import IExerciseDescriptionI from '@/types/IExerciseDescriptionI';
import ITestParameters from '@/types/ITestParameters';
import RangeType from '@/types/RangeType';
import VarSize from '@/types/VarSize';
import VarType from '@/types/VarType';
import { reactive } from 'vue';
interface StringIndexed {
    [index: string]: string;
  }
  interface TestsIndexed {
    [index: string]: ExerciseTest[];
  }
export default class ExerciseCreatorController implements  IExerciseDescriptionI,ExercsieCreatorValidationMesage{
    title!: string;
    desc!: string;
    languages!: string[];
    ram!: number;
    timeForTaskM!: number;
    timeForTaskH!: number;
    executionTimeMs!: number;
    inputType!: VarType;
    inputSize!: VarSize;
    outputType!: VarType;
    outputSize!: VarSize;
    amountOfAutoTests!: number;
    autoTestminValue!: number;
    autoTestMaxValue!: number;
    lengthRange!: RangeType;
    xArrayRange!: RangeType;
    yArrayRange!: RangeType;
    upperCaseInput!: boolean;
    lowerCaseInput!: boolean;
    numberInput!: boolean;
    specialCharacterInput!: boolean;
    breakCharacterInupt!: boolean;
    spaceInupt!: boolean;
    solutions!:StringIndexed;
    manualTestsSolutions!: TestsIndexed;
    autoTests!: TestsIndexed;



    manualTestBuffer:any= reactive([]);

    addblankTest = (
      inputType: VarType,
      outputype: VarType,
      inputSize: VarSize,
      outputSize: VarSize
    ) => {
      const input = this.getVarAcording(inputType, inputSize)
      const output = this.getVarAcording(outputype, outputSize)
      console.log('ading ' + inputType + ' _ ' + inputSize + ' :: ' + JSON.stringify(input))
      this.manualTestBuffer.push({
        input: input,
        expectedOutput: output,
        output: null,
        errorOutput: '',
        consoleOutput: '',
        isSolved: null
      }
     )
     console.log("added: "+this.manualTestBuffer )
    }
    clearTests = () => {
      this.manualTestBuffer = []
    }
    getVarAcording: any = (type: VarType, size: VarSize) => {
      if (type === 'string') {
        switch (size) {
          case 'single_value':
            return ''
          case 'array':
            return ['']
          case '2d_array':
            return [['']]
        }
      } else {
        switch (size) {
          case 'single_value':
            return 0
          case 'array':
            return [0]
          case '2d_array':
            return [[0]]
        }
      }
      return 0
    }
  

    resetParams(this: any):void{
        this.ram= 128,
        this.timeForTaskM= 10,
        this.timeForTaskH= 0,
        this.executionTimeMs= 100,
        this.inputType= ('int' as VarType),
        this.inputSize= ('single_value' as VarSize),
        this.outputType= ('int' as VarType),
        this.outputSize= ('single_value' as VarSize),
        this.amountOfAutoTests= 1,
        this.autoTestminValue= -1,
        this.autoTestMaxValue= 1,
        this.upperCaseInput= true,
        this.lowerCaseInput= true,
        this.numberInput= true,
        this.specialCharacterInput= true,
        this.breakCharacterInupt= true,
        this.languages= [],
        this.xArrayRange= { min: 1, max: 10 },
        this.yArrayRange= { min: 1, max: 10 },
        this.title= '',
        this.desc= '',
        this.lengthRange= { min: 1, max: 10 },
        this.spaceInupt= false
        this.solutions={};
        this.manualTestsSolutions={}
}

constructor(){
    this.resetParams();
}


}