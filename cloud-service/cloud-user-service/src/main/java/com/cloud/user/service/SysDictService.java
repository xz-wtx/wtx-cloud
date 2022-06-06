package com.cloud.user.service;

import com.cloud.common.response.R;
import com.cloud.user.entity.SysDictEntity;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
public interface SysDictService  {

    /**
     * 编辑字典表
     * @param dictEntity
     * @param <T>
     * @return
     */
    <T> R<T> editDict(SysDictEntity dictEntity);

    /**
     * 删除字典数据
     * @param id
     * @param <T>
     * @return
     */
    <T> R<T> delDict(Integer id);

    /**
     * 分页查询字典表
     * @param dictEntity
     * @param <T>
     * @return
     */
    <T> R<T> getPageList(SysDictEntity dictEntity);
}
