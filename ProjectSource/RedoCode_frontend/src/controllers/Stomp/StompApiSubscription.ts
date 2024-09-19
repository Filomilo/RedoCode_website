import {
  ActivationState,
  Client,
  IMessage,
  StompSubscription,
  type IFrame,
} from '@stomp/stompjs'

export default class StompApiSubscription {
  private stompClient: Client
  private onSubscriptionMethod: (object: Object) => void
  private subscriptionLocation: string
  private stompSubscription!: StompSubscription

  constructor(
    stompClient: Client,
    subscriptionLocation: string,
    onSubcriptionMethod: (object: Object) => void
  ) {
    this.stompClient = stompClient
    this.onSubscriptionMethod = onSubcriptionMethod
    this.subscriptionLocation = subscriptionLocation
  }

  private async setupSubscription() {
    this.stompSubscription = this.stompClient.subscribe(
      this.subscriptionLocation,
     async (message: IMessage) => {
        console.log(
          'received message from subscription: ' + this.subscriptionLocation
        )
        const messageObject: Object = JSON.parse(message.body)
      await this.onSubscriptionMethod(messageObject)
      }
    )
  }

  async activateSubscription() {
   await this.setupSubscription()
  }

  deactivateSubscription() {
    this.stompSubscription.unsubscribe()
  }
}
