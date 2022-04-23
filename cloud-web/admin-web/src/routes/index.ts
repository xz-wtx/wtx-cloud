import {createRouter, createWebHistory} from 'vue-router'
import MainRouter from "@/routes/MainRouter";
import RouterInterceptor from "@/routes/RouterIntercept";


const router = createRouter({
    history: createWebHistory(),
    routes: [
        ...MainRouter,
    ],
})

RouterInterceptor(router)

export default router