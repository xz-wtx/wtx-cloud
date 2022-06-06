package com.cloud.user.service;

import com.cloud.common.response.R;
import com.cloud.user.entity.SysWhitePathEntity;


/**
 * <p>
 * 系统全部请求路径 服务类
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
public interface SysWhitePathService  {

    /**
     * 编辑白名单路径
     * @param sysWhitePath
     * @param <T>
     * @return
     */
    <T> R<T> editWhitePath(SysWhitePathEntity sysWhitePath);

    /**
     * 删除白名单路径
     * @param sysWhitePath
     * @param <T>
     * @return
     */
    <T> R<T> delPath(SysWhitePathEntity sysWhitePath);

    /**
     * 分页查询白名单路径
     * @param sysWhitePath
     * @param <T>
     * @return
     */
    <T> R<T> getPageList(SysWhitePathEntity sysWhitePath);
}
