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
import { randomInt } from 'crypto'
import { PromotionDataMessage } from './types/ApiMessages/PromotionDataMessage'
export function makeServer({ environment = 'development' } = {}) {
  const exerciseData: ExerciseType[] = [
    {
      name: 'task13',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: Math.random() * 4 + 1,
      popularity: 312,
      id: 1,
      description: 'task13 description',
    },
    {
      name: 'task14',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: Math.random() * 4 + 1,
      popularity: 654,
      id: 14,
      description: 'task14 description',
    },
    {
      name: 'task15',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: Math.random() * 4 + 1,
      popularity: 111,
      id: 15,
      description: 'task15 description',
    },
    {
      name: 'task16',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: Math.random() * 4 + 1,
      popularity: 432,
      id: 16,
      description: 'task16 description',
    },
    {
      name: 'task17',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: Math.random() * 4 + 1,
      popularity: 765,
      id: 17,
      description: 'task17 description',
    },
    {
      name: 'task18',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: Math.random() * 4 + 1,
      popularity: 234,
      id: 18,
      description: 'task18 description',
    },
    {
      name: 'task19',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: Math.random() * 4 + 1,
      popularity: 654,
      id: 19,
      description: 'task19 description',
    },
    {
      name: 'task20',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: Math.random() * 4 + 1,
      popularity: 987,
      id: 20,
      description: 'task20 description',
    },
    {
      name: 'task21',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: Math.random() * 4 + 1,
      popularity: 222,
      id: 21,
      description: 'task21 description',
    },
    {
      name: 'task22',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: Math.random() * 4 + 1,
      popularity: 543,
      id: 22,
      description: 'task22 description',
    },
    {
      name: 'task23',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: Math.random() * 4 + 1,
      popularity: 876,
      id: 23,
      description: 'task23 description',
    },
    {
      name: 'task24',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: Math.random() * 4 + 1,
      popularity: 333,
      id: 24,
      description: 'task24 description',
    },
    {
      name: 'task25',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: Math.random() * 4 + 1,
      popularity: 543,
      id: 25,
      description: 'task25 description',
    },
    {
      name: 'task26',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: Math.random() * 4 + 1,
      popularity: 987,
      id: 26,
      description: 'task26 description',
    },
    {
      name: 'task27',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: Math.random() * 4 + 1,
      popularity: 234,
      id: 27,
      description: 'task27 description',
    },
    {
      name: 'task28',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: Math.random() * 4 + 1,
      popularity: 543,
      id: 28,
      description: 'task28 description',
    },
    {
      name: 'task29',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: Math.random() * 4 + 1,
      popularity: 876,
      id: 29,
      description: 'task29 description',
    },
    {
      name: 'task30',
      languages: [{ name: 'python' }, { name: 'javascript' }],
      difficulty: Math.random() * 4 + 1,
      popularity: 333,
      id: 30,
      description: 'task30 description',
    },
  ]

  const userDetails: UserDetailsMessage = {
    description: 'desc',
    emailSignature: '2***email.com',
  }

  const solutionData: SolutionsData = {
    maxExecutionTimeMs: 10000,
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
        executionTimeMs: 205,
        profilePic: '',
        solutionId: 2,
        codeRunner: CodeRunnerType.CPP_RUNNER,
      },
      {
        username: 'Username3',
        date: new Date(),
        executionTimeMs: 4004,
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

  const promotionDataMessage: PromotionDataMessage = {
    promotions: [
      {
        title: 'Exercise 1',
        description: 'Desc 11',
        languages: [CodeRunnerType.CPP_RUNNER, CodeRunnerType.JS_RUNNER],
        id: 0,
      },
      {
        title: 'Exercise 2',
        description: 'Desc 22222222222222222222222',
        languages: [CodeRunnerType.JS_RUNNER],
        id: 1,
      },
      {
        title: 'Exercise 3',
        description: `

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent auctor, libero consequat bibendum maximus, urna augue congue diam, ut aliquet turpis dui vel est. Interdum et malesuada fames ac ante ipsum primis in faucibus. Maecenas bibendum dignissim arcu nec sodales. Interdum et malesuada fames ac ante ipsum primis in faucibus. Aliquam pulvinar consequat lacus. In volutpat massa id pellentesque vehicula. Nam auctor, lectus sit amet cursus vulputate, risus ipsum mattis libero, dictum tempus lectus lectus condimentum leo. Integer neque orci, pulvinar non commodo sed, imperdiet non purus. Suspendisse hendrerit lobortis elit at luctus. Vivamus ut blandit risus. Sed maximus ligula nisi, ac pulvinar lacus porta vitae. Integer ipsum eros, accumsan sed eleifend vel, venenatis non arcu. Nam imperdiet finibus volutpat. Donec ornare, elit sed condimentum iaculis, erat sapien bibendum nibh, a tincidunt lectus tortor id neque. Suspendisse potenti. Duis scelerisque, nisl sed vehicula pharetra, ipsum leo eleifend purus, vitae posuere ipsum velit non ante.

In a elit a ipsum tristique ornare vel ac lorem. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aenean nec neque a sem ullamcorper condimentum ac sollicitudin eros. Curabitur posuere ultricies urna, a finibus urna imperdiet vel. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam mi nulla, consectetur pulvinar scelerisque id, porttitor sed libero. Maecenas ultrices accumsan augue, non sollicitudin ante cursus in. Nunc vehicula purus in consequat tempor. Etiam tempus imperdiet facilisis. Morbi accumsan faucibus nisl. In at fringilla nulla, sit amet elementum odio. Phasellus ut tellus id elit auctor vulputate quis eu nisl.

Integer quis eleifend neque, vitae gravida tortor. Nulla venenatis ipsum et semper facilisis. Curabitur pretium, libero quis condimentum condimentum, erat justo pretium nibh, et vulputate nunc orci eu mi. Suspendisse facilisis neque sit amet facilisis luctus. Nullam molestie sodales velit nec dictum. Sed ac libero suscipit, porta sem ut, porta felis. Aliquam erat volutpat. Vestibulum ut lectus velit. Interdum et malesuada fames ac ante ipsum primis in faucibus. Sed vel eros ut velit laoreet scelerisque non ac nisi. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent ut mauris fringilla, pellentesque purus ut, convallis tortor.

Etiam laoreet, enim sed consequat condimentum, leo lacus porta ligula, semper hendrerit dolor erat non lacus. Aenean auctor turpis a urna varius, nec vehicula neque malesuada. Nullam molestie ultricies nisi, non iaculis lacus convallis lacinia. Nullam scelerisque elit et vulputate pulvinar. Ut auctor eu sem vel aliquet. Morbi quis augue feugiat, tincidunt quam sit amet, dignissim orci. Etiam sagittis arcu at nibh ornare, id finibus ligula dictum. Donec ultricies augue non consectetur fermentum. Nunc in neque eget sapien cursus viverra. Duis scelerisque hendrerit lorem. Quisque sapien est, accumsan eu venenatis ac, sodales sed arcu. Morbi congue libero ac metus maximus, sit amet ultrices leo dignissim. Proin at sapien nulla. Nunc efficitur nulla mattis leo viverra dapibus.

Sed ac condimentum nisi, id semper eros. Donec mattis, libero sed viverra cursus, tellus nunc feugiat neque, nec molestie mi purus non nisl. Nulla sed ullamcorper nunc, commodo eleifend lectus. Curabitur accumsan sem et leo cursus fermentum. Interdum et malesuada fames ac ante ipsum primis in faucibus. Fusce pellentesque sit amet ex eget ultrices. Maecenas volutpat sapien quis pretium dictum. Integer condimentum tempor varius. Mauris luctus purus ac nulla convallis lobortis. Morbi congue ligula sollicitudin nibh vestibulum consequat.

Etiam pellentesque urna tellus, ut consectetur nisi molestie ut. Vestibulum ac lectus ac purus maximus finibus. Proin eget suscipit tellus. Sed auctor malesuada turpis ut efficitur. Praesent congue, tortor ac ullamcorper imperdiet, nibh odio vulputate magna, vitae elementum lacus magna in diam. Praesent accumsan nec velit non pretium. Mauris eu neque pretium, ornare tortor congue, aliquam nisl. Mauris at viverra tellus. In hac habitasse platea dictumst. Etiam at neque porta, lobortis enim vitae, accumsan velit. Vestibulum in malesuada mi. Nam elementum finibus elit, eget varius metus rutrum vitae. Nullam magna massa, semper in sodales in, euismod ac augue. Vestibulum in mauris eu nibh facilisis efficitur ac ac odio. Aenean id odio et justo aliquet congue at vel augue.

Ut odio tellus, vestibulum eget nulla nec, commodo lacinia orci. Integer fermentum, orci cursus eleifend faucibus, neque nisi posuere sem, tempus condimentum velit dui non leo. Curabitur malesuada eros nec elementum gravida. Curabitur a enim id sapien sollicitudin suscipit. Donec semper erat magna, sit amet scelerisque elit tempus in. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Etiam tempus nunc sit amet augue luctus, eu fermentum justo tempor. Fusce tempus vulputate nisi dapibus condimentum. Maecenas molestie sollicitudin arcu et tincidunt. Vestibulum non erat vel eros aliquet faucibus vel non felis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Curabitur a diam arcu. Vivamus in fermentum sem.

Morbi lorem ipsum, dapibus non rutrum non, auctor vel purus. Morbi sapien neque, porta eu dolor non, sodales tristique tellus. Pellentesque finibus est at pharetra viverra. Ut feugiat erat at tortor ornare, nec malesuada quam fermentum. Proin eget nisl magna. Mauris eu purus id lacus ornare venenatis. Morbi faucibus maximus sapien. In at orci mauris. Morbi iaculis vulputate condimentum.

Curabitur at leo in ante laoreet molestie. Aliquam in tincidunt ante, a dapibus magna. Ut sed varius metus, sed gravida dui. Vestibulum ut blandit ipsum. Suspendisse maximus turpis nec justo tincidunt, ac iaculis eros semper. Sed molestie nulla a condimentum tempus. Duis ullamcorper ultricies est. Vestibulum eleifend tempus justo, a faucibus tellus mattis a. Fusce vel felis id dolor sagittis feugiat sed et dui. In faucibus mi massa, ut cursus velit imperdiet tristique. Phasellus nibh tortor, tincidunt vel justo cursus, tempus venenatis neque. Fusce non nunc nulla. Fusce euismod lorem eu mattis malesuada. Donec rhoncus magna enim, vel tempor nisl lobortis eget.

Suspendisse at leo velit. Ut neque nibh, semper quis ligula a, sollicitudin congue metus. Nunc pulvinar facilisis bibendum. Nam non mattis nulla. Duis sit amet orci nisl. Fusce dolor diam, convallis finibus turpis in, finibus consectetur mauris. Phasellus cursus nisl elit, a interdum diam malesuada in. Vestibulum congue arcu purus, et ultrices sem ullamcorper quis. Sed bibendum nulla quam, id porttitor urna vulputate sit amet. Praesent elementum at tortor et consequat. Duis arcu velit, finibus eu dictum efficitur, eleifend ac purus. `,
        languages: [CodeRunnerType.CPP_RUNNER],
        id: 2,
      },
    ],
  }

  const exerciseListHandler = (schema: any, request: any) => {
    const req: ExerciseListRequestMessage = request.queryParams
    const start: number = (req.page - 1) * req.rowsPerPage
    const end: number =
      parseInt(String(start)) + parseInt(String(req.rowsPerPage))
    console.log('list: ' + start + ', ' + end)
    return exerciseData.slice(start, end)
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
  const promotionDataHandler = (schema: any, request: any) => {
    console.log('promotionDataHandler ' + JSON.stringify(request))

    return promotionDataMessage
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

      this.get(
        'http://localhost:8080/public/exercises/promotions',
        promotionDataHandler
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
