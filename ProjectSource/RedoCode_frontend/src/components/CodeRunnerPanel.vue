<template>
    playground
    <LanguageDropdown
    :chosenValue="chosenLangague"
    @select="onSelectLanguage"
    />


    <div :class="{ 'lock': !establishedConnection }">
    <CodeEditor language="javascript"/>
</div>
<div v-if="!establishedConnection">
<div style="position:static; 
width: 5rem;
 height: 5rem;
  background-color: red">
    loading
</div>

<div v-if="!props.connectAtStart&&!tryingToEstablishConnection">
    <BasicButton :onClick="connectToCodeRunner">
        start
    </BasicButton>
</div>

</div>
    <!-- <BasicButton width="1rem" height="2rem"/> -->

    <!-- <Button @click="subscribeButton">
        {{ subscribeStatus }}
    </Button>
    <Button @click="postButton">
        post
    </Button>
    <Button @click="postButtonPrivate">
        post-private
    </Button>
    
    <div style="height: 12rem;">
        {{ meaages }}
        </div> -->

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
const props = defineProps({
  connectAtStart: {type: Boolean, required: false}
})


const subscribeStatus=ref(false);
const meaages=ref('');
const tryingToEstablishConnection: Ref<boolean>= ref(false);

const establishedConnection: Ref<boolean>= ref(false);
const chosenLangague: Ref<String>=ref("Cpp")
const connectToCodeRunner=()=>{
    
    onConnectStomp((frame: IFrame)=>{
    console.log("connectino result: "+ JSON.stringify(frame));
    console.log("Username: "+getConnetedUserName())
    establishedConnection.value=true;
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

</script>