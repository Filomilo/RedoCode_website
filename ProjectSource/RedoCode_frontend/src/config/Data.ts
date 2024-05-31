import type { programingLanguageChoice } from '../types/ProgramingLanguageChoice'
import type CodeResult from '@/types/CodeResultsType'

export const languageChoices: string[] = ['cpp', 'js']

export const basicResultTemplate: CodeResult[] = [
  {
    Console_output: '',
    Error_output: '',
    Solution_type: 'none',
    correct_solution: null,
    achived_solution: null,
    error: null
  }
]

export const EditorLanguagesMap: any = {
  CPP_RUNNER: 'cpp',
  JS_RUNNER: 'javascript',
  UNIDENTIFIED: ''
}