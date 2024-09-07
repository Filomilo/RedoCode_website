import { createRouter, createWebHistory } from 'vue-router'
import { useActiveUserStore } from '@/stores/ActiveUserStore'

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
      beforeEnter: (to, from, next) => {
        const activeUserStore = useActiveUserStore()
        // console.log("authethication")
        if (activeUserStore.validateToken()) {
          next()
        } else {
          // console.log("Unauhenticated")
          next({ name: 'Home' })
        }
      },
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/RegisterView.vue'),
    },
    {
      path: '/Account',
      name: 'Account',
      component: () => import('@/views/AccountView.vue'),
      beforeEnter: (to, from, next) => {
        const activeUserStore = useActiveUserStore()
        console.log('authethication')
        if (activeUserStore.validateToken()) {
          next()
        } else {
          console.log('Unauhenticated')
          next({ name: 'Home' })
        }
      },
    },
    {
      path: '/Results/:id',
      name: 'Results',
      component: () => import('@/views/ResultsView.vue'),
      beforeEnter: (to, from, next) => {
        const activeUserStore = useActiveUserStore()
        console.log('authethication')
        if (activeUserStore.validateToken()) {
          next()
        } else {
          console.log('ResultsUnauthenticated')
          next({ name: 'ResultsUnauthenticated' })
        }
      },
    },
    {
      path: '/Results',
      name: 'ResultsNotFound',
      component: () => import('@/views/ResultsView.vue'),
      beforeEnter: (to, from, next) => {
        const activeUserStore = useActiveUserStore()
        console.log('authethication')
        if (activeUserStore.validateToken()) {
          next()
        } else {
          console.log('ResultsUnauthenticated')
          next({ name: 'ResultsUnauthenticated' })
        }
      },
    },
    {
      path: '/Results',
      name: 'ResultsUnauthenticated',
      component: () => import('@/views/ResultsUnathenticatedView.vue'),
      beforeEnter: (to, from, next) => {
        const activeUserStore = useActiveUserStore()
        console.log('authethication')
        if (activeUserStore.validateToken()) {
          next({ name: 'ResultsUnauthenticated' })
        } else {
          console.log('ResultsUnauthenticated')
          next()
        }
      },
    },

    {
      path: '/Solutions',
      name: 'SolutionsNoData',
      component: () => import('@/views/SolutionsView.vue'),
    },
    {
      path: '/Solutions/:id',
      name: 'Solutions',
      component: () => import('@/views/SolutionsView.vue'),
    },
    {
      path: '/test',
      name: 'test',
      component: () => import('@/views/TestView.vue'),
    },
  ],
})

export default router
