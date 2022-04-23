import axios from "./axios_config";
import { ElLoading ,ElMessage} from 'element-plus'
import * as QS from "qs";
import md5 from 'js-md5';




//弹窗
const array:any = [];
//防重复请求
const mapRequest:any = new Map();



export const methods={
    POST:'post',
    GET:'get',
    DELETE:'delete',
    PUT:'put'
}

export const contentType={
    JSON:{'content-type':"application/json"},//请求
    FROM:{'content-type':"application/x-www-form-urlencoded"},//表单请求
    FROM_DATA:{'content-type':"multipart/form-data"}//文件上传请求
}



/**
 * @param url 路径
 * @param body 参数
 * @param method 方法类型
 * @param headers 请求头 默认json
 * @param isToast Toast 默认开启
 * @param duplicate 防重复请求 默认开启
 * @param LoadingTitle load提示 默认开启
 */
export interface MyAxiosRequestConfig<D = any>{
    url:string;
    body:{};
    method:string;
    headers?:{},
    isToast?: boolean;
    duplicate?: boolean;
    LoadingTitle?:string;

}

/**
 * postFromData 文件上传请求
 * @returns {Promise<unknown>}
 * @param myConfig
 */
function all(myConfig: MyAxiosRequestConfig){
    axios.all([]).then(result => {
        console.log(result);//=>输出是一个数组，分别存储每一个请求的结果
        let [resA, resB, resC] = result;
    });
}


/**
 * 请求
 * @param myConfig
 */
 export function request(config: MyAxiosRequestConfig){

     assignDefaultValue(config);
    //
    // let key= duplicationRequest(myConfig);
    //     if (key==null){
    //         return new Promise((resolve, reject) => {
    //             reject("duplicate request")
    //         });
    //     }

    openToast(config)

    return new Promise((resolve, reject) => {
        axios(config.method!==methods.GET?
            ({url:config.url,method:config.method,data:config.body, headers:config.headers})
            :({url:config.url,method:config.method,params:config.body, headers:config.headers}))
            .then(res => {
                //rmDuplicationRequest(key);
                clearToast()
                resolve(res);
            })
            .catch(err => {
                //rmDuplicationRequest(key);
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
/**
 * 分配默认值
 * @param myConfig
 */
function assignDefaultValue(myConfig: MyAxiosRequestConfig){
    myConfig.body = objKeySort(myConfig.body);
    if (myConfig.method!==methods.GET){
        myConfig.headers??=contentType.JSON;
        switch (myConfig.headers['content-type']) {
            case contentType.JSON["content-type"]:myConfig.body=JSON.stringify(myConfig.body)
                break;
            case contentType.FROM["content-type"]:myConfig.body=QS.stringify(myConfig.body)
                break;
            default:
                console.error("获取类型失败:",myConfig)
        }
    }
    myConfig.LoadingTitle ??= "加载中...";
    myConfig.isToast??=true;
    myConfig.duplicate??= true;
}


/**
 * 对象排序
 * @param obj
 */
function objKeySort(obj) {
    //排序的函数
    let newkey = Object.keys(obj).sort(); //先用Object内置类的keys方法获取要排序对象的属性名，再利用Array原型上的sort方法对获取的属性名进行排序，newkey是一个数组

    let newObj = {}; //创建一个新的对象，用于存放排好序的键值对
    for (let i = 0; i < newkey.length; i++) {

        if (obj[newkey[i]] instanceof Object) {
            if (obj[newkey[i]] instanceof Array){
                newObj[newkey[i]] =obj[newkey[i]]
            }else{
                newObj[newkey[i]] =objKeySort(obj[newkey[i]])
            }
        } else {
            //遍历newkey数组
            newObj[newkey[i]] = obj[newkey[i]]; //向新创建的对象中按照排好的顺序依次增加键值对
        }

    }
    return newObj; //返回排好序的新对象
}

