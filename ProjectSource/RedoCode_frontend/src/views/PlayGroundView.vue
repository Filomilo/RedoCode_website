<template>
    playground
    <CodeEditor language="javascript"/>
    <!-- <BasicButton width="1rem" height="2rem"/> -->

    <Button @click="subscribeButton">
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
        </div>

</template>



<script lang="ts" setup>
import CodeEditor from '@/components/CodeEditorPanel.vue';
import BasicButton from '@/components/BasicButton.vue';
import type { Button } from 'bootstrap';
import { ref } from 'vue';
import { Client } from '@stomp/stompjs';
import axios from "axios";

const stompClient = new Client({
    brokerURL: 'ws://localhost:8080/web-socket'
});

stompClient.onConnect = (frame) => {
    console.log("conected")
    console.log('Connected: ' + frame);
    subscribeStatus.value=true;
    stompClient.subscribe('/topic/messages', (message) => {
        meaages.value+=", "+message.body

    });
    stompClient.subscribe('/user/topic/private-messages', (message) => {
        meaages.value+=", "+"#"+message.body+"#"
        console.log("Greeting: ##"+ JSON.parse(message.body).content+"##")
    });
};
stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};
stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

const subscribeStatus=ref(false);
const meaages=ref('');
const subscribeButton=()=>{
    console.log("Subcribe buton")
    if(subscribeStatus.value)
    {
        stompClient.deactivate();
        subscribeStatus.value=false;
    console.log("Disconnected"); 
    }
    else
    stompClient.activate();
}

const postButton=()=>{
    const test="test mes";
    console.log("post");
    

    stompClient.publish({
        destination: "/app/message",
        body: JSON.stringify({'test': test})
    })
    ;


}

const postButtonPrivate=()=>{
    const test="prviate  mes";
    console.log("post");
    

    stompClient.publish({
        destination: "/app/message-private",
        body: JSON.stringify({'test': test})
    })
    ;


}

</script>