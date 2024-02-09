import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ExcersicesView from '@/views/ExcersicesView.vue'
import ExcersiceView from '@/views/ExcersiceView.vue'
import PlayGroundView from '@/views/PlayGroundView.vue'
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
    }
    ,
    {
      path: '/PlayGround',
      name: 'PlayGround',
      component: PlayGroundView
    }
  ]
})

export default router
