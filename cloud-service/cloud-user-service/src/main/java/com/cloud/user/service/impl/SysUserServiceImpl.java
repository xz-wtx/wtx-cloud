package com.cloud.user.service.impl;

import com.cloud.common.enums.CommonEnum;
import com.cloud.user.dto.LoginDTO;
import com.cloud.user.entity.SysUserEntity;
import com.cloud.user.mapper.SysUserMapper;
import com.cloud.user.service.SysUserService;
import com.cloud.common.response.BR;
import com.cloud.common.response.R;
import com.cloud.wechat.dto.WechatSendDTO;
import com.cloud.wechat.feign.SendMessageFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wtx
 * @since 2022-04-11
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SendMessageFeign sendMessageFeign;

    @Override
    public R login(LoginDTO loginDTO) {
        SysUserEntity user=sysUserMapper.login(loginDTO);
        if (Objects.isNull(user)){
            return BR.genResult(CommonEnum.NO_USER);
        }
        if (user.getDeleteFlag()){
            return BR.genResult(CommonEnum.USER_DISABLE);
        }

        return BR.genSuccessResult(user);
    }


    @Override
    public <T> R<T> insert(SysUserEntity sysUserEntity) {
        sysUserMapper.insert(sysUserEntity);
        //保存成功微信发送消息
        sendMessageFeign.sendMsg(new WechatSendDTO());
        return BR.genSuccessResult();
    }


}
