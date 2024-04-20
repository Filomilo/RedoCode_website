import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { makeServer } from './server'
// import "bootstrap"
import { install as VueMonacoEditorPlugin } from '@guolao/vue-monaco-editor'
import PrimeVue from 'primevue/config'
import Toast from 'primevue/toast'
import ToastService from 'primevue/toastservice'
import Splitter from 'primevue/splitter'
import SplitterPanel from 'primevue/splitterpanel'
import Vue3EasyDataTable from 'vue3-easy-data-table'
import 'vue3-easy-data-table/dist/style.css'
import Button from 'primevue/button'

import 'primevue/resources/themes/saga-blue/theme.css'
import 'primevue/resources/primevue.min.css'
// import 'primeicons/primeicons.css';
import 'primevue/resources/primevue.css'

// import './assets/main.css'
// import 'primevue/resources/themes/saga-blue/theme.css';
// import 'primevue/resources/primevue.min.css';
// import 'primeicons/primeicons.css';
import 'primevue/resources/themes/aura-light-green/theme.css'
import '@/assets/styles/styles.scss'
import '@/assets/styles/base.css'
import 'primevue/inputtext'
import InputText from 'primevue/inputtext'
import Image from 'primevue/image'
import ScrollPanel from 'primevue/scrollpanel'
import Password from 'primevue/password'
import Dropdown from 'primevue/dropdown'
import TabView from 'primevue/tabview'
import TabPanel from 'primevue/tabpanel'
import MultiSelect from 'primevue/multiselect'
import Slider from 'primevue/slider'
import InputNumber from 'primevue/inputnumber'
import Chart from 'primevue/chart'
import RadioButton from 'primevue/radiobutton'
import Checkbox from 'primevue/checkbox'
import 'primevue/resources/themes/saga-blue/theme.css'
import 'primevue/resources/primevue.min.css'
import Textarea from 'primevue/textarea'
import { createPinia } from 'pinia'
import Dialog from 'primevue/dialog'
import ConfirmDialog from 'primevue/confirmdialog'
import ConfirmationService from 'primevue/confirmationservice'
import './interceptors/axios'

const app = createApp(App)
if (import.meta.env.MODE === 'development') {
  const { makeServer } = await import('./server')
  // makeServer()
}

app
  .use(router)

  .use(PrimeVue)

app.component('Splitter', Splitter)
app.component('SplitterPanel', SplitterPanel)
app.component('EasyDataTable', Vue3EasyDataTable)
app.component('InputText', InputText)
app.component('Button', Button)
app.component('Image', Image)
app.component('ScrollPanel', ScrollPanel)
app.component('Password', Password)
app.component('Dropdown', Dropdown)
app.component('TabView', TabView)
app.component('TabPanel', TabPanel)
app.component('MultiSelect', MultiSelect)
app.component('Slider', Slider)
app.component('InputNumber', InputNumber)
app.component('Chart', Chart)
app.component('RadioButton', RadioButton)
app.component('Checkbox', Checkbox)
app.component('Textarea', Textarea)
app.component('Dialog', Dialog)

app.component('ConfirmDialog', ConfirmDialog)

app.use(ConfirmationService)

const pinia = createPinia()

app.use(pinia)


app.use(VueMonacoEditorPlugin, {
  paths: {
    vs: 'https://cdn.jsdelivr.net/npm/monaco-editor@0.43.0/min/vs'
  },
})


app.mount('#app')
