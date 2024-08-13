import { languageChoices } from '@/config/Data'
import CodeRunnerType from '@/types/CodeRunnerTypes'

namespace LangaugeSelection {
  export function getDropDownFromLanguages(
    languageChoicesSelection: CodeRunnerType[]
  ) {
    return languageChoices.filter(element =>
      languageChoicesSelection.includes(element.value)
    )
  }
}

export default LangaugeSelection
