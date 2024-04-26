import { ActivationState, Client, type IFrame } from '@stomp/stompjs'
import type OnConnectFunc from '../types/StompConnection'
import type { IfStatement } from 'typescript'

let userName: String | null = null

export const stompClient = new Client({
  brokerURL: 'ws://localhost:8080/web-socket'
})

stompClient.onWebSocketError = (error) => {
  console.error('Error with websocket', error)
}
stompClient.onStompError = (frame) => {
  console.error('Broker reported error: ' + frame.headers['message'])
  console.error('Additional details: ' + frame.body)
}

export const connectStomp = () => {
  console.log('connecting to Stomp ')
  if (!isStompConnected()) stompClient.activate()
}

export const isStompConnected = (): boolean => {
  console.log('isStompConnected ')
  return stompClient.state === ActivationState.ACTIVE
}

export const disconnectStomp = () => {
  console.log('disconnect from stomp')
  stompClient.deactivate()
  userName = null
}

const deafultOnConnection = (frame: IFrame) => {
  console.log('default conneciton')
  userName = frame.headers['user-name']
  console.log(userName)
}

stompClient.onConnect = (frame: IFrame) => {
  deafultOnConnection(frame)
}

export const onConnectStomp = (func: OnConnectFunc) => {
  console.log('on connection')
  stompClient.onConnect = (frame: IFrame) => {
    deafultOnConnection(frame)
    func(frame)
  }
}

export const getConnetedUserName = (): String | null => {
  return userName
}
