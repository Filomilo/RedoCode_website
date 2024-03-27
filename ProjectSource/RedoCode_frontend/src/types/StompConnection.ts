import type { IFrame } from '@stomp/stompjs'

export default interface OnConnectFunc {
  (frame: IFrame): void
}
