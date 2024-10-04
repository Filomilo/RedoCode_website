import CoderunnerState from '@/types/CodeRunnerState'
import StompApiConnection from './StompApiConnection'
import ProgramResultsMessage from '@/types/ApiMessages/ProgramResultsMessage'
import ExecutionResponseBase from '@/types/ApiMessages/ExecutionResponses/ExecutionResponseBase'
import ExecutionChainScheme from '@/types/ApiMessages/ExecutionResponses/ExecutionChainScheme'
import ExecutionResponseStatusUpdate from '@/types/ApiMessages/ExecutionResponses/ExecutionResponseStatusUpdate'
import StompApiSubscription from './StompApiSubscription'
import MessageNotification from '@/types/ApiMessages/MessageNotification'

class StompApiSubscriptionController {
  private _stompApiConnection: StompApiConnection

  constructor(stompApiConnection: StompApiConnection) {
    this._stompApiConnection = stompApiConnection
    this.initSubscriptions()
  }

  private initSubscriptions() {
    console.log('this._vmStatusSubscription initialize')
    this._vmStatusSubscription = this._stompApiConnection.subscribe(
      '/user/public/topic/codeRunnerState',
      this._VmStatusCallBack.bind(this)
    )
    console.log('this._CodeResultsSubscription initialize')

    this._CodeResultsSubscription = this._stompApiConnection.subscribe(
      '/user/public/topic/codeRunnerResults',
      this._CodeResultsCallBack.bind(this)
    )

    this._ExecutionChainSubscription = this._stompApiConnection.subscribe(
      '/user/public/topic/ExecutionResponses',
      this._ExecutionChainCallBack.bind(this)
    )

    this._ServerNotificationSubscriptions = this._stompApiConnection.subscribe(
      '/user/public/topic/ServerNotifications',
      this._ServerNotificationsCallBack.bind(this)
    )
  }

  //#region Subscription objects
  private _vmStatusSubscription!: StompApiSubscription
  private _CodeResultsSubscription!: StompApiSubscription
  private _ExecutionChainSubscription!: StompApiSubscription
  private _ServerNotificationSubscriptions!: StompApiSubscription
  //#endregion

  //#region Subscription CallBacks

  private _VmStatusCallBack(message: object) {
    const state: CoderunnerState = message as CoderunnerState

    const eventHandlers: any[] = this._VmStatusSubscriptions as any[]
    console.log('codeRunnerState received  ' + JSON.stringify(state))
    console.log('typeof this.eventHandlers  ' + typeof eventHandlers)
    console.log('typeof this.eventHandlers  ' + eventHandlers)
    console.log('length  ' + JSON.stringify(eventHandlers.length))

    eventHandlers.forEach((element: { (arg: CoderunnerState): void }) => {
      console.log('|Test')
      element(state)
    })
  }
  private _CodeResultsCallBack(message: object) {
    const results: ProgramResultsMessage = message as ProgramResultsMessage
    console.log('_CodeResultsCallBack received  ' + JSON.stringify(results))
    this._CodeResultsSubscriptions.forEach(
      (element: (results: ProgramResultsMessage) => void) => {
        element(results)
      }
    )
  }
  private _ExecutionChainCallBack(message: object) {
    const responseBase: ExecutionResponseBase = message as ExecutionResponseBase
    console.log(
      '_ExecutionChainCallBack received  ' + JSON.stringify(responseBase)
    )

    if (responseBase.messageType === 'CHAIN_SCHEME')
      this._ExecutionChainSchemeSubscriptions.forEach(
        (element: (_update: ExecutionChainScheme) => void) => {
          element(responseBase as ExecutionChainScheme)
        }
      )
    else
      this._ExecutionResponseStatusUpdateSubscriptions.forEach(
        (element: (_update: ExecutionResponseStatusUpdate) => void) => {
          element(responseBase as ExecutionResponseStatusUpdate)
        }
      )
  }

  private _ServerNotificationsCallBack(message: object) {
    const results: MessageNotification = message as MessageNotification
    console.log(
      '_ServerNotificationsCallBack received  ' + JSON.stringify(results)
    )
    this._ServerNotificationsSubscriptions.forEach(
      (element: (notifications: MessageNotification) => void) => {
        console.log(
          '_ServerNotificationsCallBack subs: ' + element + ' for ' + results
        )
        element(results)
      }
    )
  }
  //#endregion

  //#region vmStatus
  private _VmStatusSubscriptions: { (arg: CoderunnerState): void }[] = []

  public addVmStatusSubscription(method: {
    (arg: CoderunnerState): void
  }): void {
    console.log('_VmStatusSubscriptions  ' + this._VmStatusSubscriptions)
    console.log('method  ' + method)
    console.log('method  ' + typeof method)

    this._VmStatusSubscriptions.push(method)
    console.log('_VmStatusSubscriptions  ' + this._VmStatusSubscriptions)
  }
  public removeVmStatusSubscription(method: {
    (arg: CoderunnerState): void
  }): void {
    console.log('Test')
    const indexToRemove = this._VmStatusSubscriptions.findIndex(
      item => item === method
    )
    if (indexToRemove !== -1) {
      this._VmStatusSubscriptions.splice(indexToRemove, 1)
    }
  }

  public clearVmStatusSubscriptions() {
    console.log('test')
    this._VmStatusSubscriptions = []
  }
  //#endregion

  //#region codeResults
  private _CodeResultsSubscriptions: { (arg: ProgramResultsMessage): void }[] =
    []

  public addCodeResultsSubscription(method: {
    (arg: ProgramResultsMessage): void
  }): void {
    console.log('addCodeResultsSubscription: ' + method)
    this._CodeResultsSubscriptions.push(method)
  }
  public removeCodeResultsSubscription(method: {
    (arg: ProgramResultsMessage): void
  }): void {
    const indexToRemove = this._CodeResultsSubscriptions.findIndex(
      item => item === method
    )
    if (indexToRemove !== -1) {
      this._CodeResultsSubscriptions.splice(indexToRemove, 1)
    }
  }

  public clearCodeResultsSubscriptions() {
    this._CodeResultsSubscriptions = []
  }
  //#endregion

  //#region execution chain scheme
  private _ExecutionChainSchemeSubscriptions: ((
    chain: ExecutionChainScheme
  ) => void)[] = []

  public addExecutionChainSchemeSubscription(
    method: (chain: ExecutionChainScheme) => void
  ): void {
    this._ExecutionChainSchemeSubscriptions.push(method)
  }
  public removeExecutionChainSchemeSubscription(
    method: (chain: ExecutionChainScheme) => void
  ): void {
    const indexToRemove = this._ExecutionChainSchemeSubscriptions.findIndex(
      item => item === method
    )
    if (indexToRemove !== -1) {
      this._ExecutionChainSchemeSubscriptions.splice(indexToRemove, 1)
    }
  }

  public clearExecutionChainSchemeSubscriptions() {
    this._CodeResultsSubscriptions = []
  }
  //#endregion

  //#region execution status update
  private _ExecutionResponseStatusUpdateSubscriptions: ((
    _update: ExecutionResponseStatusUpdate
  ) => void)[] = []

  public addEExecutionResponseStatusUpdateSubscription(
    method: (_update: ExecutionResponseStatusUpdate) => void
  ): void {
    this._ExecutionResponseStatusUpdateSubscriptions.push(method)
  }
  public removeExecutionResponseStatusUpdateSubscription(
    method: (_update: ExecutionResponseStatusUpdate) => void
  ): void {
    const indexToRemove =
      this._ExecutionResponseStatusUpdateSubscriptions.findIndex(
        item => item === method
      )
    if (indexToRemove !== -1) {
      this._ExecutionResponseStatusUpdateSubscriptions.splice(indexToRemove, 1)
    }
  }

  public clearExecutionResponseStatusUpdateSubscriptions() {
    this._CodeResultsSubscriptions = []
  }
  //#endregion

  //#region execution chain scheme
  private _ServerNotificationsSubscriptions: ((
    notifications: MessageNotification
  ) => void)[] = []

  public addServerNotificationsSubscription(
    method: (notifications: MessageNotification) => void
  ): void {
    console.log('addServerNotificationsSubscription: ' + method)
    this._ServerNotificationsSubscriptions.push(method)
  }
  public removeServerNotificationsSubscription(
    method: (notifications: MessageNotification) => void
  ): void {
    const indexToRemove = this._ServerNotificationsSubscriptions.findIndex(
      item => item === method
    )
    if (indexToRemove !== -1) {
      this._ServerNotificationsSubscriptions.splice(indexToRemove, 1)
    }
  }

  public clearServerNotificationsSubscriptions() {
    this._ServerNotificationsSubscriptions = []
  }
  //#endregion

  //#endregion
}

export default StompApiSubscriptionController
