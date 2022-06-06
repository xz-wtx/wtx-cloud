package com.cloud.user.mapper;

import com.cloud.user.entity.SysDeptEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.common.vo.TreeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
public interface SysDeptMapper extends BaseMapper<SysDeptEntity> {

    /**
     * 查询部门
     * @param sysDeptEntity
     * @return
     */
    List<TreeVO> getTreeList(@Param("dept") SysDeptEntity sysDeptEntity);
}
