import '@/assets/styles/styles.scss'
import '@/assets/styles/base.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { makeServer } from "./server"
// import "bootstrap"
import { install as VueMonacoEditorPlugin } from '@guolao/vue-monaco-editor'
import PrimeVue from 'primevue/config';
import Button from 'primevue/button';
import Toast from 'primevue/toast';
import ToastService from 'primevue/toastservice';
import Splitter from 'primevue/splitter';
import SplitterPanel from 'primevue/splitterpanel';





// import 'primevue/resources/themes/saga-blue/theme.css';
// import 'primevue/resources/primevue.min.css';
// import 'primeicons/primeicons.css';      
// import 'primevue/resources/primevue.css'    



// import './assets/main.css'
import 'primevue/resources/themes/saga-blue/theme.css';
import 'primevue/resources/primevue.min.css';
// import 'primeicons/primeicons.css';      

const app = createApp(App)
if (import.meta.env.MODE === 'development') {
  const { makeServer } = await import('./server');
  makeServer();
}

app.use(router)
.use(VueMonacoEditorPlugin, {
  paths: {
    vs: 'https://cdn.jsdelivr.net/npm/monaco-editor@0.43.0/min/vs'
  },
})
.use(PrimeVue);
app.component('Splitter',Splitter);
app.component('SplitterPanel',SplitterPanel);
app.mount('#app')