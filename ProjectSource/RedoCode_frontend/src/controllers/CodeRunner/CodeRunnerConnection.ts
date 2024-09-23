import type CodeRunnerRequestMessage from '@/types/CodeRunnerRequestMessage'
import type CoderunnerState from '@/types/CodeRunnerState'
import CodeRunnerType from '@/types/CodeRunnerTypes'

import { computed, ComputedRef, Ref, ref } from 'vue'
import { stringify, parse } from 'flatted'
import CodeRunnerStatus from '@/types/CodeRunnerStatus'
import StompApiSender from '../Stomp/StompApiSender'
import StompApiSubsciptionContorller from '../Stomp/StompApiSubsriptionsController'
import EnpointAcces from '../EndpointsAcces'
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
  public readonly isAwaitngCodeRunner = computed(
    () => this.codeRunnerState.value.state == 'AWAITING'
  )

  public onCodeRunnerStateChanged(codeRunnerState: CoderunnerState) {
    console.log('onCodeRunnerStateChanged: ' + stringify(codeRunnerState))
    this.codeRunnerState.value = codeRunnerState
  }

  constructor(
    stompApiSender: StompApiSender,
    stompApiSubscriptions: StompApiSubsciptionContorller
  ) {
    this._stompApiSender = stompApiSender
    stompApiSubscriptions.addVmStatusSubscription(
      this.onCodeRunnerStateChanged.bind(this)
    )
    console.log(JSON.stringify(this.codeRunnerState.value))
  }

  public updateCodeRunner = () => {
    EnpointAcces.unauthorized
      .getCodeRunnerState()
      .then((data: CoderunnerState) => {
        console.log('updateCodeRunner: ' + JSON.stringify(data))
        this.codeRunnerState.value = data
      })
  }

  public setAwaiting() {
    console.log('set awaitng')
    console.log('set awaitng: ' + stringify(this.codeRunnerState))
    this.codeRunnerState.value.state = CodeRunnerStatus.AWAITING
    console.log('set awaitng after : ' + stringify(this.codeRunnerState.value))
  }
  public setNoneStatus() {
    this.codeRunnerState.value.state = CodeRunnerStatus.NONE
  }
}

