export interface langaugeUsePart {
  name: string
  amount: number
}

export type LanguageUse = langaugeUsePart[]

export interface amountOfLatlyDonePart {
  date: Date
  amount: number
}

export type amountOfLatlyDone = amountOfLatlyDonePart[]

export default interface StatisticMessage {
  languageUse: LanguageUse
  amountOfLatelyDone: amountOfLatlyDone
}
