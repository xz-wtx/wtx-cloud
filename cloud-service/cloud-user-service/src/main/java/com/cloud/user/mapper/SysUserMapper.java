package com.cloud.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.user.dto.LoginDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.user.dto.SysUserDTO;
import com.cloud.user.entity.SysUserEntity;
import com.cloud.user.vo.SysUserDetailVO;
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

    /**
     * 分页查询用户信息
     * @param page
     * @param sysUser
     * @return
     */
    IPage<SysUserDetailVO> getPageUserList(Page<SysUserDetailVO> page,@Param("sysUser") SysUserDTO sysUser);



}
