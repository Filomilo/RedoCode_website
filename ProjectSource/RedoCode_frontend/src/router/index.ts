import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ExcersicesView from '@/views/ExcersicesView.vue'
import ExcersiceView from '@/views/ExcersiceView.vue'
import PlayGroundView from '@/views/PlayGroundView.vue'
import CreateView from '@/views/CreateView.vue'
import RegisterView from '@/views/RegisterView.vue'
import AccountView from '@/views/AccountView.vue'
import ResultsView from '@/views/ResultsView.vue'
import TestView from '@/views/TestView.vue'
import { useActiveUserStore } from '@/stores/ActiveUserStore'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: '',
      component: HomeView
    },
    {
      path: '/Home',
      name: 'Home',
      component: HomeView
    },
    {
      path: '/Exercises',
      name: 'Excersices',
      component: ExcersicesView
    },
    {
      path: '/Exercises/:id',
      name: 'Exercise',
      component: ExcersiceView
    },
    {
      path: '/PlayGround',
      name: 'PlayGround',
      component: PlayGroundView
    },
    {
      path: '/create',
      name: 'Create',
      component: CreateView,
      beforeEnter: (to, from, next) => {
        const activeUserStore = useActiveUserStore()
        // console.log("authethication")
        if (activeUserStore.validateToken()) {
          next()
        } else {
          // console.log("Unauhenticated")
          next({ name: 'Home' })
        }
      }
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView
    },
    {
      path: '/Account',
      name: 'Account',
      component: AccountView,
      beforeEnter: (to, from, next) => {
        const activeUserStore = useActiveUserStore()
        console.log('authethication')
        if (activeUserStore.validateToken()) {
          next()
        } else {
          console.log('Unauhenticated')
          next({ name: 'Home' })
        }
      }
    },
    {
      path: '/Results',
      name: 'Results',
      component: ResultsView
    },
    {
      path: '/test',
      name: 'test',
      component: TestView
    }
  ]
})

export default router
