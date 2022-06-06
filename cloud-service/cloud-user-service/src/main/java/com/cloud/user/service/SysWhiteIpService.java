package com.cloud.user.service;

import com.cloud.common.response.R;
import com.cloud.user.entity.SysWhiteIpEntity;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
public interface SysWhiteIpService  {

    /**
     * 编辑白名单ip
     * @param sysWhiteIp
     * @param <T>
     * @return
     */
    <T> R<T> editWhiteIp(SysWhiteIpEntity sysWhiteIp);

    /**
     * 删除ip
     * @param id
     * @param <T>
     * @return
     */
    <T> R<T> delIp(Integer id);

    /**
     * 分页查询白名单ip
     * @param sysWhiteIp
     * @param <T>
     * @return
     */
    <T> R<T> getPageList(SysWhiteIpEntity sysWhiteIp);
}
