import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ExcersicesView from '@/views/ExcersicesView.vue'
import ExcersiceView from '@/views/ExcersiceView.vue'
import PlayGroundView from '@/views/PlayGroundView.vue'
import CreateView from '@/views/CreateView.vue'
import RegisterView from '@/views/RegisterView.vue'
import AccountView from '@/views/AccountView.vue'
import ResultsView from '@/views/ResultsView.vue'

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
      // beforeEnter: (to,from,next)=>{
      //   codeRunnerStore.setExceriseDataToPlayground();
      // }
    },
    {
      path: '/create',
      name: 'Create',
      component: CreateView
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView
    },
    {
      path: '/Account',
      name: 'Account',
      component: AccountView
    },
    {
      path: '/Results',
      name: 'Results',
      component: ResultsView
    }
  ]
})

export default router
