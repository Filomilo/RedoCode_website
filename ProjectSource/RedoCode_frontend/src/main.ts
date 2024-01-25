import '@/assets/styles/styles.scss'
import '@/assets/styles/base.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import "bootstrap"


const app = createApp(App)



app.use(router)
//app.use(BootstrapVue3)
app.mount('#app')