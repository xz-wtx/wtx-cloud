package com.cloud.user.mapper;

import com.cloud.common.vo.TreeVO;
import com.cloud.user.entity.SysMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {

    /**
     * 分页查询树形菜单
     * @return
     */
    List<TreeVO> getTreeList(@Param("menu") SysMenuEntity menu);
}
