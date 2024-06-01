import { requstDefaultVmMachine, sendToExerciseCodeTests, subcribeToVmStatus, subscribeToCodeResults } from '@/config/CodeRunnerConnection';
import CoderunnerState from '@/types/CodeRunnerState';
import CodeRunnerState from '@/types/CodeRunnerState';
import ExerciseParametersType from '@/types/ExerciseParametersType'
import RangeType from '@/types/RangeType';
import VarSize from '@/types/VarSize';
import VarType from '@/types/VarType';
import { computed, ref, Ref } from 'vue';
import type ProgramResult from '@/types/ProgramResults'
import { useActiveUserStore } from '@/stores/ActiveUserStore';
import { useApiConnectionStore } from '@/stores/ApiConnectionStore';
import ExerciseCreatorController from './ExerciseCreatorControlller';
import ExerciseTestToRunMesseage from '@/types/ApiMesseages/ExerciseTestToRunMesseage';

export default class CodeRunnerConnectionControler {
  
    codeRunnerActive: CodeRunnerState={
        codeRunnerType: '',
        state: ''
      }


      disconnetWithCodeRunner = () => {
        console.log('setting code unner to none')
        this.codeRunnerActive = {
          state: '',
          codeRunnerType: ''
        }
      }

      CodeRunnerResultsCallBack = (res: ProgramResult[]) => {
        // isAwaitingCompilation.value = false
    
        console.log('new code runner resutls: ' + JSON.stringify(res))
        // exerciseData.value.tests.forEach((test: ExerciseTest, index: number) => {
        //   test.consoleOutput =
        //     res[index].consoleOutput.output === null ? '' : res[index].consoleOutput.output
        //   test.errorOutput =
        //     res[index].consoleOutput.errorOutput === null ? '' : res[index].consoleOutput.errorOutput
        //   test.output = res[index].variables
        //   test.isSolved = res[index].variables === test.expectedOutput
        // })
      }



        requestCodeRunner(codeRunnerName: string) {
        this.codeRunnerActive.state = 'AWAITING'
        subcribeToVmStatus((state: CoderunnerState)=>{
            console.log("callback: "+ JSON.stringify(state))
            this.codeRunnerActive=state;
            // console.log("  this.codeRunnerActive: "+ JSON.stringify(  this.codeRunnerActive))
            // console.log("doesHaveACtiveToCodeRunner:: "+ doe)
        })
        subscribeToCodeResults(this.CodeRunnerResultsCallBack)
        requstDefaultVmMachine(codeRunnerName)
        }
    
        // console.log("connecting to vm mahicne state callback");
      


        runExercsieTestsCode=(exerciseCreatorController: ExerciseCreatorController)=>{
          console.log("runExercsieTestsCode: "+ JSON.stringify(exerciseCreatorController) )
          const message: ExerciseTestToRunMesseage={
            code: exerciseCreatorController.solutions[this.codeRunnerActive.codeRunnerType],
            manualTests: exerciseCreatorController.manualTestsSolutions[this.codeRunnerActive.codeRunnerType],
            inputType: exerciseCreatorController.inputType,
            inputSize: exerciseCreatorController.inputSize,
            outputType: exerciseCreatorController.outputType,
            outputSize: exerciseCreatorController.outputSize,
            amountOfAutoTests: exerciseCreatorController.amountOfAutoTests,
            autoTestminValue: exerciseCreatorController.autoTestminValue,
            autoTestMaxValue: exerciseCreatorController.autoTestMaxValue,
            lengthRange: exerciseCreatorController.lengthRange,
            xArrayRange: exerciseCreatorController.xArrayRange,
            yArrayRange: exerciseCreatorController.yArrayRange,
            upperCaseInput: exerciseCreatorController.upperCaseInput,
            lowerCaseInput: exerciseCreatorController.lowerCaseInput,
            numberInput: exerciseCreatorController.numberInput,
            specialCharacterInput: exerciseCreatorController.specialCharacterInput,
            breakCharacterInupt: exerciseCreatorController.breakCharacterInupt,
            spaceInupt: exerciseCreatorController.spaceInupt
          }
          sendToExerciseCodeTests(message)
        }

    
}


// const ApiConnectionStore=useApiConnectionStore();

// const VmMachineStatusCallBack= (state: CoderunnerState) =>{
    
//     console.log('new vm machine status: ' + JSON.stringify(state.codeRunnerType))
//     ApiConnectionStore.codeRunnerConnectionControler.codeRunnerActive = state
//     ApiConnectionStore.codeRunnerConnectionControler.codeRunnerActive = 'CPP_RUNNER'
//     ApiConnectionStore.codeRunnerConnectionControler.codeRunnerActive.codeRunnerType=state.codeRunnerType==="UUIANDTIFIED"?"":state.codeRunnerType
//     console.log('codeRunnerActive: ' + JSON.stringify(this.codeRunnerActive))
//   }