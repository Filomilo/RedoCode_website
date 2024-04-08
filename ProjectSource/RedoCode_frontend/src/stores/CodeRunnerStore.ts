import { defineStore } from 'pinia'
import {ref,computed, type Ref} from 'vue'
import type CodeRunnerState from '../types/CodeRunnerState'
export const useCodeRunnerStore = defineStore('codeRunnerStore',()=>{
    const codeRunnerActive: Ref<CodeRunnerState>=ref({
        codeRunnerType: 'cpp',
        state: 'active'
    })
    const doesHaveACtiveToCodeRunner=computed(()=> {
        return codeRunnerActive.value.state==='active';
    })

    // const isAwaitngCodeRunner=computed(()=> true)
    const isAwaitngCodeRunner=computed(()=>codeRunnerActive.value.state=='awaiting')
    // const doesHaveACtiveToCodeRunner=computed(()=>false)

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
    return {codeRunnerActive, doesHaveACtiveToCodeRunner,requestCodeRunner,isAwaitngCodeRunner};
});