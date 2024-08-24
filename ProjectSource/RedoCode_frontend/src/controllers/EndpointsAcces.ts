import ExcerciseDataMessage from '@/types/ApiMesseages/ExcerciseDataMessage'
import CoderunnerState from '@/types/CodeRunnerState'
import CodeRunnerStatus from '@/types/CodeRunnerStatus'
import CodeRunnerType from '@/types/CodeRunnerTypes'
import axios from 'axios'
namespace EndpointAcces {
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
}

export default EndpointAcces
