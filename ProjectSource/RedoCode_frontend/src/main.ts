import '@/assets/styles/styles.scss'
import '@/assets/styles/base.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { makeServer } from "./server"
import "bootstrap"
import { install as VueMonacoEditorPlugin } from '@guolao/vue-monaco-editor'

const app = createApp(App)
// if (import.meta.env.MODE === 'development') {
//   const { makeServer } = await import('./server');
//   makeServer();
// }

app.use(router)

app.use(VueMonacoEditorPlugin, {
  paths: {
    vs: 'https://cdn.jsdelivr.net/npm/monaco-editor@0.43.0/min/vs'
  },
})


app.mount('#app')