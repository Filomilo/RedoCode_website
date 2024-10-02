import { createServer } from 'miragejs'
import type ExerciseType from './types/ExerciseType'
import type ExerciseListRequestMessage from './types/ExerciseListRequestMessage'
import ExerciseData from './types/ApiMessages/ExerciseDataMessage'
import CodeRunnerType from './types/CodeRunnerTypes'
import SolutionsData from './types/ApiMessages/SolutionsData'
import ResultData from './types/ApiMessages/ResultData'
import StatisticMessage from './types/ApiMessages/StatisticMessage'
import { Response } from 'miragejs'
import UserDetailsMessage from './types/ApiMessages/UserDetailsMessage'
import { uniqueNamesGenerator, adjectives, colors, animals, Config } from 'unique-names-generator';
import ExerciseListMessage from './types/ApiMessages/ExerciseListMessage'


const config: Config = {
  dictionaries: [adjectives, colors, animals],
  separator: '-',
  seed: 120498,
};



export function makeServer({ environment = 'development' } = {}) {
  const exerciseData: ExerciseType[] =[];

  for (let index = 0; index < 1000; index++) {
    exerciseData.push(
      {
        name: uniqueNamesGenerator({ dictionaries: [adjectives, colors, animals] })+'_'+(index+1),
        languages: [{ name: 'python' }, { name: 'javascript' }],
        difficulty: 4,
        popularity: 333,
        id: 30,
        description: 'task30 description',
        alreadyDone: Math.random() < 0.5
      }
    )
    
  }

  const userDetails: UserDetailsMessage = {
    description: 'desc',
    emailSignature: '2***email.com',
  }

  const solutionData: SolutionsData = {
    maxExecutionTimeMs: 10,
    solutionList: [
      {
        username: 'Username1 ',
        date: new Date(),
        executionTimeMs: 10,
        profilePic: '',
        solutionId: 1,
        codeRunner: CodeRunnerType.CPP_RUNNER,
      },
      {
        username: 'Username2',
        date: new Date(),
        executionTimeMs: 25,
        profilePic: '',
        solutionId: 2,
        codeRunner: CodeRunnerType.CPP_RUNNER,
      },
      {
        username: 'Username3',
        date: new Date(),
        executionTimeMs: 44,
        profilePic: '',
        solutionId: 3,
        codeRunner: CodeRunnerType.CPP_RUNNER,
      },
    ],
    title: 'Exercise Title',
    desc: 'Description of exercise',
    comments: [
      {
        nickname: 'example user',
        profilePicture: '',
        comment: 'Great exercise',
      },
    ],
  }

  const solutionsCodes = [
    {
      id: 1,
      code: `111111111\n11111\n`,
    },
    {
      id: 2,
      code: `2222\n222222\n2222`,
    },
    {
      id: 3,
      code: `33333\n333\n33`,
    },
  ]

  const activeExerciseData: ExerciseData = {
    inputType: 'SINGLE_INTEGER',
    id: 1,
    title: 'Fibonacci',
    desc: 'Create a fibonacci sequence with amount of numbers provide to function \n \n \n for example for \n 4 \n the result should be \n [0,1,1,2]  ',
    outputType: 'ARRAY_OF_INTEGERS',
    availableCodeRunners: [CodeRunnerType.JS_RUNNER, CodeRunnerType.CPP_RUNNER],
    tests: [
      {
        input: 1,
        output: null,
        expectedOutput: [0],
        errorOutput: '',
        consoleOutput: '',
        isSolved: null,
        uuid: '',
        executionTime: 50,
      },
      {
        input: 2,
        output: null,
        expectedOutput: [0, 1],
        errorOutput: '',
        consoleOutput: '',
        isSolved: null,
        uuid: '',
        executionTime: 50,
      },
      {
        input: 3,
        output: null,
        expectedOutput: [0, 1, 1],
        errorOutput: '',
        consoleOutput: '',
        isSolved: null,
        uuid: '',
        executionTime: 50,
      },
      {
        input: 4,
        output: null,
        expectedOutput: [0, 1, 1, 2],
        errorOutput: '',
        consoleOutput: '',
        isSolved: null,
        uuid: '',
        executionTime: 50,
      },
    ],
    automaticTests: [],
    startingFunction: 'function result(val){\n\n}',
  }

  const resultData: ResultData = {
    executionTimeMs: 250,
    maxExecutionTimeMs: 1000,
    betterThanPercent: 66,
    SolutionRanking: 5,
  }

  const statsData: StatisticMessage = {
    languageUse: [
      {
        name: 'java',
        amount: 4,
      },
      {
        name: 'cpp',
        amount: 9,
      },
    ],
    amountOfLatelyDone: [
      {
        date: new Date(2024, 4, 13),
        amount: 1,
      },
      {
        date: new Date(2024, 4, 14),
        amount: 2,
      },
      {
        date: new Date(2024, 4, 15),
        amount: 3,
      },
      {
        date: new Date(2024, 4, 16),
        amount: 4,
      },
      {
        date: new Date(2024, 4, 17),
        amount: 5,
      },
      {
        date: new Date(2024, 4, 18),
        amount: 6,
      },
      {
        date: new Date(2024, 4, 19),
        amount: 7,
      },
    ],
  }

  const exerciseListHandler = (schema: any, request: any):ExerciseListMessage => {
    const req: ExerciseListRequestMessage = request.queryParams
    const start: number = (req.page - 1) * req.rowsPerPage
    const end: number =
      parseInt(String(start)) + parseInt(String(req.rowsPerPage))
    console.log("exerciseListHandler: "+JSON.stringify(req))
      console.log('list: ' + start + ', ' + end)


    const exercisesSearched: ExerciseType[]=exerciseData.filter
    (x=>x.name.startsWith(req.searchField));


    const exercisesSorted: ExerciseType[]=exercisesSearched.sort((a, b) => {
      switch (req.sortBy) {
        case "name":
          return a.name.localeCompare(b.name); 
        case "difficulty":
          return a.difficulty - b.difficulty;    
        default:
          return 0;
      }
    });

    const exerciseDirections: ExerciseType[]=req.sortBy?exercisesSorted:exercisesSorted.reverse();

    const mess:ExerciseListMessage={
      fullAmount: exerciseDirections.length,
      list: exerciseDirections.slice(start, end)
    }
    return mess
  }

  const exerciseDataHandler = () => {
    return activeExerciseData
  }

  const solutionsDataHandler = (schema: any, request: any) => {
    console.log('solutionsDataHandler ' + JSON.stringify(request))

    return solutionData
  }

  const userStatsDataHandler = (schema: any, request: any) => {
    console.log('solutionsDataHandler ' + JSON.stringify(request))

    return statsData
  }
  const userDetailsDataHandler = (schema: any, request: any) => {
    console.log('userDetailsDataHandler ' + JSON.stringify(request))

    return userDetails
  }

  const resultDataHandler = (schema: any, request: any) => {
    console.log('resultDataHandler ' + JSON.stringify(request))

    return resultData
  }

  const solutionsCodesDataHandler = (schema: any, request: any) => {
    console.log('solutionsCodesDataHandler ' + JSON.stringify(request))
    console.log(
      'request.queryParams.id ' + JSON.stringify(request.queryParams.id)
    )
    const found = solutionsCodes.find(x => x.id == request.queryParams.id)
    console.log('found ' + JSON.stringify(found))

    return found === undefined ? '' : found.code
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

      this.get(
        'http://localhost:8080/secure/exercises/solutions',
        solutionsDataHandler
      )

      this.get('http://localhost:8080/secure/user/stats', userStatsDataHandler)
      this.get(
        'http://localhost:8080/secure/user/details',
        userDetailsDataHandler
      )

      this.get(
        'http://localhost:8080/secure/exercises/solutionsCodes',
        solutionsCodesDataHandler
      )
      this.get(
        'http://localhost:8080/secure/exercises/results',
        resultDataHandler
      )
      this.post(
        'http://localhost:8080/secure/exercises/comment',
        (schema, request) => {
          const attrs = JSON.parse(request.requestBody)

          return {
            status: 'success',
            message: 'comment posted successfully!',
            submittedData: attrs,
          }
        }
      )
      this.post(
        'http://localhost:8080/secure/user/changePassword',
        (schema, request) => {
          const attrs = JSON.parse(request.requestBody)
          console.log(
            'http://localhost:8080/secure/user/changePassword: ' +
              JSON.stringify(attrs)
          )
          return {
            status: 'success',
            message: 'description changed successfully!',
            submittedData: attrs,
          }
        }
      )

      this.post(
        'http://localhost:8080/secure/exercises/rate',
        (schema, request) => {
          const attrs = JSON.parse(request.requestBody)

          return {
            status: 'success',
            message: 'rating saved!',
            submittedData: attrs,
          }
        }
      )
      this.post(
        'http://localhost:8080/secure/user/description',
        (schema, request) => {
          const attrs = JSON.parse(request.requestBody)
          userDetails.description = (attrs as any).description
          return {
            status: 'success',
            message: 'rating saved!',
            submittedData: attrs,
          }
        }
      )

      this.post(
        'http://localhost:8080/secure/user/profilePicture',
        (schema, request) => {
          const attrs = JSON.parse(request.requestBody)
          console.log('/secure/user/profilePicture: ' + JSON.stringify(request))
          return {
            status: 'success',
            message: 'rating saved!',
            submittedData: attrs,
          }
        }
      )

      this.post(
        'http://localhost:8080/secure/user/remove',
        (schema, request) => {
          const attrs = JSON.parse(request.requestBody)
          console.log('/secure/user/remove: ' + JSON.stringify(request))

          return {
            status: 'ok',
            message: 'rating saved!',
            submittedData: attrs,
          }
        }
      )
    },
  })

  return server
}
