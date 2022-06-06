package com.cloud.user.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.user.entity.SysRoleEntity;
import com.cloud.user.entity.SysUserEntity;
import com.cloud.user.entity.SysUserRoleEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
@Data
public class SysUserDetailVO extends SysUserEntity {

    public String roleNames;

}
