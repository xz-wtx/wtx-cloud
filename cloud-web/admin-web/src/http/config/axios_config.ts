import axios from 'axios';
import * as QS from "qs";
import md5 from 'js-md5';
import store from "@/store";
import message from "@/plugins/use/useMessage";

axios.defaults.withCredentials = false
axios.defaults.baseURL = 'http://localhost:7999/';
// 请求超时时间
axios.defaults.timeout = 100000;


// 请求拦截器
axios.interceptors.request.use(
    config => {

        let token =store.getters.getUser.token;
        let signStr =store.getters.getUser.signStr;
        let timestamp = new Date().getTime();
        // @ts-ignore
        config.headers["auth_token"]=token;
        // @ts-ignore
        config.headers['timestamp'] = timestamp;
        let sign=token+"&"+timestamp+"&"+signStr+"&"+config.url+"&";
        if (config.method === 'get') {
            config.paramsSerializer = function (params) {
                return QS.stringify(params, {arrayFormat: 'repeat'})
            }
            sign+=QS.stringify(config.params)
        }else{
            sign+=config.data
        }
        //console.log(sign)
        // @ts-ignore
        config.headers['sign'] = md5(sign);
        //console.log(config)
        return config;
    },
    (error) => {
        return error;
    })


// http response 拦截器
axios.interceptors.response.use(
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
        let msg=error.response.data.message;
        switch (error.response.data.status) {
            case 401:
                message.alertBox({title:msg}).then(() => {
                    window.location.href="/"
                });
                break;
            default:
                message.error(msg);
        }
        return Promise.reject(error) // 返回接口返回的错误信息
    })

export default axios;
