import {request,methods} from "@/http/config/http";
/**
 *分页查询
 */
export const getPageList=p=>request({
    url:"/user/admin/role/getPageList",
    body:p,
    method:methods.GET
});

/**
 *新增
 */
export const insert=p=>request({
    url:"/user/admin/role",
    body:p,
    method:methods.POST
});
/**
 *编辑
 */
export const edit=p=>request({
    url:"/user/admin/role/"+p.id,
    body:p,
    method:methods.PUT
});
/**
 *角色列表
 */
export const getList=p=>request({
    url:"/user/admin/role/getList",
    body:p,
    method:methods.GET
});
/**
 *分配菜单
 */
export const allotMenu=p=>request({
    url:"/user/admin/role/allotMenu/"+p.id,
    body:p,
    method:methods.PUT
});

export default {
    getPageList,
    insert,
    edit,
    getList,
    allotMenu
}