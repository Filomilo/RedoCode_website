import { defineStore } from 'pinia'
import {ref,computed, type Ref} from 'vue'
import type CodeRunnerState from '../types/CodeRunnerState'
import ExerciseData from '@/types/ExerciseData';
import axios from 'axios';
import '../interceptors/axios'
import {languageChoices} from '../config/Data'
export const useCodeRunnerStore = defineStore('codeRunnerStore',()=>{
    const codeRunnerActive: Ref<CodeRunnerState>=ref({
        codeRunnerType: 'cpp',
        state: 'active'
    })
    const doesHaveACtiveToCodeRunner=computed(()=> {
        return codeRunnerActive.value.state==='active';
    })
    const playGroundBase: ExerciseData={
        inputType: "playground",
        outputType: "",
        availbleCodeRunners: languageChoices.map(element => element.name), 
        tests: [{
            input: '',
            output: '',
            errorOutput: '',
            consoleOutput: '',
            isSolved: null
        }],
        automaticTests: [],
        startingFunction: ''
    }
   
    const exerciseData: Ref<ExerciseData>=ref(playGroundBase)



    const isAwaitngCodeRunner=computed(()=>codeRunnerActive.value.state=='awaiting')

    const requestCodeRunner= (codeRunnerName: string)=>{
        console.log("new type: "+codeRunnerName)
        codeRunnerActive.value.state='awaiting';

        // codeRunnerActive.value=  codeRunnerName;
        setTimeout(()=>{
            codeRunnerActive.value.codeRunnerType=codeRunnerName;
            console.log("type: "+codeRunnerActive.value.codeRunnerType)
            codeRunnerActive.value.state='active'
        },5000)
      
    }

    
    const setExceriseDataToPlayground=()=>{
        exerciseData.value=playGroundBase
    }

    const isAwaitingCompilation: Ref<boolean>=ref(false)
    

    const runCode=async (code: string)=>{
        // await axios.post('/runCode',code, {
        //     headers: {
        //       'Content-Type': 'application/json'
        //     }
        //   });
        isAwaitingCompilation.value=true;
          setTimeout(()=>{
            if(code.includes('print("Hello world")'))
            {
                exerciseData.value.tests[0].consoleOutput="hello world";
                exerciseData.value.tests[0].errorOutput='';
            }
            else
            {
                exerciseData.value.tests[0].consoleOutput="";
                exerciseData.value.tests[0].errorOutput="this is examoke the code should include phrase "+'print("Hello world")';
            }
            isAwaitingCompilation.value=false;
        },5000)
    }


    return {
        codeRunnerActive, 
        doesHaveACtiveToCodeRunner,
        requestCodeRunner,
        isAwaitngCodeRunner,
        exerciseData,
        isAwaitingCompilation,
        setExceriseDataToPlayground,
        runCode
    };
});