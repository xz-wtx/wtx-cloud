import {request,methods} from "@/http/config/http";
/**
 *分页查询
 */
export const getPageList=p=>request({
    url:"/user/admin/whitePath/getPageList",
    body:p,
    method:methods.GET
});
/**
 *新增
 */
export const insert=p=>request({
    url:"/user/admin/whitePath",
    body:p,
    method:methods.POST
});
/**
 *编辑
 */
export const edit=p=>request({
    url:"/user/admin/whitePath/"+p.id,
    body:p,
    method:methods.PUT
});
/**
 *删除
 */
export const del=p=>request({
    url:"/user/admin/whitePath/"+p.id,
    body:p,
    method:methods.DELETE
});

export default {
    getPageList,
    edit,
    insert,
    del,
}