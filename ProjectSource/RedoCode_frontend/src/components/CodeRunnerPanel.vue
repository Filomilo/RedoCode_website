<template>
    playground
    <CodeEditor language="javascript"/>
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
import { ref,onMounted } from 'vue';
import { onBeforeRouteLeave,onBeforeRouteUpdate} from 'vue-router'
import axios from "axios";
import {connectStomp,disconnectStomp,onConnectStomp, getConnetedUserName} from "../config/StompApiConnection"
import type { IFrame } from '@stomp/stompjs';
import { json } from 'stream/consumers';

// const stompClient = new Client({
//     brokerURL: 'ws://localhost:8080/web-socket'
// });

// stompClient.onConnect = (frame) => {
//     console.log("conected")
//     console.log('Connected: ' + frame);
//     subscribeStatus.value=true;
//     stompClient.subscribe('/topic/messages', (message) => {
//         meaages.value+=", "+message.body

//     });
//     stompClient.subscribe('/user/topic/private-messages', (message) => {
//         meaages.value+=", "+"#"+message.body+"#"
//         console.log("Greeting: ##"+ JSON.parse(message.body).content+"##")
//     });
// };

const subscribeStatus=ref(false);
const meaages=ref('');


const connectToCodeRunner=()=>{
    
    onConnectStomp((frame: IFrame)=>{
    console.log("connectino result: "+ JSON.stringify(frame));
    console.log("Username: "+getConnetedUserName())
})

    connectStomp();
    
}

const diconnectFromCodeRunners=()=>{
    // console.log("diconnect from code runners")
    disconnectStomp();
}




onMounted(()=>{
    connectToCodeRunner();
})

onBeforeRouteLeave(async (to, from ,next)=>{
    diconnectFromCodeRunners();
    

    next();
})

</script>