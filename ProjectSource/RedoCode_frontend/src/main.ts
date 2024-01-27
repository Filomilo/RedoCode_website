import '@/assets/styles/styles.scss'
import '@/assets/styles/base.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { makeServer } from "./server"
import "bootstrap"


const app = createApp(App)

if (process.env.NODE_ENV === "development") {
    makeServer()
  }

app.use(router)
//app.use(BootstrapVue3)
app.mount('#app')