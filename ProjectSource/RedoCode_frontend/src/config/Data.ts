import type { programingLanguageChoice } from '../types/ProgramingLanguageChoice'
import type CodeResult from '@/types/CodeResultsType'

export const languageChoices: programingLanguageChoice[] = [ 'cpp',  'js' ]

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
