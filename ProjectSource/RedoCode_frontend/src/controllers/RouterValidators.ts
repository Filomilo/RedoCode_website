import { useActiveUserStore } from '@/stores/ActiveUserStore'
import ExerciseSolvingState from '@/types/ExerciseSolvingState'
import EndpointAccess from './EndpointsAccess'
namespace RouterValidators {
  export async function AccountAccessValidate(): Promise<string | null> {
    if (import.meta.env.MODE === 'development') return null
    const activeUserStore = useActiveUserStore()
    console.log('AccountAccessValidate authentication')
    if (await activeUserStore.validateAuthentication()) {
      return null
    } else {
      console.log('Unauthenticated')
      return 'Home'
    }
  }

  export async function ResultsAccessValidate(
    params: any
  ): Promise<string | null> {
    if (import.meta.env.MODE === 'development') return null
    const activeUserStore = useActiveUserStore()
    console.log('ExerciseSolvingValidation authentication')
    if (params['id'] === undefined) return 'ResultsUnauthenticated'
    if (!(await activeUserStore.validateAuthentication()))
      return 'ResultsUnauthenticated'
    let state = ExerciseSolvingState.UNSOLVED
    try {
      state = await EndpointAccess.authorized.getExerciseSolvingState(
        params['id']
      )
    } catch (ex) {
      console.error(ex)
    }
    console.log('state ' + state)
    if (
      state === ExerciseSolvingState.RATED ||
      state === ExerciseSolvingState.AUTHOR
    )
      return 'Solutions'
    if (state == ExerciseSolvingState.UNSOLVED) return 'Exercise'
    return 'Results'
  }

  export async function SolutionsAccessValidate(): Promise<string | null> {
    if (import.meta.env.MODE === 'development') return null
    const activeUserStore = useActiveUserStore()
    console.log('SolutionsAccessValidate authentication')
    if (await activeUserStore.validateAuthentication()) {
      return null
    } else {
      console.log('ResultsUnauthenticated')
      return 'ResultsUnauthenticated'
    }
  }
  export async function RegisterValidation(): Promise<string | null> {
    if (import.meta.env.MODE === 'development') return null
    const activeUserStore = useActiveUserStore()

    const authState = await activeUserStore.validateAuthentication()
    console.log(
      'SolutionsAccessValidate authentication, authState: ' + authState
    )
    if (!authState) {
      return null
    } else {
      console.log('ResultsUnauthenticated')
      return 'Home'
    }
  }
  export async function CreateValidation(): Promise<string | null> {
    if (import.meta.env.MODE === 'development') return null
    const activeUserStore = useActiveUserStore()
    console.log('CreateValidation authentication')
    if (await activeUserStore.validateAuthentication()) {
      return null
    } else {
      console.log('ResultsUnauthenticated')
      return 'Home'
    }
  }

  export async function ExerciseSolvingValidation(
    params: any
  ): Promise<string | null> {
    if (import.meta.env.MODE === 'development') return null
    const activeUserStore = useActiveUserStore()
    console.log('ExerciseSolvingValidation authentication')
    if (params['id'] === undefined) return 'Exercises'
    const authState = await activeUserStore.validateAuthentication()

    if (!authState) return null
    let state = ExerciseSolvingState.UNSOLVED
    try {
      state = await EndpointAccess.authorized.getExerciseSolvingState(
        params['id']
      )
    } catch (ex) {
      console.error(ex)
    }
    console.log('state ' + state)
    if (
      state === ExerciseSolvingState.RATED ||
      state === ExerciseSolvingState.AUTHOR
    )
      return 'Solutions'
    if (state == ExerciseSolvingState.SOLVED) return 'Results'
    return null
  }

  export async function ExerciseRatingValidation(
    params: any
  ): Promise<string | null> {
    if (import.meta.env.MODE === 'development') return null
    const activeUserStore = useActiveUserStore()
    console.log('ExerciseSolvingValidation authentication')
    if (params['id'] === undefined) return 'Exercises'
    if (await !(await activeUserStore.validateAuthentication()))
      return 'Exercise'
    let state = ExerciseSolvingState.UNSOLVED
    try {
      state = await EndpointAccess.authorized.getExerciseSolvingState(
        params['id']
      )
    } catch (ex) {
      console.error(ex)
    }
    console.log('state ' + state)

    if (
      state === ExerciseSolvingState.RATED ||
      state === ExerciseSolvingState.AUTHOR
    )
      return 'Solutions'
    if (state == ExerciseSolvingState.UNSOLVED) return 'Exercise'
    return null
  }
}

export default RouterValidators
