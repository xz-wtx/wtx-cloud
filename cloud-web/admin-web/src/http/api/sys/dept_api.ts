import {request,methods} from "@/http/config/http";
/**
 *分页查询
 */
export const getPageList=p=>request({
    url:"/user/admin/dept/getPageList",
    body:p,
    method:methods.GET
});
/**
 *单查询
 */
export const query=p=>request({
    url:"/user/admin/dept/"+p.id,
    body:p,
    method:methods.GET
});
/**
 *新增
 */
export const insert=p=>request({
    url:"/user/admin/dept",
    body:p,
    method:methods.POST
});
/**
 *编辑
 */
export const edit=p=>request({
    url:"/user/admin/dept/"+p.id,
    body:p,
    method:methods.PUT
});

/**
 *树形部门(简化)
 */
export const getTree=p=>request({
    url:"/user/admin/dept/getTree",
    body:p,
    method:methods.GET
});
/**
 *树形部门
 */
export const getTreeList=p=>request({
    url:"/user/admin/dept/getTreeList",
    body:p,
    method:methods.GET
});
export default {
    getPageList,
    query,
    insert,
    edit,
    getTree,
    getTreeList
}