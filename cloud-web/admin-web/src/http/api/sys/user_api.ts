import {request,methods,contentType} from "@/http/config/http";

/**
 *分页查询
 */
export const getPageUserList=p=>request({
    url:"/user/admin/user/getPageUserList",
    body:p,
    method:methods.GET
});
/**
 *单查询
 */
export const getUser=p=>request({
    url:"/user/admin/user/"+p.id,
    body:p,
    method:methods.GET
});
/**
 *新增
 */
export const insert=p=>request({
    url:"/user/admin/user",
    body:p,
    method:methods.POST
});
/**
 *编辑
 */
export const editUser=p=>request({
    url:"/user/admin/user/"+p.id,
    body:p,
    method:methods.PUT
});
/**
 *重置密码
 */
export const resetPassword=p=>request({
    url:"/user/admin/user/resetPassword",
    body:p,
    method:methods.PUT,
    headers:contentType.FROM
});

export default {
    getPageUserList,
    getUser,
    insert,
    editUser,
    resetPassword
}