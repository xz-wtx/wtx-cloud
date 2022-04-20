package com.cloud.user.mapper;

import com.cloud.user.dto.LoginDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.user.entity.SysUserEntity;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author wtx
 * @since 2022-04-14
 */
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

    /**
     * 用户登录
     * @param loginDTO
     * @return
     */
    SysUserEntity login(@Param("login") LoginDTO loginDTO);
}
