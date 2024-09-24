import ChainNodeStatus from '@/types/ApiMessages/ExecutionResponses/ChainNodeStatus'
import ExecutionChainScheme from '@/types/ApiMessages/ExecutionResponses/ExecutionChainScheme'
import StompApiSubscriptionController from './Stomp/StompApiSubscriptionController'
import ExecutionResponseStatusUpdate from '@/types/ApiMessages/ExecutionResponses/ExecutionResponseStatusUpdate'

export interface ExecutionChainControls {
  _executionChain: ChainNodeStatus[]
  _shouldBeVisible: boolean
  _closeReady: boolean
}

class ExecutionChainController implements ExecutionChainControls {
  public _executionChain: ChainNodeStatus[] = []

  public _shouldBeVisible: boolean = false
  public _closeReady: boolean = false
  public onCloseSuccess: () => void = () => {}

  public onVisibilityUpdate: (state: ExecutionChainController) => void =
    () => {}

  get executionChain(): ChainNodeStatus[] {
    return this._executionChain
  }

  get shouldBeVisible(): boolean {
    return this._shouldBeVisible
  }
  get closeReady(): boolean {
    return this._closeReady
  }

  get resolvedLength(): number {
    return this.executionChain.filter(x => x.status === 'SUCCESS').length
  }
  constructor(stompApiSubscriptionController: StompApiSubscriptionController) {
    this.reset()
    stompApiSubscriptionController.addExecutionChainSchemeSubscription(
      this.loadChainScheme.bind(this)
    )
    stompApiSubscriptionController.addEExecutionResponseStatusUpdateSubscription(
      this.updateStatus.bind(this)
    )
  }

  private reset() {
    this._executionChain = []
    this._shouldBeVisible = false
    this._closeReady = false
    this.onVisibilityUpdate(this)
    this.onCloseSuccess = () => {}
  }

  public loadChainScheme = (scheme: ExecutionChainScheme) => {
    console.log('ExecutionResponses  loadChainScheme:' + JSON.stringify(scheme))
    this._executionChain = scheme.levels
    this._shouldBeVisible = true

    this.onVisibilityUpdate(this)
  }

  private waitForScheme = (
    update: ExecutionResponseStatusUpdate,
    timeout: number
  ) => {
    console.log('CHAIN waiting for' + JSON.stringify(update))
    return new Promise<void>((resolve, reject) => {
      // resolve();
      const interval = 50
      const checkArraySize = () => {
        if (this.validateUpdate(update.stepUpdate, update)) {
          resolve()
        } else if (timeout <= 0) {
          reject(new Error('Error receiving messages'))
        } else {
          timeout -= interval
          setTimeout(checkArraySize, interval)
        }
      }
      checkArraySize()
    })
  }

  private validateUpdate(
    stepUpdate: number,
    updateStatus: ExecutionResponseStatusUpdate
  ): boolean {
    if (
      this.executionChain === undefined ||
      this.executionChain === null ||
      this.executionChain.length == 0
    )
      return false
    if (
      this.executionChain[stepUpdate].status === 'PENDING' &&
      updateStatus.lvlStatus === 'RUNNING'
    )
      return true

    if (
      this.executionChain[stepUpdate].status === 'RUNNING' &&
      (updateStatus.lvlStatus === 'FAILED' ||
        updateStatus.lvlStatus === 'SUCCESS')
    )
      return true

    if (updateStatus.lvlStatus === 'FAILED') return true

    return false
  }

  public get isAllSolved() {
    return (
      this.executionChain.filter(x => x.status === 'SUCCESS').length ===
      this.executionChain.length
    )
  }

  public updateStatus = async (update: ExecutionResponseStatusUpdate) => {
    console.log('CHAIN  update:' + JSON.stringify(update))
    this.waitForScheme(update, 5000)
      .then(() => {
        console.log('_______________UPDATE: ' + JSON.stringify(update))
        this.executionChain[update.stepUpdate].processingMessage =
          update.message
        this.executionChain[update.stepUpdate].status = update.lvlStatus
        console.log('UPdate check if can close: ' + JSON.stringify(update))
        if (this.isAllSolved || update.lvlStatus === 'FAILED') {
          console.log(
            'UPdate check if can close: ' +
              JSON.stringify(update) +
              'RESULTED IN TRUE'
          )
          this._closeReady = true
        }
        console.log(
          'UPdate check if can close: ' +
            JSON.stringify(update) +
            'RESULTED IN false'
        )
        this.onVisibilityUpdate(this)
      })
      .catch(() => {
        console.log(
          'UPdate check if can close: ' +
            JSON.stringify(update) +
            'RESULTED IN TRUE'
        )
        this._closeReady = true
        this.onVisibilityUpdate(this)
      })
  }

  public close() {
    console.log('Execution Chain close')
    if (this.isAllSolved || import.meta.env.MODE === 'development') {
      console.log('Execution Chain close SUCCESS')
      this.onCloseSuccess()
    }
    this.reset()
  }
}

export default ExecutionChainController
