import ExercsieCreatorValidationMesage from '@/types/ApiMesseages/ExercsieCreatorValidationMesage'
import ExerciseTest from '@/types/ExcericseTest'
import ExerciseData from '@/types/ExerciseData'
import ExerciseParametersType from '@/types/ExerciseParametersType'
import IExerciseDescriptionI from '@/types/IExerciseDescriptionI'
import ITestParameters from '@/types/ITestParameters'
import RangeType from '@/types/RangeType'
import CodeRunnerType from '@/types/CodeRunnerTypes'
import TestsController from "@/controllers/CodeRunner/GroupOfTestsController"
import { computed, ComputedRef, reactive, toHandlerKey } from 'vue'
import VarType, {
  setTypeToArray,
  isTypeDoubleArray,
  isTypeString,
  isTypeArray,
  isTypeInt,
  isTypeFloat,
  setTypeToDoubleArray,
  setTypeToSingle,
  setTypeToString,
  setTypeToInt,
  setTypeToFloat,
  isTypeSingle,
} from '@/types/VarType'
import ProgramResultsMessage from '@/types/ApiMesseages/ProgramResultsMessage'
import ProgramResult from '@/types/ProgramResults'

type StringIndexed = {
  [key in CodeRunnerType]?: string
}
type TestsIndexed = { [key in CodeRunnerType]?: TestsController }

export default class ExerciseCreatorController 
{
  //#region data

  private _languages!: CodeRunnerType[]

  get languages(): CodeRunnerType[]{
    return this._languages;
  }

  set languages(newLanguages: CodeRunnerType[]) {
    this._languages = newLanguages;
    this.updateTestsFields();
  } 

  title!: string
  description!: string



  ram!: number
  timeForTaskMin!: number
  timeForExecutionMs!: number
  inputType!: VarType
  outputType!: VarType
  amountOfAutoTests!: number
  autoTestminValue!: number
  autoTestMaxValue!: number
  lengthRange!: RangeType
  xArrayRange!: RangeType
  yArrayRange!: RangeType
  upperCaseInput!: boolean
  lowerCaseInput!: boolean
  numberInput!: boolean
  specialCharacterInput!: boolean
  breakCharacterInupt!: boolean
  spaceInupt!: boolean
  solutionCodes!: StringIndexed
  manualTestsSolutions!: TestsIndexed
  executionTime!: number
  isSolved!: boolean

  //#endregion

//#region 

  resetParams(this: any): void {
    ;(this.ram = 128),
      (this.timeForTaskMin = 15),
      (this.timeForExecutionMs = 100),
      (this.inputType = 'SINGLE_INTEGER' as VarType),
      (this.outputType = 'SINGLE_INTEGER' as VarType),
      (this.amountOfAutoTests = 1),
      (this.autoTestminValue = -1),
      (this.autoTestMaxValue = 1),
      (this.upperCaseInput = true),
      (this.lowerCaseInput = true),
      (this.numberInput = true),
      (this.specialCharacterInput = true),
      (this.breakCharacterInupt = true),
      (this.languages = []),
      (this.xArrayRange = { min: 1, max: 10 }),
      (this.yArrayRange = { min: 1, max: 10 }),
      (this.title = 'TESTESTTEST'),
      (this.description = 'DEscritptionDescription'),
      (this.lengthRange = { min: 1, max: 10 }),
      (this.spaceInupt = false)
    ; (this.solutionCodes = {}),
      (this.manualTestsSolutions = {} as TestsIndexed),
      (this.executionTime = 100)
  }

  constructor() {
    this.resetParams()
  }

  public updateSubmitAcces() {
    this.isSolved = this.validateAllTests()
  }

  private validateAllTests():boolean {
    // if (Object.values(this.manualTestsSolutions).length == 0) return false
    // console.log(
    //   '---isSolved values: ' + JSON.stringify(this.manualTestsSolutions)
    // )
    // console.log(
    //   '---isSolved values amont: : ' +
    //     Object.values(this.manualTestsSolutions).length +
    //     ' : ' +
    //     JSON.stringify(Object.values(this.manualTestsSolutions))
    // )

    // for (const tests of Object.values(this.manualTestsSolutions)) {
    //   for (const test of tests) {
    //     console.log('test: ' + JSON.stringify(test))
    //     if (test.isSolved !== true) {
    //       console.log('false')
    //       return false // Exit the outer function early
    //     }
    //   }
    // }

    console.log('TRUe')
    return true;
  }

  //#region 

  private getAmountOfLanguages(): number{
    return this.languages.length;
  }

  private getSingleCodeRunnerKey():CodeRunnerType{
    return this.languages[0];
  }


  //#endregion


//#region code mangament

public updateSolutionCode(code: string, type:CodeRunnerType) {
  this.solutionCodes[type]=code;
}



//#endregion


  //#region Test managment


  private updateTestsFields()
  {

    this.manualTestsSolutions={};
    this.languages.forEach((x:CodeRunnerType)=>{
      this.manualTestsSolutions[x]=new TestsController();
    })
    console.log("Update test fields: "+ JSON.stringify(this.manualTestsSolutions))
  }

  public clearTests(): void
  {

  }

  public addEmptyTest()
  {
    if(this.getAmountOfLanguages()==0)
      throw "First you need select languages"
    
  if (this.manualTestsSolutions[this.getSingleCodeRunnerKey()]!.tests.length >= 10) {
      throw 'only 10 manual test are allowed';
    }
    console.log("output type: "+ this.outputType)
    Object.keys(this.manualTestsSolutions).forEach((x:string)=>{
      const type:CodeRunnerType=x as CodeRunnerType;
      this.manualTestsSolutions[type]?.addblankTest(
        this.inputType
        ,this.outputType
      );
    })
    
  }

  get getSingleRowOfManualTests(): ExerciseTest[]{
    if(this.getAmountOfLanguages()==0)
      return [];
      
      return this.manualTestsSolutions[this.getSingleCodeRunnerKey()]!.tests;
  }

  set getSingleRowOfManualTests(tests: ExerciseTest[]) {
    if (this.getAmountOfLanguages() === 0 || !this.manualTestsSolutions[this.getSingleCodeRunnerKey()]) {
      throw "Cannot set varailbes input"
    }

    this._languages.forEach((x:CodeRunnerType)=>{
      this.manualTestsSolutions[x]!.tests=tests
    })
  }



  public setTestValue(isInput:boolean, index:number, value: any)
  {

        this.languages.forEach((x: CodeRunnerType)=>{

          if (isInput) {
          this.manualTestsSolutions[x]!.tests[index].input=value;
          }
          else{
            console.log("set output: "+ value)
            this.manualTestsSolutions[x]!.tests[index].expectedOutput=value;
          }
        }
      );
  }


  // private updateCreationTestData(reuslts: ProgramResult[])  {
  //   console.log(
  //     '----updateTestData: ' +
  //       JSON.stringify(
  //         this.manualTestsSolutions[
  //           apiConnectionStore.codeRunnerConnection.codeRunnerState
  //             .codeRunnerType
  //         ]
  //       )
  //   )

  public updateTests (results: ProgramResultsMessage,langauge:CodeRunnerType) {
    console.log('create test rtesult update: ' + JSON.stringify(results))
    // this.updateCreationTestData(results.results)
    this.updateSubmitAcces()
  }

  get ExerciseSetupError() {
    if (this.languages.length == 0) {
      return 'at least one programing lnaguage should be available'
    }
    if (this.timeForTaskMin < 15) {
      return 'at least 15 minute should be for task'
    }
    if (this.getSingleRowOfManualTests.length < 3) {
      return 'tthere should be at least 3 manuall tests'
    }
    if (this.getSingleRowOfManualTests.length > 10) {
      return 'amount of manula test cannot exceed 10'
    }

    return ''
  }


  public removeTest(index:number) {
    this._languages.forEach((type: CodeRunnerType)=>{
      this.manualTestsSolutions[type]!.tests.splice(index,1);
    })
  }

    public getTestString(isInput: boolean, index: number): string{
      return isInput
      ?JSON.stringify( this.getSingleRowOfManualTests[index].input)
      :JSON.stringify( this.getSingleRowOfManualTests[index].expectedOutput)
     
    }

    

  //#endregion

//#region Time management
public updateAmountOfMInutes(newNum: number):void
{
  this.timeForTaskMin =
  newNum * 60 +
  (this.timeForTaskMin -
    Math.floor(
      this.timeForTaskMin / 60
    ) *
      60)
}



public getMinutesBound():number{
 return Math.floor(
    this.timeForTaskMin / 60
  )
}


public updateAmountOfHours(newNum: number):void
{
  this.timeForTaskMin =
  Math.floor(
    this.timeForTaskMin /
      60
  ) *
    60 +
  +newNum
}

public getHoursBound():number{
  return this.timeForTaskMin -
  Math.floor(
    this.timeForTaskMin / 60
  ) *
    60
}

//#endregion



//#region input output type management 

//#region input

public setInputTypeInt(): void
{
  this.clearTests();
  this.inputType=setTypeToInt( this.inputType);
}

public setInputTypeFloat(): void
{
  this.clearTests();
  this.inputType=setTypeToFloat( this.inputType);
}
public setInputTypeString(): void
{
  this.clearTests();
  this.inputType=setTypeToString( this.inputType);
}
public setInputTypeSingle(): void
{
  this.clearTests();
  this.inputType=setTypeToSingle( this.inputType);
}

public setInputTypeArray(): void
{
  this.clearTests();
  this.inputType=setTypeToArray( this.inputType);
}

public setInputTypeDoubleArray(): void
{
  this.clearTests();
  this.inputType=setTypeToDoubleArray( this.inputType);
}


//#endregion

//#region ouptut

public setOutputTypeInt(): void
{
  this.clearTests();
  this.outputType=setTypeToInt( this.outputType);
}

public setOutputTypeFloat(): void
{
  this.clearTests();
  this.outputType=setTypeToFloat( this.outputType);
}
public setOutputTypeString(): void
{
  this.clearTests();
  this.outputType=setTypeToString( this.outputType);
}
public setOutputTypeSingle(): void
{
  this.clearTests();
  this.outputType=setTypeToSingle( this.outputType);
}

public setOutputTypeArray(): void
{
  this.clearTests();
  this.outputType=setTypeToArray( this.outputType);
}

public setOutputTypeDoubleArray(): void
{
  this.clearTests();
  this.outputType=setTypeToDoubleArray( this.outputType);
}


//#endregion

//#endregion


 

}
