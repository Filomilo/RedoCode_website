import { ActivationState, Client, IFrame } from '@stomp/stompjs'
import StompApiSubscription from './StompApiSubscription'

export default class StompApiConnection {
  userName: String | null = null

  private _stompClient
  private _onBeforeConnection: (message: string) => void
  private _onConnected: (message: string) => void
  private _onError: (message: string) => void
  private _subscriptions: StompApiSubscription[] = []

  constructor(
    connectionUrl: string,
    onBeforeConnection: (message: string) => void,
    onConnected: (message: string) => void,
    onError: (message: string) => void
  ) {
    this._onBeforeConnection = onBeforeConnection
    this._onConnected = onConnected
    this._onError = onError

    this._stompClient = new Client({
      brokerURL: connectionUrl,
      beforeConnect: () => {
        console.log(connectionUrl + ' beforeConnect')
        this._onBeforeConnection('connecting with server')
      },
      onConnect: (frame: IFrame) => {
        console.log(connectionUrl + ' on connect')
        this._subscriptions.forEach((sub: StompApiSubscription) => {
          sub.activateSubscription()
        })
        this._onConnected('succesfully conntected')
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
      }
    })
  }

  setConnectionAuthentication(nick: string, password: string) {
    this._stompClient.connectHeaders = {
      loign: nick,
      passcode: password
    }
  }

  public activate() {
    this._stompClient.activate()
  }

  public deactivate() {
    this._subscriptions.forEach((sub: StompApiSubscription) => {
      sub.deactivateSubscription()
    })
    this._stompClient.deactivate()
  }

  public getIsActive() {
    this._stompClient.active
  }

  public subscribe(location: string, callback: (mesage: Object) => void): StompApiSubscription {
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
    this._stompClient.publish({
      destination: destination,
      body: JSON.stringify(message)
    })
  }
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