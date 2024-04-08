import { createServer, Model } from 'miragejs'
import type ExerciseType from './types/ExerciseType'
import type ExerciseListRequestMessage from './types/ExerciseListRequestMessage'
export function makeServer({ environment = 'development' } = {}) {
  const exerciseData: ExerciseType[] = [
    {
      name: 'task1',
      language: ['c++', 'js'],
      difficulty: 'hard',
      popularity: 222,
      id: 1,
      description: 'task1 description'
    },
    {
      name: 'task2',
      language: ['any'],
      difficulty: 'hard',
      popularity: 222122,
      id: 2,
      description: 'task2 description'
    },
    {
      name: 'task3',
      language: ['c++', 'java'],
      difficulty: 'easy',
      popularity: 23,
      id: 3,
      description: 'task3 description'
    },
    {
      name: 'task4',
      language: ['python'],
      difficulty: 'medium',
      popularity: 320,
      id: 4,
      description: 'task4 description'
    },
    {
      name: 'task5',
      language: ['javascript'],
      difficulty: 'medium',
      popularity: 543,
      id: 5,
      description: 'task5 description'
    },
    {
      name: 'task6',
      language: ['java'],
      difficulty: 'easy',
      popularity: 421,
      id: 6,
      description: 'task6 description'
    },
    {
      name: 'task7',
      language: ['python', 'javascript'],
      difficulty: 'hard',
      popularity: 654,
      id: 7,
      description: 'task7 description'
    },
    {
      name: 'task8',
      language: ['c++', 'python'],
      difficulty: 'medium',
      popularity: 342,
      id: 8,
      description: 'task8 description'
    },
    {
      name: 'task9',
      language: ['java', 'javascript'],
      difficulty: 'easy',
      popularity: 233,
      id: 9,
      description: 'task9 description'
    },
    {
      name: 'task10',
      language: ['c++'],
      difficulty: 'medium',
      popularity: 122,
      id: 10,
      description: 'task10 description'
    },
    {
      name: 'task11',
      language: ['python'],
      difficulty: 'hard',
      popularity: 423,
      id: 11,
      description: 'task11 description'
    },
    {
      name: 'task12',
      language: ['javascript'],
      difficulty: 'easy',
      popularity: 555,
      id: 12,
      description: 'task12 description'
    },
    {
      name: 'task13',
      language: ['java'],
      difficulty: 'medium',
      popularity: 312,
      id: 13,
      description: 'task13 description'
    },
    {
      name: 'task14',
      language: ['c++', 'javascript'],
      difficulty: 'hard',
      popularity: 654,
      id: 14,
      description: 'task14 description'
    },
    {
      name: 'task15',
      language: ['python', 'java'],
      difficulty: 'easy',
      popularity: 111,
      id: 15,
      description: 'task15 description'
    },
    {
      name: 'task16',
      language: ['c++'],
      difficulty: 'medium',
      popularity: 432,
      id: 16,
      description: 'task16 description'
    },
    {
      name: 'task17',
      language: ['python', 'javascript'],
      difficulty: 'hard',
      popularity: 765,
      id: 17,
      description: 'task17 description'
    },
    {
      name: 'task18',
      language: ['c++', 'java'],
      difficulty: 'easy',
      popularity: 234,
      id: 18,
      description: 'task18 description'
    },
    {
      name: 'task19',
      language: ['javascript'],
      difficulty: 'medium',
      popularity: 654,
      id: 19,
      description: 'task19 description'
    },
    {
      name: 'task20',
      language: ['python'],
      difficulty: 'hard',
      popularity: 987,
      id: 20,
      description: 'task20 description'
    },
    {
      name: 'task21',
      language: ['java'],
      difficulty: 'easy',
      popularity: 222,
      id: 21,
      description: 'task21 description'
    },
    {
      name: 'task22',
      language: ['c++', 'python'],
      difficulty: 'medium',
      popularity: 543,
      id: 22,
      description: 'task22 description'
    },
    {
      name: 'task23',
      language: ['javascript', 'java'],
      difficulty: 'hard',
      popularity: 876,
      id: 23,
      description: 'task23 description'
    },
    {
      name: 'task24',
      language: ['python', 'c++'],
      difficulty: 'easy',
      popularity: 333,
      id: 24,
      description: 'task24 description'
    },
    {
      name: 'task25',
      language: ['java'],
      difficulty: 'medium',
      popularity: 543,
      id: 25,
      description: 'task25 description'
    },
    {
      name: 'task26',
      language: ['c++', 'javascript'],
      difficulty: 'hard',
      popularity: 987,
      id: 26,
      description: 'task26 description'
    },
    {
      name: 'task27',
      language: ['python', 'java'],
      difficulty: 'easy',
      popularity: 234,
      id: 27,
      description: 'task27 description'
    },
    {
      name: 'task28',
      language: ['c++'],
      difficulty: 'medium',
      popularity: 543,
      id: 28,
      description: 'task28 description'
    },
    {
      name: 'task29',
      language: ['python', 'javascript'],
      difficulty: 'hard',
      popularity: 876,
      id: 29,
      description: 'task29 description'
    },
    {
      name: 'task30',
      language: ['c++', 'java'],
      difficulty: 'easy',
      popularity: 333,
      id: 30,
      description: 'task30 description'
    }
  ]

  const exerciseListHandler = (schema: any, request: any) => {
    const req: ExerciseListRequestMessage = request.queryParams
    const start: number = (req.page - 1) * req.rowsPerPage
    const end: number = parseInt(String(start)) + parseInt(String(req.rowsPerPage))
    console.log('list: ' + start + ', ' + end)
    return exerciseData.slice(start, end)
  }

  const server = createServer({
    environment,

    routes() {
      // this.namespace = "http://localhost:9090"

      this.get('http://localhost:8080/exercises', exerciseListHandler)
    }
  })

  return server
}
