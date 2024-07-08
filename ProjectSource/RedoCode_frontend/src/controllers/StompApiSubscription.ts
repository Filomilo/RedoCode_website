import { ActivationState, Client, IMessage, StompSubscription, type IFrame } from '@stomp/stompjs'

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

  private setupSubscription() {
    this.stompSubscription = this.stompClient.subscribe(
      this.subscriptionLocation,
      (message: IMessage) => {
        console.log('received message from subscription: ' + this.subscriptionLocation)
        const messageObject: Object = JSON.parse(message.body)
        this.onSubscriptionMethod(messageObject)
      }
    )
  }

  activateSubscription() {
    this.setupSubscription()
  }

  deactivateSubscription() {
    this.stompSubscription.unsubscribe()
  }
}
