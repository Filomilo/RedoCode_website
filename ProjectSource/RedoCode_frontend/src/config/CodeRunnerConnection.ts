import type { IMessage, IPublishParams, messageCallbackType } from '@stomp/stompjs'
import {stompClient} from './StompApiConnection'
import type CodeRunnerRequestMessage from '@/types/CodeRunnerRequestMessage'
import type CodeRunnerStateCallBack from '@/types/CodeRunnerStateCallBack'
import type CoderunnerState from '@/types/CodeRunnerState'
export const requstDefaultVmMachine=(type: string)=>{

    const request:CodeRunnerRequestMessage={
        CodeRunnerType: type
    }

    const message: IPublishParams={
        destination: "/app/codeRunnerRequest",
        body: JSON.stringify(request)
    }
    console.log("requewsting vm: "+ JSON.stringify(message))
    stompClient.publish(message)
}



export const subcribeToVmStatus=(func: CodeRunnerStateCallBack)=>{
    console.log("subscribing");
    stompClient.subscribe("/user/topic/codeRunnerState",(mesage: IMessage) =>{
        console.log("staee: "+JSON.stringify(mesage.body));
        const state: CoderunnerState=JSON.parse(mesage.body);
        func(state)
    })
}