import { useActiveUserStore } from '@/stores/ActiveUserStore'
import ExerciseSolviingState from '@/types/ExerciseSolviingState'
import EndpointAcces from './EndpointsAcces'
namespace RouterValidators {
  export async function AccountAccesValidate(
    params: any
  ): Promise<string | null> {
    const activeUserStore = useActiveUserStore()
    console.log('AccountAccesValidate authethication')
    if (activeUserStore.validateToken()) {
      return null
    } else {
      console.log('Unauhenticated')
      return 'Home'
    }
  }

  export async function ResultsAccesValidate(
    params: any
  ): Promise<string | null> {
    const activeUserStore = useActiveUserStore()
    console.log('ExerciseSolvingValidation authethication')
    if (params['id'] === undefined) return 'Exercises'
    if (!activeUserStore.validateToken()) return 'Exercise'
    let state = ExerciseSolviingState.UNSOLVED
    try {
      state = await EndpointAcces.authorized.getExerciseSolvingState(
        params['id'],
        activeUserStore.getToken()
      )
    } catch (ex) {
      console.error(ex)
    }
    console.log('state ' + state)
    if (
      state === ExerciseSolviingState.RATED ||
      state === ExerciseSolviingState.AUTHOR
    )
      return 'Solutions'
    if (state == ExerciseSolviingState.UNSOLVED) return 'Exercise'
    return 'Results'
  }

  export async function SolutionsAccesValidate(
    params: any
  ): Promise<string | null> {
    const activeUserStore = useActiveUserStore()
    console.log('SolutionsdAccesValidate authethication')
    if (activeUserStore.validateToken()) {
      return null
    } else {
      console.log('ResultsUnauthenticated')
      return 'ResultsUnauthenticated'
    }
  }
  export async function RegisterValidation(
    params: any
  ): Promise<string | null> {
    const activeUserStore = useActiveUserStore()
    console.log('SolutionsdAccesValidate authethication')
    if (!activeUserStore.validateToken()) {
      return null
    } else {
      console.log('ResultsUnauthenticated')
      return 'Home'
    }
  }
  export async function CreateValidation(params: any): Promise<string | null> {
    const activeUserStore = useActiveUserStore()
    console.log('CreateValidation authethication')
    if (activeUserStore.validateToken()) {
      return null
    } else {
      console.log('ResultsUnauthenticated')
      return 'Home'
    }
  }

  export async function ExerciseSolvingValidation(
    params: any
  ): Promise<string | null> {
    const activeUserStore = useActiveUserStore()
    console.log('ExerciseSolvingValidation authethication')
    if (params['id'] === undefined) return 'Exercises'
    if (!activeUserStore.validateToken()) return null
    let state = ExerciseSolviingState.UNSOLVED
    try {
      state = await EndpointAcces.authorized.getExerciseSolvingState(
        params['id'],
        activeUserStore.getToken()
      )
    } catch (ex) {
      console.error(ex)
    }
    console.log('state ' + state)
    if (
      state === ExerciseSolviingState.RATED ||
      state === ExerciseSolviingState.AUTHOR
    )
      return 'Solutions'
    if (state == ExerciseSolviingState.SOLVED) return 'Results'
    return null
  }

  export async function ExerciseRatingValidation(
    params: any
  ): Promise<string | null> {
    const activeUserStore = useActiveUserStore()
    console.log('ExerciseSolvingValidation authethication')
    if (params['id'] === undefined) return 'Exercises'
    if (!activeUserStore.validateToken()) return 'Exercise'
    let state = ExerciseSolviingState.UNSOLVED
    try {
      state = await EndpointAcces.authorized.getExerciseSolvingState(
        params['id'],
        activeUserStore.getToken()
      )
    } catch (ex) {
      console.error(ex)
    }
    console.log('state ' + state)

    if (
      state === ExerciseSolviingState.RATED ||
      state === ExerciseSolviingState.AUTHOR
    )
      return 'Solutions'
    if (state == ExerciseSolviingState.UNSOLVED) return 'Exercise'
    return null
  }
}

export default RouterValidators
