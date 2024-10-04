export enum messageType {
  ERROR = 'ERROR',
  INFO = 'INFO',
  WARNING = 'WARNING',
}

export default interface MessageNotification {
  type: messageType
  message: string
}
