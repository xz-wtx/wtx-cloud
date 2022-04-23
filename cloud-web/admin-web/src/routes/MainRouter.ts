import {RouteRecordRaw} from "vue-router";

const MainRouter: RouteRecordRaw[] = [
    {
        path: '/',
        component: () => import("@/views/base/login.vue")
    },
    {
        name:"index",
        path: '/index',
        component: () => import("@/views/base/index.vue"),
        children:[
            {
                name:"h_layout",
                path: '/layout',
                component: () => import("@/components/layout/layout.vue"),
                children:[
                    {
                        name:"personal",
                        path: '/personal',
                        component: () => import("@/views/base/personal.vue"),
                        meta:{
                            title:"个人信息"
                        }
                    },
                    {
                        name:"home",
                        path: '/home',
                        component: () => import("@/views/base/home.vue"),
                        meta:{
                            title:"主页"
                        }
                    },
                ]
            },
        ]
    },
]

export default MainRouter