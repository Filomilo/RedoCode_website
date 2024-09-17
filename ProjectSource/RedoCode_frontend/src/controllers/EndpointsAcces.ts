import AccountInfo from '@/types/ApiMesseages/AccountInfo'
import AuthenticationRequest from '@/types/ApiMesseages/Authentication/AuthenticationRequest'
import RegisterRequest from '@/types/ApiMesseages/Authentication/RegisterRequest'
import ExcerciseDataMessage from '@/types/ApiMesseages/ExcerciseDataMessage'
import ResultData from '@/types/ApiMesseages/ResultData'
import SolutionsData from '@/types/ApiMesseages/SolutionsData'
import CoderunnerState from '@/types/CodeRunnerState'
import CodeRunnerStatus from '@/types/CodeRunnerStatus'
import CodeRunnerType from '@/types/CodeRunnerTypes'
import ExerciseListRequestMessage from '@/types/ExerciseListRequestMessage'
import ExerciseSolviingState from '@/types/ExerciseSolviingState'
import axios from 'axios'
import { isArray } from 'chart.js/helpers'
import { stringify } from 'flatted'

namespace EndpointAcces {
  export namespace unauthorized {
    
  export async function register (email: string, nickname: string, pass: string):Promise<string>{
    const request: RegisterRequest = {
      nickname: nickname,
      password: pass,
      email: email,
    }
   const response= await axios.post('/public/auth/register', request);
        console.log('Response: ' + JSON.stringify(response))
        if (response.status == 200) {
          console.log("/public/auth/register succes" )
          return response.data.token;
        } else {
          throw "Couldn't register user"
        }
  }


    export async function login(email: string, password: string):Promise<string> {
      try {
        const request: AuthenticationRequest = {
          password: password,
          email: email,
        }
        const response= await axios.post('/public/auth/login', request);
        if(response.status!==200)
        {
          throw "Incorrect login details"
        }
        return response.data.token;

      } catch (error) {
        console.error('updateCodeRunner Error:', JSON.stringify(error))
        throw "Incorrect login details"
      }
    }

   

    export async function getExerciseData(
      exercsieId: number
    ): Promise<ExcerciseDataMessage> {
      const params = {
        id: exercsieId,
      }
      const response = await axios.get('/public/exercises/data', {
        params: params,
      })
      console.log('/public/exercises/data Response:', response)
      if (
        response === undefined ||
        response.data === '' ||
        response.headers['Content-Length'] == 0
      )
        throw 'no exercise data retrived '

      return response.data
    }

    export async function getListOfExercises(
      sortByInput: string,
      sortDirection: string,
      rowsPerPage: number,
      page: number
    ) {
      const sortby: string =
        sortByInput === undefined
          ? 'name'
          : isArray(sortByInput)
            ? sortByInput[0]
            : sortByInput

      const request: ExerciseListRequestMessage = {
        sortBy: sortby,
        rowsPerPage: rowsPerPage,
        page: page,
        sortDirection: sortDirection === 'desc',
      }
      console.log('Getting exercises')
      const response = await axios.get('/public/exercises/list', {
        params: request,
      })
      if (response === undefined) {
        console.error("couldn't retrieve excercise list from server")
        throw "couldn't retrieve excercise list from server"
      }
      console.log('Exercises respones: ' + JSON.stringify(response))
      return response.data
      // exerciseData.value = response.data
      // console.log('exerciseData.value: ' + JSON.stringify(exerciseData.value))
    }
    export async function getCodeRunnerState(
    ): Promise<CoderunnerState> {
      try {
        // if (token === '') throw 'token empty'
        // console.log('token: ' + token)
        const response = await axios.post('/public/coderunner/state')
        console.log('updateCodeRunner Response:', response)
        if (
          response === undefined ||
          response.data === '' ||
          response.headers['Content-Length'] == 0
        )
          throw 'no status codeRunenr'
        return response.data
      } catch (error) {
        console.log('updateCodeRunner Error:', error)
        return {
          codeRunnerType: CodeRunnerType.UNIDENTIFIED,
          state: CodeRunnerStatus.NONE,
        }
      }
    }
  }

  export namespace authorized {
    



    export async function getSolutionsData(
      exerciseid: number,
    ): Promise<SolutionsData> {
      console.log('attempitng /secure/exercises/solutions ')
      const params = {
        id: exerciseid,
      }
      const response = await axios.get('/secure/exercises/solutions', {
        params: params,
      })
      console.log('/secure/exercises/solutions Response:', response)
      if (
        response === undefined ||
        response.data === '' ||
        response.headers['Content-Length'] == 0
      )
        throw 'no solutions data retrived '
      console.log(
        ' /secure/exercises/solutions data: ' + stringify(response.data)
      )
      return response.data
    }

    export async function getSolutionsCodesData(
      solutionId: number
    ): Promise<string> {
      console.log('attempitng /secure/exercises/solutionsCodes ')
      const params = {
        id: solutionId,
      }
      const response = await axios.get('/secure/exercises/solutionsCodes', {
        params: params,
      })
      console.log('/secure/exercises/solutionsCodes Response:', response)
      if (
        response === undefined ||
        response.data === '' ||
        response.headers['Content-Length'] == 0
      )
        throw 'no solution code data retrived '

      return response.data
    }

    export async function postComment(
      comment: string,
      exerciseId: number
    ): Promise<number> {
      try {
        const data = {
          id: exerciseId,
          comment: comment,
        }
        const response = await axios.post('/secure/exercises/comment', data)

        console.log('response postComment: ' + JSON.stringify(response))
        return response.status
      } catch (ex) {
        return -1
      }
    }

    export async function getResultData(
      exerciseid: number
    ): Promise<ResultData> {
      console.log('attempitng /secure/exercises/solutions ')
      const params = {
        id: exerciseid,
      }
      try {
        const response = await axios.get('/secure/exercises/results', {
          params: params,
        })
        if (response.status == 404) throw "couldn't get result data"
        console.log('/public/exercises/solutions Response:', response)
        if (
          response === undefined ||
          response.data === '' ||
          response.headers['Content-Length'] == 0
        )
          throw 'no solutions data retrived '
        console.log(
          '/public/exercises/solutions Response data:',
          stringify(response.data)
        )
        return response.data
      } catch (ex) {
        throw "couldn't get result info"
      }
    }

    export async function postRate(
      selectedRating: number,
      exercsieID: number,
    ) {
      const data = {
        id: exercsieID,
        rate: selectedRating,
      }
      const response = await axios.post('/secure/exercises/rate', data)

      console.log('response postComment: ' + JSON.stringify(response))

      return response.status
    }

    export async function getExerciseSolvingState(
      id: number
    ): Promise<ExerciseSolviingState> {
      const data = {
        id: id,
      }
      console.log(JSON.stringify(data))
      const response = await axios.get(
        '/secure/exercises/ExerciseSolvingState',
        {
          params: data,
        }
      )

      console.log(
        'getExerciseSolvingState: response.data ' +
          JSON.stringify(response.data)
      )
      return response.data
    }

    export async function getUserInfo(): Promise<AccountInfo>
    {

      console.log("getUserInfo")
      const response = await axios.get(
        '/secure/user/info'
      )

      console.log(
        'getUserInfo: response.data ' +
          JSON.stringify(response.data)
      )
      return response.data
    }




  }
}
export default EndpointAcces
