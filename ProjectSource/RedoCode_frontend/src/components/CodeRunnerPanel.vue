<template>
    <!-- playground:
    {{ code }}
    <LanguageDropdown :chosenValue="String(chosenLangague)" @select="onSelectLanguage" />


    <div :class="{ 'lock': !establishedConnection }">
    <CodeEditor v-model="code" :code="code" :chosenLangague="chosenLangague" />
    </div>

    <div v-if="VmAcces">
    <BasicButton :onClick="onRunCode">
        run
    </BasicButton>
    </div>

    <div v-if="!establishedConnection && tryingToEstablishConnection">
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

    <div :class="{ 'lock': !VmAcces }">
    <ResultsPanel :resultData="resultData" />
    </div> -->
    <!-- code runner panel -->

    <Splitter style="height: 100vh; width:100vw; background-color: red">
        <SplitterPanel style="background-color: rgba(35, 255, 189, 0.514); display: flex; flex-direction: column" >
            <LanguageDropdown :chosenValue="String(chosenLangague)" @select="onSelectLanguage" />
            <!-- <div :class="{ 'lock': !establishedConnection }"> -->
            <CodeEditor v-model="code" :code="code" :chosenLangague="chosenLangague" class="CodeEditor"/>
            <!-- </div> -->
        </SplitterPanel>
        <SplitterPanel style="background-color: rgba(252, 222, 52, 0.514);" :size="10" :minSize="10">test</SplitterPanel>
        <!-- <SplitterPanel class="flex align-items-center justify-content-center" :size="20" :minSize="10"> Panel 1 </SplitterPanel>
        <SplitterPanel :size="80">
            <Splitter layout="vertical">
                <SplitterPanel class="flex align-items-center justify-content-center" :size="15"> Panel 2 </SplitterPanel>
                <SplitterPanel :size="85">
                    <Splitter>
                        <SplitterPanel class="flex align-items-center justify-content-center" :size="20"> Panel 3 </SplitterPanel>
                        <SplitterPanel class="flex align-items-center justify-content-center" :size="80"> Panel 4 </SplitterPanel>
                    </Splitter>
                </SplitterPanel>
            </Splitter>
        </SplitterPanel> -->
    </Splitter>
</template>



<script lang="ts" setup>
import CodeEditor from '@/components/CodeEditorPanel.vue';
import BasicButton from '@/components/BasicButton.vue';
import type { Button } from 'bootstrap';
import { ref, onMounted, type Ref } from 'vue';
import { onBeforeRouteLeave, onBeforeRouteUpdate } from 'vue-router'
import axios from "axios";
import { connectStomp, disconnectStomp, onConnectStomp, getConnetedUserName } from "../config/StompApiConnection"
import type { IFrame } from '@stomp/stompjs';
import LanguageDropdown from './LanguageDropdown.vue';
import { requstDefaultVmMachine, subcribeToVmStatus, sendToCompile, subscribeToCodeResults } from '../config/CodeRunnerConnection'
import type CodeRunnerState from '@/types/CodeRunnerState';
import type CodeToRunMessage from '@/types/CodeToRunMessage';
import ResultsPanel from './ResultsPanel.vue';
import { basicResultTemplate, languageChoices } from '../config/Data'
import type CodeResultsType from '@/types/CodeResultsType';

const props = defineProps({
    connectAtStart: { type: Boolean, required: false }
})


const subscribeStatus = ref(false);
const meaages = ref('');
const tryingToEstablishConnection: Ref<boolean> = ref(false);
const establishedConnection: Ref<boolean> = ref(false);
const VmAcces: Ref<boolean> = ref(false);
const chosenLangague: Ref<String> = ref(languageChoices[0].name)
const code: Ref<string> = ref("Write Code")

const resultData = ref(basicResultTemplate)



const updateVmStatus = (state: CodeRunnerState) => {
    console.log("status: " + state);
    if (state.state == "STOPPED" || state.state == "RUNNING_MACHINE") {
        console.log("vmacces")
        VmAcces.value = true;
    }
    else
        VmAcces.value = false;
}

const updateResults = (results: CodeResultsType[]) => {
    console.log("results recived: " + JSON.stringify(results))
    resultData.value = results;
}

const connectToCodeRunner = () => {

    onConnectStomp((frame: IFrame) => {
        console.log("connectino result: " + JSON.stringify(frame));
        console.log("Username: " + getConnetedUserName())
        establishedConnection.value = true;
        requstDefaultVmMachine(String(chosenLangague.value));
        subcribeToVmStatus(updateVmStatus);
        subscribeToCodeResults(updateResults);
    })
    tryingToEstablishConnection.value = true;
    connectStomp();

}

const diconnectFromCodeRunners = () => {
    // console.log("diconnect from code runners")
    disconnectStomp();
}




onMounted(() => {
    console.log("props: " + JSON.stringify(props))
    if (props.connectAtStart) {
        connectToCodeRunner();
    }
})

onBeforeRouteLeave(async (to, from, next) => {
    diconnectFromCodeRunners();


    next();
})

const onSelectLanguage = (lang: string) => {
    console.log("info selcted:" + lang)
    chosenLangague.value = lang;
    if (establishedConnection.value)
        requstDefaultVmMachine(lang);
}

const onRunCode = () => {
    console.log("on run code: " + code.value)
    const toCompielMes: CodeToRunMessage = {
        code: code.value,
        exercise_id: null
    }
    sendToCompile(toCompielMes);
}

</script>