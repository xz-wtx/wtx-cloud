import {request,methods} from "@/http/config/http";



/**
 *登录
 */
export const login=p=>request({
    url:"/user/admin/user/login",
    body:p,
    method:methods.POST
});
/**
 *登录
 */
export const captcha=p=>request({
    url:"/user/admin/captcha",
    body:p,
    method:methods.GET,
    isToast:false
});
/**
 *获取菜单
 */
export const getUserMenu=p=>request({
    url:"/user/admin/user/getUserMenu",
    body:p,
    method:methods.POST
});


export default {
    login,
    captcha,
    getUserMenu
}