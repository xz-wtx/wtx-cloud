# 创建项目
npm init @vitejs/app admin-web

# 安装路由
npm i vue-router@next

1. src文件下创建routes/index.ts
```
import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
    {
        path: '/login',
        component: () => import("../views/login/login.vue")
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

//动态加载路由
//注意：确定自己避免了路由守卫进入死循环
let oneRun = true; //通过oneRun变量控制 避免陷入死循环
router.beforeEach((to, from, next) => {
    if (to.path === "/") {
        next();
    } else {
        if (oneRun) {
            oneRun = false;//必须在creatNewRouter() 执行
            const modules = import.meta.glob("@/views/**/**.vue")
            let item = sessionStorage.getItem("menuList");
            if (item != null) {
               //获取菜单（根据自己需求改）
                let list = createNewRouter(modules, JSON.parse(item));
                
                let obj = {
                    path: `/newIndex`,
                    name: "newIndex",
                    component: index,
                    children: list
                }
                router.addRoute(obj)
            }
            next(to.path);
            return;
        }
        next();
    }
})

//创建动态路径
function createNewRouter(modules, menuList) {
    return menuList.filter(router => {
        if (router.component === "layout") {
            // Layout组件特殊处理
            router.component = layout;
        } else {
            //处理组件---重点
            router.component = modules["../views" + router.component];
        }
        //存在子集
        if (router.children && router.children.length) {
            router.children = createNewRouter(modules, router.children);
        }
        return true;
    });
}

export default router
```
2. main.ts 下引入
```
import { createApp } from 'vue'
import App from './App.vue'
import router from "./routes";

let app = createApp(App);
app.use(router)
app.mount('#app')
```

# 安装vuex
npm i vuex@next

1. src文件下创建store/index.ts
```
import { createStore } from 'vuex'
//定义一个state的接口
export interface State {
    count: number
}
const store = createStore<State>({
    state() {
        return {
            count: 0
        }
    },
    mutations: {
        // 累加功能
        increment(state) {
            state.count++
        }
    }
})
export default store
```
2. main.ts 下引入
```
import { createApp } from 'vue'
import App from './App.vue'
import store from "./store";
let app = createApp(App);
.use(store)
```

# 简单使用
1. 创建index.vue
```<template>
  <div>
    <h1>{{ name }}</h1>
    <h1>{{ state.count }}</h1>
    <h2>{{counts}}</h2>
    <h2>{{data}}</h2>
    <button @click="handleClickIncrement">累加</button>
    <button @click="updateName">更改名称</button>
  </div>
</template>

<script  lang="ts">
import {ref, computed, defineComponent, reactive} from 'vue';
import { useStore } from 'vuex';
export default defineComponent({
  name:"login",
  components:{},
  mounted() {
    this.updateName();
  },

  setup(){
    const store = useStore();
    // 为基本数据类型添加响应式状态
    const name = ref('Neo');
    // 为复杂数据类型添加响应式状态，赋值使用:state.count.value=1111
    const state = ref({ count: 1})
    //reactive和ref类似；区别是不需要.value
    const data = reactive<any>({
      tables: [],
      ss:"",
    });

    //store在计算属性中使用
    let counts = computed(()=>{
      return store.state.count
    })

    //函数集合
    const methods={
      //store函数
       handleClickIncrement() {
        store.commit("increment");
      },
      //函数
       updateName() {
        name.value="更改："+new Date();
         let obj={"name":1}
         data.tables.push(obj);
         data.ss="1231"
      }
    }

    return {
      counts,
      name,
      state,
      data,
      ...methods
    };
  },
})
</script>
```
# 安装element-plus
执行: npm i element-plus 

src文件下创建：plugins/element.ts

```
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//全局导入element-icons
import * as ElIcons from '@element-plus/icons-vue'

export default (app: any) => {

    app.use(ElementPlus)

    //全局导入element-icons
    for (const name in ElIcons) {
        app.component(name, ElIcons[name])
    }
}
```
main.ts文件下添加
```
import installElementPlus from './plugins/element'
const app = createApp(App)
installElementPlus(app)
```

# vue3 ts 引入别名和ts支持js
tsconfig.json
```aidl
  "compilerOptions": {
    //支持js
    "allowJs": true,// 允许js
    "noImplicitAny": false,// 不进行any语法检查
    
    //别名配置
    "baseUrl": ".",
    "paths": {
      "@/*": ["src/*"]
    }
  }
```
vite.config.ts
```aidl
import { defineConfig } from 'vite'
import {resolve } from 'path'
export default defineConfig({
  resolve: {
    alias: {
       //别名配置
      '@': resolve('src'),
      '*': resolve(''),
    },
  },
})

```
1. 关于vue3:vue3项目中配置viteconfigts时使用path模块报错
2. 起因：path模块是node.js内置的性能，然而node.js自身并不反对ts
   解决方案：装置@types/node(执行：npm install @types/node -D)

# 安装axios
npm install axios --save

1. src下创建http/config文件夹，在创建http.ts和request.ts
2. http.ts根据需要修改
```aidl
import request from "./request";
import { ElLoading ,ElMessage} from 'element-plus'
import * as QS from "qs";
import md5 from 'js-md5';



//弹窗
const array:any = [];
//防重复请求
const mapRequest:any = new Map();



/**
 * @param url 路径
 * @param body 参数
 * @param duplicate 防重复请求
 * @param isToast 开启Toast
 * @param LoadingTitle load提示
 */
export interface MyAxiosRequestConfig<D = any>{
    url:string;
    body:{};
    headers?:{},
    method?:string;
    isToast?: boolean;
    duplicate?: boolean;
    LoadingTitle?:string;
}

/**
 * get请求
 * @returns {Promise<unknown>}
 * @param myConfig
 */
export  function  get(myConfig: MyAxiosRequestConfig) {
    return new Promise((resolve, reject) => {
        myConfig.method="get";
        requestAxios(myConfig).then(r => {
            resolve(r)
        }).catch(err=>{
            reject(err)
        })
    })
}/**
 * postJson请求
 * @returns {Promise<unknown>}
 * @param myConfig
 */
export function post(myConfig: MyAxiosRequestConfig) {
    return new Promise((resolve, reject) => {
        myConfig.method="post";
        myConfig.body=JSON.stringify(myConfig.body);
        myConfig.headers={'content-type':"application/json"};
        requestAxios(myConfig).then(r => {
            resolve(r)
        }).catch(err=>{
            reject(err)
        })
    })
}
/**
 * postFrom 表单请求
 * @returns {Promise<unknown>}
 * @param myConfig
 */
export function postFrom(myConfig: MyAxiosRequestConfig) {
    return new Promise((resolve, reject) => {
        myConfig.method="post";
        myConfig.body=QS.stringify(myConfig.body);
        myConfig.headers={'content-type':"application/x-www-form-urlencoded"};
        requestAxios(myConfig).then(r => {
            resolve(r)
        }).catch(err=>{
            reject(err)
        })
    })
}

/**
 * postFromData 文件上传请求
 * @returns {Promise<unknown>}
 * @param myConfig
 */
export function postFromData(myConfig: MyAxiosRequestConfig) {
    return new Promise((resolve, reject) => {
        myConfig.method="post";
        myConfig.headers={'content-type':"multipart/form-data"};
        requestAxios(myConfig).then(r => {
            resolve(r)
        }).catch(err=>{
            reject(err)
        })
    })
}

/**
 * 请求
 * @returns {Promise<unknown>}
 * @param myConfig
 */
function requestAxios(myConfig: MyAxiosRequestConfig){

    assignDefaultValue(myConfig);

    let key= duplicationRequest(myConfig);
        if (key==null){
            return new Promise((resolve, reject) => {
                reject("duplicate request")
            });
        }

    openToast(myConfig)

    return new Promise((resolve, reject) => {
        request(myConfig.method!=='get'?
            ({url:myConfig.url,method:myConfig.method,data:myConfig.body, headers:myConfig.headers})
            :({url:myConfig.url,method:myConfig.method,params:myConfig.body, headers:myConfig.headers}))
            .then(res => {
                rmDuplicationRequest(key);
                clearToast()
                resolve(res);
            })
            .catch(err => {
                rmDuplicationRequest(key);
                clearToast()
                reject(err)
            })
    });
}


/**
 * 快速是否请求
 * @param myConfig
 */
function duplicationRequest(myConfig: MyAxiosRequestConfig){
    if (!myConfig.duplicate){
        return null;
    }
    let key = md5(myConfig.url+myConfig.body);
    if (!mapRequest.has(key)){
        mapRequest.set(key,"");
        return key;
    }else{
        ElMessage({
            message: '请求过于频繁',
            type: 'warning',
        })
        return null;
    }
}

/**
 * 删除保留请求
 * @param key
 */
function rmDuplicationRequest(key=""){
    if (key!=null){
        mapRequest.delete(key)
    }

}

/**
 * 分配默认值
 * @param myConfig
 */
function assignDefaultValue(myConfig: MyAxiosRequestConfig){
    myConfig.LoadingTitle ??= "加载中...";
    myConfig.isToast??=true;
    myConfig.duplicate??= true;
}

/**
 * 打开加载load
 * @returns {ILoadingInstance}
 * @param myConfig
 */
export function openToast(myConfig: MyAxiosRequestConfig) {
    if (myConfig.isToast){
        let toast= ElLoading.service({
            lock: true,
            text: myConfig.LoadingTitle,
            background: 'rgba(0, 0, 0, 0.7)'
        });
        array.push(toast);
    }

}

/**
 * 关闭load
 */
export function clearToast() {
    if (array.length>0){
        array.pop().close();
    }

}
```
3. request.ts 根据需要修改
```aidl
import axios from 'axios';
import { ElMessageBox } from 'element-plus';
const config = {
    // baseURL: process.env.baseURL
    baseURL: 'https://restapi.amap.com/',
    timeout: 1000,
};

const api = axios.create(config);

// 请求拦截器
api.interceptors.request.use(
    config => {
        return config;
    },
    (error) => {
        return error;
    })


// http response 拦截器
api.interceptors.response.use(
    response => {
        //拦截响应，做统一处理
        if (response.data.code) {
            switch (response.status) {
                case 301:
                    console.log('登录过期');
            }
        }
        return response
    },
    //接口错误状态处理，也就是说无响应时的处理
    error => {
        return Promise.reject(error.response.status) // 返回接口返回的错误信息
    })

export default api;

```
3. 使用示例：
```aidl
http格式如下:
   api
    login
        login_api.ts
    index.ts
    config
        http.ts
        request.ts
    

**~~示例~~**
~~1. login_api.ts文件内容~~
import {get, post} from "@/http/config/http";
/**
 *登录
 */
export const login=p=>get({
    url:"v3/weather/weatherInfo?key=5d2d3e6c0d5188bec134fc4fc1b139e0&city=%E4%BB%99%E6%B8%B8&extensions=base",
    body:p
});
export default {
    login
}


~~2. index.ts文件内容~~
import login_api from './login/login_api'
export default {
    admin:{
        login_api
    }
};

~~3. main.ts 文件调整引入~~
import { createApp } from 'vue'
import App from './App.vue'
import api from './http/api';

let app = createApp(App);
app.config.globalProperties.$api = api;
app.mount('#app')
```

# vue3 ts 获取实例（this）
1. 在plugins文件创建useCurrentInstance.ts
```aidl
import { ComponentInternalInstance, getCurrentInstance } from 'vue'
export default function useCurrentInstance() {
    const { appContext } = getCurrentInstance() as ComponentInternalInstance
    const proxy = appContext.config.globalProperties
    return {
        proxy
    }
}
```
2. 使用示例
```aidl
<script  lang="ts">
import useCurrentInstance from '@/plugins/useCurrentInstance';
import {ref, computed, defineComponent, reactive} from 'vue'
export default defineComponent({
  setup(){
    //获取实例
    const { proxy } = useCurrentInstance();
    //函数集合
    const methods={
      submit() {
        //调用api请求
        proxy.$api.admin.login_api.login(data);
        console.log(proxy)
        //路由跳转
        proxy.$router.push({ path: 'index' })
      },
    }
    //暴露方法，数据
    return {
      data,
      ...methods
    };
  },


})
</script>
```
