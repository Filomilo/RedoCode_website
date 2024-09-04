import ExcerciseDataMessage from '@/types/ApiMesseages/ExcerciseDataMessage'
import SolutionsData from '@/types/ApiMesseages/SolutionsData'
import CoderunnerState from '@/types/CodeRunnerState'
import CodeRunnerStatus from '@/types/CodeRunnerStatus'
import CodeRunnerType from '@/types/CodeRunnerTypes'
import ExerciseListRequestMessage from '@/types/ExerciseListRequestMessage'
import axios from 'axios'
import { isArray } from 'chart.js/helpers'

namespace EndpointAcces {


export namespace unauthorized{

  export async function getCodeRunnerState(
    token: string
  ): Promise<CoderunnerState> {
    try {
      if (token === '') throw 'token empty'
      console.log('token: ' + token)
      const response = await axios.post('/public/coderunner/state', token)
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


  export async function getListOfExercises(sortByInput:string,sortDirection:string,rowsPerPage: number, page: number) {
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
  const response= await axios.get('/public/exercises/list', { params: request });
  if (response === undefined) {
      console.error("couldn't retrieve excercise list from server")
      throw "couldn't retrieve excercise list from server"
    }
    console.log('Exercises respones: ' + JSON.stringify(response))
    return response.data;
    // exerciseData.value = response.data
    // console.log('exerciseData.value: ' + JSON.stringify(exerciseData.value))
  }


}

export namespace authorized{

  function getAuthHeader(token: string)
  {
   return {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json' 
        };
    
  }

  export async function getSolutionsData(exerciseid: number, token: string): Promise<SolutionsData> {
    console.log("attempitng /public/exercises/solutions ")
    const params = {
      id: exerciseid,
    }
    const response = await axios.get('/public/exercises/solutions', {
      headers: getAuthHeader(token),
      params: params,
    })
    console.log('/public/exercises/solutions Response:', response)
    if (
      response === undefined ||
      response.data === '' ||
      response.headers['Content-Length'] == 0
    )
      throw 'no solutions data retrived '

    return response.data
  }

  export async function getSolutionsCodesData(solutionId: number,token: string): Promise<string> {
    console.log("attempitng /public/exercises/solutionsCodes ")
    const params = {
      id: solutionId,
    }
    const response = await axios.get('/public/exercises/solutionsCodes', {
      headers: getAuthHeader(token),
      params: params,
    })
    console.log('/public/exercises/solutionsCodes Response:', response)
    if (
      response === undefined ||
      response.data === '' ||
      response.headers['Content-Length'] == 0
    )
      throw 'no solution code data retrived '

    return response.data
  }


  export async function postComment(comment: string, exerciseId: number, token: string): Promise<number> {
try{
const data = {
    id: exerciseId,
    comment: comment
};
const response=await axios.post('/public/exercises/comment', data, {
    headers: getAuthHeader(token)
})

console.log("response postComment: "+ JSON.stringify (response))
return response.status;
}
catch(ex)
{
  return -1;
}
}
}










}
export default EndpointAcces;
