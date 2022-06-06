package com.cloud.user.service;


import com.cloud.common.response.R;
import com.cloud.user.entity.SysDeptEntity;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
public interface SysDeptService {

    /**
     * 编辑部门
     * @param sysDeptEntity
     * @param <T>
     * @return
     */
    <T> R<T> editDept(SysDeptEntity sysDeptEntity);

    /**
     * 查询部门
     * @param sysDeptEntity
     * @param <T>
     * @return
     */
    <T> R<T> queryDept(SysDeptEntity sysDeptEntity);

    /**
     * 获取树形部门
     * @param sysDeptEntity
     * @param <T>
     * @return
     */
    <T> R<T> getTreeList(SysDeptEntity sysDeptEntity);

    /**
     * 获取树形部门（简化）
     * @param sysDeptEntity
     * @param <T>
     * @return
     */
    <T> R<T> getTree(SysDeptEntity sysDeptEntity);
}
