import { useActiveUserStore } from '@/stores/ActiveUserStore'
import AccountInfo from '@/types/ApiMessages/AccountInfo'
import AuthenticationRequest from '@/types/ApiMessages/Authentication/AuthenticationRequest'
import RegisterRequest from '@/types/ApiMessages/Authentication/RegisterRequest'
import ExerciseDataMessage from '@/types/ApiMessages/ExerciseDataMessage'
import ResultData from '@/types/ApiMessages/ResultData'
import SolutionsData from '@/types/ApiMessages/SolutionsData'
import StatisticMessage from '@/types/ApiMessages/StatisticMessage'
import UserDetailsMessage from '@/types/ApiMessages/UserDetailsMessage'
import CoderunnerState from '@/types/CodeRunnerState'
import CodeRunnerStatus from '@/types/CodeRunnerStatus'
import CodeRunnerType from '@/types/CodeRunnerTypes'
import ExerciseListRequestMessage from '@/types/ExerciseListRequestMessage'
import ExerciseSolvingState from '@/types/ExerciseSolvingState'
import axios from 'axios'
import { isArray } from 'chart.js/helpers'
import { stringify } from 'flatted'

namespace EndpointAccess {
  export namespace unauthorized {

    export async function register(
      email: string,
      nickname: string,
      pass: string
    ): Promise<string> {
      try{
      const request: RegisterRequest = {
        nickname: nickname,
        password: pass,
        email: email,
      }
      const response = await axios.post('/public/auth/register', request)
      console.log('Response: ' + JSON.stringify(response))
      if (response.status == 200) {
        console.log('/public/auth/register success')
        return response.data.token
      } else {
        throw "Couldn't register user"
      }

    }
    catch({ response }: any)
    {
      throw response.data;
    }
    }

    export async function login(
      email: string,
      password: string
    ): Promise<string> {
      try {
        const request: AuthenticationRequest = {
          password: password,
          email: email,
        }
        const response = await axios.post('/public/auth/login', request)
        if (response.status !== 200) {
          throw 'Incorrect login details'
        }
        return response.data.token
      }     
      catch({ response }: any)
      {
        throw response.data;
      }
    }

    export async function getExerciseData(
      exerciseId: number
    ): Promise<ExerciseDataMessage> {
      try{
      const params = {
        id: exerciseId,
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
        throw 'no exercise data retrieved '

      return response.data
    }
    catch({ response }: any)
    {
      throw response.data;
    }
    }

    export async function getListOfExercises(
      sortByInput: string,
      sortDirection: string,
      rowsPerPage: number,
      page: number
    ) {
      try{
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
        console.error("couldn't retrieve exercise list from server")
        throw "couldn't retrieve exercise list from server"
      }
      console.log('Exercises response: ' + JSON.stringify(response))
      return response.data
    }
    catch({ response }: any)
    {
      throw response.data;
    }
    }
    export async function getCodeRunnerState(): Promise<CoderunnerState> {
      try {
        const activeUserStore = useActiveUserStore()

        if (!activeUserStore.IsToken) throw 'token empty'
        // console.log('token: ' + token)
        const response = await axios.post('/public/coderunner/state')
        console.log('updateCodeRunner Response:', response)
        if (
          response === undefined ||
          response.data === '' ||
          response.headers['Content-Length'] == 0
        )
          throw 'no status codeRunner'
        return response.data
      }
      catch({ response }: any)
      {
        throw response.data;
      }
    }
  }

  export namespace authorized {
    export async function getSolutionsData(
      exercised: number
    ): Promise<SolutionsData> {
      try{
      console.log('attempting /secure/exercises/solutions ')
      const params = {
        id: exercised,
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
        throw 'no solutions data retrieved '
      console.log(
        ' /secure/exercises/solutions data: ' + stringify(response.data)
      )
      return response.data
    }
    catch({ response }: any)
    {
      throw response.data;
    }
    }

    export async function getSolutionsCodesData(
      solutionId: number
    ): Promise<string> {
      console.log('attempting /secure/exercises/solutionsCodes ')
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
        throw 'no solution code data retrieved '

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
      }
      catch({ response }: any)
      {
        throw response.data;
      }
    }

    export async function postAccountPic(
      image: string
    ): Promise<string> {
      try {
        const data = {
          image: image
        }
        console.log("postAccountPic: "+JSON.stringify(data))
        const response = await axios.post('/secure/user/profilePicture', data)

        console.log('response postAccountPic: ' + JSON.stringify(response))
        if(response.status!=200 && response.status!=201)
          throw response.data;
        return "successfully changed image"
      }
      catch({ response }: any)
      {
        if(response.data)
        throw response.data;
        else
        throw response
      }
    }
    export async function postChangePassword(password: string, newPassword: string): Promise<string> {
      try {
        const data = {
          password: password,
          newPassword: newPassword
        }
        console.log("postChangePassword: "+JSON.stringify(data))
        const response = await axios.post('/secure/user/changePassword', data)
        console.log('response postChangePassword: ' + JSON.stringify(response))
        if(response.status!=200 && response.status!=201)
          throw response.data;
        return "successfully removed password"
      }
      catch({ response }: any)
      {
        throw response.data;
      }
    }

    export async function postRemoveAccount(pass: string) {

        const data = {
          password: pass
        }
        try{
        console.log("postRemoveAccount: "+JSON.stringify(data))
        const response = await axios.post('/secure/user/remove', data)
        console.log('response postRemoveAccount: ' + JSON.stringify(response))
        if(response.status!==200 && response.status!==201 )
          throw response.data;
        return "successfully changed password"
      }
      catch({ response }: any)
      {
        throw response.data;
      }
      }

    export async function getResultData(
      exerciseId: number
    ): Promise<ResultData> {
      console.log('attempting /secure/exercises/solutions ')
      const params = {
        id: exerciseId,
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
          throw 'no solutions data retrieved '
        console.log(
          '/public/exercises/solutions Response data:',
          stringify(response.data)
        )
        return response.data
      }
      catch({ response }: any)
      {
        throw response.data;
      }
    }

    export async function getUserDetails(
    ): Promise<UserDetailsMessage> {
      try{
      console.log('attempting /secure/user/details ')
        const response = await axios.get('/secure/user/details')
        if (response.status !== 200 && response.status !== 201) throw "couldn't get detail data"
        console.log('/secure/user/details Response:', response)
        if (
          response === undefined ||
          response.data === '' ||
          response.headers['Content-Length'] == 0
        )
          throw 'no details data retrieved '
        console.log(
          '/secure/user/details Response data:',
          stringify(response.data)
        )
        return response.data
      }catch({ response }: any)
      {
        throw response.data;
      }
    }


    export async function postRate(selectedRating: number, exerciseID: number) {
     try{
      const data = {
        id: exerciseID,
        rate: selectedRating,
      }
      const response = await axios.post('/secure/exercises/rate', data)

      console.log('response postComment: ' + JSON.stringify(response))

      return response.status
    }
      catch({ response }: any)
      {
        throw response.data;
      }
    }


    export async function postChangeDescription(desc: string) {
      try{
      const data = {
        description: desc,
      }
      const response = await axios.post('/secure/user/description', data)

      console.log('response /secure/user/description: ' + JSON.stringify(response))
      if(response.status!==200 && response.status!==201)
        throw "failed to change description"
      return response.status
    }
    catch({ response }: any)
    {
      throw response.data;
    }
    }


    export async function getExerciseSolvingState(
      id: number
    ): Promise<ExerciseSolvingState> {
      try{
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
    catch({ response }: any)
    {
      throw response.data;
    }
    }

    export async function getUserInfo(): Promise<AccountInfo> {
      try{
      console.log('getUserInfo')
      const response = await axios.get('/secure/user/info')

      console.log('getUserInfo: response.data ' + JSON.stringify(response.data))
      return response.data
    }
    catch({ response }: any)
    {
      throw response.data;
    }
    }

    export async function getUserStatisticData(): Promise<StatisticMessage> {
      try{
      console.log('getUserStatisticData')
      const response = await axios.get('/secure/user/stats')

      console.log(
        'getUserStatisticData: response.data ' + JSON.stringify(response.data)
      )
      return response.data
    }
    catch({ response }: any)
    {
      throw response.data;
    }
    }







  }
}
export default EndpointAccess
