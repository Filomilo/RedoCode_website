import type CoderunnerState from '@/types/CodeRunnerState'
import CodeRunnerType from '@/types/CodeRunnerTypes'

import { computed, ComputedRef, Ref, ref } from 'vue'
import { stringify } from 'flatted'
import CodeRunnerStatus from '@/types/CodeRunnerStatus'
import StompApiSender from '../Stomp/StompApiSender'
import StompApiSubscriptionController from '../Stomp/StompApiSubscriptionController'
import EndpointAccess from '../EndpointsAccess'
import { useActiveUserStore } from '@/stores/ActiveUserStore'
export default class CodeRunnerConnection {
  private _stompApiSender: StompApiSender
  private activeUserStore = useActiveUserStore()

  public codeRunnerState: Ref<CoderunnerState> = ref({
    codeRunnerType: CodeRunnerType.UNIDENTIFIED,
    state: CodeRunnerStatus.NONE,
  })

  public readonly doesHaveACtiveToCodeRunner: ComputedRef<Boolean> = computed(
    () => {
      return this.codeRunnerState.value.state === 'ACTIVE'
    }
  )
  public readonly isAwaitingCodeRunner = computed(
    () => this.codeRunnerState.value.state == 'AWAITING'
  )

  public onCodeRunnerStateChanged(codeRunnerState: CoderunnerState) {
    console.log('onCodeRunnerStateChanged: ' + stringify(codeRunnerState))
    this.codeRunnerState.value = codeRunnerState
  }

  constructor(
    stompApiSender: StompApiSender,
    stompApiSubscriptions: StompApiSubscriptionController
  ) {
    this._stompApiSender = stompApiSender
    stompApiSubscriptions.addVmStatusSubscription(
      this.onCodeRunnerStateChanged.bind(this)
    )
    console.log(JSON.stringify(this.codeRunnerState.value))
  }

  public updateCodeRunner = () => {
    EndpointAccess.unauthorized
      .getCodeRunnerState()
      .then((data: CoderunnerState) => {
        console.log('updateCodeRunner: ' + JSON.stringify(data))
        this.codeRunnerState.value = data
      })
  }

  public setAwaiting() {
    console.log('set awaiting')
    console.log('set awaiting: ' + stringify(this.codeRunnerState))
    this.codeRunnerState.value.state = CodeRunnerStatus.AWAITING
    console.log('set awaiting after : ' + stringify(this.codeRunnerState.value))
  }
  public setNoneStatus() {
    this.codeRunnerState.value.state = CodeRunnerStatus.NONE
  }
}
