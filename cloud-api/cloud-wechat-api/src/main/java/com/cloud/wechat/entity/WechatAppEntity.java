package com.cloud.wechat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author wtx
 * @since 2022-04-16
 */
@Getter
@Setter
@TableName("wechat_app")
public class WechatAppEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 第三方用户唯一凭证
     */
    private String appid;

    /**
     * 第三方用户唯一凭证密钥，即appsecret
     */
    private String secret;


}
