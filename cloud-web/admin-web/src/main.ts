import { createApp } from 'vue'
import App from './App.vue'
import router from "./routes"
import store from "./store"
import Load from '../src/components/index'
import installElementPlus from './plugins/element'
import api from './http/api';



let app = createApp(App);
app.config.globalProperties.$api = api;
installElementPlus(app)
app.use(router).use(store).use(Load)
app.mount('#app')
