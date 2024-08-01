import type { programingLanguageChoice } from '../types/ProgramingLanguageChoice'
import type CodeResult from '@/types/CodeResultsType'
import CodeRunnerType, {
  languageDropDownType,
  LanguageName,
} from '@/types/CodeRunnerTypes'
export const languageChoices: languageDropDownType[] = [
  {
    label: 'cpp',
    value: CodeRunnerType.CPP_RUNNER,
  },
  {
    label: 'js',
    value: CodeRunnerType.JS_RUNNER,
  },
]

export const basicResultTemplate: CodeResult[] = [
  {
    Console_output: '',
    Error_output: '',
    Solution_type: 'none',
    correct_solution: null,
    achived_solution: null,
    error: null,
  },
]

export const EditorLanguagesMap: any = {
  CPP_RUNNER: 'cpp',
  JS_RUNNER: 'javascript',
  UNIDENTIFIED: '',
}
export const CodeRunnerMap: any = {
  cpp: 'CPP_RUNNER',
  js: 'JS_RUNNER',
}

type CodeRunnerStringMap = { [key in CodeRunnerType]: LanguageName }
type StrinCodeRunnergMap = { [key in LanguageName]: CodeRunnerType }

export const CodeRunnerTypeToLangName: CodeRunnerStringMap = {
  [CodeRunnerType.CPP_RUNNER]: 'cpp',
  [CodeRunnerType.JS_RUNNER]: 'js',
  [CodeRunnerType.UNIDENTIFIED]: 'none',
}

export const LangNameToCodeRunnerType: StrinCodeRunnergMap = {
  ['cpp']: CodeRunnerType.CPP_RUNNER,
  ['js']: CodeRunnerType.CPP_RUNNER,
  ['none']: CodeRunnerType.UNIDENTIFIED,
}
