import { ActivationState, Client, IFrame, StompHeaders } from '@stomp/stompjs'
import StompApiSubscription from './StompApiSubscription'
import { stompClient } from './StompApiConnectionold'
import { useActiveUserStore } from '@/stores/ActiveUserStore'
import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'

export default class StompApiConnection {
  userName: String | null = null

  private _stompClient: Client
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
      connectHeaders: {
        token: 'your-token-here',
      },
      beforeConnect: () => {
        console.log(connectionUrl + ' beforeConnect')
        this._onBeforeConnection('connecting with server')
      },
      onConnect: (frame: IFrame) => {
        console.log(connectionUrl + ' on connect')
        this._subscriptions.forEach((sub: StompApiSubscription) => {
          sub.activateSubscription()
        })
        const activeUserStore = useActiveUserStore()
        console.log(
          'on connected: ' +
            JSON.stringify(activeUserStore.getToken()._rawValue)
        )
        if (activeUserStore.getToken().length > 0) {
          this.sendMessage('/public/app/tokenAuth', {
            token: activeUserStore.getToken(),
          })
        }
        const codeRunnerStore = useCodeRunnerStore()
        codeRunnerStore.updateCodeRunner()
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
      },
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
    console.log('THrough: ' + JSON.stringify(stompClient))
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
