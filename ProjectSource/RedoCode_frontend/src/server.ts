import { createServer, Model } from 'miragejs'
import type ExerciseType from './types/ExerciseType'
import type ExerciseListRequestMessage from './types/ExerciseListRequestMessage'
import ExerciseData from './types/ExerciseData'
import CodeRunnerType from './types/CodeRunnerTypes'
export function makeServer({ environment = 'development' } = {}) {
  const exerciseData: ExerciseType[] = [
    {
      name: 'task13',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: 'medium',
      popularity: 312,
      id: 1,
      description: 'task13 description',
    },
    {
      name: 'task14',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: 'hard',
      popularity: 654,
      id: 14,
      description: 'task14 description',
    },
    {
      name: 'task15',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: 'easy',
      popularity: 111,
      id: 15,
      description: 'task15 description',
    },
    {
      name: 'task16',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: 'medium',
      popularity: 432,
      id: 16,
      description: 'task16 description',
    },
    {
      name: 'task17',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: 'hard',
      popularity: 765,
      id: 17,
      description: 'task17 description',
    },
    {
      name: 'task18',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: 'easy',
      popularity: 234,
      id: 18,
      description: 'task18 description',
    },
    {
      name: 'task19',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: 'medium',
      popularity: 654,
      id: 19,
      description: 'task19 description',
    },
    {
      name: 'task20',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: 'hard',
      popularity: 987,
      id: 20,
      description: 'task20 description',
    },
    {
      name: 'task21',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: 'easy',
      popularity: 222,
      id: 21,
      description: 'task21 description',
    },
    {
      name: 'task22',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: 'medium',
      popularity: 543,
      id: 22,
      description: 'task22 description',
    },
    {
      name: 'task23',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: 'hard',
      popularity: 876,
      id: 23,
      description: 'task23 description',
    },
    {
      name: 'task24',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: 'easy',
      popularity: 333,
      id: 24,
      description: 'task24 description',
    },
    {
      name: 'task25',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: 'medium',
      popularity: 543,
      id: 25,
      description: 'task25 description',
    },
    {
      name: 'task26',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: 'hard',
      popularity: 987,
      id: 26,
      description: 'task26 description',
    },
    {
      name: 'task27',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: 'easy',
      popularity: 234,
      id: 27,
      description: 'task27 description',
    },
    {
      name: 'task28',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: 'medium',
      popularity: 543,
      id: 28,
      description: 'task28 description',
    },
    {
      name: 'task29',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: 'hard',
      popularity: 876,
      id: 29,
      description: 'task29 description',
    },
    {
      name: 'task30',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: 'easy',
      popularity: 333,
      id: 30,
      description: 'task30 description',
    },
  ]

  const activeExerciseData: ExerciseData = {
    inputType: 'int',
    id: 1,
    title: 'Fibonacci',
    description:
      'Create a fibonacci sequance with amount of numbers provide to function \n \n \n for example for \n 4 \n the result should be \n [0,1,1,2]  ',
    outputType: 'int[]',
    availbleCodeRunners: [CodeRunnerType.JS_RUNNER],
    tests: [
      {
        input: 1,
        output: null,
        expectedOutput: [0],
        errorOutput: '',
        consoleOutput: '',
        isSolved: null,
      },
      {
        input: 2,
        output: null,
        expectedOutput: [0, 1],
        errorOutput: '',
        consoleOutput: '',
        isSolved: null,
      },
      {
        input: 3,
        output: null,
        expectedOutput: [0, 1, 1],
        errorOutput: '',
        consoleOutput: '',
        isSolved: null,
      },
      {
        input: 4,
        output: null,
        expectedOutput: [0, 1, 1, 2],
        errorOutput: '',
        consoleOutput: '',
        isSolved: null,
      },
    ],
    automaticTests: [],
    startingFunction: 'function result(val){\n\n}',
  }

  const exerciseListHandler = (schema: any, request: any) => {
    const req: ExerciseListRequestMessage = request.queryParams
    const start: number = (req.page - 1) * req.rowsPerPage
    const end: number =
      parseInt(String(start)) + parseInt(String(req.rowsPerPage))
    console.log('list: ' + start + ', ' + end)
    return exerciseData.slice(start, end)
  }

  const exerciseDataHandler = (schema: any, request: any) => {
    return activeExerciseData
  }

  const server = createServer({
    environment,

    routes() {
      // this.namespace = "http://localhost:9090"

      this.get(
        'http://localhost:8080/public/exercises/list',
        exerciseListHandler
      )

      this.get(
        'http://localhost:8080/public/exercises/data',
        exerciseDataHandler
      )
    },
  })

  return server
}
