import { ActivationState, Client, IFrame, StompHeaders } from '@stomp/stompjs'
import StompApiSubscription from './StompApiSubscription'

export default class StompApiConnection {
  userName: String | null = null

  private _stompClient: Client
  private _onBeforeConnection: () => void
  private _onError: (message: string) => void
  private _subscriptions: StompApiSubscription[] = []
  private _onConnected(){
    console.log("on connected evetn")
    this._onConnectedEvents.forEach((element:()=>void) => {
      element();
    });
  }
  private _onDisconnected(){
    console.log("on disconnected evetn")
    this._onConnectedEvents.forEach((element:()=>void) => {
      element();
    });
  }




  constructor(
    connectionUrl: string,
    onBeforeConnection: () => void,
    onConnected: () => void,
    onError: (mes:string) => void
  ) {
    this._onBeforeConnection = onBeforeConnection
    this._onError = onError
    this.addOnConnectEvent(onConnected);

    this._stompClient = new Client({
      brokerURL: connectionUrl,
      connectHeaders: {
        token: 'your-token-here',
      },
      beforeConnect: () => {
        console.log(connectionUrl + ' beforeConnect')
        this._onBeforeConnection()
      },
      onConnect: (frame: IFrame) => {
        console.log(connectionUrl + ' on connect')
        this._subscriptions.forEach((sub: StompApiSubscription) => {
          sub.activateSubscription()
        })

        this._onConnected()
      },
      onStompError: (frame: IFrame) => {
        console.log(connectionUrl + ' onStompError')
        this._onError('there was an error wtih server connection')
      },
      onUnhandledFrame: (frame: IFrame) => {
        console.log(connectionUrl + ' onUnhandledFrame')
        this._onError('unhandled message recived from server')
      },
      onWebSocketError: (frame: IFrame) => {
        console.log(connectionUrl + ' onWebSocketError')
        this._onError('there was an websocket error wtih server connection')
      },
      onDisconnect: (frame: IFrame)=>{
        this._onDisconnected();
      }
    })

    this._stompClient.connectHeaders = {
      login: 'AAA',
    }
  }

  setConnectionAuthentication(token: string) {
    console.log('setConnectionAuthentication')
    this._stompClient.connectHeaders = {
      token: token,
    }
  }

  public activate() {
    this._stompClient.activate()
  }

  public deactivate() {
    if (this._stompClient.connected) {
      this._subscriptions.forEach((sub: StompApiSubscription) => {
        sub.deactivateSubscription()
      })
    }
    this._stompClient.deactivate()
  }

  public getIsActive() {
    this._stompClient.active
  }

  public subscribe(
    location: string,
    callback: (mesage: Object) => void
  ): StompApiSubscription {
    console.log('new subsciption on ' + location)
    const newSubsricption: StompApiSubscription = new StompApiSubscription(
      this._stompClient,
      location,
      callback
    )
    this._subscriptions.push(newSubsricption)
    return newSubsricption
  }

  public sendMessage(destination: string, message: Object) {
    console.log('desitn: ' + destination)

    const obj = {
      destination: destination,
      body: JSON.stringify(message),
    }

    this._stompClient.publish(obj)
    console.log('published ' + JSON.stringify(obj))

  }




//#region events handling

private _onConnectedEvents: (()=>void)[]=[]
private _onDisconnectEvents: (()=>void)[]=[]

public addOnConnectEvent(method: (()=> void)):void{
  this._onConnectedEvents.push(method)
}
public removeOnConnectEvent(method: (()=> void)):void{
   const indexToRemove = this._onConnectedEvents.findIndex(item => item === method);
   if (indexToRemove !== -1) {
       this._onConnectedEvents.splice(indexToRemove, 1);
   }
}
public clearOnConnectEvents(){
   this._onConnectedEvents=[];
}

public addOnDisconnectEvent(method: (()=> void)):void{
  this._onDisconnectEvents.push(method)
}
public removeOnDisconnectEvent(method: (()=> void)):void{
   const indexToRemove = this._onConnectedEvents.findIndex(item => item === method);
   if (indexToRemove !== -1) {
       this._onDisconnectEvents.splice(indexToRemove, 1);
   }
}
public clearOnDisconnectEvents(){
   this._onDisconnectEvents=[];
}



//#endregion

}

// stompClient.onWebSocketError = (error) => {
//   console.error('Error with websocket', error)
// }
// stompClient.onStompError = (frame) => {
//   console.error('Broker reported error: ' + frame.headers['message'])
//   console.error('Additional details: ' + frame.body)
// }

// export const connectStomp = () => {
//   console.log('connecting to Stomp ')
//   if (!isStompConnected()) stompClient.activate()
// }

// export const isStompConnected = (): boolean => {
//   console.log('isStompConnected ')
//   return stompClient.state === ActivationState.ACTIVE
// }

// export const disconnectStomp = () => {
//   console.log('disconnect from stomp')
//   stompClient.deactivate()
//   userName = null
// }

// const deafultOnConnection = (frame: IFrame) => {
//   console.log('default conneciton')
//   userName = frame.headers['user-name']
//   console.log(userName)
// }

// stompClient.onConnect = (frame: IFrame) => {
//   deafultOnConnection(frame)
// }

// export const onConnectStomp = (func: OnConnectFunc) => {
//   console.log('on connection')
//   stompClient.onConnect = (frame: IFrame) => {
//     deafultOnConnection(frame)
//     func(frame)
//   }
// }

// export const getConnetedUserName = (): String | null => {
//   return userName
// }

// }
