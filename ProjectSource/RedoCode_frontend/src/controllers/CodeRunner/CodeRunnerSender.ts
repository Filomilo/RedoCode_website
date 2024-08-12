import CodeRunnerRequestMessage from "@/types/CodeRunnerRequestMessage";
import StompApiSender from "../Stomp/StompApiSender";
import CodeRunnerType from "@/types/CodeRunnerTypes";
import { useCodeRunnerStore } from "@/stores/CodeRunnerStore";
import CodeRunnerConnection from "./CodeRunnerConnection";
import RawCodeToRunMessage from "@/types/ApiMesseages/RawCodeToRunMessage";
import ExerciseCreatorController from "./ExerciseCreatorControlller";
import ExerciseTestToRunMesseage from "@/types/ApiMesseages/ExerciseTestToRunMesseage";
import ExercsieCreatorValidationMesage from "@/types/ApiMesseages/ExercsieCreatorValidationMesage";
import MessageCreator from "@/tools/MessageCreator";

class CodeRunnerSender{
  
    private _stompApiSender: StompApiSender;
    private _CodeRnnerCOnnection: CodeRunnerConnection;
    constructor (stompApiSender: StompApiSender, codeRunnerConnection: CodeRunnerConnection)
    {
        this._stompApiSender=stompApiSender;
        this._CodeRnnerCOnnection=codeRunnerConnection;
    }

    

  public readonly requestCodeRunner = (codeRunnerName: CodeRunnerType) => {
    this._CodeRnnerCOnnection.setAwaiting();
    console.log('codeRunnerName: ' + JSON.stringify(codeRunnerName))
    const request: CodeRunnerRequestMessage = {
      CodeRunnerType: codeRunnerName,
    }
    console.log('requestCodeRunner: ' + JSON.stringify(request))
    this._stompApiSender.codeRunnerRequest(request);

  }
  runRawCode = (code: string) => {
    const message: RawCodeToRunMessage = {
      code: code
    }
    console.log('runRawCode: ' + JSON.stringify(message))
    this._stompApiSender.runRawCode(message)
  }


  runSingleExerciseCreationTest= (exerciseCreatorController: ExerciseCreatorController, type: CodeRunnerType) => {
    const exerciseTestToRunMesseage: ExerciseTestToRunMesseage=MessageCreator.createExerciseTestToRunMesseage(exerciseCreatorController,type);
    console.log('runSignleExerciseCreationTest: ' + JSON.stringify(exerciseTestToRunMesseage))
    this._stompApiSender.runExerciseTestsCode(exerciseTestToRunMesseage)
  }

  runExerciseCreationValistaion= (exerciseCreatorController: ExerciseCreatorController) => {

    console.log('runSignleExerciseCreationTest: ' + JSON.stringify(exerciseCreatorController))

    const exercsieCreatorValidationMesage: ExercsieCreatorValidationMesage={
      title: exerciseCreatorController.title,
      description: exerciseCreatorController.description,
      ram: exerciseCreatorController.ram,
      inputType: exerciseCreatorController.inputType,
      outputType: exerciseCreatorController.outputType,
      amountOfAutoTests: exerciseCreatorController.amountOfAutoTests,
      autoTestminValue: exerciseCreatorController.autoTestminValue,
      autoTestMaxValue: exerciseCreatorController.autoTestMaxValue,
      lengthRange: exerciseCreatorController.lengthRange,
      xArrayRange: exerciseCreatorController.xArrayRange,
      yArrayRange: exerciseCreatorController.yArrayRange,
      solutionCodes: exerciseCreatorController.solutionCodes,
      timeForTaskMin: exerciseCreatorController.timeForTaskMin,
      timeForExecutionMs: exerciseCreatorController.timeForExecutionMs,
      manualTests: exerciseCreatorController.getSingleRowOfManualTests,
      upperCaseInput: exerciseCreatorController.upperCaseInput,
      lowerCaseInput: exerciseCreatorController.lowerCaseInput,
      numberInput: exerciseCreatorController.numberInput,
      specialCharacterInput: exerciseCreatorController.specialCharacterInput,
      breakCharacterInupt: exerciseCreatorController.breakCharacterInupt,
      spaceInupt: exerciseCreatorController.spaceInupt,
      executionTime: exerciseCreatorController.executionTime
    }


    this._stompApiSender.runExerciseCreatorValidationCode(exercsieCreatorValidationMesage)
  }

}

export default CodeRunnerSender;

    // code: exerciseCreatorController.solutionCodes[type]!,
      // manualTests: exerciseCreatorController.getSingleRowOfManualTests,
      // inputType: exerciseCreatorController.inputType,
      // outputType: exerciseCreatorController.outputType,
      // amountOfAutoTests: exerciseCreatorController.amountOfAutoTests,
      // autoTestminValue: exerciseCreatorController.autoTestminValue,
      // autoTestMaxValue: exerciseCreatorController.autoTestMaxValue,
      // lengthRange: exerciseCreatorController.lengthRange,
      // xArrayRange: exerciseCreatorController.xArrayRange,
      // yArrayRange: exerciseCreatorController.yArrayRange,
