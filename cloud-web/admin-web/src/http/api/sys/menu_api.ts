import {request,methods,contentType} from "@/http/config/http";

/**
 *单查询
 */
export const query=p=>request({
    url:"/user/admin/menu/"+p.id,
    body:p,
    method:methods.GET
});
/**
 *新增
 */
export const insert=p=>request({
    url:"/user/admin/menu",
    body:p,
    method:methods.POST
});
/**
 *编辑
 */
export const edit=p=>request({
    url:"/user/admin/menu/"+p.id,
    body:p,
    method:methods.PUT
});
/**
 *复制改目录及子目录
 */
export const copyMenu=p=>request({
    url:"/user/admin/menu/copyMenu/"+p.id,
    body:p,
    method:methods.PUT
});
/**
 *获取树形菜单（简化）
 */
export const getTree=p=>request({
    url:"/user/admin/menu/getTree",
    body:p,
    method:methods.GET,
    headers:contentType.FROM
});
/**
 *获取树形菜单
 */
export const getTreeList=p=>request({
    url:"/user/admin/menu/getTreeList",
    body:p,
    method:methods.GET
});
/**
 *更改排序
 */
export const changeSort=p=>request({
    url:"/user/admin/menu/changeSort",
    body:p,
    method:methods.PUT
});

export default {
    query,
    insert,
    edit,
    getTree,
    getTreeList,
    copyMenu,
    changeSort
}