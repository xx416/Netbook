import { createApp } from 'vue'
import { createPinia } from "pinia";//引入pinia
import App from './App.vue'
import router from './router'
import axios from "axios";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate"; //pinia 持久化

import 'element-plus/theme-chalk/dark/css-vars.css'
import 'element-plus/dist/index.css'

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate); //pinia数据持久化

axios.defaults.baseURL = 'http://localhost:8080'

const app = createApp(App)

app.use(router)
app.use(pinia); //pinia 挂载到vue 实例上
app.mount('#app')
