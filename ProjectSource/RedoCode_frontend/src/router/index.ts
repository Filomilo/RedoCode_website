import {
  createRouter,
  createWebHistory,
  NavigationGuardNext,
  RouteLocationNormalized,
} from 'vue-router'
import { useActiveUserStore } from '@/stores/ActiveUserStore'
import RouterValidators from '@/controllers/RouterValidators'
import { stringify } from 'flatted'

type ValidateFunction = (params: any) => Promise<string | null>
const validate = async (
  to: RouteLocationNormalized,
  from: RouteLocationNormalized,
  next: NavigationGuardNext,
  validateFunction: ValidateFunction
) => {
  console.log('validation  to ' + JSON.stringify(to.name))
  const validationResult = await validateFunction(to.params)
  console.log('validation route Result: ' + JSON.stringify(validationResult))
  if (validationResult === null || to.name == validationResult) next()
  else {
    console.log(
      'validation route swiihcing to ' + JSON,
      stringify(validationResult) + ' params: ' + JSON.stringify(to.params)
    )
    next({ name: validationResult, params: to.params })
  }
}

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: '',
      component: () => import('@/views/HomeView.vue'),
    },
    {
      path: '/Home',
      name: 'Home',
      component: () => import('@/views/HomeView.vue'),
    },
    {
      path: '/Exercises',
      name: 'Excersices',
      component: () => import('@/views/ExcersicesView.vue'),
    },
    {
      path: '/Exercises/:id',
      name: 'Exercise',
      component: () => import('@/views/ExcersiceView.vue'),
      beforeEnter: async (to, from, next) => {
        await validate(
          to,
          from,
          next,
          RouterValidators.ExerciseSolvingValidation
        )
      },
    },
    {
      path: '/PlayGround',
      name: 'PlayGround',
      component: () => import('@/views/PlayGroundView.vue'),
    },
    {
      path: '/create',
      name: 'Create',
      component: () => import('@/views/CreateView.vue'),
      beforeEnter: async (to, from, next) => {
        await validate(to, from, next, RouterValidators.CreateValidation)
      },
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/RegisterView.vue'),
      beforeEnter: async (to, from, next) => {
        await validate(to, from, next, RouterValidators.RegisterValidation)
      },
    },
    {
      path: '/Account',
      name: 'Account',
      component: () => import('@/views/AccountView.vue'),
      beforeEnter: async (to, from, next) => {
        await validate(to, from, next, RouterValidators.AccountAccesValidate)
      },
    },
    {
      path: '/Results/:id',
      name: 'Results',
      component: () => import('@/views/ResultsView.vue'),
      beforeEnter: async (to, from, next) => {
        await validate(to, from, next, RouterValidators.ResultsAccesValidate)
      },
    },
    {
      path: '/Results',
      name: 'ResultsNotFound',
      component: () => import('@/views/ResultsView.vue'),
      beforeEnter: async (to, from, next) => {
        await validate(to, from, next, RouterValidators.ResultsAccesValidate)
      },
    },
    {
      path: '/Results',
      name: 'ResultsUnauthenticatedNoID',
      component: () => import('@/views/ResultsUnathenticatedView.vue'),
    },
    {
      path: '/Results/:id',
      name: 'ResultsUnauthenticated',
      component: () => import('@/views/ResultsUnathenticatedView.vue'),
    },

    {
      path: '/Solutions',
      name: 'SolutionsNoData',
      component: () => import('@/views/SolutionsView.vue'),
      beforeEnter: async (to, from, next) => {
        await validate(to, from, next, RouterValidators.SolutionsAccesValidate)
      },
    },
    {
      path: '/Solutions/:id',
      name: 'Solutions',
      component: () => import('@/views/SolutionsView.vue'),
      beforeEnter: async (to, from, next) => {
        await validate(to, from, next, RouterValidators.SolutionsAccesValidate)
      },
    },
    {
      path: '/notFound',
      name: 'NotFound',
      component: () => import('@/views/WebisteNotExist.vue'),
    },
    {
      path: '/test',
      name: 'test',
      component: () => import('@/views/TestView.vue'),
    },
  ],
})

export default router
