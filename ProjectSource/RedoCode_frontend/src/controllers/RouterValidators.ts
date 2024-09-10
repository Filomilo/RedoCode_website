import { useActiveUserStore } from "@/stores/ActiveUserStore"
import ExerciseSolviingState from "@/types/ExerciseSolviingState";
import EndpointAcces from "./EndpointsAcces";
import deasync from "deasync"
namespace RouterValidators{


    export function AccountAccesValidate(params: any): string| null
    {
        const activeUserStore = useActiveUserStore()
        console.log('AccountAccesValidate authethication')
        if (activeUserStore.validateToken()) {
         return null;
        } else {
          console.log('Unauhenticated')
          return 'Home'
        }
    }


    export async function  ResultsAccesValidate(params: any)
    {
        const activeUserStore = useActiveUserStore()
        console.log('ExerciseSolvingValidation authethication')
         if(params["id"]===undefined)
             return "Exercises"
         if (!activeUserStore.validateToken()) 
           return "Exercise";
         let state=ExerciseSolviingState.UNSOLVED;
         try{
         state=await EndpointAcces.authorized.getExerciseSolvingState(params["id"],activeUserStore.getToken())
         }
         catch(ex)
         {
             console.error(ex)
         }
         console.log("state "+ state)
         if(state===ExerciseSolviingState.RATED)
             return "Solutions"
              if(state==ExerciseSolviingState.UNSOLVED)
             return "Exercise"
           return "Results";
    }



    export function SolutionsAccesValidate(params: any)
    {
        const activeUserStore = useActiveUserStore()
        console.log('SolutionsdAccesValidate authethication')
        if (activeUserStore.validateToken()) {
          return null;
        } else {
          console.log('ResultsUnauthenticated')
          return 'ResultsUnauthenticated';
        }
    }
    export function RegisterValidation(params: any)
    {
        const activeUserStore = useActiveUserStore()
        console.log('SolutionsdAccesValidate authethication')
        if (!activeUserStore.validateToken()) {
          return null;
        } else {
          console.log('ResultsUnauthenticated')
          return 'Home';
        }
    }
    export function CreateValidation(params: any)
    {
        const activeUserStore = useActiveUserStore()
        console.log('CreateValidation authethication')
        if (activeUserStore.validateToken()) {
          return null;
        } else {
          console.log('ResultsUnauthenticated')
          return 'Home';
        }
    }

    export async function  ExerciseSolvingValidation(params: any)
    {
        const activeUserStore = useActiveUserStore()
       console.log('ExerciseSolvingValidation authethication')
        if(params["id"]===undefined)
            return "Exercises"
        if (!activeUserStore.validateToken()) 
          return null;
        let state=ExerciseSolviingState.UNSOLVED;
        try{
        state=await  EndpointAcces.authorized.getExerciseSolvingState(params["id"],activeUserStore.getToken())
        }
        catch(ex)
        {
            console.error(ex)
        }
        console.log("state "+ state)
        if(state===ExerciseSolviingState.RATED)
            return "Solutions"
             if(state==ExerciseSolviingState.SOLVED)
            return "Results"
          return null;
        
    }

    export async function  ExerciseRatingValidation(params: any)
    {
        const activeUserStore = useActiveUserStore()
       console.log('ExerciseSolvingValidation authethication')
        if(params["id"]===undefined)
            return "Exercises"
        if (!activeUserStore.validateToken()) 
          return "Exercise";
        let state=ExerciseSolviingState.UNSOLVED;
        try{
        state=await  EndpointAcces.authorized.getExerciseSolvingState(params["id"],activeUserStore.getToken())
        }
        catch(ex)
        {
            console.error(ex)
        }
        console.log("state "+ state)
        if(state===ExerciseSolviingState.RATED)
            return "Solutions"
             if(state==ExerciseSolviingState.UNSOLVED)
            return "Exercise"
          return null;
        
    }


}


export default RouterValidators;