<template>
    playground
    <LanguageDropdown
    :chosenValue="String(chosenLangague)"
    @select="onSelectLanguage"
    />


    <div :class="{ 'lock': !establishedConnection }">
    <CodeEditor language="javascript"/>
</div>

<div v-if="VmAcces">
    <BasicButton :onClick="onRunCode">
        run
    </BasicButton>
</div>

<div v-if="!establishedConnection&&tryingToEstablishConnection">
<div style="position:static; 
width: 5rem;
 height: 5rem;
  background-color: red">
    loading
</div>
</div>
<div v-if="!props.connectAtStart&&!tryingToEstablishConnection">
    <BasicButton :onClick="connectToCodeRunner">
        start
    </BasicButton>
</div>

<!-- <div :class="{ 'lock': !VmAcces }"> -->
    <ResultsPanel
    :resultData="resultData"
    />
<!-- </div> -->

</template>



<script lang="ts" setup>
import CodeEditor from '@/components/CodeEditorPanel.vue';
import BasicButton from '@/components/BasicButton.vue';
import type { Button } from 'bootstrap';
import { ref,onMounted, type Ref } from 'vue';
import { onBeforeRouteLeave,onBeforeRouteUpdate} from 'vue-router'
import axios from "axios";
import {connectStomp,disconnectStomp,onConnectStomp, getConnetedUserName} from "../config/StompApiConnection"
import type { IFrame } from '@stomp/stompjs';
import LanguageDropdown from './LanguageDropdown.vue';
import {requstDefaultVmMachine, subcribeToVmStatus,sendToCompile } from '../config/CodeRunnerConnection'
import type CodeRunnerState from '@/types/CodeRunnerState';
import type CodeToRunMessage from '@/types/CodeToRunMessage';
import ResultsPanel from './ResultsPanel.vue';
import {basicResultTemplate} from '../config/Data'


const props = defineProps({
  connectAtStart: {type: Boolean, required: false}
})


const subscribeStatus=ref(false);
const meaages=ref('');
const tryingToEstablishConnection: Ref<boolean>= ref(false);
const establishedConnection: Ref<boolean>= ref(false);
const VmAcces: Ref<boolean>= ref(false);
const chosenLangague: Ref<String>=ref("Cpp")


const resultData=ref(basicResultTemplate)



const updateVmStatus=(state: CodeRunnerState)=>{
    console.log("status: "+ state);
    if(state=="STOPPED"||state=="RUNNING_MACHINE")
    {
        console.log("vmacces")
        VmAcces.value=true;
    }
    else
    VmAcces.value=false;
}

const connectToCodeRunner=()=>{
    
    onConnectStomp((frame: IFrame)=>{
        console.log("connectino result: "+ JSON.stringify(frame));
        console.log("Username: "+getConnetedUserName())
        establishedConnection.value=true;
        requstDefaultVmMachine(String(chosenLangague.value));
        subcribeToVmStatus(updateVmStatus);
})
tryingToEstablishConnection.value=true;
    connectStomp();
    
}

const diconnectFromCodeRunners=()=>{
    // console.log("diconnect from code runners")
    disconnectStomp();
}




onMounted(()=>{
    console.log("props: "+JSON.stringify(props))
    if(props.connectAtStart){
    connectToCodeRunner();
    }
})

onBeforeRouteLeave(async (to, from ,next)=>{
    diconnectFromCodeRunners();
    

    next();
})

const onSelectLanguage=(lang: string)=>{
    console.log("info selcted:" + lang)
    chosenLangague.value=lang;
}

const onRunCode=()=>{
    const toCompielMes: CodeToRunMessage={
        code: "test"
    }
    sendToCompile(toCompielMes);
}

</script>